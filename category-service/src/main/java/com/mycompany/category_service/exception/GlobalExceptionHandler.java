package com.mycompany.category_service.exception;

import com.mycompany.category_service.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;

import static com.mycompany.category_service.constant.CategoryConstant.*;


@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCategoryAlreadyExistsException(CategoryAlreadyExistsException ex , HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setMethod(request.getMethod());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setErrorCode(CATEGORY_ALREADY_EXISTS);
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(CategoryAndSalonMismatchException.class)
    public ResponseEntity<ErrorResponse> handleCategoryAndSalonMismatchException(CategoryAndSalonMismatchException ex,HttpServletRequest request){
        ErrorResponse errorResponse=new ErrorResponse();
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setMethod(request.getMethod());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setErrorCode(CATEGORY_AND_SALON_MISMATCH);
        errorResponse.setMessage(ex.getMessage());
        // un-procesable content entity 422 status code
        errorResponse.setStatus(HttpStatus.UNPROCESSABLE_CONTENT.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_CONTENT);


    }
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(CategoryNotFoundException ex,HttpServletRequest request){
        ErrorResponse errorResponse=new ErrorResponse();
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setMethod(request.getMethod());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setErrorCode(CATEGORY_NOT_FOUND);
        errorResponse.setMessage(ex.getMessage());
        // un-procesable content entity 422 status code
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);


    }
// exception and Runtimeexception


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(Exception ex,HttpServletRequest request){
        ErrorResponse errorResponse=new ErrorResponse();
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setMethod(request.getMethod());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setErrorCode(BAD_REQUEST_FOR_CATEGORY);
        errorResponse.setMessage(ex.getMessage());
        // un-procesable content entity 422 status code
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);


    }





}
