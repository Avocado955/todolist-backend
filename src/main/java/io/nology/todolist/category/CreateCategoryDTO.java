package io.nology.todolist.category;

import jakarta.validation.constraints.NotBlank;

public class CreateCategoryDTO {
  @NotBlank
  private String name;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
