package com.easyticket.corebusiness.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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
	private final ModelMapper maper;
	
	@Transactional
	public AddressModel saveAddress(@Valid NewAddressModelRequest request) {
		return save(fromAddressRequestToEntity(request));
	}

	private AddressModel fromAddressRequestToEntity(NewAddressModelRequest request) {
		String zipCode = letStringOnlyWithNumbers(request.getZipCode());
		return request.toEntity(zipCode, zipCodeService.getViaCepData(zipCode));
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
	
	@Transactional
	public AddressModelDto updateAddress(@Valid NewAddressModelRequest request, Long id) {
		var addressToBeUpdated = findById(id);
		var addressModelDto = maper.map(fromAddressRequestToEntity(request), AddressModelDto.class);
		maper.map(addressModelDto, addressToBeUpdated);
		return addressModelDto;
	}

	public AddressModel findById(Long id) {
		return addressModelRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(String.format("The AddressModel with id '%s' was not found!", id)));
	}
	
	@Transactional
	public AddressModelDto getById(Long id) {
		return maper.map(findById(id), AddressModelDto.class);
	}
	
	@Transactional
	public void deleteById(Long id) {
		addressModelRepository.delete(findById(id));
	}
	
}
