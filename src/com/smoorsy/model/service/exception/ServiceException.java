package com.smoorsy.model.service.exception;

public class ServiceException extends RuntimeException {
    public ServiceException(Throwable cause) {
        super(cause);
    }
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
