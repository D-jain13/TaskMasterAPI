package com.dhairya.taskMaster.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceAlreadyExistsException extends RuntimeException {

	String resourceName;
	String fieldName;
	String fieldValue;
	
	public ResourceAlreadyExistsException(String resourceName,String fieldName,String fieldValue) {
		super(String.format("%s already exists with %s : %s",resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
}
