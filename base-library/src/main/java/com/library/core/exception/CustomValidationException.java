package com.library.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class CustomValidationException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3678845204980877084L;

	public CustomValidationException(String message) {
		super(message);
	}

}
