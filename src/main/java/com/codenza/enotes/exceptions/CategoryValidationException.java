package com.codenza.enotes.exceptions;

import java.util.Map;

public class CategoryValidationException extends RuntimeException{

	
	private Map<String, Object> error;

	public CategoryValidationException(Map<String, Object> error) {
		super("Validation failed..");
		this.error = error;
	}
	
	public Map<String, Object> getErrors(){
		return error;
		
	}
}
