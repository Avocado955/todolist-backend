package io.nology.todolist.todos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.nology.todolist.category.Category;
import io.nology.todolist.category.CategoryService;
import io.nology.todolist.common.ValidationErrors;
import io.nology.todolist.common.exceptions.ServiceValidationException;
import jakarta.validation.Valid;

@Service
public class ToDosService {

  @Autowired
  private ToDosRepository repo;

  @Autowired
  private CategoryService categoryService;

  public ToDo createToDo(@Valid CreateToDosDTO data) throws Exception {
    ValidationErrors errors = new ValidationErrors();
    ToDo newToDo = new ToDo();
    newToDo.setTask(data.getTask().trim());
    newToDo.setIsCompleted(data.getIsCompleted());
    Optional<Category> categoryResult = this.categoryService.findById(data.getCategoryId());
    if (categoryResult.isEmpty()) {
      errors.addError("category", String.format("Category with id %s does not exist", data.getCategoryId()));
    }

    if (errors.hasErrors()) {
      throw new ServiceValidationException(errors);
    }
    newToDo.setCategory(categoryResult.get());
    return this.repo.save(newToDo);
  }

  public List<ToDo> findAll() {
    return this.repo.findAll();
  }

  public Optional<ToDo> findById(Long id) {
    return this.repo.findById(id);
  }

  public Optional<ToDo> updateToDoById(Long id, @Valid UpdateToDosDTO data) throws Exception {
    Optional<ToDo> result = this.findById(id);
    if (result.isEmpty()) {
      return result;
    }
    ToDo foundToDo = result.get();
    ValidationErrors errors = new ValidationErrors();
    if (data.getTask() != null) {
      foundToDo.setTask(data.getTask().trim());
    }
    if (data.getIsCompleted() != null) {
      foundToDo.setIsCompleted(data.getIsCompletedBoolean());
    }
    if (data.getCategoryId() != null) {
      Optional<Category> categoryResult = this.categoryService.findById(data.getCategoryId());
      if (categoryResult.isEmpty()) {
        errors.addError("category", String.format("Category with id %s fors not exist", data.getCategoryId()));
      } else {
        foundToDo.setCategory(categoryResult.get());
      }
    }
    if (errors.hasErrors()) {
      throw new ServiceValidationException(errors);
    }
    ToDo updateToDo = this.repo.save(foundToDo);
    return Optional.of(updateToDo);
  }

  public boolean deleteById(Long id) {
    Optional<ToDo> result = this.findById(id);
    if (result.isEmpty()) {
      return false;
    }
    this.repo.delete(result.get());
    return true;
  }
}
