package dev.productivity.todolist_core.Services;
import dev.productivity.todolist_core.Repositories.TaskListRepository;
import dev.productivity.todolist_core.TaskList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;




@Service
public class TaskListService {

    private final TaskListRepository taskListRepository;

    @Autowired
    public TaskListService(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    public TaskList saveTaskList(TaskList taskList) {
        return taskListRepository.save(taskList);
    }

    public Optional<TaskList> findTaskListById(Long id) {
        return taskListRepository.findById(id);
    }

    public List<TaskList> findAllTaskLists() {
        return taskListRepository.findAll();
    }

    public void deleteTaskListById(Long id) {
        taskListRepository.deleteById(id);
    }

    public Optional<TaskList> findTaskListByName(String name) {
        return taskListRepository.findByName(name);
    }

    public void deleteTaskListByName(String name) {
        taskListRepository.deleteByName(name);
    }
}

