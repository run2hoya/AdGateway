package com.castis.adgateway.dto.csis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AudioTypeList implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7631241174014001159L;
	
	List<String> audioType = new ArrayList<String>();

	
	public AudioTypeList() {
		super();
	}

	public AudioTypeList(List<String> audioType) {
		super();
		this.audioType = audioType;
	}

	public List<String> getAudioType() {
		return audioType;
	}

	public void setAudioType(List<String> audioType) {
		this.audioType = audioType;
	}

	public void addAudioType(String audioType) {
		this.audioType.add(audioType);
	}
	
	@Override
	public String toString() {
		return "AudioTypeList [audioType=" + audioType + "]";
	}

}
