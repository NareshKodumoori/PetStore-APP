package com.naresh.petstore.exception;

public class PetAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PetAlreadyExistsException() {
	}

	public PetAlreadyExistsException(String message) {
		super(message);
	}

}