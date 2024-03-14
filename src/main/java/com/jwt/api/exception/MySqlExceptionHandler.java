package com.jwt.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class MySqlExceptionHandler {
        @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ResponseBody
        public ResponseEntity<String> handleValidationExceptions(SQLIntegrityConstraintViolationException ex) {
            String msg = ex.getMessage();
            return ResponseEntity.badRequest().body(msg);
        }
}
