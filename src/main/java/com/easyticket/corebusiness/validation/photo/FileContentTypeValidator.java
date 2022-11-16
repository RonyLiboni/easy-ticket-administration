package com.easyticket.corebusiness.validation.photo;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

	private List<String> allowedContentTypes;
	
	@Override
	public void initialize(FileContentType fileContentType) {
		this.allowedContentTypes = Arrays.asList(fileContentType.allowed());
	}
	
	@Override
	public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
		return multipartFile == null 
				|| this.allowedContentTypes.contains(multipartFile.getContentType());
	}

}
