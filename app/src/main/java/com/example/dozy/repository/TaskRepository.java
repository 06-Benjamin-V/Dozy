package com.example.dozy.repository;

import com.example.dozy.listeners.ValueEventListener;
import com.example.dozy.model.Task;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference taskRef;

    public TaskRepository() {
        String userId = FirebaseAuth.getInstance().getCurrentUser() != null
                ? FirebaseAuth.getInstance().getCurrentUser().getUid()
                : null;

        if (userId == null) {
            throw new IllegalStateException("User must be logged in to access tasks.");
        }

        taskRef = db.collection("users").document(userId).collection("tasks");
    }


    //Metodo para agregar tarea
    public void addTask(Task task, OnSuccessListener<Void> onSuccess, OnFailureListener onFailure) {
        if (task.getId() == null || task.getId().isEmpty()) {
            String newId = taskRef.document().getId();
            task.setId(newId);
        }

        taskRef.document(task.getId())
                .set(task)
                .addOnSuccessListener(unused -> onSuccess.onSuccess(null))
                .addOnFailureListener(onFailure);
    }


    //Metodo para obtener las tareas
    public void getTasks(ValueEventListener<List<Task>> listener) {
        taskRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Task> tasks = new ArrayList<>();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Task task = doc.toObject(Task.class);
                        if (task != null) {
                            tasks.add(task);
                        }
                    }
                    listener.onDataChange(tasks);
                })
                .addOnFailureListener(listener::onError);
    }
    //obtener uan tarea por id
    public void getTaskById(String taskId, ValueEventListener<Task> listener) {
        taskRef.document(taskId).get()
                .addOnSuccessListener(snapshot -> {
                    Task task = snapshot.toObject(Task.class);
                    listener.onDataChange(task);
                })
                .addOnFailureListener(listener::onError);
    }


    //Metodo para actualizar las tareas
    public void updateTask(Task task, OnSuccessListener<Void> onSuccess, OnFailureListener onFailure) {
        taskRef.document(task.getId())
                .set(task)
                .addOnSuccessListener(unused -> onSuccess.onSuccess(null))
                .addOnFailureListener(onFailure);
    }

    //Metodo para eliminar las tareas
    public void deleteTask(String taskId, OnSuccessListener<Void> onSuccess, OnFailureListener onFailure) {
        taskRef.document(taskId)
                .delete()
                .addOnSuccessListener(unused -> onSuccess.onSuccess(null))
                .addOnFailureListener(onFailure);
    }

}