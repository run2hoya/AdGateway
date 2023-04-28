package com.castis.adgateway.dto.response.v1_5;

import com.castis.adgateway.common.AdConstants;
import com.castis.adgateway.dto.response.v1_5.responseAd.CorePlacement;
import com.castis.adgateway.dto.response.v1_5.responseAd.PlacementDecisionADSM;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlacementDecision {

	@JacksonXmlProperty(isAttribute = true)
	String				id;

	@JacksonXmlProperty(isAttribute = true, localName = "placementOpportunityRef")
	String				opportunityRef;

	@JacksonXmlProperty(localName = "OpportunityBinding")
	OpportunityBinding	opportunitybinding;
	@JacksonXmlProperty(localName = "Placement")
	@JacksonXmlElementWrapper(useWrapping = false)
	List<Placement>		placementList = new ArrayList<Placement>();

	public void addDummyPlacement(String dummyAdFile) {
		CoreAssetRef coreAssetRef = new CoreAssetRef(AdConstants.ad.DUMMY_PROVIDER_ID, AdConstants.ad.DUMMY_ASSET_ID);
		CoreContent coreContent = new CoreContent(coreAssetRef, dummyAdFile, AdConstants.ad.DUMMY_TR_ID);
		Placement placement = new Placement(UUID.randomUUID().toString(), coreContent);

		placementList.add(placement);
	}

	public void addPlacement(String assetName, String trackingId, int idIndex) {
		CoreAssetRef coreAssetRef = new CoreAssetRef(AdConstants.ad.DUMMY_PROVIDER_ID, AdConstants.ad.DUMMY_ASSET_ID);
		CoreContent coreContent = new CoreContent(coreAssetRef, assetName, trackingId);
		Placement placement = new Placement(AdConstants.ad.PLACEMENT_ID.concat(String.format( "%1$02d" , idIndex )), coreContent);

		placementList.add(placement);
	}

	public void addPlacement(String id, String assetName, String trackingId) {
		CoreAssetRef coreAssetRef = new CoreAssetRef(AdConstants.ad.DUMMY_PROVIDER_ID, AdConstants.ad.DUMMY_ASSET_ID);
		CoreContent coreContent = new CoreContent(coreAssetRef, assetName, trackingId);
		Placement placement = new Placement(id, coreContent);

		placementList.add(placement);
	}

	public PlacementDecision(PlacementDecisionADSM placementDecisionADSM) {
		this.id = placementDecisionADSM.getId();
		this.opportunityRef = placementDecisionADSM.getOpportunityRef();
		this.opportunitybinding = placementDecisionADSM.getOpportunitybinding();

		if(placementDecisionADSM.getCorePlacementList() != null) {

			for(CorePlacement corePlacement : placementDecisionADSM.getCorePlacementList()) {
				addPlacement(corePlacement.getId(), corePlacement.getContent().getAssetName(), corePlacement.getContent().getTracking());
			}
		}

	}
}
