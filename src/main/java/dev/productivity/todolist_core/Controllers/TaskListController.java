package dev.productivity.todolist_core.Controllers;

import dev.productivity.todolist_core.Services.TaskListService;
import dev.productivity.todolist_core.Services.TaskService;
import dev.productivity.todolist_core.TaskList;
import dev.productivity.todolist_core.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasklists")
public class TaskListController {

    private final TaskListService taskListService;
    private final TaskService taskService;

    @Autowired
    public TaskListController(TaskListService taskListService, TaskService taskService) {
        this.taskListService = taskListService;
        this.taskService = taskService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskList> createTaskList(@RequestBody TaskList taskList) {
        TaskList savedTaskList = taskListService.saveTaskList(taskList);
        return ResponseEntity.ok(savedTaskList);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<TaskList>> getTaskListById(@PathVariable Long id) {
        Optional<TaskList> taskList = taskListService.findTaskListById(id);
        return ResponseEntity.ok(taskList);
    }

    @GetMapping
    public ResponseEntity<List<TaskList>> getAllTaskLists() {
        List<TaskList> taskLists = taskListService.findAllTaskLists();
        return ResponseEntity.ok(taskLists);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteTaskListById(@PathVariable Long id) {
        taskListService.deleteTaskListById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<TaskList>> getTaskListByName(@PathVariable String name) {
        Optional<TaskList> taskList = taskListService.findTaskListByName(name);
        return ResponseEntity.ok(taskList);
    }

    @DeleteMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteTaskListByName(@PathVariable String name) {
        taskListService.deleteTaskListByName(name);
        return ResponseEntity.noContent().build();
    }

    // Task endpoints
    @PostMapping(value = "/{taskListId}/tasks", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> createTask(@PathVariable Long taskListId, @RequestBody Task task) {
        // Fetch the TaskList by ID to ensure it exists
        Optional<TaskList> taskListOptional = taskListService.findTaskListById(taskListId);
        if (taskListOptional.isPresent()) {
            TaskList taskList = taskListOptional.get();
            task.setTaskList(taskList);  // Associate task with task list
            Task savedTask = taskService.saveTask(task);
            return ResponseEntity.ok(savedTask);
        } else {
            return ResponseEntity.notFound().build();  // TaskList not found
        }
    }

    @GetMapping(value = "/{taskListId}/tasks/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Task>> getTaskById(@PathVariable Long taskListId, @PathVariable Long taskId) {
        // Fetch the Task to ensure it belongs to the specified TaskList
        Optional<Task> taskOptional = taskService.findTaskById(taskId);
        if (taskOptional.isPresent() && taskOptional.get().getTaskList().getId().equals(taskListId)) {
            return ResponseEntity.ok(taskOptional);
        } else {
            return ResponseEntity.notFound().build();  // Task not found or doesn't belong to the specified TaskList
        }
    }

    @GetMapping(value = "/{taskListId}/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Task>> getAllTasks(@PathVariable Long taskListId) {
        // Fetch tasks for the specific TaskListId
        Optional<TaskList> taskListOptional = taskListService.findTaskListById(taskListId);
        if (taskListOptional.isPresent()) {
            List<Task> tasks = taskService.findTasksByTaskList(taskListOptional.get());
            return ResponseEntity.ok(tasks);
        } else {
            return ResponseEntity.notFound().build();  // TaskList not found
        }
    }

    @DeleteMapping(value = "/{taskListId}/tasks/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long taskListId, @PathVariable Long taskId) {
        // Fetch the Task to ensure it belongs to the specified TaskList
        Optional<Task> taskOptional = taskService.findTaskById(taskId);
        if (taskOptional.isPresent() && taskOptional.get().getTaskList().getId().equals(taskListId)) {
            taskService.deleteTaskById(taskId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();  // Task not found or doesn't belong to the specified TaskList
        }
    }
}
