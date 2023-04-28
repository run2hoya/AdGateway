package com.castis.adgateway.dto.csis;


import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

// <Asset>
//<ProviderId>cjc</ProviderId>
//<AssetId>T000000000000009A38B</AssetId>
//<RunningTime>01:36</RunningTime>
//<HDContent>N</HDContent>
//<Genre>Horror/Thriller</Genre>
//<Rating>12</Rating>
//<Provider>MBC</Provider>
//<AudioType>AC3</AudioType>
//</Asset>



public class Asset implements Serializable {
	
	static final long	serialVersionUID = -5291383707299673709L;
	
	String		providerId;
	String		assetId;
	String		runningTime;
	String		hdContent;
	String		genre;
	String		rating;
	String		provider;
	FileList	fileList;
	//추가
	String		audioType;

	
	//----------------------------------------------------------
	// Getter/setter methods

	

	@XmlElement(name="ProviderId")
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	
	@XmlElement(name="AssetId")
	public String getAssetId() {
		return assetId;
	}
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	
	@XmlElement(name="RunningTime")
	public String getRunningTime() {
		return runningTime;
	}
	public void setRunningTime(String runningTime) {
		this.runningTime = runningTime;
	}
	
	@XmlElement(name="HDContent")
	public String getHdContent() {
		return hdContent;
	}
	public void setHdContent(String hdContent) {
		this.hdContent = hdContent;
	}
	
	@XmlElement(name="Genre")
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	@XmlElement(name="Rating")
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	
	@XmlElement(name="Provider")
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	
	@XmlElement(name="AudioType")
	public String getAudioType() {
		return audioType;
	}
	public void setAudioType(String audioType) {
		this.audioType = audioType;
	}
	@XmlElement(name="FileList")
	public FileList getFileList() {	return fileList; }
	public void setFileList(FileList fileList) {this.fileList = fileList;}
	// - - - - - - - - - - - - - - - -
	// Public methods
	
	
	@Override
	public String toString() {
		return "Asset [providerId=" + providerId + ", assetId=" + assetId
				+ ", runningTime=" + runningTime + ", hdContent=" + hdContent
				+ ", genre=" + genre + ", rating=" + rating + ", provider="
				+ provider + ", audioType=" + audioType + "]";
	}

	public Asset() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Asset(String providerId, String assetId, String runningTime,
			String hdContent, String genre, String rating, String provider,
			String audioType) {
		super();
		this.providerId = providerId;
		this.assetId = assetId;
		this.runningTime = runningTime;
		this.hdContent = hdContent;
		this.genre = genre;
		this.rating = rating;
		this.provider = provider;
		this.audioType = audioType;
	}
	
	
}
