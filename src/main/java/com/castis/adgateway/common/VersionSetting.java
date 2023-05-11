package com.castis.adgateway.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VersionSetting {
	
	static final Log		log = LogFactory.getLog( VersionSetting.class );
	
	public static final String	PRODUCT_VERSION_KEY	= "productVersion";
	public static final	String	PRODUCT_VERSION		=	"1.0.0.qr9";
	
	public VersionSetting() {
		super();		
		String enc = new java.io.OutputStreamWriter(System.out).getEncoding();
        log.info("######### default encoding = " + enc);
	}
	
}
