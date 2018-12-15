package com.kmu.timemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    MembershipOpenHelper openHelper;
    EditText emailEt, pwdEt;
    SQLiteDatabase db;
    Button loginBtn, registerButton;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        openHelper = new MembershipOpenHelper(this);
        db = openHelper.getWritableDatabase();
        emailEt = (EditText) findViewById(R.id.idText);
        pwdEt = (EditText) findViewById(R.id.passwordText);
        loginBtn = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);
        loginBtn.setOnClickListener(listener);
        registerButton.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.registerButton:
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                    finish();
                    break;
                case R.id.loginButton:
                    String userId = emailEt.getText().toString();
                    String userPassword = pwdEt.getText().toString();

                    String sql = "select * from membership where userID = '"+userId+"' and userPassword = '"+userPassword+"'";
                    Cursor cursor = db.rawQuery(sql, null);
                    while (cursor.moveToNext()) {
                        String no = cursor.getString(0);
                        String rest_id = cursor.getString(1);
                        Log.d("select ", "no : " + no + "\nrest_id : " + rest_id);
                    }
                    if(cursor.getCount() == 1) {
                        // 해당 이메일과 아이디가 있으면 1개의 row를 가져오겠죠?
                        Toast.makeText(LoginActivity.this, userId+ "님 환영합니다", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("userId", userId);
                        intent.putExtra("userPassword", userPassword);
                        startActivity(intent);
                        finish();
                    } else {
                        // 없다면 아무 값도 가져오지 않으므로 count 가 0 이겠죠?
                        Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                    }
                    cursor.close();
                    break;
            }
        }
    };
}