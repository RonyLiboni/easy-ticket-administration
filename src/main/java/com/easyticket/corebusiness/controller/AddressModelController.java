package com.easyticket.corebusiness.controller;

import static java.lang.String.format;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyticket.corebusiness.configuration.documentation.annotations.GetMappingDocumentation;
import com.easyticket.corebusiness.configuration.documentation.annotations.PostMappingDocumentation;
import com.easyticket.corebusiness.dto.AddressModelDto;
import com.easyticket.corebusiness.dto.NewAddressModelRequest;
import com.easyticket.corebusiness.service.AddressModelService;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/addresses")
public class AddressModelController {
	
	private final AddressModelService addressModelService;
	
	@PostMapping
	@PostMappingDocumentation(summary= "Creates a new Address")
	public ResponseEntity<?> createNewAddressModel(@RequestBody @Valid NewAddressModelRequest request){
		return ResponseEntity.created(URI.create(format("v1/addresses/%s", addressModelService.saveAddress(request).getId()))).build();
	}
	
	@GetMapping
	@GetMappingDocumentation(summary= "Gets all saved Addresses")
	public ResponseEntity<Page<AddressModelDto>> getAllAddressModel(@PageableDefault()@Parameter(hidden=true) Pageable page){
		return ResponseEntity.ok(addressModelService.getAllDto(page));
	}

}
