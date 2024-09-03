package io.nology.todolist.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import io.nology.todolist.common.exceptions.ServiceValidationException;

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

}
