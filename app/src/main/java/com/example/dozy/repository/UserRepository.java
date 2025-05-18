package com.example.dozy.repository;

import com.example.dozy.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRepository {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Task<Void> saveUser(String uid, User user){
        return db.collection("users").document(uid).set(user);
    }

    public Task<User> getUser(String uid){
        return db.collection("users").document(uid)
                .get()
                .continueWith(task -> {
                    if(task.isSuccessful() && task.getResult().exists()){
                        return task.getResult().toObject(User.class);
                    }else{
                        return null;
                    }
                });
    }
}
