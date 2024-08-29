package io.nology.todolist.todos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateToDosDTO {
  @NotBlank
  @Length(min = 3)
  private String task;

  @NotNull
  @Min(1)
  private Long categoryId;

  @NotNull
  @Max(1)
  private Integer isCompleted;

  public String getTask() {
    return this.task;
  }

  public Long getCategoryId() {
    return this.categoryId;
  }

  public boolean getIsCompleted() {
    return this.isCompleted == 0 ? false : true;
  }

  public void setTask(String task) {
    this.task = task;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public void setIsCompleted(boolean isCompleted) {
    if (isCompleted == false) {
      this.isCompleted = 0;
    }
    this.isCompleted = 1;
  }

}
