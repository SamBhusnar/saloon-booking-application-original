package com.mycompany.service_offering.exception;

public class ServiceOfferingAlreadyExistsException extends RuntimeException {
    public ServiceOfferingAlreadyExistsException(String message) {
        super(message);
    }
}
