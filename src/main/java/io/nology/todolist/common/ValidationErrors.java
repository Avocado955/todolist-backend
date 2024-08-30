package io.nology.todolist.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ValidationErrors {
  private HashMap<String, ArrayList<String>> errors;

  public ValidationErrors() {
    this.errors = new HashMap<>();
  }

  public Boolean isEmpty() {
    return this.errors.isEmpty();
  }

  public Boolean hasErrors() {
    return !this.isEmpty();
  }

  public Map<String, ArrayList<String>> getErrors() {
    return Collections.unmodifiableMap(this.errors);
  }

  public void addError(String field, String errorMessage) {
    this.errors.computeIfAbsent(field, f -> new ArrayList<>()).add(errorMessage);
  }

  @Override
  public String toString() {
    return this.errors.toString();
  }
}
