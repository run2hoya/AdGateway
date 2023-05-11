package com.castis.adgateway.core.model;


import com.castis.adgateway.common.Properties;
import com.castis.adgateway.core.model.abstractClass.AdDecisionModel;
import com.castis.adgateway.dto.response.v1_5.PlacementResponse;
import com.castis.adgateway.dto.response.v1_5.responseAd.PlacementResponseADSM;
import com.castis.adgateway.model.Description;
import com.castis.adlib.dto.TransactionID;
import com.castis.adlib.util.HttpConnectorUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;

import java.io.InputStream;
import java.net.URL;

@Slf4j
@Data
public class HomechoiceAdDecision extends AdDecisionModel {

	public HomechoiceAdDecision(TransactionID trId, Properties properties, Description description, String vodRequestId, String messageId) {
		super(trId, properties, description, vodRequestId, messageId);
	}

	@Override
	public void convertRequest() {

	}

	@Override
	public PlacementResponse decisionAd() {

		try {
			String requestUrl = properties.getAdsmUrl() + "/PlacementRequest";
			URIBuilder builder = new URIBuilder(requestUrl);
			builder.addParameter("VOD_Session_ID", messageId);
			builder.addParameter("VOD_Request_ID", vodRequestId);

			URL url = new URL(builder.build().toString());
			log.info("{} {} homeChoice 광고 요청 request = {}", trId, (isRetry)? "RETRY" : "", url);

			InputStream is = HttpConnectorUtil.getResponse(url, HttpConnectorUtil.HTTP_GET_METHOD, null, properties.getAdsmServerTimeOut());

			if(is != null) {

				String adResponse = HttpConnectorUtil.convertStreamToString(is);
				log.info("{} adResponse = {}", trId, adResponse);

				ObjectMapper xmlMapper = new XmlMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
				PlacementResponseADSM placementResponse = xmlMapper.readValue(adResponse, PlacementResponseADSM.class);
				log.info("{} homeChoice 광고 응답 = {}", trId, placementResponse);

				return new PlacementResponse(placementResponse);

			} else
				log.error(trId + "InputStream is null");
		} catch (Exception e) {
			log.error(trId + "" , e);
		}


		return null;
	}

}
