package com.rbc.dowjones.repository.exception;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.rbc.dowjones.repository.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDto> handleBadRequest(BadRequestException ex) {
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
                ErrorResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()));

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(ex.getMessage(), HttpStatus.NO_CONTENT.value(), LocalDateTime.now()));
    }

    @ExceptionHandler(CsvProcessingException.class)
    public ResponseEntity<ErrorResponseDto> handleCsvError(CsvProcessingException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErrorResponseDto(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY.value(), LocalDateTime.now()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDto("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now()));
    }

    /*@ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleTypeMismatch(MethodArgumentTypeMismatchException exception){

        String message;
        if ("id".equals(exception.getName())){
            message="Sorry, character are not allowed here. ID must be a number";
        } else {
            message="Invalid request parameter";
        }
        ErrorResponseDto error=new ErrorResponseDto(message,HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return ResponseEntity.badRequest().body(error);

    }*/
}
