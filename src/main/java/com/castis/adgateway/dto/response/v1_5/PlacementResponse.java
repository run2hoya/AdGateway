package com.castis.adgateway.dto.response.v1_5;


import com.castis.adgateway.dto.response.v1_5.responseAd.PlacementResponseADSM;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.*;

@JacksonXmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlacementResponse {


	@JacksonXmlProperty(isAttribute = true, localName = "Request_ID")
	String				requestID;
	@JacksonXmlProperty(isAttribute = true, localName = "Session_ID")
	String				sessionID;
	@JacksonXmlProperty(isAttribute = true, localName = "xmlns:core")
	String				core;
	@JacksonXmlProperty(localName="PlacementDecision")
	PlacementDecision	placementDecision;


	public PlacementResponse(PlacementResponseADSM adsm) {
		this.requestID = adsm.getRequestID();
		this.sessionID = adsm.getSessionID();
		this.core = "http://www.scte.org/schemas/130-2/2008a/core";
		this.placementDecision = new PlacementDecision(adsm.getPlacementDecision());
	}
}
