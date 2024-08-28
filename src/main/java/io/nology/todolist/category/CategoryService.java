package io.nology.todolist.category;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class CategoryService {
  @Autowired
  private CategoryRepository repo;

  public Category create(@Valid CreateCategoryDTO data) throws Exception {
    String formattedName = data.getName().trim().toLowerCase();
    if (repo.existsByName(formattedName)) {
      throw new Exception("The Category with name " + data.getName() + " already exists");
    }
    Category newCategory = new Category();
    newCategory.setName(formattedName);
    return this.repo.save(newCategory);
  }

  public List<Category> findAll() {
    return this.repo.findAll();
  }

  public Optional<Category> findById(Long categoryId) {
    return this.repo.findById(categoryId);
  }
}
