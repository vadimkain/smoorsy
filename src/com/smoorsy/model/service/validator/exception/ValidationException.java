package com.smoorsy.model.service.validator.exception;

import com.smoorsy.model.service.validator.Error;

import java.util.List;

public class ValidationException extends RuntimeException {

    private final List<Error> errors;

    public ValidationException(List<Error> errors) {
        this.errors = errors;
    }

    public List<Error> getErrors() {
        return this.errors;
    }
}
