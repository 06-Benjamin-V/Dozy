package com.example.dozy.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dozy.R;
import com.example.dozy.managers.AuthManager;
import com.example.dozy.repository.UserRepository;
import com.example.dozy.ui.MainActivity;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText emailET, passwordET;
    private Button loginBt, goToRegisterBt;

    private AuthManager authManager;
    private UserRepository userRepository;

    private View loaderContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loaderContainer = findViewById(R.id.loaderContainer);


        // Inicializar managers
        authManager = new AuthManager();
        userRepository = new UserRepository();

        // Referencias a los elementos UI
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        loginBt = findViewById(R.id.loginBt);
        goToRegisterBt = findViewById(R.id.goToRegisterBt);

        // Botón para ir al registro
        goToRegisterBt.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));

        // Botón de login
        loginBt.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();

        // Validaciones
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password.", Toast.LENGTH_SHORT).show();
            return;
        }

        // iniciar sesion con usuario registrado en firestore
        showLoader();

        authManager.login(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = authManager.getCurrentUser();
                        if (firebaseUser != null) {
                            userRepository.getUser(firebaseUser.getUid())
                                    .addOnCompleteListener(userTask -> {
                                        hideLoader();
                                        if (userTask.isSuccessful() && userTask.getResult() != null) {
                                            startActivity(new Intent(this, MainActivity.class));
                                            finish();
                                        } else {
                                            Toast.makeText(this, "Error loading data", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            hideLoader();
                            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        hideLoader();
                        Toast.makeText(this, "Failed login: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showLoader() {
        loaderContainer.setVisibility(View.VISIBLE);
    }

    private void hideLoader() {
        loaderContainer.setVisibility(View.GONE);
    }

}
