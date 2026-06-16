package com.mycompany.userservice.exception;

import com.mycompany.userservice.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.mycompany.userservice.constant.UserConstant.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest req) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), USER_NOT_FOUND, LocalDateTime.now(), 404, req.getRequestURI(), req.getMethod());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse>
    handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest req) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()));

        ErrorResponse errorResponse = new ErrorResponse(errors.toString(), METHOD_ARGUMENT_NOT_VALID, LocalDateTime.now(), 400, req.getRequestURI(), req.getMethod());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    // internal server exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest req) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), INTERNAL_SERVER_ERROR, LocalDateTime.now(), 500, req.getRequestURI(), req.getMethod());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }


}
