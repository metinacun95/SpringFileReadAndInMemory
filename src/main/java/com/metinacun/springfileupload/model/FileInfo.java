package com.metinacun.springfileupload.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FileInfo {
	
	@Id
	private int id;
	private long fileSize;
	private long fileLinesCount;
	
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public long getFileLinesCount() {
		return fileLinesCount;
	}
	public void setFileLinesCount(long fileLinesCount) {
		this.fileLinesCount = fileLinesCount;
	}
	@Override
	public String toString() {
		return "FileInfo [id=" + id + ", fileSize=" + fileSize + ", fileLinesCount=" + fileLinesCount + "]";
	}
	
}
