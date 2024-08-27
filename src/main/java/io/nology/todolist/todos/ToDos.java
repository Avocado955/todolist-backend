package io.nology.todolist.todos;

import io.nology.todolist.common.BaseEntity;
import jakarta.persistence.Column;

public class ToDos extends BaseEntity {

  // Constructor
  public ToDos() {
  }

  // Properties
  @Column
  private String task;

  // @Column
  // private String title;

  // @Column
  // private String description;

  @Column // Change this eventually to a many to one (one category can have many ToDos)
  private String category;

  @Column
  private Boolean isCompleted;

  public String getTask() {
    return task;
  }

  // public String getTitle() {
  // return title;
  // }

  // public String getDescription() {
  // return description;
  // }

  public String getCategory() {
    return category;
  }

  public Boolean getIsCompleted() {
    return isCompleted;
  }

  public void setTask(String task) {
    this.task = task;
  }

  // public void setTitle(String title) {
  // this.title = title;
  // }

  // public void setDescription(String description) {
  // this.description = description;
  // }

  public void setCategory(String category) {
    this.category = category;
  }

  public void setIsCompleted(Boolean isCompleted) {
    this.isCompleted = isCompleted;
  }

}
