package com.example.seoulo1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText nickname, email, phoneNum, password, username;
    Button btn_register, login_button;

    private FirebaseAuth auth;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nickname = findViewById(R.id.et_Nickname);
        email = findViewById(R.id.et_email);
        phoneNum = findViewById(R.id.et_callNum);
        password = findViewById(R.id.et_pwd);
        username = findViewById(R.id.et_name);
        btn_register = findViewById(R.id.btn_register);
        login_button = findViewById(R.id.login_button);

        auth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(v -> {
            String txt_nickname = nickname.getText().toString();
            String txt_email = email.getText().toString();
            String txt_password = password.getText().toString();
            String txt_phoneNum = phoneNum.getText().toString();
            String txt_username = username.getText().toString();

            if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)
                    || TextUtils.isEmpty(txt_nickname) || TextUtils.isEmpty(txt_phoneNum)){
                Toast.makeText(RegisterActivity.this,"빈칸을 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else if (txt_password.length() < 6) {
                Toast.makeText(RegisterActivity.this,"비밀번호를 6자리 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                register(txt_username, txt_email, txt_password, txt_phoneNum, txt_username);
            }
        });
        login_button.setOnClickListener(v ->{
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void register(String nickname, String email, String password, String phoneNum, String username){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                FirebaseUser firebaseUser = auth.getCurrentUser();
                assert firebaseUser != null;
                String userid = firebaseUser.getUid();

                reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id", userid);
                hashMap.put("pwd", password);
                hashMap.put("nickname", nickname);
                hashMap.put("phoneNum", phoneNum);
                hashMap.put("username", username);
                hashMap.put("imageURL","default");
                hashMap.put("status","offline");
                hashMap.put("search", nickname.toLowerCase());

                reference.setValue(hashMap).addOnCompleteListener(task1 -> {
                    if(task1.isSuccessful()) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
            } else {
                Log.d("회원가입 실패", task.getException().toString());

                Toast.makeText(RegisterActivity.this, "가입 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
}