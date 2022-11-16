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
import com.easyticket.corebusiness.dto.NewShowModelRequest;
import com.easyticket.corebusiness.dto.ShowModelDto;
import com.easyticket.corebusiness.service.ShowModelService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/v1/shows")
@Tag(name = "Show Controller",description = "You will be able to create, read, update and delete a ShowModel in these endpoints.")
public class ShowModelController {
	
	private final ShowModelService showModelService;
	
	@PostMapping
	@PostMappingDocumentation(summary= "Creates a new ShowModel")
	public ResponseEntity<?> createNewShowModel(@RequestBody @Valid NewShowModelRequest request){
		return ResponseEntity.created(URI.create(format("v1/shows/%s", showModelService.saveShowModel(request).getId()))).build();
	}
	
	@GetMapping
	@GetMappingDocumentation(summary= "Gets all saved ShowModel")
	public ResponseEntity<Page<ShowModelDto>> getAllAddressModel(@PageableDefault()@Parameter(hidden=true) Pageable page){
		return ResponseEntity.ok(showModelService.getAllDto(page));
	}
	
	@PutMapping("/{id}")
	@PutMappingDocumentation(summary= "Updates an ShowModel")
	public ResponseEntity<ShowModelDto> updateAnAddressModel(@RequestBody @Valid NewShowModelRequest request,@PathVariable("id") Long id){
		return ResponseEntity.ok(showModelService.updateShowModel(request, id));
	}
	
	@GetMapping("/{id}")
	@GetMappingDocumentation(summary= "Gets an ShowModel by its Id")
	public ResponseEntity<ShowModelDto> getAnAddressModelById(@PathVariable("id") Long id){
		return ResponseEntity.ok(showModelService.getDtoById(id));
	}
	
	@DeleteMapping("/{id}")
	@DeleteMappingDocumentation(summary= "Deletes an ShowModel by its Id")
	public ResponseEntity<?> deleteAnAddressModelById(@PathVariable("id") Long id){
		showModelService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
