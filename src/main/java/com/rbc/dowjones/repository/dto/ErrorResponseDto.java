package com.rbc.dowjones.repository.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponseDto {

    private String message;
    private int status;
    private LocalDateTime timestamp;
    private Map<String, String> errors;

    public ErrorResponseDto(String message, int status, LocalDateTime timestamp){
        this.message=message;
        this.status=status;
        this.timestamp=LocalDateTime.now();
    }

    public ErrorResponseDto(String message, int status, LocalDateTime timestamp, Map<String, String> errors){

        this.message=message;
        this.status=status;
        this.timestamp=timestamp;
        this.errors=errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

}
