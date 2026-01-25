package com.rbc.dowjones.repository.exception;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.rbc.dowjones.repository.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDto> handleBadRequest(BadRequestException ex) {
        log.error("BAD REQUEST | message={}", ex.getMessage(),ex);
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
                ErrorResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()));

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(ResourceNotFoundException ex) {
        log.error("RESOURCE NOT FOUND | message={}", ex.getMessage(),ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(new ErrorResponseDto(ex.getMessage(),
                        HttpStatus.NO_CONTENT.value(), LocalDateTime.now()));
    }

    @ExceptionHandler(CsvProcessingException.class)
    public ResponseEntity<ErrorResponseDto> handleCsvError(CsvProcessingException ex) {
        log.error("CSV PROCESSING ERROR | message={}", ex.getMessage(),ex);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErrorResponseDto(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY.value(), LocalDateTime.now()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleJsonError(HttpMessageNotReadableException ex){
        log.error("INVALID REQUEST BODY" ,ex);

        return ResponseEntity.badRequest().body(new ErrorResponseDto(
                "Invalid request body. Please check required fields and data type", HttpStatus.BAD_REQUEST.value(),LocalDateTime.now()));

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationErrors
            (MethodArgumentNotValidException ex){
        log.error("VALIDATION FAILED", ex);


        Map<String, String> errors= new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->{
                errors.put(error.getField(), error.getDefaultMessage());
        });

        ErrorResponseDto response=new ErrorResponseDto("Validation failed",HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        response.setErrors(errors);

        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolation(ConstraintViolationException ex){
        log.error("CONSTRAINT VIOLATION",ex);

        String message=ex.getConstraintViolations().iterator().next().getMessage();

        ErrorResponseDto error=new ErrorResponseDto(message,
                HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneric(Exception ex) {
        log.error("INTERNAL SERVER ERROR",ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
                ErrorResponseDto("Internal server error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now()));
    }
}
