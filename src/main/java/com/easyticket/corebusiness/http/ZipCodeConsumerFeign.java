package com.easyticket.corebusiness.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "zipCode-consumer", url = "https://viacep.com.br/ws/")
public interface ZipCodeConsumerFeign {
	
	@GetMapping(value = "/{zipCode}/json/")
	public ViaCepAddress getFullAddres(@PathVariable("zipCode") String zipCode); 

}
