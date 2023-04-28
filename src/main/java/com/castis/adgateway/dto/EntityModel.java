package com.castis.adgateway.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JacksonXmlRootElement(localName = "EntityModel")
public class EntityModel {

	@JacksonXmlProperty
	String ID;
	@JacksonXmlProperty
	String NAME;
	@JacksonXmlProperty
	String DOB;
	@JacksonXmlProperty
	String PINCODE;
}
