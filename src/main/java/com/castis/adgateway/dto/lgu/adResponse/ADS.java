package com.castis.adgateway.dto.lgu.adResponse;

import lombok.Data;

import java.util.List;

@Data
public class ADS {

	String requestId;
	String adsNo;
	String cmpNo;
	String creativeNo;

	String filePath;

	Integer slot;

	String startEvent;
	String completeEvent;
	String clickEvent;


}
