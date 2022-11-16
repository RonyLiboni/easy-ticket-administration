package com.easyticket.corebusiness.configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import com.amazonaws.regions.Regions;
import com.easyticket.corebusiness.exception.StorageException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("storage")
public class StorageProperties {

	private Local local = new Local();
	private S3 s3 = new S3();
	private StorageType type = StorageType.LOCAL;

	public enum StorageType {

		LOCAL, S3

	}

	@Getter
	@Setter
	public class Local {

		private Path photosDirectory = photoLocalStorageInProject();
		
		private Path photoLocalStorageInProject() {
			try {
				return Path.of(new File(".").getCanonicalPath(),
						"/src/main/resources/static/");
			} catch (IOException e) {
				throw new StorageException("Chosen local directory is unavailable.");
			}
		}
	}

	@Getter
	@Setter
	public class S3 {

		private String accessKeyId;
		private String secretAccessKey;
		private String bucket;
		private Regions region;
		private String photosDirectory;

	}

}