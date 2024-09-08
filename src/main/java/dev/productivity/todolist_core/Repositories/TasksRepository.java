package dev.productivity.todolist_core.Repositories;

import dev.productivity.todolist_core.Task;
import dev.productivity.todolist_core.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Task, Long> {

    List<Task> findByTaskList(TaskList taskList);


}
