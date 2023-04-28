package com.castis.adgateway.dto.csis;

import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@XmlType(propOrder={"fileName","codec","avContainer","resolution","audioTypeList"})
public class File implements Serializable {

	private static final long serialVersionUID = -7122269097006288155L;
	
	String fileName;
	String codec;
	String avContainer;
	AudioTypeList audioTypeList;
	String resolution;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getCodec() {
		return codec;
	}
	public void setCodec(String codec) {
		this.codec = codec;
	}
	public String getAvContainer() {
		return avContainer;
	}
	public void setAvContainer(String avContainer) {
		this.avContainer = avContainer;
	}
	public AudioTypeList getAudioTypeList() {
		return audioTypeList;
	}
	public void setAudioTypeList(AudioTypeList audioTypeList) {
		this.audioTypeList = audioTypeList;
	}
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	
	public String findFirstAudioType() {
		if(audioTypeList != null) {
			List<String> audioType = audioTypeList.getAudioType();
			if(audioType != null && audioType.size() >0)
				return audioType.get(0);
		}
		return null;
	}
	
	
	@Override
	public String toString() {
		return "File [fileName=" + fileName + ", codec=" + codec
				+ ", avContainer=" + avContainer + ", audioTypeList="
				+ audioTypeList + ", resolution=" + resolution + "]";
	}

}
