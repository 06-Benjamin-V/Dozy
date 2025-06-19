package com.example.dozybackend.service;

import com.example.dozybackend.dao.Task;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
@Slf4j
public class TaskService {
    private Firestore firestore;

    public String createTask(String userId,Task task) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("users")
                .document(userId)
                .collection("tasks")
                .document();
        task.setId(docRef.getId());
        task.setUserId(userId);

        ApiFuture<WriteResult> result = docRef.set(task);
        result.get();
        return "Task created with ID: " + docRef.getId();
    }

    public Task getTask(String userId, String taskId) throws ExecutionException, InterruptedException {
        DocumentSnapshot snapshot = firestore.collection("users")
                .document(userId)
                .collection("tasks")
                .document(taskId)
                .get().get();
        return snapshot.exists() ? snapshot.toObject(Task.class) : null;
    }

    public String updateTask(String userId, String taskId, Task task) throws ExecutionException, InterruptedException {
        task.setId(taskId);
        task.setUserId(userId);
        ApiFuture<WriteResult> result = firestore.collection("users")
                .document(userId)
                .collection("task")
                .document(taskId)
                .set(task);
        result.get();
        return "Task updated with ID: " + taskId;
    }

    public String deleteTask(String userId, String taskId) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> result = firestore.collection("users")
                .document(userId)
                .collection("tasks")
                .document(taskId)
                .delete();
        result.get();
        return "Task deleted with ID: " + taskId;
    }

    public List<Task> getTaskByUser(String userId) throws ExecutionException, InterruptedException {
        List<Task> tasks = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = firestore.collection("users")
                .document(userId)
                .collection("tasks")
                .get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for(QueryDocumentSnapshot doc : documents) {
            tasks.add(doc.toObject(Task.class));
        }
        return tasks;
    }

}
