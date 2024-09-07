package io.nology.todolist.todos;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import io.nology.todolist.category.Category;

public interface ToDosRepository extends JpaRepository<ToDo, Long> {
  public List<ToDo> findTodosWithCategory(Category category);
}
