package com.easyticket.corebusiness.service;

import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.util.FileCopyUtils;
import com.easyticket.corebusiness.configuration.StorageProperties;
import com.easyticket.corebusiness.exception.StorageException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LocalPhotoStorageService implements PhotoStorageService {

	private final StorageProperties storageProperties;
	
	@Override
	public RetrievedPhoto retrieve(String fileName) {
		try {
			return RetrievedPhoto.builder()
									.inputStream(Files.newInputStream(getFilePath(fileName)))
									.build();
		} catch (Exception e) {
			throw new StorageException("It was not possible to retrieve the file.", e);
		}
	}
	
	@Override
	public void store(NewPhoto newPhoto) {
		try {
			FileCopyUtils.copy(newPhoto.getInputStream(), 
							   Files.newOutputStream(getFilePath(newPhoto.getFileName())));
		} catch (Exception e) {
			throw new StorageException("it was not possible to store the file.", e);
		}
	}
	
	@Override
	public void remove(String fileName) {
		try {
			Files.deleteIfExists(getFilePath(fileName));
		} catch (Exception e) {
			throw new StorageException("It was not possible to delete the file.", e);
		}
	}

	private Path getFilePath(String fileName) {
		return storageProperties.getLocal().getPhotosDirectory()
									.resolve(Path.of(fileName));
	}

}
