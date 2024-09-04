package io.nology.todolist.todo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import io.nology.todolist.category.Category;
import io.nology.todolist.category.CategoryRepository;
import io.nology.todolist.todos.CreateToDosDTO;
import io.nology.todolist.todos.ToDo;
import io.nology.todolist.todos.ToDosRepository;
import io.nology.todolist.todos.UpdateToDosDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ToDoEndToEndTest {

  @LocalServerPort
  private int port;

  @Autowired
  private ToDosRepository toDosRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @BeforeEach
  public void setUp() {
    RestAssured.port = port;
    toDosRepository.deleteAll();
    categoryRepository.deleteAll();

    Category category1 = new Category();
    category1.setName("code");
    categoryRepository.save(category1);

    Category category2 = new Category();
    category2.setName("art");
    categoryRepository.save(category2);

    ToDo todo1 = new ToDo();
    todo1.setCategory(category1);
    todo1.setTask("test task 1");
    todo1.setIsCompleted(false);
    toDosRepository.save(todo1);

    ToDo todo2 = new ToDo();
    todo2.setCategory(category2);
    todo2.setTask("some other task");
    todo2.setIsCompleted(false);
    toDosRepository.save(todo2);

  }

  @Test
  public void getAllTodos() {
    given()
        .when()
        .get("/todos")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("$", hasSize(2))
        .body("task", hasItems("test task 1", "some other task"));
  }

  @Test
  public void createToDo_success() {
    CreateToDosDTO data = new CreateToDosDTO();
    data.setCategoryId(1L);
    data.setTask("clean the kitchen");
    data.setIsCompleted(false);

    given()
        .contentType(ContentType.JSON)
        .body(data)
        .when()
        .post("/todos")
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .body("task", equalTo("clean the kitchen"))
        .body("id", notNullValue());

    // Check if it is in the find All as well
    given()
        .when()
        .get("/todos")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("$", hasSize(3))
        .body("task", hasItems("test task 1", "some other task", "clean the kitchen"));
  }

  @Test
  public void createToDo_failure() {
    CreateToDosDTO data = new CreateToDosDTO();
    data.setCategoryId(3L);
    data.setTask("clean the kitchen");
    data.setIsCompleted(false);

    given()
        .contentType(ContentType.JSON)
        .body(data)
        .when()
        .post("/todos")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("errors.category[0]", equalTo("Category with id 3 does not exist"));
  }

  @Test
  public void deleteById_success() {
    given()
        .when()
        .delete("/todos/1")
        .then()
        .statusCode(HttpStatus.NO_CONTENT.value());
  }

  @Test
  public void deleteById_failure() {
    given()
        .when()
        .delete("/todos/3")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .contentType(ContentType.TEXT)
        .body(containsString("Could not find ToDo with id: 3"));
  }

  @Test
  public void updateToDoById_success() {
    UpdateToDosDTO data = new UpdateToDosDTO();
    data.setTask("new updated task name");
    data.setIsCompleted(1);
    data.setCategoryId(1L);
    given()
        .contentType(ContentType.JSON)
        .body(data)
        .when()
        .patch("/todos/2")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("task", equalTo("new updated task name"))
        .body("isCompleted", equalTo(true))
        .body("id", notNullValue());
  }

  @Test
  public void updateToDoById_fails_invalidId() {
    UpdateToDosDTO data = new UpdateToDosDTO();
    data.setIsCompleted(0);
    given()
        .contentType(ContentType.JSON)
        .body(data)
        .when()
        .patch("/todos/3")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .contentType(ContentType.TEXT)
        .body(containsString("ToDo not found with id: 3"));
  }

  @Test
  public void updateToDoById_fails_invalidCategoryId() {
    UpdateToDosDTO data = new UpdateToDosDTO();
    data.setIsCompleted(0);
    data.setCategoryId(3L);
    given()
        .contentType(ContentType.JSON)
        .body(data)
        .when()
        .patch("/todos/2")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("errors.category[0]", equalTo("Category with id 3 does not exist"));
  }

}
