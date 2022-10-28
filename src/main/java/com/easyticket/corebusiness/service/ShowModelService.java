package com.easyticket.corebusiness.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.easyticket.corebusiness.dto.NewShowModelRequest;
import com.easyticket.corebusiness.dto.ShowModelDto;
import com.easyticket.corebusiness.entity.AddressModel;
import com.easyticket.corebusiness.entity.ShowModel;
import com.easyticket.corebusiness.repository.ShowModelRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowModelService {
	
	private final ShowModelRepository showModelRepository;
	private final ModelMapper mapper;
	
	@Transactional
	public ShowModel saveShowModel(@Valid NewShowModelRequest request) {						
		return save(mapper.map(request, ShowModel.class));
	}

	private ShowModel save(ShowModel showModel) {
		return showModelRepository.save(showModel);
	}
	
	public Page<ShowModelDto> getAllDto(Pageable page){
		return getAll(page).map(showModel -> mapper.map(showModel, ShowModelDto.class));
	}
	
	private Page<ShowModel> getAll(Pageable page) {
		return showModelRepository.findAll(page);
	}
	
	@Transactional
	public ShowModelDto updateShowModel(@Valid NewShowModelRequest request, Long id) {
		var showModel = findById(id);
		showModel.setAddress(new AddressModel()); 
		// The step above is necessary because as JPA manages the AddressModel entity inside ShowModel,
		// when we update it, JPA undestands that we want to update AddressModel entity in database to,
		// but we only want to change the AddressModel reference in ShowModel, not update the addressModel
		mapper.map(request, showModel);
		return mapper.map(showModel, ShowModelDto.class);
	}

	private ShowModel findById(Long id) {
		return showModelRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(String.format("The ShowModel with id '%s' was not found!", id)));
	}
	
	@Transactional
	public ShowModelDto getById(Long id) {
		return mapper.map(findById(id), ShowModelDto.class);
	}
	
	@Transactional
	public void deleteById(Long id) {
		showModelRepository.delete(findById(id));
	}
}
