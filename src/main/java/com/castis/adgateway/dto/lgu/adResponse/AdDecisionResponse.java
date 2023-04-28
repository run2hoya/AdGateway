package com.castis.adgateway.dto.lgu.adResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AdDecisionResponse {

	String returnCode;
	String message;

	Integer count;

	List<ADS> ads;


}
