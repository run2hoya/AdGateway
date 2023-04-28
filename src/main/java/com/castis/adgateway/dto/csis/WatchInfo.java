package com.castis.adgateway.dto.csis;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

//<WatchInfo>
//<WatchRequestTime>20101214212244796</WatchRequestTime>
//<Offset>12345</Offset>
//</WatchInfo>

public class WatchInfo implements Serializable {
	
	static final long	serialVersionUID = -5745582816401402800L;
	
	String			watchRequestTime;
	long			offset;
	
	//----------------------------------------------------------
	// Getter/setter methods


	@XmlElement(name="WatchRequestTime")
	public String getWatchRequestTime() {
		return watchRequestTime;
	}
	public void setWatchRequestTime(String watchRequestTime) {
		this.watchRequestTime = watchRequestTime;
	}
	
	@XmlElement(name="Offset")
	public long getOffset() {
		return offset;
	}
	public void setOffset(long offset) {
		this.offset = offset;
	}
	
	
	// - - - - - - - - - - - - - - - -
	// Public methods

	@Override
	public String toString() {
		return "WatchInfo [watchRequestTime=" + watchRequestTime + ", offset="
				+ offset + "]";
	}
	public WatchInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WatchInfo(String watchRequestTime, long offset) {
		super();
		this.watchRequestTime = watchRequestTime;
		this.offset = offset;
	}
}
