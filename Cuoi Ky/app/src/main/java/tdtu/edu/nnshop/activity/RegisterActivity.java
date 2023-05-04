package tdtu.edu.nnshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import tdtu.edu.nnshop.R;
import tdtu.edu.nnshop.model.User;


public class RegisterActivity extends AppCompatActivity {

    EditText edtUsername, edtEmail, edtPassword, edtConfirmPassword;

    Button btnSignUp;

    private FirebaseAuth auth;
    private FirebaseDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        edtUsername = findViewById(R.id.username);
        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);
        edtConfirmPassword = findViewById(R.id.confirm_password);
        btnSignUp = findViewById(R.id.signup_button);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                String confirmPassword = edtConfirmPassword.getText().toString();

                if (username.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter username", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(RegisterActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                } else if (confirmPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Password and confirm password do not match", Toast.LENGTH_SHORT).show();
                } else {
                    auth.createUserWithEmailAndPassword(email, confirmPassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User user = new User(username, email, password);
                                String id=task.getResult().getUser().getUid();
                                db.getReference().child("Users").child(id).setValue(user);
                                Toast.makeText(RegisterActivity.this, "Register success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                            } else {
                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(RegisterActivity.this, "Register failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

}