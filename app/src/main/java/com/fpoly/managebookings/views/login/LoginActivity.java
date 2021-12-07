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
import com.fpoly.managebookings.tool.SharedPref_InfoUser;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderWaitingActivity;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity implements ApiLoginUserInterface {
    private TextInputEditText edtPassword;
    private TextView tvForgetPass;
    private TextInputEditText edtUsername;
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
        edtPassword = (TextInputEditText) findViewById(R.id.edtPassword);
        tvForgetPass = (TextView) findViewById(R.id.tvForgetPass);
        edtUsername = (TextInputEditText) findViewById(R.id.edtUsername);
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }

    private void login() {
        if (checkLogin()) {
            mApiUser.login(edtUsername.getText().toString().trim(), edtPassword.getText().toString().trim());
//            loadingDialog.startLoadingDialog(2000);
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

            SharedPref_InfoUser.getInstance(this).storeUserEmail(user.getEmail());
            SharedPref_InfoUser.getInstance(this).storeUserFullName(user.getFullName());
            SharedPref_InfoUser.getInstance(this).storeUserAvatar(user.getAvatar());

            Intent intent = new Intent(this, ListOrderWaitingActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void loginFail(String status) {
        fixSizeForToast.fixSizeToast("Incorrect account or password");
    }
}