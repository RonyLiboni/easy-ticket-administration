package com.easyticket.corebusiness.exception;

import feign.FeignException;
import lombok.Getter;

@Getter
public class RequestToOtherMicroserviceFailedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errorMessage;
	private String detailMessage;
	
	public RequestToOtherMicroserviceFailedException(FeignException exception) {
		this.errorMessage = exception.contentUTF8();
		this.detailMessage = exception.getMessage();
	}
}
