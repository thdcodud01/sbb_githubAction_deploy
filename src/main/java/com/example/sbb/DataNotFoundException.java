package com.example.sbb;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found") // DataNotFoundException이 발생하면 @ResponseStatus 애너테이션에 의해 404 오류(HttpStatus.NOT_FOUND)가 나타날 것
public class DataNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public DataNotFoundException(String message) {
        super(message);
    }
}
