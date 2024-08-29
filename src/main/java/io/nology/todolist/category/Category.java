package io.nology.todolist.category;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.nology.todolist.common.BaseEntity;
import io.nology.todolist.todos.ToDo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

  @Column
  private String name;

  @OneToMany(mappedBy = "category")
  @JsonIgnoreProperties("category")
  private List<ToDo> todos;

  public Category() {
  }

  public String getName() {
    return name;
  }

  public List<ToDo> getTodos() {
    return todos;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setTodos(List<ToDo> todos) {
    this.todos = todos;
  }

  @Override
  public String toString() {
    return "Category [name=" + this.name + ", todos=" + this.todos + "]";
  }

}
