package com.easyticket.corebusiness.service;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
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
	
	@EventListener
	public void registerCustomerInSecurityService(NewCustomerEvent event) {
		try {
			securityServiceFeign.registerNewUser(new NewUserRequest(event.getEmail(), event.getPassword()));
		} catch (FeignException e) {
			throw new RequestToOtherMicroserviceFailedException(e);
		}	
	}	
	
}
