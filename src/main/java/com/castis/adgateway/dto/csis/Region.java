package com.castis.adgateway.dto.csis;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

//<Region>
//<ExternalSoId>44</ExternalSoId>
//</Region>
//
//or
//
//<Region>
//<RegionId>44</RegionId>
//</Region>



public class Region implements Serializable {

	static final long	serialVersionUID = -5144000659981052668L;
	
	String			externalSoId;
	
	//추가
	String			regionId;
	
	//----------------------------------------------------------
	// Getter/setter methods


	@XmlElement(name="ExternalSoId")
	public String getExternalSoId() {
		return externalSoId;
	}
	public void setExternalSoId(String externalSoId) {
		this.externalSoId = externalSoId;
	}
	
	@XmlElement(name="RegionId")
	public String getRegionId() {
		if(regionId == null || regionId.isEmpty())	return externalSoId;
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	
	// - - - - - - - - - - - - - - - -
	// Public methods
	
	
	@Override
	public String toString() {
		return "Region [externalSoId=" + externalSoId + ", regionID="
				+ regionId + "]";
	}
	public Region() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Region(String externalSoId, String regionId) {
		super();
		this.externalSoId = externalSoId;
		this.regionId = regionId;
	}

}
