package com.bugcatcher.exception;

import java.io.IOException;

public class StorageFileNotFoundException extends IOException {

	public StorageFileNotFoundException() {
		super();
	}

	public StorageFileNotFoundException(String message) {
		super(message);
	}

	public StorageFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public StorageFileNotFoundException(Throwable cause) {
		super(cause);
	}	

}
