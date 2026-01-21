package com.rbc.dowjones.repository.exception;

public class CsvProcessingException extends RuntimeException{
    public CsvProcessingException(String message){
        super(message);
    }
}
