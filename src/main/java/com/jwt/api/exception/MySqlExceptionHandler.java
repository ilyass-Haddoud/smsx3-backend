package com.jwt.api.exception;

import org.springframework.dao.DataIntegrityViolationException;
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
        @ExceptionHandler(DataIntegrityViolationException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ResponseBody
        public ResponseEntity<String> handleValidationExceptions(DataIntegrityViolationException ex) {
            String msg = ex.getMessage();
            if(msg.contains("duplicate"))
            {
                return ResponseEntity.badRequest().body("cannot insert duplicated values");
            }
            if(msg.contains("nulls"))
            {
                return ResponseEntity.badRequest().body("cannot insert null values");
            }
            return ResponseEntity.badRequest().body(msg);
        }
}
