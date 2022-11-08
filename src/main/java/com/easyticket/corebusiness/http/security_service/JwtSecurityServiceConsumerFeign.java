package com.easyticket.corebusiness.http.security_service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "jwt-security-consumer", url = "http://localhost:8080/security-ms")
public interface JwtSecurityServiceConsumerFeign {
	
	@PostMapping("/all/v1/users")
	public ResponseEntity<?> registerNewUser(NewUserRequest request); 
	
	@DeleteMapping("/admin/v1/users/{username}")
	public ResponseEntity<Void> deleteUserByUsername(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, 
																					@PathVariable("username") String username);
		
}
