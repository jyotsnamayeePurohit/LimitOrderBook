package com.rmb.limitOrderBook.exception;

public class NoSuchElementException  extends  RuntimeException{
    public NoSuchElementException(String message,Throwable throwable){
        super(message,throwable);
    }
}
