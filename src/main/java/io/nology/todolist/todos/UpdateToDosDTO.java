package io.nology.todolist.todos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;

public class UpdateToDosDTO {
  @Length(min = 3)
  private String task;

  @Min(1)
  private Long categoryId;

  private Boolean isCompleted;

  public String getTask() {
    return task;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public Boolean getIsCompleted() {
    return this.isCompleted;
  }

  public void setTask(String task) {
    this.task = task;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public void setIsCompleted(Boolean isCompleted) {
    this.isCompleted = isCompleted;
  }

}
