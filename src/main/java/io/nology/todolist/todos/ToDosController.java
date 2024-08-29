package io.nology.todolist.todos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("todos")
public class ToDosController {
  @Autowired
  private ToDosService toDosService;

  @PostMapping()
  public ResponseEntity<ToDo> createToDo(@Valid @RequestBody CreateToDosDTO data) throws Exception {
    ToDo createdToDo = this.toDosService.createToDo(data);
    return new ResponseEntity<>(createdToDo, HttpStatus.CREATED);
  }

  @GetMapping()
  public ResponseEntity<List<ToDo>> getAllTodos() {
    List<ToDo> allToDos = this.toDosService.findAll();
    return new ResponseEntity<>(allToDos, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ToDo> getToDoById(@PathVariable Long id) throws Exception {
    Optional<ToDo> result = this.toDosService.findById(id);
    ToDo foundPost = result.orElseThrow(() -> new Exception("Todo with id: " + id + " not found"));
    return new ResponseEntity<>(foundPost, HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ToDo> updateToDoById(@PathVariable Long id, @Valid @RequestBody UpdateToDosDTO data)
      throws Exception {
    Optional<ToDo> result = this.toDosService.updateToDoById(id, data);
    ToDo foundToDo = result.orElseThrow(() -> new Exception("ToDo not found with id: " + id));
    return new ResponseEntity<>(foundToDo, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteToDoById(@PathVariable Long id) throws Exception {
    boolean deleteSuccessful = this.toDosService.deleteById(id);
    if (!deleteSuccessful) {
      throw new Exception("Could not find ToDo with id: " + id);
    }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
