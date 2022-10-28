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
import com.easyticket.corebusiness.dto.AddressModelDto;
import com.easyticket.corebusiness.dto.NewAddressModelRequest;
import com.easyticket.corebusiness.service.AddressModelService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/addresses")
@Tag(name = "Address Controller",description = "You will be able to create, read, update and delete an AddressModel in these endpoints.")
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
	
	@PutMapping("/{id}")
	@PutMappingDocumentation(summary= "Updates an Addresses")
	public ResponseEntity<AddressModelDto> updateAnAddressModel(@RequestBody @Valid NewAddressModelRequest request,@PathVariable("id") Long id){
		return ResponseEntity.ok(addressModelService.updateAddress(request, id));
	}
	
	@GetMapping("/{id}")
	@GetMappingDocumentation(summary= "Gets an Addresses by its Id")
	public ResponseEntity<AddressModelDto> getAnAddressModelById(@PathVariable("id") Long id){
		return ResponseEntity.ok(addressModelService.getById(id));
	}
	
	@DeleteMapping("/{id}")
	@DeleteMappingDocumentation(summary= "Deletes an Addresses by its Id")
	public ResponseEntity<?> deleteAnAddressModelById(@PathVariable("id") Long id){
		addressModelService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
