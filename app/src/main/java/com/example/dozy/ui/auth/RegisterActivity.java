package com.example.dozy.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dozy.R;
import com.example.dozy.managers.AuthManager;
import com.example.dozy.model.User;
import com.example.dozy.repository.UserRepository;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameET, phoneET, emailET, passwordET, confirmPasswordET;
    private Button registerBtn, goToLoginBtn;

    private AuthManager authManager;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inicializar managers
        authManager = new AuthManager();
        userRepository = new UserRepository();

        // Referencias a los elementos UI
        usernameET = findViewById(R.id.usernameET);
        phoneET = findViewById(R.id.phoneET);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        confirmPasswordET = findViewById(R.id.confirmPasswordET);
        registerBtn = findViewById(R.id.registerBt);
        goToLoginBtn = findViewById(R.id.goToLoginBt);

        // Botón para ir al login
        goToLoginBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        // Botón de registro
        registerBtn.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String username = usernameET.getText().toString().trim();
        String phone = phoneET.getText().toString().trim();
        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        String confirmPassword = confirmPasswordET.getText().toString().trim();

        // Validaciones
        if (username.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Registrar usuario en Firebase Auth
        authManager.register(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = authManager.getCurrentUser();
                if (firebaseUser != null) {
                    String uid = firebaseUser.getUid();
                    User newUser = new User(username, phone, email);

                    // Guardar usuario en Firestore
                    userRepository.saveUser(uid, newUser).addOnCompleteListener(saveTask -> {
                        Toast.makeText(this, "successful registration", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();
                    }).addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to save user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                } else {
                    Toast.makeText(this, "Error: Firebase user is null", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
