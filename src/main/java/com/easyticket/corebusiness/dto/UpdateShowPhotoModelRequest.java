package com.easyticket.corebusiness.dto;

import java.io.IOException;
import java.io.InputStream;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.easyticket.corebusiness.validation.photo.FileContentType;
import com.easyticket.corebusiness.validation.photo.FileSize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateShowPhotoModelRequest {

	@NotNull
	@FileSize(max = "500KB")
	@FileContentType(allowed = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	private MultipartFile photo;
	
	@NotBlank
	private String description;
	
	public InputStream getInputStream() throws IOException {
		return this.photo.getInputStream();
	}
	
	public String getContentType() {
		return this.photo.getContentType();
	}
	
	public String getOriginalFilename() {
		return this.photo.getOriginalFilename();
	}
	
	public Long getFileSize() {
		return this.photo.getSize();
	}
	
}
