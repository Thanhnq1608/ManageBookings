package com.fpoly.managebookings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_dangnhap extends AppCompatActivity implements View.OnClickListener{

    EditText et_username, et_password;
//    Button btn_login;

    private TextView tvForgetPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);

        initView();

        tvForgetPass.setOnClickListener(this);
    }

    private void initView() {
        tvForgetPass = findViewById(R.id.tvForgetPass);
    }

    private void Login(){
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvForgetPass:
                startActivity(new Intent(this, Activity_quen_mk.class));
                break;
        }
    }
}
