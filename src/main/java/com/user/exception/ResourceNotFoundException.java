package com.user.exception;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException{

	public ResourceNotFoundException() {
		super("Resource Not Found ");
	}
	
	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
