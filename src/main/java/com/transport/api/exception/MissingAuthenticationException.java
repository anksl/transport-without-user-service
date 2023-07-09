package com.transport.api.exception;

public class MissingAuthenticationException extends RuntimeException{
    public MissingAuthenticationException(String message) {
        super(message);
    }
}
