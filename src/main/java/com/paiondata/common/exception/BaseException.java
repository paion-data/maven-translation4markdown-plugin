package com.paiondata.common.exception;

/**
 * 异常处理
 */
public class BaseException extends RuntimeException{
    public BaseException() {
    }

    public BaseException(String msg) {
        super(msg);
    }
}
