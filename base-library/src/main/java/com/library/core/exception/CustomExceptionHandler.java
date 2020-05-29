package com.library.core.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.library.core.bean.ExceptionResponseBean;


public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		ExceptionResponseBean exceptionResponse = new ExceptionResponseBean(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getDescription(false),
				ex.getMessage(), new Date());
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	/**
	 * Record Not found Exception
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(RecordNotFoundException.class)
	public final ResponseEntity<Object> handleRecordNotFoundExceptions(Exception ex, WebRequest request) {
		ExceptionResponseBean exceptionResponse = new ExceptionResponseBean(HttpStatus.NOT_FOUND.value(), request.getDescription(false),
				ex.getMessage(), new Date());
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Validation Exception
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(CustomValidationException.class)
	public final ResponseEntity<Object> handleValidationExceptions(Exception ex, WebRequest request) {
		ExceptionResponseBean exceptionResponse = new ExceptionResponseBean(HttpStatus.NOT_ACCEPTABLE.value(), request.getDescription(false),
				ex.getMessage(), new Date());
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
	}

}
