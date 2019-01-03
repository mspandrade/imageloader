package com.mspaulo.imageloader.data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UploadFileResponse {

	private String id;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", locale = "pt-BR", timezone = "Brazil/East")
	private Date uploadDate;
	
	public UploadFileResponse() {}
	
	public UploadFileResponse(String id, Date uploadDate) {
		this.id = id;
		this.uploadDate = uploadDate;
	}
	
}
