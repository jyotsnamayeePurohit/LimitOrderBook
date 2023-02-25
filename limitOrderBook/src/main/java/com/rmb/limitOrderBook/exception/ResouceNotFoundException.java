package com.rmb.limitOrderBook.exception;

public class ResouceNotFoundException extends  RuntimeException{

    public ResouceNotFoundException(String message){
        super(message);
    }

    public ResouceNotFoundException(String message,Throwable throwable){
        super(message,throwable);
    }

}
