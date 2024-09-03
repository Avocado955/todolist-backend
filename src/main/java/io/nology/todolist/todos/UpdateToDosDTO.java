package io.nology.todolist.todos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class UpdateToDosDTO {
  @Length(min = 3)
  private String task;

  @Min(1)
  private Long categoryId;

  @Max(1)
  private Integer isCompleted;

  public String getTask() {
    return task;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public Integer getIsCompleted() {
    return this.isCompleted;
  }

  public boolean getIsCompletedBoolean() {
    return this.isCompleted == 0 ? false : true;
  }

  public void setTask(String task) {
    this.task = task;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public void setIsCompleted(Integer isCompleted) {
    this.isCompleted = isCompleted;
  }

}
