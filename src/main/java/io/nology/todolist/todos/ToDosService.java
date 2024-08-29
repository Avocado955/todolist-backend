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

  public ToDos createToDo(@Valid CreateToDosDTO data) throws Exception {
    ToDos newToDo = new ToDos();
    newToDo.setTask(data.getTask());
    newToDo.setIsCompleted(data.getIsCompleted());
    Optional<Category> categoryResult = this.categoryService.findById(data.getCategoryId());
    if (categoryResult.isEmpty()) {
      // Dont make a category here, return an error and throw it from the Controller
      throw new Exception("The category with name " + data.getCategoryId() + " does not exist");
    }
    newToDo.setCategory(categoryResult.get());
    return this.repo.save(newToDo);
  }

  public List<ToDos> findAll() {
    return this.repo.findAll();
  }

  public Optional<ToDos> findById(Long id) {
    return this.repo.findById(id);
  }

}
