package com.example.dozy.managers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthManager {
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public Task<AuthResult> login(String email, String password){
        return auth.signInWithEmailAndPassword(email,password);
    }

    public Task<AuthResult> register(String email,String password){
        return auth.createUserWithEmailAndPassword(email,password);
    }

    public FirebaseUser getCurrentUser(){
        return auth.getCurrentUser();
    }

    public void logout(){
        auth.signOut();
    }
}
