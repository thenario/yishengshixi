package com.yisheng.Config;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T>  {
    String message;
    Integer code;
    T data;
    public static <T> Result<T> success(String message,Integer code,T data){
        return new Result<>(message,code,data);
    }

    public static <T> Result<T> error(Integer code,T data){
        return new Result<>("server error",code,data);
    }
}
