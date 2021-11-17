package com.fpoly.managebookings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_dangnhap extends AppCompatActivity {

    EditText et_username, et_password;
//    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);

        Login();
    }

    void Login(){
        et_username = (EditText)findViewById(R.id.edtUsername);
        et_password = (EditText)findViewById(R.id.edtPassword);
//        btn_login = (Button)findViewById(R.id.btnLogin);

//        btn_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(et_username.getText().toString().equals("admin") && et_password.getText().toString().equals("admin")){
//                    Toast.makeText(dangnhap.this, "Username and Password is correct", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(dangnhap.this,quenmk.class);
//                    startActivity(intent);
//                }else{
//                    Toast.makeText(dangnhap.this, "Username or Password is incorrect", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
}
