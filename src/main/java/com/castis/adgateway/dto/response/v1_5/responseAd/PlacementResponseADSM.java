package com.castis.adgateway.dto.response.v1_5.responseAd;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@JacksonXmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlacementResponseADSM {


	@JacksonXmlProperty(isAttribute = true, localName = "Request_ID")
	String				requestID;
	@JacksonXmlProperty(isAttribute = true, localName = "Session_ID")
	String				sessionID;
	@JacksonXmlProperty(isAttribute = true, localName = "xmlns:core")
	String				core;
	@JacksonXmlProperty(localName="PlacementDecision")
    PlacementDecisionADSM placementDecision;


}
