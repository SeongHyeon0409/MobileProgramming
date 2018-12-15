package com.kmu.timemanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterActivity extends AppCompatActivity {

    MembershipOpenHelper openHelper;
    SQLiteDatabase db;
    EditText emailEt, pwdEt, userEmailet;
    Button joinBtn, backButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        openHelper = new MembershipOpenHelper(this);
        db = openHelper.getWritableDatabase();
        emailEt = (EditText) findViewById(R.id.idText2);
        pwdEt = (EditText) findViewById(R.id.passwordText2);
        userEmailet = (EditText) findViewById(R.id.emailText);
        joinBtn = (Button) findViewById(R.id.registerButton2);
        backButton = (Button) findViewById(R.id.backButton);
        joinBtn.setOnClickListener(listener);
        backButton.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.backButton:
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                    break;
                case R.id.registerButton2:
                    String userId = emailEt.getText().toString();
                    String userPassword = pwdEt.getText().toString();
                    String userEmail = userEmailet.getText().toString();
                    String sql = "select * from membership where userID = '"+userId+"'";
                    Cursor cursor = db.rawQuery(sql, null);
                    if(cursor.getCount() == 1) {
                        // 해당 이메일과 아이디가 있을 경우
                        Toast.makeText(RegisterActivity.this, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        // 해당 아이디가 없을 경우
                        String sql2 ="insert into membership(userId, userPassword, userEmail) values('"+userId+"','"+userPassword+"','"+userEmail+"')";
                        db.execSQL(sql2);
                        Toast.makeText(RegisterActivity.this, "회원가입을 축하합니다.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }
                    cursor.close();
                    break;
            }
        }
    };
}
