package com.mycompany.service_offering.exception;

import com.mycompany.service_offering.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.mycompany.service_offering.constant.ServiceOfferingConstant.*;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ServiceOfferingAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleServiceOfferingAlreadyExistsException(ServiceOfferingAlreadyExistsException ex, HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setMethod(request.getMethod());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setErrorCode(SERVICE_OFFERING_ALREADY_EXISTS);
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(ServiceOfferingNotExistsException.class)
    public ResponseEntity<ErrorResponse> handleServiceOfferingServiceNotExistsException(ServiceOfferingNotExistsException ex, HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setMethod(request.getMethod());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setErrorCode(SERVICE_OFFERING_NOT_EXISTS);
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

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

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setPath(req.getRequestURI());
        errorResponse.setMethod(req.getMethod());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setErrorCode(METHOD_ARGUMENT_NOT_VALID);
        errorResponse.setMessage(errors.toString());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleCommonException(Exception ex, HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setMethod(request.getMethod());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setErrorCode(SOMETHING_WENT_WRONG);
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }


}
