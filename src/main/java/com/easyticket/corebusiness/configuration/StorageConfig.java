package com.easyticket.corebusiness.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.easyticket.corebusiness.configuration.StorageProperties.StorageType;
import com.easyticket.corebusiness.service.LocalPhotoStorageService;
import com.easyticket.corebusiness.service.PhotoStorageService;
import com.easyticket.corebusiness.service.S3PhotoStorageService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class StorageConfig {

	private final StorageProperties storageProperties;
	
	@Bean
	@ConditionalOnProperty(name = "storage.type", havingValue = "s3")
	public AmazonS3 amazonS3() {
		var credentials = new BasicAWSCredentials(
										storageProperties.getS3().getAccessKeyId(), 
										storageProperties.getS3().getSecretAccessKey());
		
		return AmazonS3ClientBuilder.standard()
										.withCredentials(new AWSStaticCredentialsProvider(credentials))
										.withRegion(storageProperties.getS3().getRegion())
										.build();
	}
	
	@Bean
	public PhotoStorageService fotoStorageService() {
		if (StorageType.S3.equals(storageProperties.getType())) {
			return new S3PhotoStorageService(amazonS3(), storageProperties);
		} else {
			return new LocalPhotoStorageService(storageProperties);
		}
	}
	
}
