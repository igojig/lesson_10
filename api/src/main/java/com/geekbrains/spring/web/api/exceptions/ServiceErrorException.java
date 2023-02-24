package com.geekbrains.spring.web.api.exceptions;

public class ServiceErrorException extends RuntimeException{
    public ServiceErrorException(String message) {
        super(message);
    }
}
