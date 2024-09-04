package io.nology.todolist.todo;

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

import io.nology.todolist.category.Category;
import io.nology.todolist.category.CategoryService;
import io.nology.todolist.common.exceptions.ServiceValidationException;
import io.nology.todolist.todos.CreateToDosDTO;
import io.nology.todolist.todos.ToDo;
import io.nology.todolist.todos.ToDosRepository;
import io.nology.todolist.todos.ToDosService;
import io.nology.todolist.todos.UpdateToDosDTO;

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

  @Test
  void createToDo_success() throws Exception {
    // given
    CreateToDosDTO data = new CreateToDosDTO();
    data.setCategoryId(1L);
    data.setTask("This is the task");
    data.setIsCompleted(false);
    Category mockCategory = new Category();
    mockCategory.setName("test");
    Optional<Category> result = Optional.of(mockCategory);

    ToDo mockTodo = new ToDo();
    mockTodo.setCategory(mockCategory);
    mockTodo.setIsCompleted(false);
    mockTodo.setTask("This is the task");

    // when
    when(categoryService.findById(1L)).thenReturn(result);
    when(repo.save(any(ToDo.class))).thenReturn(mockTodo);

    // then
    ToDo toDoResult = service.createToDo(data);
    assertNotNull(toDoResult);
    assertEquals(mockTodo, toDoResult);
    verify(repo).save(any(ToDo.class)); // Cant verify due to creating the new ToDo
    // inside of the actual function (not being mocked)
  }

  @Test
  void deleteToDo_success() {
    // given
    Long id = 1L;
    ToDo mockToDo = new ToDo();
    Optional<ToDo> findResult = Optional.of(mockToDo);

    // when
    when(repo.findById(id)).thenReturn(findResult);

    // then
    Boolean result = service.deleteById(id);
    assertEquals(true, result);
    verify(repo).delete(mockToDo);
  }

  @Test
  void deleteToDo_fails() {
    // given
    Long id = 1L;
    Optional<ToDo> findResult = Optional.empty();

    // when
    when(repo.findById(id)).thenReturn(findResult);

    // then
    Boolean result = service.deleteById(id);
    assertEquals(false, result);
    verify(repo, never()).delete(any());
  }

  @Test
  void updateToDoById_fails_whenToDoIdInvalid() throws Exception {
    // given
    Long id = 1L;
    UpdateToDosDTO data = new UpdateToDosDTO();
    Optional<ToDo> findResult = Optional.empty();

    // when
    when(repo.findById(id)).thenReturn(findResult);

    // then
    Optional<ToDo> result = service.updateToDoById(id, data);
    assertEquals(findResult, result);
    verify(repo, never()).save(any());
  }

  @Test
  void updateToDoById_fails_whenCategoryIdInvalid() throws Exception {
    // given
    Long id = 1L;
    UpdateToDosDTO data = new UpdateToDosDTO();
    data.setCategoryId(id);
    ToDo mockToDo = new ToDo();
    Optional<ToDo> toDoFindResult = Optional.of(mockToDo);
    Optional<Category> categoryFindResult = Optional.empty();

    // when
    when(repo.findById(id)).thenReturn(toDoFindResult);
    when(categoryService.findById(id)).thenReturn(categoryFindResult);

    // then
    assertThrows(ServiceValidationException.class, () -> service.updateToDoById(id, data));
    verify(repo, never()).save(any());
  }

  @Test
  void updateToDoById_success() throws Exception {
    // given
    Long id = 1L;
    UpdateToDosDTO data = new UpdateToDosDTO();
    data.setCategoryId(id);
    data.setIsCompleted(0);
    data.setTask("testing");
    ToDo mockToDo = new ToDo();
    mockToDo.setTask("something like this");
    Optional<ToDo> toDoFindResult = Optional.of(mockToDo);
    Category mockCategory = new Category();
    mockCategory.setName("code");
    Optional<Category> categoryFindResult = Optional.of(mockCategory);

    // when
    when(repo.findById(id)).thenReturn(toDoFindResult);
    when(categoryService.findById(id)).thenReturn(categoryFindResult);
    when(repo.save(any(ToDo.class))).thenReturn(mockToDo);

    // then
    Optional<ToDo> result = service.updateToDoById(id, data);
    assertEquals(toDoFindResult, result);
    verify(repo).save(mockToDo);
  }
}
