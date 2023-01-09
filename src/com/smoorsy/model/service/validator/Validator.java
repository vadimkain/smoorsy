package com.smoorsy.model.service.validator;

public interface Validator<T> {
    ValidationResult isValid(T object);
}
