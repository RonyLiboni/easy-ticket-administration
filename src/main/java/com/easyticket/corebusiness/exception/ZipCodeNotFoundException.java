package com.easyticket.corebusiness.exception;

import javax.persistence.EntityNotFoundException;

public class ZipCodeNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public ZipCodeNotFoundException(String message) {
		super(message);
	}
}
