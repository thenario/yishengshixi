package com.yisheng.config;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessError.class)
    public Result<Void> handleBusinessError(BusinessError error){
        return new Result<>(error.getMessage(),error.getCode(),null);
    }
    //目前没有办法在这里自定义code，但是异常通常不会被这个捕获
    @ExceptionHandler(Exception.class)
    public Result<Void> handlException(Exception error){
        return new Result<>(error.getMessage(),404,null);
    }
}
