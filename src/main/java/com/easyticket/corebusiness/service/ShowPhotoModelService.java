package com.easyticket.corebusiness.service;

import java.io.IOException;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.easyticket.corebusiness.dto.ShowPhotoModelDto;
import com.easyticket.corebusiness.dto.UpdateShowPhotoModelRequest;
import com.easyticket.corebusiness.entity.ShowPhotoModel;
import com.easyticket.corebusiness.exception.StorageException;
import com.easyticket.corebusiness.repository.ShowPhotoRepository;
import com.easyticket.corebusiness.service.PhotoStorageService.NewPhoto;
import com.easyticket.corebusiness.service.PhotoStorageService.RetrievedPhoto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowPhotoModelService {

	private final ShowModelService showModelService;
	private final ShowPhotoRepository showPhotoModelRepository;
	private final PhotoStorageService photoStorageService;
	private final ModelMapper mapper;

	@Transactional
	public ShowPhotoModelDto updateShowModelPhoto(Long showId, UpdateShowPhotoModelRequest request) {
		return mapper.map(savePhoto(request, showId), ShowPhotoModelDto.class);
	}

	private ShowPhotoModel savePhoto(UpdateShowPhotoModelRequest request, Long showId){
		var showPhotoModel = buildShowPhotoModel(request, showId);
		var showPhotoModelOptional = showPhotoModelRepository.findById(showId);
		String existingFileName = getFileNameAndDeleteFileIfExists(showId, showPhotoModelOptional);
		
		showPhotoModel.setFileName(photoStorageService.createFileName(request.getOriginalFilename()));
		showPhotoModel = showPhotoModelRepository.save(showPhotoModel);
		showPhotoModelRepository.flush();
		
		try {
			var newPhoto = NewPhoto.builder()
									.fileName(showPhotoModel.getFileName())
									.contentType(request.getContentType())
									.inputStream(request.getInputStream())
									.build();
			photoStorageService.replace(existingFileName, newPhoto);
			return showPhotoModel;
		} catch (IOException e) {
			throw new StorageException("Request failed while getting file Input Stream.");
		}
	}

	private String getFileNameAndDeleteFileIfExists(Long showId, Optional<ShowPhotoModel> showPhotoModelOptional) {
		if (showPhotoModelOptional.isPresent()) {
			showPhotoModelRepository.deleteById(showId);
			return showPhotoModelOptional.get().getFileName();
		}
		return null;
	}

	private ShowPhotoModel buildShowPhotoModel(UpdateShowPhotoModelRequest request, Long showId) {
		var showPhotoModel = mapper.map(request, ShowPhotoModel.class);
		showPhotoModel.setShow(showModelService.findById(showId));
		return showPhotoModel;
	}
	
	@Transactional
	public void delete(Long showId) {
		var showPhotoModel = findById(showId);
		showPhotoModelRepository.delete(showPhotoModel);
		showPhotoModelRepository.flush();
		photoStorageService.remove(showPhotoModel.getFileName());
	}
	
	public ShowPhotoModel findById(Long showId) {
		return showPhotoModelRepository.findById(showId)
				.orElseThrow(()-> new EntityNotFoundException(String.format("The ShowPhotoModel with id '%s' was not found!", showId)));
	}
	
	@Transactional
	public ShowPhotoModelDto getDtoById(Long showId) {
		return mapper.map(findById(showId), ShowPhotoModelDto.class);
	}

	public RetrievedPhoto retrievePhotoById(String fileName) {
		return photoStorageService.retrieve(fileName);
	}

}
