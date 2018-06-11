package com.fusemachines.predict.exception;

public class BackendException extends RuntimeException {

	private static final long serialVersionUID = -3083287207241740585L;

	public BackendException(String message, Throwable cause) {
		super(message, cause);
	}

	public BackendException(String message) {
		super(message);
	}
	
	

}
