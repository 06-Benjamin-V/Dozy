package com.example.dozybackend.service;

import com.example.dozybackend.dao.Task;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    private Firestore firestore;
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        firestore = mock(Firestore.class);
        taskService = new TaskService(firestore);
    }

    @Test
    void testCreateTask() throws Exception {
        String userId = "user123";
        Task task = new Task();

        CollectionReference usersCollection = mock(CollectionReference.class);
        DocumentReference userDoc = mock(DocumentReference.class);
        CollectionReference tasksCollection = mock(CollectionReference.class);
        DocumentReference taskDoc = mock(DocumentReference.class);
        ApiFuture<WriteResult> future = mock(ApiFuture.class);

        when(firestore.collection("users")).thenReturn(usersCollection);
        when(usersCollection.document(userId)).thenReturn(userDoc);
        when(userDoc.collection("tasks")).thenReturn(tasksCollection);
        when(tasksCollection.document()).thenReturn(taskDoc);
        when(taskDoc.set(task)).thenReturn(future);
        when(future.get()).thenReturn(mock(WriteResult.class));
        when(taskDoc.getId()).thenReturn("task123");

        String result = taskService.createTask(userId, task);

        assertTrue(result.contains("task123"));
        assertEquals("task123", task.getId());
        assertEquals(userId, task.getUserId());
    }

    @Test
    void testGetTask() throws Exception {
        String userId = "user1";
        String taskId = "task1";
        Task mockTask = new Task();
        mockTask.setId(taskId);
        mockTask.setUserId(userId);

        DocumentSnapshot snapshot = mock(DocumentSnapshot.class);
        ApiFuture<DocumentSnapshot> future = mock(ApiFuture.class);

        CollectionReference users = mock(CollectionReference.class);
        DocumentReference userDoc = mock(DocumentReference.class);
        CollectionReference tasks = mock(CollectionReference.class);
        DocumentReference taskDoc = mock(DocumentReference.class);

        when(firestore.collection("users")).thenReturn(users);
        when(users.document(userId)).thenReturn(userDoc);
        when(userDoc.collection("tasks")).thenReturn(tasks);
        when(tasks.document(taskId)).thenReturn(taskDoc);
        when(taskDoc.get()).thenReturn(future);
        when(future.get()).thenReturn(snapshot);
        when(snapshot.exists()).thenReturn(true);
        when(snapshot.toObject(Task.class)).thenReturn(mockTask);

        Task result = taskService.getTask(userId, taskId);

        assertNotNull(result);
        assertEquals(taskId, result.getId());
    }

    @Test
    void testDeleteTask() throws Exception {
        String userId = "user1";
        String taskId = "task1";

        CollectionReference users = mock(CollectionReference.class);
        DocumentReference userDoc = mock(DocumentReference.class);
        CollectionReference tasks = mock(CollectionReference.class);
        DocumentReference taskDoc = mock(DocumentReference.class);
        ApiFuture<WriteResult> future = mock(ApiFuture.class);

        when(firestore.collection("users")).thenReturn(users);
        when(users.document(userId)).thenReturn(userDoc);
        when(userDoc.collection("tasks")).thenReturn(tasks);
        when(tasks.document(taskId)).thenReturn(taskDoc);
        when(taskDoc.delete()).thenReturn(future);
        when(future.get()).thenReturn(mock(WriteResult.class));

        String result = taskService.deleteTask(userId, taskId);

        assertTrue(result.contains(taskId));
    }
}
