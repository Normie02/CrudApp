package org.example.crudapp.exception;

import org.example.crudapp.dto.CreateExceptionResponseDto;
import org.example.crudapp.dto.ValidationExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<CreateExceptionResponseDto> HandleDuplicateResourceFoundException(DuplicateResourceException re, HttpServletRequest request) {

        CreateExceptionResponseDto exResponse = new CreateExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                "Duplicate resource",
                request.getRequestURI()



        );

        return ResponseEntity.status(409).body(exResponse);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CreateExceptionResponseDto> HandleResourceNotFoundException(ResourceNotFoundException re,HttpServletRequest request) {
        CreateExceptionResponseDto exResponse = new CreateExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                "Resource not found",
                request.getRequestURI()



        );

        return ResponseEntity.status(404).body(exResponse);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CreateExceptionResponseDto> HandleRuntimeException(RuntimeException re,HttpServletRequest request) {
        CreateExceptionResponseDto exResponse = new CreateExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Runtime exception",
                request.getRequestURI()



        );

        return ResponseEntity.status(500).body(exResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CreateExceptionResponseDto> HandleGenericException(Exception re,HttpServletRequest request) {
        CreateExceptionResponseDto exResponse = new CreateExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Exception",
                request.getRequestURI()
        );

        return ResponseEntity.status(500).body(exResponse);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionResponseDto> HandleMethodArgumentNotValidException(MethodArgumentNotValidException re,HttpServletRequest request) {

        Map<String,String> fe = new HashMap<>();

        re.getBindingResult().getFieldErrors().forEach(error -> fe.put(error.getField(),error.getDefaultMessage()));
        ValidationExceptionResponseDto exResponse = new ValidationExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation error",
                request.getRequestURI(),
                fe
        );

        return ResponseEntity.badRequest().body(exResponse);
    }
}
