package io.nology.todolist.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;

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
        .statusCode(HttpStatus.OK.value());
  }
}
