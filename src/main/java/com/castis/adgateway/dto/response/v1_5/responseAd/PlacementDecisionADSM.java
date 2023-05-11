package com.castis.adgateway.dto.response.v1_5.responseAd;

import com.castis.adgateway.dto.response.v1_5.OpportunityBinding;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlacementDecisionADSM {

	@JacksonXmlProperty(isAttribute = true)
	String				id;

	@JacksonXmlProperty(isAttribute = true, localName = "placementOpportunityRef")
	String				opportunityRef;

	@JacksonXmlProperty(localName = "OpportunityBinding")
	OpportunityBinding opportunitybinding;

	@JacksonXmlProperty(localName = "Placement")
	@JacksonXmlElementWrapper(useWrapping = false)
	List<CorePlacement>		corePlacementList = new ArrayList<CorePlacement>();

	
}
