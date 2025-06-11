package com.example.dozybackend.service;

import com.example.dozybackend.dao.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private Firestore firestore;

    public String createUser(User user) throws ExecutionException, InterruptedException {
        try{
            ApiFuture<DocumentReference> users = firestore.collection("users").add(user);
            return "Document saved: userId = " + users.get().getId();
        }catch (InterruptedException | ExecutionException e){
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public User getUser(String userId) {
        ApiFuture<DocumentSnapshot> users = firestore.collection("users").document(userId).get();
        try{
            return users.get().toObject(User.class);
        }catch (InterruptedException | ExecutionException e){
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String updateUser(String userId, User updatedUser) {
        try {
            firestore.collection("users").document(userId).set(updatedUser);
            return "User updated with ID: " + userId;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public String deleteUser(String userId) {
        try {
            firestore.collection("users").document(userId).delete();
            return "User deleted with ID: " + userId;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }



}
