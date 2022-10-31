package com.easyticket.corebusiness.service;

import org.springframework.stereotype.Service;

import com.easyticket.corebusiness.exception.ZipCodeNotFoundException;
import com.easyticket.corebusiness.http.zipcode_service.ViaCepAddress;
import com.easyticket.corebusiness.http.zipcode_service.ZipCodeConsumerFeign;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZipCodeService {
	
	private final ZipCodeConsumerFeign zipCodeConsumerFeign;
	
	public ViaCepAddress getViaCepData(String zipCode) {
		ViaCepAddress viaCep = null;
		try {
			viaCep = zipCodeConsumerFeign.getFullAddres(zipCode);
		} catch (FeignException e) {
			throw new ZipCodeNotFoundException(String.format("The zip code %s doesn't exist!", zipCode));
		}
		if (viaCep.getLogradouro() == null) {
			throw new ZipCodeNotFoundException(String.format("The zip code %s doesn't exist!", zipCode));
		}
		return zipCodeConsumerFeign.getFullAddres(zipCode);
	}
}
