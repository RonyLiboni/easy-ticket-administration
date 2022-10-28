package com.easyticket.corebusiness.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.easyticket.corebusiness.dto.NewCustomerModelRequest;
import com.easyticket.corebusiness.dto.NewShowModelRequest;
import com.easyticket.corebusiness.dto.ShowModelDto;
import com.easyticket.corebusiness.entity.CustomerModel;
import com.easyticket.corebusiness.entity.ShowModel;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {	
		var modelMapper = new ModelMapper();
			
		modelMapper.createTypeMap(ShowModel.class, ShowModelDto.class)
					.addMapping(ShowModel::getAddress, ShowModelDto::setAddressModelDto);
		
		modelMapper.createTypeMap(NewShowModelRequest.class, ShowModel.class)
					.addMappings(mapper -> mapper.skip(ShowModel::setId));
		
		modelMapper.createTypeMap(NewCustomerModelRequest.class, CustomerModel.class)
					.addMappings(mapper -> mapper.skip(CustomerModel::setId));
		
		return modelMapper;
	}
	
}
