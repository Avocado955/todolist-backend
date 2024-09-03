package io.nology.todolist.category;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class CategoryServiceUnitTest {
  @Mock
  private CategoryRepository repo;

  @InjectMocks
  @Spy
  private CategoryService categoryService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void findAll() {
    categoryService.findAll();
    verify(repo).findAll();
  }

  @Test
  public void findById() {
    Long categoryId = 1L;
    categoryService.findById(categoryId);
    verify(repo).findById(categoryId);
  }

}
