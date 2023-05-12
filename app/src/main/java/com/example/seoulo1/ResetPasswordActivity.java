package com.example.seoulo1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText send_email;
    Button btn_reset;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        send_email = findViewById(R.id.send_email);
        btn_reset = findViewById(R.id.btn_reset);

        auth = FirebaseAuth.getInstance();

        btn_reset.setOnClickListener((View.OnClickListener) v -> {
            //email 보내기
            String email = send_email.getText().toString();

            if(email.equals("")){
                Toast.makeText(ResetPasswordActivity.this, "이메일을 입력해주세요!", Toast.LENGTH_SHORT).show();
            } else {
                auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        Toast.makeText(ResetPasswordActivity.this, "메일함을 확인해주세요!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                    } else {
                        String error = task.getException().getMessage();
                        Toast.makeText(ResetPasswordActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}