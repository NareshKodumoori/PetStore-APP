package com.naresh.petstore.exception;

public class InvalidPetIdException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidPetIdException() {

	}

	public InvalidPetIdException(String message) {
		super(message);
	}

}