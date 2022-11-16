package com.easyticket.corebusiness.controller;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyticket.corebusiness.configuration.documentation.annotations.DeleteMappingDocumentation;
import com.easyticket.corebusiness.configuration.documentation.annotations.GetMappingDocumentation;
import com.easyticket.corebusiness.configuration.documentation.annotations.PutMappingDocumentation;
import com.easyticket.corebusiness.dto.ShowPhotoModelDto;
import com.easyticket.corebusiness.dto.UpdateShowPhotoModelRequest;
import com.easyticket.corebusiness.entity.ShowPhotoModel;
import com.easyticket.corebusiness.service.PhotoStorageService.RetrievedPhoto;
import com.easyticket.corebusiness.service.ShowPhotoModelService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("admin/v1/shows/{showId}/photo")
@RequiredArgsConstructor
@Tag(name = "Show Photo Controller",description = "You will be able to update, get and delete a photo that belongs to a show model.")
public class ShowPhotoModelController {

	private final ShowPhotoModelService showPhotoModelService;

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PutMappingDocumentation(summary = "Updates the photo related to the show id passed in Path Variable")
	public ResponseEntity<ShowPhotoModelDto> update(@PathVariable Long showId,
													@Valid UpdateShowPhotoModelRequest request) {
		return ResponseEntity.ok(showPhotoModelService.updateShowModelPhoto(showId, request));
	}

	@DeleteMapping
	@DeleteMappingDocumentation(summary = "Deletes the photo related to the show id passed in Path Variable")
	public ResponseEntity<Void> delete(@PathVariable Long showId) {
		showPhotoModelService.delete(showId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMappingDocumentation(summary = "Gets the photo related to the show id passed in Path Variable")
	public ResponseEntity<ShowPhotoModelDto> findById(@PathVariable Long showId) {
		return ResponseEntity.ok(showPhotoModelService.getDtoById(showId));
	}

	@GetMapping(produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	@GetMappingDocumentation(summary = "Gets the photo related to the show id passed in Path Variable")
	@ApiResponse(responseCode= "406", description = "The media type you asked is not available.")
	public ResponseEntity<?> getImage(@PathVariable Long showId, @Parameter(hidden = true) @RequestHeader(name = "accept") String acceptHeader) 
											throws HttpMediaTypeNotAcceptableException {
		ShowPhotoModel showPhotoModel;
		List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);
		try {
			showPhotoModel = showPhotoModelService.findById(showId);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		MediaType mediaTypeFoto = MediaType.parseMediaType(showPhotoModel.getContentType());
		
		isMediaTypeCompatible(mediaTypeFoto, mediaTypesAceitas);
		RetrievedPhoto retrievedPhoto = showPhotoModelService.retrievePhotoById(showPhotoModel.getFileName());

		if (retrievedPhoto.hasUrl()) {
			return ResponseEntity.status(HttpStatus.FOUND)
								 .header(HttpHeaders.LOCATION, retrievedPhoto.getUrl())
								 .build();
		} else {
			return ResponseEntity.ok().contentType(mediaTypeFoto)
					.body(new InputStreamResource(retrievedPhoto.getInputStream()));
		}
	}
	
	private void isMediaTypeCompatible(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas)
			throws HttpMediaTypeNotAcceptableException {
		boolean isCompatible = mediaTypesAceitas.stream()
				.anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));
		if (!isCompatible) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
		}
	}

}
