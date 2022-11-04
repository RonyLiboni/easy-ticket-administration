package com.easyticket.corebusiness.controller;

import static java.lang.String.format;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyticket.corebusiness.configuration.documentation.annotations.DeleteMappingDocumentation;
import com.easyticket.corebusiness.configuration.documentation.annotations.GetMappingDocumentation;
import com.easyticket.corebusiness.configuration.documentation.annotations.PostMappingDocumentation;
import com.easyticket.corebusiness.configuration.documentation.annotations.PutMappingDocumentation;
import com.easyticket.corebusiness.dto.CustomerModelDto;
import com.easyticket.corebusiness.dto.NewCustomerModelRequest;
import com.easyticket.corebusiness.service.CustomerModelService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/v1/customers")
@Tag(name = "Customer Controller",description = "You will be able to create, read, update and delete a customer in these endpoints.")
public class CustomerModelController {
	
	private final CustomerModelService customerModelService;
	
	@PostMapping
	@PostMappingDocumentation(summary= "Creates a new CustomerModel")
	public ResponseEntity<?> createNewCustomerModel(@RequestBody @Valid NewCustomerModelRequest request){
		return ResponseEntity.created(URI.create(format("v1/customers/%s", customerModelService.saveCustomerModel(request).getId()))).build();
	}
	
	@GetMapping
	@GetMappingDocumentation(summary= "Gets all saved CustomerModel")
	public ResponseEntity<Page<CustomerModelDto>> getAllCustomerModel(@PageableDefault()@Parameter(hidden=true) Pageable page){
		return ResponseEntity.ok(customerModelService.getAllDto(page));
	}
	
	@PutMapping("/{id}")
	@PutMappingDocumentation(summary= "Updates an CustomerModel")
	public ResponseEntity<CustomerModelDto> updateAnCustomerModel(@RequestBody @Valid NewCustomerModelRequest request, @PathVariable("id") Long id){
		return ResponseEntity.ok(customerModelService.updateCustomerModel(request, id));
	}
	
	@GetMapping("/{id}")
	@GetMappingDocumentation(summary= "Gets an CustomerModel by its Id")
	public ResponseEntity<CustomerModelDto> getAnCustomerModelById(@PathVariable("id") Long id){
		return ResponseEntity.ok(customerModelService.getById(id));
	}
	
	@DeleteMapping("/{id}")
	@DeleteMappingDocumentation(summary= "Deletes an CustomerModel by its Id")
	public ResponseEntity<?> deleteAnCustomerModelById(@PathVariable("id") Long id){
		customerModelService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
