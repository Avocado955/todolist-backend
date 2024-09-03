package io.nology.todolist.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CategoryEndToEndTest {
  @LocalServerPort
  private int port;

  @Autowired
  private CategoryRepository categoryRepository;

  @BeforeEach
  public void setUp() {
    RestAssured.port = port;
    categoryRepository.deleteAll();
    Category category1 = new Category();
    category1.setName("code");
    categoryRepository.save(category1);

    Category category2 = new Category();
    category2.setName("art");
    categoryRepository.save(category2);
  }

  @Test
  public void getAllCategories() {
    given()
        .when()
        .get("/categories")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("$", hasSize(2))
        .body("name", hasItems("code", "art"));
        //Schema causes an error with the Github actions testing
        // This schema is for when we get an array of objects back
        // .body(matchesJsonSchemaInClasspath("io/nology/todolist/category/schemas/categories-schema.json"))
  }

  @Test
  public void createCategory_success() {
    // Set up a DTO
    CreateCategoryDTO data = new CreateCategoryDTO();
    data.setName("new category");
    given()
        .contentType(ContentType.JSON)
        .body(data)
        .when()
        .post("/categories")
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .body("name", equalTo("new category"))
        .body("id", notNullValue());
        // This schema is for when we get only one object back which is the Category
        // .body(matchesJsonSchemaInClasspath("io/nology/todolist/category/schemas/category-schema.json"));

    // Check this category is in find all
    given()
        .when()
        .get("/categories")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("$", hasSize(3))
        .body("name", hasItems("code", "art", "new category"));
  }

  @Test
  public void createCategory_failure() {
    // set up a DTO
    CreateCategoryDTO data = new CreateCategoryDTO();
    data.setName("code");
    given()
        .contentType(ContentType.JSON)
        .body(data)
        .when()
        .post("/categories")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("errors.category[0]", equalTo("Category with name code already exists"));
  }
}