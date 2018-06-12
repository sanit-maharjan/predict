package com.fusemachines.predict.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
@RestController
public class GlobalExceptionHandler /*extends ResponseEntityExceptionHandler*/ {
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> resourceNotFound(ResourceNotFoundException ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, WebRequest request) {		
		String message = ex.getRootCause().getLocalizedMessage();
		Map<String, String> map = new HashMap<>();
		map.put("message", message.split("String \"")[1].split(" at ")[0].replaceAll("\":", " is").trim());
		map.put("status", HttpStatus.BAD_REQUEST.toString());
		return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
	}
	

}
