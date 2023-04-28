package com.castis.adgateway.dto.response.v1_5;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAttribute;
@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class CoreAssetRef {

	@JacksonXmlProperty(isAttribute = true, localName = "providerID")
	String providerId;
	@JacksonXmlProperty(isAttribute = true, localName = "assetID")
	String assetId;

}
