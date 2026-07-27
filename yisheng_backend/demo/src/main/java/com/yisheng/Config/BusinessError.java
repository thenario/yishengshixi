package com.yisheng.Config;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
public class BusinessError extends Exception {
    String message;
    Integer code;
    public BusinessError(String message,Integer code){
        super();
        this.code = code;
        this.message = message;
    }
}
