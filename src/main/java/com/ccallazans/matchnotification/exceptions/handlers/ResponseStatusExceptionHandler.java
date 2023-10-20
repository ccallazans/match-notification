package com.ccallazans.matchnotification.exceptions.handlers;

import com.ccallazans.matchnotification.exceptions.NotFoundException;
import com.ccallazans.matchnotification.exceptions.ValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ResponseStatusExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<Object> handleValidationException(Exception ex, WebRequest request) {
        ReponseError message = new ReponseError(ex.getMessage());

        return new ResponseEntity<Object>(message, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Void> handleNotFoundException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}