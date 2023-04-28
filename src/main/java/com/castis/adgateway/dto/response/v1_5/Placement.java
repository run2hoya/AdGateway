package com.castis.adgateway.dto.response.v1_5;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Placement {

	@JacksonXmlProperty(isAttribute = true)
	String			id;

	@JacksonXmlProperty(localName = "core:Content")
	CoreContent content;

	
}
