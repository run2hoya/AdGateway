package com.castis.adgateway.dto.response.v1_5.responseAd;

import com.castis.adgateway.dto.response.v1_5.CoreContent;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class CorePlacement {

	@JacksonXmlProperty(isAttribute = true)
	String			id;

	@JacksonXmlProperty(namespace = "core", localName = "Content")
	Content content;

	
}
