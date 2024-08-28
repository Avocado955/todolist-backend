package io.nology.todolist.todos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateToDosDTO {
  @NotBlank
  @Length(min = 3)
  private String task;

  @NotBlank
  private String categoryName;

  @NotNull
  private Integer isCompleted;

  public String getTask() {
    return this.task;
  }

  public String getCategoryName() {
    return this.categoryName;
  }

  public Integer getIsCompleted() {
    return this.isCompleted;
  }

  public void setTask(String task) {
    this.task = task;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public void setIsCompleted(Integer isCompleted) {
    this.isCompleted = isCompleted;
  }

}
