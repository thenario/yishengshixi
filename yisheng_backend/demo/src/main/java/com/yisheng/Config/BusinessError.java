package com.yisheng.Config;


public class BusinessError extends Exception {
    Integer code;
    public BusinessError(String message,Integer code){
        super(message);
        this.code = code;    }
}
