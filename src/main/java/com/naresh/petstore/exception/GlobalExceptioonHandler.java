package com.naresh.petstore.exception;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptioonHandler extends ResponseEntityExceptionHandler {

	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, 
    		HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String,String> err=new HashMap<>();
       for(FieldError fieldError:ex.getFieldErrors()){
           err.put(fieldError.getField(),fieldError.getDefaultMessage());
       }
       return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
	 }
	 
    @ExceptionHandler(AuthorizationException.class)
    protected ResponseEntity<ErrorDetails> authExp() {
        return new ResponseEntity<>(new ErrorDetails("Invalid username/password supplied"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorDetails> enittyExp() {
        return new ResponseEntity<>(new ErrorDetails("Invalid ID supplied"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUsernameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorDetails> userExp() {
        return new ResponseEntity<>(new ErrorDetails("Invalid username supplied"), HttpStatus.BAD_REQUEST);
    }
    

    @ExceptionHandler(PetAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorDetails> petExistsExp() {
        return new ResponseEntity<>(new ErrorDetails("Pet already exists"), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(InvalidPetIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorDetails> petIdExp() {
        return new ResponseEntity<>(new ErrorDetails("Invalid Pet id supplied"), HttpStatus.BAD_REQUEST);
    }
}
