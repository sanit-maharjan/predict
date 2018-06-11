package com.fusemachines.predict.exception;

public class ErrorResponse {
	private String message;
	private int errorCode;

	public ErrorResponse(int code,String message) {
		this.message = message;
		this.setErrorCode(code);
	}

	public ErrorResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
