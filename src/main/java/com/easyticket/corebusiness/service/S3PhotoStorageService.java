package com.easyticket.corebusiness.service;

import java.net.URL;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.easyticket.corebusiness.configuration.StorageProperties;
import com.easyticket.corebusiness.exception.StorageException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class S3PhotoStorageService implements PhotoStorageService {

	private final AmazonS3 amazonS3;
	private final StorageProperties storageProperties;
	
	@Override
	public RetrievedPhoto retrieve(String fileName) {
		URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), getFilePath(fileName));		
		return RetrievedPhoto.builder()
								.url(url.toString()).build();
	}

	@Override
	public void store(NewPhoto newPhoto) {
		try {
			var objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(newPhoto.getContentType());
			
			amazonS3.putObject(new PutObjectRequest(storageProperties.getS3().getBucket(),
													getFilePath(newPhoto.getFileName()),
													newPhoto.getInputStream(),
													objectMetadata)
											.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (Exception e) {
			throw new StorageException("It was not possible to send the file to Amazon S3.", e);
		}
	}

	@Override
	public void remove(String fileName) {
		try {
			amazonS3.deleteObject(new DeleteObjectRequest(storageProperties.getS3().getBucket(), 
														  getFilePath(fileName)));
		} catch (Exception e) {
			throw new StorageException("It was not possible remove the file from Amazon S3.", e);
		}
	}
	
	private String getFilePath(String fileName) {
		return String.format("%s/%s", storageProperties.getS3().getPhotosDirectory(), fileName);
	}

}
