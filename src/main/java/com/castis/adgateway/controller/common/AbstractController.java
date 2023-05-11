package com.castis.adgateway.controller.common;


import com.castis.adgateway.common.Properties;
import com.castis.adlib.dto.ResultDetail;
import com.castis.adlib.dto.TransactionID;
import com.castis.adlib.util.idgenerator.IdGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;


@Slf4j
public abstract class AbstractController {
		
	@Autowired
	Properties properties;
	private XmlMapper xmlMapper = new XmlMapper();

	public TransactionID startLog(HttpServletRequest req, String eventType) throws Exception {
		
		TransactionID trId = new TransactionID(TransactionID.TRANSACTION_ID_TYPE, IdGenerator.getInstance().generateId());
		StringBuilder logString = new StringBuilder(trId + ">>> " + "[" + eventType + "] ");
		logString.append("clientAddr["+ req.getRemoteAddr()+":" + Integer.toString(req.getRemotePort())+"], ");
		logString.append("RequestURI"+ req.getRequestURI());
		
		log.info(logString.toString());
		return trId; 
	}
	
	public TransactionID startLog(HttpServletRequest req, String eventType, Principal user) throws Exception {		
		
		TransactionID trId = new TransactionID(TransactionID.TRANSACTION_ID_TYPE, IdGenerator.getInstance().generateId());		
		StringBuilder logString = new StringBuilder(trId + ">>> " + "[" + eventType + "] ");
		if(user != null)
			logString.append("["+ user.getName() + "] ");
		logString.append("clientAddr["+ req.getRemoteAddr()+":" + Integer.toString(req.getRemotePort())+"], ");
		logString.append("RequestURI"+ req.getRequestURI());
		
		log.info(logString.toString());
		return trId; 
	}
	
	public TransactionID startLog(HttpServletRequest req, String eventType, String body, Principal user) throws Exception {		
		
		TransactionID trId = new TransactionID(TransactionID.TRANSACTION_ID_TYPE, IdGenerator.getInstance().generateId());		
		StringBuilder logString = new StringBuilder(trId + ">>> " + "[" + eventType + "] ");
		if(user != null)
			logString.append("["+ user.getName() + "] ");
		logString.append("clientAddr["+ req.getRemoteAddr()+":" + Integer.toString(req.getRemotePort())+"], ");
		logString.append("RequestURI:"+ req.getRequestURI());
		logString.append(", RequestBody:"+ body);
		
		log.info(logString.toString());
		return trId; 
	}
	
	public TransactionID startLog(HttpServletRequest req, String eventType, String body) throws Exception {		
		
		TransactionID trId = new TransactionID(TransactionID.TRANSACTION_ID_TYPE, IdGenerator.getInstance().generateId());		
		StringBuilder logString = new StringBuilder(trId + ">>> " + "[" + eventType + "] ");
		logString.append("clientAddr["+ req.getRemoteAddr()+":" + Integer.toString(req.getRemotePort())+"], ");
		logString.append("RequestURI:"+ req.getRequestURI());
		logString.append(", RequestBody:"+ body);
		
		log.info(logString.toString());
		return trId; 
	}
	
	public void endLog(long startTime, String eventType, TransactionID trId, ResultDetail error) {
					
		StringBuilder logString = new StringBuilder(trId + "<<< " + "[" + eventType + "]");
		if(error != null)
			logString.append(String.format(" ErrorName[%s], Description[%s]", error.getName(), error.getDescription()));
		
		logString.append(String.format(" processingTimeSec[%s]", String.valueOf((System.currentTimeMillis() - startTime)/1000.0)));
		log.info(logString.toString());
	}

	public HttpHeaders getHeaders(Object obj) throws JsonProcessingException {
		XmlMapper xmlMapper = new XmlMapper();
		xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
		String xmlString = xmlMapper.writeValueAsString(obj);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);
		headers.setContentLength(xmlString.getBytes().length);

		return headers;
	}
	
}	
		
		
		
		
