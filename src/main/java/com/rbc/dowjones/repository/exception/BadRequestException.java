package com.rbc.dowjones.repository.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}
