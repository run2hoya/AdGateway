package com.castis.adgateway.dto.csis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FileList implements Serializable {

	private static final long serialVersionUID = -8217394298717266501L;

	public enum SUPER_FILE_ATTR {FILE_NAME, AUDIO_TYPE, VIDEO_TYPE, SYSTEM_TYPE};
	
	
	List<File> file = null;

	
	public List<File> getFile() {
		return file;
	}

	public void setFile(List<File> file) {
		this.file = file;
	}

	public File getFile(int index) {
		if(file == null || file.size() <= index)	return null;
		return file.get(index);
	}
	
//	public String getFileValue(int index, SUPER_FILE_ATTR key) {
//		if(superFile == null || superFile.size() <= index)	return null;
//		
//		switch (key) {
//		case FILE_NAME:
//			return superFile.get(index).getSuperFileName();
//		case AUDIO_TYPE:
//			return superFile.get(index).getAudioType();
//		case VIDEO_TYPE:
//			return superFile.get(index).getVideoType();
//		case SYSTEM_TYPE:
//			return superFile.get(index).getSystemType();
//		}
//		
//		return null;
//	}
	
	public void addFile(File file) {
		if(this.file == null) this.file = new ArrayList<File>();
		this.file.add(file);
	}
	
	@Override
	public String toString() {
		return "" + file;
	}
}
