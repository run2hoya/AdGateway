package com.castis.adgateway.dto.response.v1_5;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAttribute;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OpportunityBinding {

	@JacksonXmlProperty(isAttribute = true, localName = "opportunityType")
	String opportunityType;

}
