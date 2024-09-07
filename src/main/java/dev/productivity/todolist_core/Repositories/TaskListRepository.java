package dev.productivity.todolist_core.Repositories;

import dev.productivity.todolist_core.TaskList;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, Long> {

    Optional<TaskList> findByName(String name);
    void deleteByName(String name);


}
