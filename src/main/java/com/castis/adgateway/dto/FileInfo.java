package com.castis.adgateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo {

	public File file;

	public String extension;
	public String baseName;

	public String sdFile;
	public String hdFile;
	public String fileName;

	public FileInfo(File file) {
		this.file = file;
		this.fileName = file.getName();
		this.baseName = FilenameUtils.getBaseName(file.getName());
		this.extension = FilenameUtils.getExtension(file.getName());

		this.sdFile = this.baseName + "_sd." + extension;
		this.hdFile = this.baseName + "_hd." + extension;

	}
}
