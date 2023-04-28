package com.castis.adgateway.dto.response.v1_5;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CoreContent {

	@JacksonXmlProperty(localName = "core:AssetRef")
	CoreAssetRef assetref;

	@JacksonXmlProperty(localName = "core:AssetName")
	String assetName;

	@JacksonXmlProperty(localName = "core:Tracking")
	String tracking;
	

}
