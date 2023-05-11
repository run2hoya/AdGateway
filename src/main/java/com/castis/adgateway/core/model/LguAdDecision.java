package com.castis.adgateway.core.model;


import com.castis.adgateway.common.setting.AdConstants;
import com.castis.adgateway.common.Properties;
import com.castis.adgateway.core.model.abstractClass.AdDecisionModel;
import com.castis.adgateway.dto.lgu.adResponse.ADS;
import com.castis.adgateway.dto.lgu.adResponse.AdDecisionResponse;
import com.castis.adgateway.dto.response.v1_5.OpportunityBinding;
import com.castis.adgateway.dto.response.v1_5.PlacementDecision;
import com.castis.adgateway.dto.response.v1_5.PlacementResponse;
import com.castis.adgateway.model.Description;
import com.castis.adgateway.model.Tracking;
import com.castis.adgateway.repository.TrackingRepository;
import com.castis.adlib.dto.FileInfo;
import com.castis.adlib.dto.TransactionID;
import com.castis.adlib.util.HttpConnectorUtil;
import com.castis.adlib.util.idgenerator.IdGenerator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Data
public class LguAdDecision extends AdDecisionModel {

	public TrackingRepository trackingRepository;

	public LguAdDecision(TransactionID trId, Properties properties, Description description, String vodRequestId, String messageId, TrackingRepository trackingRepository) {
		super(trId, properties, description, vodRequestId, messageId);
		this.trackingRepository = trackingRepository;
	}

	@Override
	public void convertRequest() {

	}

	@Override
	public PlacementResponse decisionAd() {

		try {
			String requestUrl = properties.getLguAdUrl();

			URIBuilder builder = new URIBuilder(requestUrl);
			builder.addParameter("mediaId", "dlive_vod");
			builder.addParameter("productClass", "VC");
			builder.addParameter("userKey", description.getUserKey());
			builder.addParameter("deviceType", "IPTV");
			builder.addParameter("deviceModel", "DLIVE_STP");
			builder.addParameter("category", description.getCategory());
			builder.addParameter("contentProvider", description.getContentProvider());
			builder.addParameter("watchingGrade", description.getWatchingGrade());
			builder.addParameter("series", description.getSeries());
			builder.addParameter("regionCode", description.getRegionCode());
			builder.addParameter("payPerView", description.getPayPerView());
			builder.addParameter("resumeYn", description.getResumeYn());
			builder.addParameter("albumId", description.getAlbumId());
			builder.addParameter("runTime", String.valueOf(description.getRunTime()));

			URL url = new URL(builder.build().toString());
			log.info("{} {} LGU 광고 요청 request = {}", trId, (isRetry)? "RETRY" : "", url);

			InputStream is = HttpConnectorUtil.getResponse(url, HttpConnectorUtil.HTTP_GET_METHOD, null, properties.getLguAdServerTimeOut());

			if(is != null) {
				AdDecisionResponse adDecisionResponse = (AdDecisionResponse) HttpConnectorUtil.getObjFromJson(is, AdDecisionResponse.class);
				log.info("{} LGU 광고 응답 = {}", trId, adDecisionResponse);

				if(adDecisionResponse.getReturnCode().equalsIgnoreCase(AdConstants.ad.SUCCESS))
					return convertADResponse(adDecisionResponse.getAds(), description.isHdContent());

			} else
				log.error(trId + "InputStream is null");
		} catch (Exception e) {
			log.error(trId + "" , e);
		}

		return null;
	}

	public PlacementResponse convertADResponse(List<ADS> ads, boolean hdContent) throws Exception {
		PlacementResponse adResponse = new PlacementResponse();
		adResponse.setRequestID(vodRequestId);
		adResponse.setSessionID(messageId);
		adResponse.setCore( "http://www.scte.org/schemas/130-2/2008a/core");

		PlacementDecision decision = new PlacementDecision();
		decision.setId( UUID.randomUUID().toString());				// This UUID is not used.
		decision.setOpportunityRef( UUID.randomUUID().toString());	// This UUID is not used.
		adResponse.setPlacementDecision(decision);

		if(hdContent)
			decision.addDummyPlacement(properties.getDummyHdAdFile());
		else
			decision.addDummyPlacement(properties.getDummySdAdFile());

		List<Tracking> trackingList = new ArrayList<Tracking>();
		for(ADS ad : ads) {
			String trackingId = IdGenerator.getInstance().generateId();

			FileInfo fileInfo = new FileInfo(new File(ad.getFilePath()));
			decision.addPlacement(
					(hdContent) ? fileInfo.getHdFile() : fileInfo.getSdFile(),
					trackingId,
					ad.getSlot());

			//광고 수집용 정보 저장
			trackingList.add(new Tracking(trackingId, ad.getStartEvent(), ad.getCompleteEvent(), ad.getClickEvent()));
		}

		trackingRepository.saveAll(trackingList);

		OpportunityBinding binding = new OpportunityBinding();
		binding.setOpportunityType( "preRoll" );
		decision.setOpportunitybinding( binding );
		return adResponse;
	}
}
