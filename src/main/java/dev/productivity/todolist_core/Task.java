package dev.productivity.todolist_core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Task {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Column(name = "taskName")
        private String taskName;

        @Column(name = "completed")
        private boolean completed;

        @Column(name = "description")
        private String description;

        @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinTable(
                name = "task_tag",
                joinColumns = @JoinColumn(name = "task_id"),
                inverseJoinColumns = @JoinColumn(name = "tag_id")
        )
        @JsonProperty("tags")
        private Set<Tag> tags;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "task_list_id")
        @JsonIgnore
        @JsonProperty("taskList")
        private TaskList taskList;

        // Constructors, getters, and setters

        public Task() {
            this.tags = new HashSet<>();
        }

        public Task(@JsonProperty("taskName") String taskName, @JsonProperty("completed") boolean completed, @JsonProperty("description") String description) {
            this.taskName = taskName;
            this.completed = completed;
            this.description = description;
            this.tags = new HashSet<>();
        }

        public Task(String taskName) {
            this.taskName = taskName;
            this.completed = false;
            this.description = "";
            this.tags = new HashSet<>();
        }

        public Task(String taskName, String description, List<String> tags) {
            this.taskName = taskName;
            this.completed = false;
            this.description = description;
            this.tags = new HashSet<>();
            for (String tag : tags) {
                this.tags.add(new Tag(tag));
            }
        }

        public Task(String taskName, List<String> tags) {
            this.taskName = taskName;
            this.completed = false;
            this.description = "";
            this.tags = new HashSet<>();
            for (String tag : tags) {
                this.tags.add(new Tag(tag));
            }
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Set<Tag> getTags() {
            return tags;
        }

        public void setTags(Set<Tag> tags) {
            this.tags = tags;
        }

        public TaskList getTaskList() {
            return taskList;
        }

        public void setTaskList(TaskList taskList) {
            this.taskList = taskList;
        }
    }

