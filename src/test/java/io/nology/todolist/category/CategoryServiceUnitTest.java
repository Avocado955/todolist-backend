package io.nology.todolist.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import io.nology.todolist.common.exceptions.ServiceValidationException;
import io.nology.todolist.todos.ToDo;

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

  @Test
  public void create_success() throws Exception {
    // given
    CreateCategoryDTO mockDTO = new CreateCategoryDTO();
    mockDTO.setName("test");
    Category mockCategory = new Category();

    // when
    when(repo.existsByName(mockDTO.getName())).thenReturn(false);
    when(repo.save(any(Category.class))).thenReturn(mockCategory);

    // then
    Category result = categoryService.create(mockDTO);
    assertNotNull(result);
    assertEquals(mockCategory, result);
    verify(repo).save(any(Category.class));
  }

  @Test
  public void create_existingCategory_failure() {
    // given
    CreateCategoryDTO mockDTO = new CreateCategoryDTO();
    mockDTO.setName("test");

    // when
    when(repo.existsByName(mockDTO.getName())).thenReturn(true);

    // then
    assertThrows(ServiceValidationException.class, () -> categoryService.create(mockDTO));
    verify(repo, never()).save(any());
  }

  @Test
  void deleteCategory_success() {
    // given
    Long id = 1L;
    Category mockCategory = new Category();
    Optional<Category> findResult = Optional.of(mockCategory);

    // when
    when(repo.findById(id)).thenReturn(findResult);

    // then
    Boolean result = categoryService.deleteById(id);
    assertEquals(true, result);
    verify(repo).delete(mockCategory);
  }

  @Test
  void deleteCategory_failure() {
    Long id = 1L;
    Optional<Category> findResult = Optional.empty();

    // when
    when(repo.findById(id)).thenReturn(findResult);

    // then
    Boolean result = categoryService.deleteById(id);
    assertEquals(false, result);
    verify(repo, never()).delete(any());
  }

}
