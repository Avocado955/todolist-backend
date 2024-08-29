package io.nology.todolist.todos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.nology.todolist.category.Category;
import io.nology.todolist.category.CategoryService;
import jakarta.validation.Valid;

@Service
public class ToDosService {

  @Autowired
  private ToDosRepository repo;

  @Autowired
  private CategoryService categoryService;

  public ToDo createToDo(@Valid CreateToDosDTO data) throws Exception {
    ToDo newToDo = new ToDo();
    newToDo.setTask(data.getTask().trim());
    newToDo.setIsCompleted(data.getIsCompleted());
    Optional<Category> categoryResult = this.categoryService.findById(data.getCategoryId());
    if (categoryResult.isEmpty()) {
      // Dont make a category here, return an error and throw it from the Controller
      throw new Exception("The category with name " + data.getCategoryId() + " does not exist");
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

    if (data.getTask() != null) {
      foundToDo.setTask(data.getTask().trim());
    }
    if (data.getIsCompleted() != null) {
      foundToDo.setIsCompleted(data.getIsCompletedBoolean());
    }
    if (data.getCategoryId() != null) {
      Optional<Category> categoryResult = this.categoryService.findById(data.getCategoryId());
      if (categoryResult.isEmpty()) {
        throw new Exception("The Category with id: " + data.getCategoryId() + " does not exist");
      } else {
        foundToDo.setCategory(categoryResult.get());
      }
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
