package com.example.dozy.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import com.example.dozy.R;
import com.example.dozy.managers.AuthManager;
import com.example.dozy.ui.auth.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    AuthManager authManager;

    Button logoutBt;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoutBt = findViewById(R.id.logoutBt);

        authManager = new AuthManager();
        logoutBt.setOnClickListener(v ->{
            logoutSesion();
        });
    }
    private void logoutSesion() {
        authManager.logout();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null) {
            startActivity(new Intent(this, LoginActivity.class));
            Toast.makeText(this, "Closed Sesion", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
