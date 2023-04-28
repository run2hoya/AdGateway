package com.castis.adgateway.dto.csis;


import com.castis.adlib.util.MyJAXBContext;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;


@XmlRootElement(name="response")
@XmlType(propOrder={})
public class Response {

	public static final String	RESPONSE_OK = "OK";
	public static final String	RESPONSE_FAIL = "Fail";
	
	
	String		result;
	String		message;

	static final JAXBContext jaxbContext = MyJAXBContext.initContext(Response.class);
	
	
	@XmlElement
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	@XmlElement
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isOK() {
		if(RESPONSE_OK.equals(result))	return true;
		else							return false;
	}

	public void marshaling(StringWriter sw) throws JAXBException
	{
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty( "jaxb.formatted.output", true  );
		marshaller.marshal(this, sw);
	}

	
	public void marshaling(OutputStream xmlOuput) throws JAXBException
	{
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty( "jaxb.formatted.output", true  );
		
		marshaller.marshal(this, xmlOuput);
	}
	

	public static Response unmarshaling(InputStream xmlInput) throws JAXBException 
	{
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Response req = (Response) unmarshaller.unmarshal(xmlInput);
		return req;
	}
	
}
