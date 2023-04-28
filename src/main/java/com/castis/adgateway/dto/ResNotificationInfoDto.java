package com.castis.adgateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResNotificationInfoDto {

	public String categoryInfo;
	public String provider;
	public String regionCode;
	public String series;
}
