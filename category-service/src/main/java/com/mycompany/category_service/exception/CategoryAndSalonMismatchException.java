package com.mycompany.category_service.exception;

public class CategoryAndSalonMismatchException extends  RuntimeException{
    public CategoryAndSalonMismatchException(String message) {
        super(message);
    }
}
