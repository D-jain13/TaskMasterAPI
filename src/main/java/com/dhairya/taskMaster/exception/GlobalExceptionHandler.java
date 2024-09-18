package com.dhairya.taskMaster.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException ex) {
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}

	
	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<ApiResponse> resourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CONFLICT);
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationError(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ApiResponse> handleValidationError(MissingServletRequestParameterException ex) {

		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);

		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiResponse> handleValidationError(MethodArgumentTypeMismatchException ex) {

		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);

		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResponse> handleValidationError(HttpMessageNotReadableException ex) {

		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);

		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ApiResponse> jwtExpiredException(ExpiredJwtException ex) {
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CONFLICT);
	}
	
//	@ExceptionHandler(FileNotFoundException.class)
//    public ResponseEntity<String> handleFileNotFoundException(FileNotFoundException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(InvalidFileNameException.class)
//    public ResponseEntity<String> handleInvalidFileNameException(InvalidFileNameException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//    
//    @ExceptionHandler(IOException.class)
//    public ResponseEntity<String> handleIOException(IOException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
