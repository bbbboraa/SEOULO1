package com.example.seoulo1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class MypageActivity extends AppCompatActivity {

    Button btn_pwdReset, btn_info_update;
    EditText  phoneNum;
    TextView txt_email, txt_nickname;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        btn_info_update = findViewById(R.id.btn_info_update);
        btn_pwdReset = findViewById(R.id.btn_pwdReset);
        txt_email = findViewById(R.id.txt_email);
        txt_nickname = findViewById(R.id.txt_nickname);
        phoneNum = findViewById(R.id.et_callNum);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        firebaseUser.getEmail();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MemberInfo memberinfo = dataSnapshot.getValue(MemberInfo.class);
                txt_nickname.setText(memberinfo.getNickname());
                txt_email.setText(firebaseUser.getEmail());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        btn_info_update.setOnClickListener(v -> {
            String txt_phoneNum = phoneNum.getText().toString();
            if (!TextUtils.isEmpty(txt_phoneNum)){
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference.child("phoneNum").setValue(txt_phoneNum);

                        Toast.makeText(MypageActivity.this,"회원정보 수정 되었습니다.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MypageActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            } else {
                Toast.makeText(MypageActivity.this,"수정할 정보를 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });

        btn_pwdReset.setOnClickListener(v -> {
            startActivity(new Intent(MypageActivity.this, ResetPasswordActivity.class));
            finish();
        });


    }
}