package com.fpoly.managebookings.views.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.api.sendNotifyFirebase.ApiSendNotifyWithFirebase;
import com.fpoly.managebookings.api.user.ApiLoginUserInterface;
import com.fpoly.managebookings.api.user.ApiUser;
import com.fpoly.managebookings.models.User;
import com.fpoly.managebookings.tool.FixSizeForToast;
import com.fpoly.managebookings.tool.LoadingDialog;
import com.fpoly.managebookings.tool.SharedPref_InfoUser;
import com.fpoly.managebookings.tool.SharedPref_RememberUserAndPass;
import com.fpoly.managebookings.views.forgertPass.ForgetPasswordActivity;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderWaitingActivity;
import com.fpoly.managebookings.views.listRoomEmpty.ListRoomEmptyActivity;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity implements ApiLoginUserInterface {
    private static final String SENDER_ID = "ManageBooking";
    private TextInputEditText edtPassword;
    private TextView tvForgetPass;
    private TextInputEditText edtPhone;
    private Button btnLogin;
    private CheckBox cb_remember;
    private ApiUser mApiUser = new ApiUser(this);
    private FixSizeForToast fixSizeForToast = new FixSizeForToast(this);
    private LoadingDialog loadingDialog;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        getSupportActionBar().hide();
        initView();
        loadingDialog = new LoadingDialog(this);

        if (SharedPref_RememberUserAndPass.getInstance(this).loggedInUsername() != null){
            edtPhone.setText(SharedPref_RememberUserAndPass.getInstance(this).loggedInUsername());
            edtPassword.setText(SharedPref_RememberUserAndPass.getInstance(this).loggedInPassword());
            cb_remember.setChecked(true);
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

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
        cb_remember = findViewById(R.id.cb_remember_account);
    }

    private void login() {
        if (checkLogin()) {
            mApiUser.login(edtPhone.getText().toString().trim(), edtPassword.getText().toString().trim());
            if (cb_remember.isChecked()){
                SharedPref_RememberUserAndPass.getInstance(this).storeUsername(edtPhone.getText().toString().trim());
                SharedPref_RememberUserAndPass.getInstance(this).storePassword(edtPassword.getText().toString().trim());
            }else {
                SharedPref_RememberUserAndPass.getInstance(this).clearSharedPreferences();
            }
            Log.e("Test",""+edtPhone.getText().toString().trim()+edtPassword.getText().toString().trim());
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
        } else if (user.getRole().equalsIgnoreCase("dataEntry")){
            fixSizeForToast.fixSizeToast(status);

            SharedPref_InfoUser.getInstance(this).storeUserEmail(user.getEmail());
            SharedPref_InfoUser.getInstance(this).storeUserFullName(user.getFullName());
            SharedPref_InfoUser.getInstance(this).storeUserAvatar(user.getAvatar());

            Intent intent = new Intent(this, ListRoomEmptyActivity.class);
            intent.putExtra("DATAENTRY", true);
            startActivity(intent);
            finishAffinity();
        }
        else {
            fixSizeForToast.fixSizeToast(status);

            SharedPref_InfoUser.getInstance(this).storeUserEmail(user.getEmail());
            SharedPref_InfoUser.getInstance(this).storeUserFullName(user.getFullName());
            SharedPref_InfoUser.getInstance(this).storeUserAvatar(user.getAvatar());
            SharedPref_InfoUser.getInstance(this).storeUserToKen(token);
            SharedPref_InfoUser.getInstance(this).storeUserToKenIdApp(user.getTokenId());
            SharedPref_InfoUser.getInstance(this).storeUserId(user.getId());

            Intent intent = new Intent(this, ListOrderWaitingActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void loginFail(String status) {
        fixSizeForToast.fixSizeToast(status);
    }
}