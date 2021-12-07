package com.fpoly.managebookings.views.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.api.user.ApiLoginUserInterface;
import com.fpoly.managebookings.api.user.ApiUser;
import com.fpoly.managebookings.models.User;
import com.fpoly.managebookings.tool.FixSizeForToast;
import com.fpoly.managebookings.tool.LoadingDialog;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderWaitingActivity;

public class LoginActivity extends AppCompatActivity implements ApiLoginUserInterface {
    private EditText edtPassword;
    private TextView tvForgetPass;
    private EditText edtUsername;
    private Button btnLogin;
    private ApiUser mApiUser = new ApiUser(this);
    private FixSizeForToast fixSizeForToast = new FixSizeForToast(this);
    private LoadingDialog loadingDialog = new LoadingDialog(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);

//        getSupportActionBar().hide();
        initView();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void initView() {
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        tvForgetPass = (TextView) findViewById(R.id.tvForgetPass);
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }

    private void login() {
        if (checkLogin()) {
            mApiUser.login(edtUsername.getText().toString().trim(), edtPassword.getText().toString().trim());
            loadingDialog.startLoadingDialog(2000);
        }
    }

    boolean checkLogin() {
        if (edtPassword.getText().length() == 0 || edtUsername.getText().length() == 0) {
            fixSizeForToast.fixSizeToast("Account or password cannot be empty!");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void loginSuccess(String token, User user, String status) {
        if (user.getRole().equalsIgnoreCase("customer")) {
            fixSizeForToast.fixSizeToast("Incorrect account or password");
        } else {
            fixSizeForToast.fixSizeToast(status);
            Intent intent = new Intent(this, ListOrderWaitingActivity.class);
            intent.putExtra("TOKEN", token);
            intent.putExtra("USER", user);
            startActivity(intent);
        }
    }

    @Override
    public void loginFail(String status) {
        fixSizeForToast.fixSizeToast("Incorrect account or password");
    }
}