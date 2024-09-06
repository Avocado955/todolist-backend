package io.nology.todolist.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.nology.todolist.common.exceptions.NotFoundException;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("categories")
public class CategoryController {
  @Autowired
  private CategoryService categoryService;

  @PostMapping()
  public ResponseEntity<Category> createCategory(@Valid @RequestBody CreateCategoryDTO data) throws Exception {
    Category newCategory = this.categoryService.create(data);
    return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
  }

  @GetMapping()
  public ResponseEntity<List<Category>> getAllCategories() {
    List<Category> allCategories = this.categoryService.findAll();
    return new ResponseEntity<>(allCategories, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) throws Exception {
    boolean deleteSuccessful = this.categoryService.deleteById(id);
    if (!deleteSuccessful) {
      throw new NotFoundException("Could not find Category with id: " + id);
    }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
