package com.castis.adgateway.service;


import com.castis.adgateway.common.enumeration.ADCompanyType;
import com.castis.adgateway.common.setting.Properties;
import com.castis.adgateway.core.model.HomechoiceAdDecision;
import com.castis.adgateway.core.model.LguAdDecision;
import com.castis.adgateway.core.model.abstractClass.AdDecisionModel;
import com.castis.adgateway.dto.csis.Notification;
import com.castis.adgateway.dto.response.v1_5.OpportunityBinding;
import com.castis.adgateway.dto.response.v1_5.PlacementDecision;
import com.castis.adgateway.dto.response.v1_5.PlacementResponse;
import com.castis.adgateway.model.Description;
import com.castis.adgateway.repository.DescriptionRepository;
import com.castis.adgateway.repository.TrackingRepository;
import com.castis.adlib.dto.TransactionID;
import com.castis.adlib.dto.exception.CiException;
import com.castis.adlib.dto.exception.CiRuntimeException;
import com.castis.adlib.util.HttpConnectorUtil;
import com.castis.adlib.util.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@Slf4j
@Data
public class AdDecisionService {

    private final Properties properties;
    private final DescriptionRepository descriptionRepository;
    private final TrackingRepository trackingRepository;



    public PlacementResponse processDecision(TransactionID trId, String vodRequestId, String messageId) {
        try {
            Description description = descriptionRepository.findTopByVodRequestId(vodRequestId);

            if(description == null) {
                log.error("{} description 정보를 찾을수 없습니다. vodRequestId = {}", trId, vodRequestId);
                return generateADResponseFail(vodRequestId, messageId);
            }
            log.info("{} description load. vodRequestId = {}, load {}", trId, vodRequestId, description);

            //일정 확률로 lgu 또는 홈초이스 광고를 선택
            ADCompanyType adCompanyType = selectAdCompany();

            AdDecisionModel adDecisionModel;
            if(adCompanyType == ADCompanyType.LGU) {
                adDecisionModel = new LguAdDecision(trId, properties, description, vodRequestId, messageId, trackingRepository);
            } else if(adCompanyType == ADCompanyType.HOME_CHOICE) {
                adDecisionModel = new HomechoiceAdDecision(trId, properties, description, vodRequestId, messageId);
            } else
                throw new CiRuntimeException("Invalid AdCompanyType");

            PlacementResponse placementResponse = adDecisionModel.decisionAd();

            //광고가 없을 경우 반대쪽으로 다시 요청
            if(placementResponse == null || !StringUtil.isNotEmpty(placementResponse.getPlacementDecision().getPlacementList())) {

                log.info("{} 광고 결정 정보가 없습니다. 다른 결정 서버로 광고 요청합니다.", trId);

                if(adCompanyType == ADCompanyType.LGU) {
                    adDecisionModel = new HomechoiceAdDecision(trId, properties, description, vodRequestId, messageId);
                } else {
                    adDecisionModel = new LguAdDecision(trId, properties, description, vodRequestId, messageId, trackingRepository);
                }

                placementResponse = adDecisionModel.decisionAd();
            }

            if(placementResponse == null)
                return generateADResponseFail(vodRequestId, messageId);

            return placementResponse;

        } catch (Exception e) {
            log.error(trId + "" , e);
        }

        return generateADResponseFail(vodRequestId, messageId);
    }

    public ADCompanyType selectAdCompany()
    {
        //2개 이상의 광고 제공 회사가 있을 경우 아래 코드 수정
        float totalProportion = 100;

        /* 0.0 ~ 1.0 */
        double	randomNum = Math.random() * totalProportion;

        if (randomNum <= properties.getLguAdChance()) {
            return ADCompanyType.LGU;
        } else
            return ADCompanyType.HOME_CHOICE;
    }

    public PlacementResponse generateADResponseFail(String vodRequestId, String messageId) {
        PlacementResponse adResponse = new PlacementResponse();
        adResponse.setRequestID(vodRequestId);
        adResponse.setSessionID(messageId);
        adResponse.setCore( "http://www.scte.org/schemas/130-2/2008a/core");
        PlacementDecision decision = new PlacementDecision();
        decision.setId( UUID.randomUUID().toString());				// This UUID is not used.
        decision.setOpportunityRef( UUID.randomUUID().toString());	// This UUID is not used.
        adResponse.setPlacementDecision(decision);

        OpportunityBinding binding = new OpportunityBinding();
        binding.setOpportunityType( "preRoll" );
        decision.setOpportunitybinding( binding );
        return adResponse;
    }
}
