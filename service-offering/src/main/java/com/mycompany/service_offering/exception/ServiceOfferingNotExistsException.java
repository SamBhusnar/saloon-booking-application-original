package com.mycompany.service_offering.exception;

public class ServiceOfferingNotExistsException extends RuntimeException {
    public ServiceOfferingNotExistsException(String message) {
        super(message);
    }
}
