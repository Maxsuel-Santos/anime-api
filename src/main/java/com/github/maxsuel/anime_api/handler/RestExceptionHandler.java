package com.github.maxsuel.anime_api.handler;

import org.springframework.http.HttpHeaders;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.github.maxsuel.anime_api.exceptions.BadRequestException;
import com.github.maxsuel.anime_api.exceptions.BadRequestExceptionDetails;
import com.github.maxsuel.anime_api.exceptions.ExceptionDetails;
import com.github.maxsuel.anime_api.exceptions.ValidationExceptionDetails;

import jakarta.annotation.Nullable;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<BadRequestExceptionDetails> handleBadRequestException(BadRequestException bre) {
                return new ResponseEntity<>(
                                BadRequestExceptionDetails.builder()
                                                .timestamp(LocalDateTime.now())
                                                .status(HttpStatus.BAD_REQUEST.value())
                                                .title("Bad Request Exception, Check the Documentation.")
                                                .details(bre.getMessage())
                                                .developerMessage(bre.getClass().getName())
                                                .build(),
                                HttpStatus.BAD_REQUEST);
        }

        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(
                MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request
        ) {
                List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

                String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
                String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage)
                                .collect(Collectors.joining(", "));

                return new ResponseEntity<>(
                                ValidationExceptionDetails.builder()
                                                .timestamp(LocalDateTime.now())
                                                .status(HttpStatus.BAD_REQUEST.value())
                                                .title("Bad Request Exception, Check the Documentation.")
                                                .details("Check the field(s) error")
                                                .developerMessage(exception.getClass().getName())
                                                .fields(fields)
                                                .fieldsMessage(fieldsMessage)
                                                .build(),
                                HttpStatus.BAD_REQUEST);
        }

        @Override
        protected ResponseEntity<Object> handleExceptionInternal(
                        Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode status,
                        WebRequest request) {

                // Proteção contra NullPointerException caso não haja causa
                String title = (ex.getCause() != null) ? ex.getCause().getMessage() : "Internal Server Error";

                ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                                .timestamp(LocalDateTime.now())
                                .status(status.value())
                                .title(title)
                                .details(ex.getMessage())
                                .developerMessage(ex.getClass().getName())
                                .build();

                return new ResponseEntity<>(exceptionDetails, headers, status);
        }

}
