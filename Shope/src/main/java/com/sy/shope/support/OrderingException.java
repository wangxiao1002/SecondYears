package com.sy.shope.support;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: wang xiao
 * @description: 订单异常
 * @date: Created in 11:46 2020/6/2
 */
@Setter
@Getter
public class OrderingException extends RuntimeException {

    private String code;

    private String message;


    public OrderingException(String code,String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public OrderingException(String message) {
        this.code = "500";
        this.message = message;
    }


}
