package com.processo.seletivo.core.exception;

public class CustomUnauthorizedException extends RuntimeException {

    public CustomUnauthorizedException(String msg) {
        super(msg);
    }
}