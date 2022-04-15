package com.poa.server.exception;

import lombok.Getter;

/**
 * customer exception
 */
@Getter
public class PoaException extends RuntimeException {
    private int code;
    private String message;

    public PoaException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public PoaException(String message) {
        this.code = 500;
        this.message = message;
    }
}
