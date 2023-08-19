package com.easybbs.aop;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerException {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleInvalidException(MethodArgumentNotValidException e) {
        return e.getBindingResult().getFieldError().getDefaultMessage();
    }
}
