package com.mycompany.saloon_service.exception;

public class SalonAndOwnerNotMatchException extends RuntimeException {
    public SalonAndOwnerNotMatchException(String message) {
        super(message);
    }

}
