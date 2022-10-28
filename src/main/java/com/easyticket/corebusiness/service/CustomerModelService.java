package com.easyticket.corebusiness.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.easyticket.corebusiness.dto.CustomerModelDto;
import com.easyticket.corebusiness.dto.NewCustomerModelRequest;
import com.easyticket.corebusiness.entity.CustomerModel;
import com.easyticket.corebusiness.repository.CustomerModelRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerModelService {
	
	private final CustomerModelRepository customerModelRepository;
	private final ModelMapper mapper;
	
	@Transactional
	public CustomerModel saveCustomerModel(@Valid NewCustomerModelRequest request) {						
		return save(mapper.map(request, CustomerModel.class));
	}

	private CustomerModel save(CustomerModel customerModel) {
		return customerModelRepository.save(customerModel);
	}
	
	public Page<CustomerModelDto> getAllDto(Pageable page){
		return getAll(page).map(customerModel -> mapper.map(customerModel, CustomerModelDto.class));
	}
	
	private Page<CustomerModel> getAll(Pageable page) {
		return customerModelRepository.findAll(page);
	}
	
	@Transactional
	public CustomerModelDto updateCustomerModel(@Valid NewCustomerModelRequest request, Long id) {
		var customerModel = findById(id);
		mapper.map(request, customerModel);
		return mapper.map(customerModel, CustomerModelDto.class);
	}

	private CustomerModel findById(Long id) {
		return customerModelRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(String.format("The CustomerModel with id '%s' was not found!", id)));
	}
	
	@Transactional
	public CustomerModelDto getById(Long id) {
		return mapper.map(findById(id), CustomerModelDto.class);
	}
	
	@Transactional
	public void deleteById(Long id) {
		customerModelRepository.delete(findById(id));
	}
}
