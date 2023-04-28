package com.castis.adgateway.common;


import com.castis.adgateway.common.setting.VersionSetting;
import org.slf4j.MDC;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestInterceptor extends HandlerInterceptorAdapter {

	protected static final Class<org.springframework.web.bind.annotation.RequestMapping> annotationClass = org.springframework.web.bind.annotation.RequestMapping.class;
	
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		try{
		if(MDC.get(VersionSetting.PRODUCT_VERSION_KEY)==null)
			MDC.put(VersionSetting.PRODUCT_VERSION_KEY, VersionSetting.PRODUCT_VERSION);
		}catch(Exception e){
			
		}
		
		return true;
	}
	
	
	
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView
	) throws Exception {
		
	}

	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
