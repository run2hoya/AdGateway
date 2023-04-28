package com.castis.adgateway.dto.csis;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;


//<User>
//<Stbid>KT</Stdid>
//</User>
//
//or
//
//<User>
//<DomainId>KT</DomainId>
//<UserId>SETTOP100000</UserId>
//<TerminalDeviceType>iPhone</TerminalDeviceType>
//</User>


public class User implements Serializable
{
	static final long	serialVersionUID = 4182079473593145389L;
	
	String		stbId;
	
	String		domainId;
	String		userId;
	String		terminalDeviceType;
	
	//----------------------------------------------------------
	// Getter/setter methods
	


	@XmlElement(name="StbId")
	public String getStbId() {
		return stbId;
	}

	public void setStbId(String stbId) {
		this.stbId = stbId;
	}	
	
	@XmlElement(name="DomainId")
	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	
	@XmlElement(name="UserId")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@XmlElement(name="TerminalDeviceType")
	public String getTerminalDeviceType() {
		return terminalDeviceType;
	}

	public void setTerminalDeviceType(String terminalDeviceType) {
		this.terminalDeviceType = terminalDeviceType;
	}

	// - - - - - - - - - - - - - - - -
	// Public methods
	
	@Override
	public String toString() {
		return "User [stbId=" + stbId + ", domainId=" + domainId + ", userId="
				+ userId + ", terminalDeviceType=" + terminalDeviceType + "]";
	}

	
	//Has가 현재 StbId를 사용하고 있지만 추후 UserId를 사용하는것으로 바뀔것이다.
	//따라서 UserId가 없으면 StbId를 report로 사용하는것으로 정한다.(2012.2월2주기)
	public String getUserIdForReport()
	{
		if(getUserId() == null || getUserId().equals(""))
			return getStbId();
		else
			return getUserId();
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String stbId, String domainId, String userId,
			String terminalDeviceType) {
		super();
		this.stbId = stbId;
		this.domainId = domainId;
		this.userId = userId;
		this.terminalDeviceType = terminalDeviceType;
	}

}

	

