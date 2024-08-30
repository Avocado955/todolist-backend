package io.nology.todolist.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.nology.todolist.common.exceptions.NotFoundException;
import io.nology.todolist.common.exceptions.ServiceValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> handleNotFoundException(NotFoundException exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ServiceValidationException.class)
  public ResponseEntity<ValidationErrors> handleServiceValidationException(ServiceValidationException exception) {
    return new ResponseEntity<ValidationErrors>(exception.getErrors(), HttpStatus.BAD_REQUEST);
  }

}
