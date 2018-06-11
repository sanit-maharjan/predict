package com.fusemachines.predict.exception;

public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public BadRequestException(String message,Throwable exception){
		super(message,exception);
	}
	
	public BadRequestException(String message) {
		super(message);
	}
}
