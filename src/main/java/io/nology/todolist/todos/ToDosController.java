package io.nology.todolist.todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

}
