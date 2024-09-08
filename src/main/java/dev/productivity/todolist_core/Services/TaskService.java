package dev.productivity.todolist_core.Services;

import dev.productivity.todolist_core.Repositories.TasksRepository;
import dev.productivity.todolist_core.Task;
import dev.productivity.todolist_core.TaskList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private final TasksRepository tasksRepository;

    @Autowired
    public TaskService(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public Task saveTask(Task task) {
        return tasksRepository.save(task);
    }

    public Optional<Task> findTaskById(Long id) {
        return tasksRepository.findById(id);
    }

    public List<Task> findAllTasks() {
        return tasksRepository.findAll();
    }

    public void deleteTaskById(Long id) {
        tasksRepository.deleteById(id);
    }

    public List<Task> findTasksByTaskList(TaskList taskList) {
        return tasksRepository.findByTaskList(taskList);
    }

    public void deleteTaskByTaskList(TaskList taskList) {
        List<Task> tasks = findTasksByTaskList(taskList);
        tasksRepository.deleteAll(tasks);
    }

    public void removeTagFromAllTasks(Long tagId) {
        List<Task> tasks = findAllTasks();
        for (Task task : tasks) {
            task.getTags().removeIf(tag -> tag.getId().equals(tagId));
            saveTask(task);
        }
    }
}




