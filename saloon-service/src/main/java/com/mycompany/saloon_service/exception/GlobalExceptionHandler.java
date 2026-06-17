package com.mycompany.saloon_service.exception;

import com.mycompany.saloon_service.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;

import static com.mycompany.saloon_service.constant.SalonConstant.SALON_AND_OWNER_NOT_MATCH;
import static com.mycompany.saloon_service.constant.SalonConstant.SALON_NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(SalonNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSalonNotFoundException(SalonNotFoundException ex , HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setMethod(request.getMethod());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setErrorCode(SALON_NOT_FOUND);
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(SalonAndOwnerNotMatchException.class)
    public ResponseEntity<ErrorResponse> handleSalonAndOwnerNotMatchException(SalonAndOwnerNotMatchException ex,HttpServletRequest request){
        ErrorResponse errorResponse=new ErrorResponse();
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setMethod(request.getMethod());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setErrorCode(SALON_AND_OWNER_NOT_MATCH);
        errorResponse.setMessage(ex.getMessage());
        // un-procesable content entity 422 status code
        errorResponse.setStatus(HttpStatus.UNPROCESSABLE_CONTENT.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_CONTENT);


    }




}
