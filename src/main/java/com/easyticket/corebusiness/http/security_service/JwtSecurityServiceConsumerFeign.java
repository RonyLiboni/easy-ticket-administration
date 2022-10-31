package com.easyticket.corebusiness.http.security_service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "jwt-security-consumer", url = "http://localhost:8082/security-ms/")
public interface JwtSecurityServiceConsumerFeign {
	
	@PostMapping(value = "/v1/user")
	public ResponseEntity<?> registerNewUser(NewUserRequest request); 

}
