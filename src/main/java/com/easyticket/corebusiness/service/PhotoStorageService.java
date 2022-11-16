package com.easyticket.corebusiness.service;

import java.io.InputStream;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

public interface PhotoStorageService {

	RetrievedPhoto retrieve(String fileName);

	void store(NewPhoto newPhoto);

	void remove(String fileName);

	default void replace(String oldFileName, NewPhoto newPhoto) {
		this.store(newPhoto);

		if (oldFileName != null) {
			this.remove(oldFileName);
		}
	}

	default String createFileName(String originalName) {
		return UUID.randomUUID().toString() + "_" + originalName;
	}

	@Builder
	@Getter
	class NewPhoto {

		private String fileName;
		private String contentType;
		private InputStream inputStream;

	}

	@Builder
	@Getter
	class RetrievedPhoto {

		private InputStream inputStream;
		private String url;

		public boolean hasUrl() {
			return url != null;
		}

		public boolean temInputStream() {
			return inputStream != null;
		}

	}
}
