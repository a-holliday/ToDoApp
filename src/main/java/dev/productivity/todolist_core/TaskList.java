package dev.productivity.todolist_core;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TaskList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty("taskListName")
    private String name;

    @JsonProperty("tasks")
    @OneToMany(mappedBy = "taskList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    // Constructors, getters, and

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(String name) {
        this.name = name;
        this.tasks = new ArrayList<>();
    }

    public TaskList(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
        task.setTaskList(this);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        task.setTaskList(null);
    }

    public void clearTasks() {
        for (Task task : tasks) {
            task.setTaskList(null);
        }
        tasks.clear();
    }

    @Override
    public String toString() {
        return "TaskList{" +
                "name='" + name + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}






