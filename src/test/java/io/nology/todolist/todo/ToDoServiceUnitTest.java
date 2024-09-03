package io.nology.todolist.todo;

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

import io.nology.todolist.category.Category;
import io.nology.todolist.category.CategoryService;
import io.nology.todolist.common.exceptions.ServiceValidationException;
import io.nology.todolist.todos.CreateToDosDTO;
import io.nology.todolist.todos.ToDosRepository;
import io.nology.todolist.todos.ToDosService;

public class ToDoServiceUnitTest {

  @InjectMocks
  @Spy
  private ToDosService service;

  @Mock
  private ToDosRepository repo;

  @Mock
  private CategoryService categoryService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createToDo_fails_whenCategoryIdInvalid() {
    // given
    CreateToDosDTO data = new CreateToDosDTO();
    data.setCategoryId(1L);
    data.setTask("This is the task");
    data.setIsCompleted(false);
    Optional<Category> result = Optional.empty();

    // when
    when(categoryService.findById(1L)).thenReturn(result);

    // then
    assertThrows(ServiceValidationException.class, () -> service.createToDo(data));

    verify(repo, never()).save(any());
  }

}
