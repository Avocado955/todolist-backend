package io.nology.todolist.todos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.nology.todolist.category.Category;
import io.nology.todolist.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "todos")
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

  @ManyToOne
  @JoinColumn(name = "category_id")
  @JsonIgnoreProperties("todos") // Change this eventually to a many to one (one category can have many ToDos)
  private Category category;

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

  public Category getCategory() {
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

  public void setCategory(Category category) {
    this.category = category;
  }

  public void setIsCompleted(Boolean isCompleted) {
    this.isCompleted = isCompleted;
  }

}
