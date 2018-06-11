package com.fusemachines.predict.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@RestController
public class MethodArgumentNotValidExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Error handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		return processFieldErrors(fieldErrors);
	}

	private Error processFieldErrors(
			List<org.springframework.validation.FieldError> fieldErrors) {
		Error error = new Error(HttpStatus.BAD_REQUEST.value(),
				"validation error");
		fieldErrors.forEach(fieldError -> 
			error.addFieldError(fieldError.getObjectName(),fieldError.getField(),
					fieldError.getDefaultMessage())
		);
		return error;
	}

	static class Error {
		private final int status;
		private final String message;
		private List<FieldError> fieldErrors = new ArrayList<>();

		Error(int status, String message) {
			this.status = status;
			this.message = message;
		}

		public int getStatus() {
			return status;
		}

		public String getMessage() {
			return message;
		}

		public void addFieldError(String objectName, String field,String defaultMessage) {
			FieldError error = new FieldError(objectName, field, defaultMessage);
			fieldErrors.add(error);
		}

		public List<FieldError> getFieldErrors() {
			return fieldErrors;
		}
	}

}
