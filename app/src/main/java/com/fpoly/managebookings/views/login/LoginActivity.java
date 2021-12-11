package com.fpoly.managebookings.views.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.api.sendNotifyFirebase.ApiSendNotifyWithFirebase;
import com.fpoly.managebookings.api.user.ApiLoginUserInterface;
import com.fpoly.managebookings.api.user.ApiUser;
import com.fpoly.managebookings.models.User;
import com.fpoly.managebookings.models.firebase.DataSendMessFirebase;
import com.fpoly.managebookings.tool.FixSizeForToast;
import com.fpoly.managebookings.tool.LoadingDialog;
import com.fpoly.managebookings.tool.SharedPref_InfoUser;
import com.fpoly.managebookings.views.forgertPass.ForgetPasswordActivity;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderWaitingActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonObject;

public class LoginActivity extends AppCompatActivity implements ApiLoginUserInterface {
    private static final String SENDER_ID = "ManageBooking";
    private TextInputEditText edtPassword;
    private TextView tvForgetPass;
    private TextInputEditText edtPhone;
    private Button btnLogin;
    private ApiUser mApiUser = new ApiUser(this);
    private FixSizeForToast fixSizeForToast = new FixSizeForToast(this);
    private LoadingDialog loadingDialog;
    private boolean doubleBackToExitPressedOnce = false;
    private ApiSendNotifyWithFirebase apiSendNotifyWithFirebase = new ApiSendNotifyWithFirebase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);

//        getSupportActionBar().hide();
        initView();
        loadingDialog = new LoadingDialog(this);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
                loadingDialog.startLoadingDialog(2000);

            }
        });

        tvForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
            finish();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        fixSizeForToast.fixSizeToast("Nhấn  " + '"' + "TRỞ VỀ" + '"' + "  lần nữa để thoát");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }

    private void initView() {
        edtPassword = (TextInputEditText) findViewById(R.id.edtPassword);
        tvForgetPass = (TextView) findViewById(R.id.tvForgetPass);
        edtPhone = (TextInputEditText) findViewById(R.id.edtPhoneNumber);
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }

    private void login() {
        if (checkLogin()) {
            mApiUser.login(edtPhone.getText().toString().trim(), edtPassword.getText().toString().trim());
//            loadingDialog.startLoadingDialog(2000);
        }
    }

    boolean checkLogin() {
        if (edtPassword.getText().length() == 0 || edtPhone.getText().length() == 0) {
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