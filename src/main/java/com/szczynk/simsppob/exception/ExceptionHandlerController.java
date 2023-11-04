package com.szczynk.simsppob.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.szczynk.simsppob.model.response.WebResponse;

import jakarta.validation.ConstraintViolationException;

import java.util.Objects;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<WebResponse<String>> httpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException exception) {

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(
                        WebResponse.<String>builder()
                                .status(102)
                                .message(exception.getMessage())
                                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<WebResponse<String>> methodArgumentNotValidException(
            MethodArgumentNotValidException exception) {

        String errorMessage = Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        WebResponse.<String>builder()
                                .status(102)
                                .message(errorMessage)
                                .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<WebResponse<String>> constraintViolationException(
            ConstraintViolationException exception) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(WebResponse.<String>builder()
                        .status(102)
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(BadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<WebResponse<String>> badBadRequest(
            BadRequest exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        WebResponse.<String>builder()
                                .status(102)
                                .message(exception.getMessage())
                                .build());
    }

    @ExceptionHandler(ResourceNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<WebResponse<String>> resourceNotFoundException(
            ResourceNotFound exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        WebResponse.<String>builder()
                                .message(exception.getMessage())
                                .build());
    }

    @ExceptionHandler(ResourceAlreadyExisted.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<WebResponse<String>> resourceAResourceAlreadyExisted(
            ResourceAlreadyExisted exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        WebResponse.<String>builder()
                                .message(exception.getMessage())
                                .build());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<String>> apiException(
            ResponseStatusException exception) {
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(
                        WebResponse
                                .<String>builder()
                                .message(exception.getMessage())
                                .build());
    }
}
