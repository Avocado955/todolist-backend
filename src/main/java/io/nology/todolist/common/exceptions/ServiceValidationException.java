package io.nology.todolist.common.exceptions;

import io.nology.todolist.common.ValidationErrors;

public class ServiceValidationException extends Exception {
  private ValidationErrors errors;

  public ServiceValidationException(ValidationErrors errors) {
    this.errors = errors;
  }

  public ValidationErrors getErrors() {
    return this.errors;
  }
}
