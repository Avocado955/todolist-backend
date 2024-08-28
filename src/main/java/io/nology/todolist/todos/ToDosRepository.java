package io.nology.todolist.todos;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDosRepository extends JpaRepository<ToDos, Long> {

}
