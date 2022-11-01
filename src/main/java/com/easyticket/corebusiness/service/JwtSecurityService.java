package com.easyticket.corebusiness.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.easyticket.corebusiness.event.AdminDeleteCustomerEvent;
import com.easyticket.corebusiness.event.NewCustomerEvent;
import com.easyticket.corebusiness.exception.RequestToOtherMicroserviceFailedException;
import com.easyticket.corebusiness.http.security_service.JwtSecurityServiceConsumerFeign;
import com.easyticket.corebusiness.http.security_service.NewUserRequest;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtSecurityService {
	
	private final JwtSecurityServiceConsumerFeign securityServiceFeign;
	private final HttpServletRequest httpRequest;
	
	
	@EventListener
	public void registerCustomerInSecurityService(NewCustomerEvent event) {
		try {
			securityServiceFeign.registerNewUser(new NewUserRequest(event.getEmail(), event.getPassword()));
		} catch (FeignException e) {
			throw new RequestToOtherMicroserviceFailedException(e);
		}	
	}
	
	@EventListener
	public void deleteCustomerInSecurityService(AdminDeleteCustomerEvent event) {
		try {
 			securityServiceFeign.deleteUserByUsername(getAuthorizationHeader(), event.getUsername());
		} catch (FeignException e) {
			throw new RequestToOtherMicroserviceFailedException(e);
		}		
	}
	
	private String getAuthorizationHeader() {
		return httpRequest.getHeader("Authorization");
	}
	
}
