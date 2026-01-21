package com.rbc.dowjones.repository.exception;

import com.rbc.dowjones.repository.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDto> handleBadRequest(BadRequestException ex) {
        return new ResponseEntity<>(
                new ErrorResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(
                new ErrorResponseDto(ex.getMessage(), HttpStatus.NOT_FOUND.value()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(CsvProcessingException.class)
    public ResponseEntity<ErrorResponseDto> handleCsvError(CsvProcessingException ex) {
        return new ResponseEntity<>(
                new ErrorResponseDto(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY.value()),
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneric(Exception ex) {
        return new ResponseEntity<>(
                new ErrorResponseDto("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
