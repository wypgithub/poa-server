package com.poa.server.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * unified exception handling class
 */
@Slf4j
@ControllerAdvice
public class ExceptionCatch {


    /**
     * customer exception handling
     *
     * @param e DpoaException
     */
    @ResponseBody
    @ExceptionHandler(PoaException.class)
    public ResponseEntity<PoaException> poaException(PoaException e) {
        return ResponseEntity.status(e.getCode()).body(e);
    }




}
