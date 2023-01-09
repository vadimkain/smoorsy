package com.smoorsy.model.service.mapper;

public interface Adapter<From, To> {
    To fromObject(From fromObject);
}
