package com.example.dozybackend.controller;

import com.example.dozybackend.dao.Task;
import com.example.dozybackend.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/users/{userId}/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<String> createTask(@PathVariable String userId, @RequestBody Task task) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(taskService.createTask(userId, task));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable String userId, @PathVariable String taskId) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(taskService.getTask(userId, taskId));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<String> updateTask(@PathVariable String userId, @PathVariable String taskId, @RequestBody Task task) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(taskService.updateTask(userId, taskId, task));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable String userId, @PathVariable String taskId) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(taskService.deleteTask(userId, taskId));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getTasksByUser(@PathVariable String userId) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(taskService.getTaskByUser(userId));
    }
}
