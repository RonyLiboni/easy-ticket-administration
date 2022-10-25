package com.easyticket.corebusiness.service;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.easyticket.corebusiness.dto.AddressModelDto;
import com.easyticket.corebusiness.dto.NewAddressModelRequest;
import com.easyticket.corebusiness.entity.AddressModel;
import com.easyticket.corebusiness.repository.AddressModelRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressModelService {
	private final AddressModelRepository addressModelRepository;
	private final ZipCodeService zipCodeService;
	
	@Transactional
	public AddressModel saveAddress(@Valid NewAddressModelRequest request) {
		var zipCode = letStringOnlyWithNumbers(request.getZipCode());
		var viaCep = zipCodeService.getViaCepData(zipCode);
		return save(request.toEntity(zipCode, viaCep));
	}

	private AddressModel save(AddressModel addressModel) {
		return addressModelRepository.save(addressModel);
	}
	
	private String letStringOnlyWithNumbers(String string) {
		return string.replaceAll("[^0-9]", "");
	}

	public Page<AddressModelDto> getAllDto(Pageable page){
		return getAll(page).map(AddressModelDto::new);
	}
	
	private Page<AddressModel> getAll(Pageable page) {
		return addressModelRepository.findAll(page);
	}
	
	
	
}
