package com.castis.adgateway.core.model.abstractClass;


import com.castis.adgateway.common.Properties;
import com.castis.adgateway.dto.response.v1_5.PlacementResponse;
import com.castis.adgateway.model.Description;
import com.castis.adlib.dto.TransactionID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AdDecisionModel {

	public Properties properties;
	public Description 	description;
	public TransactionID trId;
	public String vodRequestId, messageId;
	public boolean isRetry = false;

	public AdDecisionModel(TransactionID trId, Properties properties, Description description, String vodRequestId, String messageId) {
		this.trId = trId;
		this.properties = properties;
		this.description = description;
		this.vodRequestId = vodRequestId;
		this.messageId = messageId;
	}

	public abstract void convertRequest();
	public abstract PlacementResponse decisionAd();



}
