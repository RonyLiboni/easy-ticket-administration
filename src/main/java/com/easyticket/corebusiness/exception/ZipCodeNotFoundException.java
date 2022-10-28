package com.easyticket.corebusiness.exception;

public class ZipCodeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ZipCodeNotFoundException(String message) {
		super(message);
	}
}
