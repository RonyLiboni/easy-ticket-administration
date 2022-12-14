package com.easyticket.corebusiness.exception_handler;

import lombok.Getter;

@Getter
public enum ProblemType {

	RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found."), 
	MESSAGE_NOT_READABLE("/message-not-readable","Message not readable."), 
	INTERNAL_SERVER_ERROR("/internal-server-error","Internal Server Error."), 
	OBJECT_WITH_VALIDATION_ERROR("/object-validation-error","Object Validation Error."),
	INVALID_PARAMETER("/invalid-parameter", "Invalid parameter"),
	REQUEST_TO_OTHER_MICROSERVICE_FAILED("/request-to-other-microservice-fail","Request with other microservice failed"), 
	FILE_STORAGE_FAILED("/file-storage-failed","File storage failed"), 
	ENTITY_IN_USE("/entity-in-use","Entity in use");

	private String title;
	private String uri;
	
	ProblemType(String path, String title) {
		this.uri = "https://easytickets.microservices" + path;
		this.title = title;
	}
	
}
