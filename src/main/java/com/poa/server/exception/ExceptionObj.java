package com.poa.server.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionObj {

    private HttpStatus httpStatus;

    private String message;

    public static ExceptionObj of(HttpStatus httpStatus, String message) {
        return new ExceptionObj(httpStatus, message);
    }

}
