package com.castis.adgateway.dto.response.v1_5.responseAd;


import com.castis.adgateway.dto.response.v1_5.CoreAssetRef;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Content {

	@JacksonXmlProperty(namespace = "core", localName = "AssetRef")
	CoreAssetRef assetref;

	@JacksonXmlProperty(namespace = "core", localName = "AssetName")
	String assetName;

	@JacksonXmlProperty(namespace = "core", localName = "Tracking")
	String tracking;
	

}
