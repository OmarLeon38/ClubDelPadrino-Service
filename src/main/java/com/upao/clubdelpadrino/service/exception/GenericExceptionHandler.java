package com.upao.clubdelpadrino.service.exception;

import com.upao.clubdelpadrino.service.utils.GenericResponse;
import com.upao.clubdelpadrino.service.utils.Global;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class GenericExceptionHandler {
    @ExceptionHandler(Exception.class)
    public GenericResponse genericException(Exception ex) {
        return new GenericResponse("exception", -1, Global.OPERACION_ERRONEA, ex.getMessage());
    }
}