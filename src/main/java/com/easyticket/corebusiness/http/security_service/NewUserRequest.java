package com.easyticket.corebusiness.http.security_service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NewUserRequest {
	
	private String username;
	private String password;
	
}
