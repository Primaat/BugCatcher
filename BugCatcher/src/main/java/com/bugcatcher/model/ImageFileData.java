package com.bugcatcher.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@SequenceGenerator(name="IMAGEFILEDATA_SEQ", sequenceName="imagefiledata_sequence")
public class ImageFileData {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "IMAGEFILEDATA_SEQ")
	private Long id;
	
	private Long ticketId;
	
	private String fileName;
	
	private String uploader;
	
	private String description;	
	
	private String path;
	
	@CreatedDate
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")	
	private ZonedDateTime uploadTimeStamp = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("CET"));	

	public ImageFileData() {
	}

	public ImageFileData(String fileName, Long ticketId, String uploader, String description) {		
		this.ticketId = ticketId;
		this.fileName = fileName;
		this.uploader = uploader;
		this.description = description;
	}		
	
	public Long getId() {
		return id;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUploader() {
		return uploader;
	}

	public void setUploader(String uploader) {
		this.uploader = uploader;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((ticketId == null) ? 0 : ticketId.hashCode());
		result = prime * result + ((uploadTimeStamp == null) ? 0 : uploadTimeStamp.hashCode());
		result = prime * result + ((uploader == null) ? 0 : uploader.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImageFileData other = (ImageFileData) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (ticketId == null) {
			if (other.ticketId != null)
				return false;
		} else if (!ticketId.equals(other.ticketId))
			return false;
		if (uploadTimeStamp == null) {
			if (other.uploadTimeStamp != null)
				return false;
		} else if (!uploadTimeStamp.equals(other.uploadTimeStamp))
			return false;
		if (uploader == null) {
			if (other.uploader != null)
				return false;
		} else if (!uploader.equals(other.uploader))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ImageFileData [id=" + id + ", ticketId=" + ticketId + ", fileName=" + fileName + ", uploader="
				+ uploader + ", description=" + description + ", path=" + path + "]";
	}
}
