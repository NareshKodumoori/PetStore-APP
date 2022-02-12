package com.naresh.petstore.exception;

public class ErrorConstants {

	private ErrorConstants(){
		throw new IllegalStateException("Exception Constant class");
	}
	
	public static final String PET_STORE_DATA_REQUIRED_MISSING = "PetStore-601";
	
}
