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
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText nickname, email, phoneNum, password, username, checkPwd;
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
        checkPwd = findViewById(R.id.et_checkPwd);
        login_button = findViewById(R.id.login_button);

        auth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(v -> {
            String txt_nickname = nickname.getText().toString();
            String txt_email = email.getText().toString();
            String txt_password = password.getText().toString();
            String txt_phoneNum = phoneNum.getText().toString();
            String txt_username = username.getText().toString();
            String txt_checkPwd = checkPwd.getText().toString();
            Pattern Pattern1 = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,16}$");
            String pattern2 = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
            Matcher passMatcher = Pattern1.matcher(txt_password);

            if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)
                    || TextUtils.isEmpty(txt_nickname) || TextUtils.isEmpty(txt_phoneNum)){
                Toast.makeText(RegisterActivity.this,"빈칸을 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else if (!Pattern.matches(pattern2, txt_email)) {
                Toast.makeText(RegisterActivity.this,"올바른 이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
            } else if(!passMatcher.find()){
                Toast.makeText(RegisterActivity.this,"비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다.", Toast.LENGTH_SHORT).show();
            } else if (!txt_password.equals(txt_checkPwd)){
                Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            } else {
                register(txt_nickname, txt_email, txt_password, txt_phoneNum, txt_username);
            }
        });
                login_button.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));


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
//                hashMap.put("pwd", password);
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
                Log.d("회원가입 실패", Objects.requireNonNull(task.getException()).toString());
                Toast.makeText(RegisterActivity.this, "가입 실패", Toast.LENGTH_SHORT).show();
            }
            try {
                //단독으로 쓸 씨 튕김. 예외 처리 해줘야 한다.
                task.getResult();
            }catch (Exception e) {
                //이메일 중복 체크
                e.printStackTrace();
                Log.d("Fail_register_email",e.getMessage());
                Toast.makeText(RegisterActivity.this, "이미있는 이메일 형식입니다 다시 입력해주세요", Toast.LENGTH_LONG).show();
            }
        });
    }
}