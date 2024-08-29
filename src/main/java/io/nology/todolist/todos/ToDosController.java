package io.nology.todolist.todos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("todos")
public class ToDosController {
  @Autowired
  private ToDosService toDosService;

  @PostMapping()
  public ResponseEntity<ToDos> createToDo(@Valid @RequestBody CreateToDosDTO data) throws Exception {
    ToDos createdToDo = this.toDosService.createToDo(data);
    return new ResponseEntity<>(createdToDo, HttpStatus.CREATED);
  }

  @GetMapping()
  public ResponseEntity<List<ToDos>> getAllTodos() {
    List<ToDos> allToDos = this.toDosService.findAll();
    return new ResponseEntity<>(allToDos, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ToDos> getToDoById(@PathVariable Long id) throws Exception {
    Optional<ToDos> result = this.toDosService.findById(id);
    ToDos foundPost = result.orElseThrow(() -> new Exception("Todo with id: " + id + " not found"));
    return new ResponseEntity<>(foundPost, HttpStatus.OK);
  }

}
