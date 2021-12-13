package com.fpoly.managebookings.views.forgertPass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.api.user.ApiForgetPassInterface;
import com.fpoly.managebookings.api.user.ApiUser;
import com.fpoly.managebookings.tool.FixSizeForToast;
import com.fpoly.managebookings.tool.Formater;
import com.fpoly.managebookings.tool.fcm.VerifyPhoneNumber;
import com.fpoly.managebookings.views.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ForgetPasswordActivity extends AppCompatActivity implements VerifyPhoneNumber.VerifySuccessInterface, ApiForgetPassInterface {
    private static final String TAG = ForgetPasswordActivity.class.getName();
    private EditText edtPhone;
    private Button btnSendOtp;
    private EditText edtOtp;
    private TextInputEditText edtPass,edtRepPass;
    private TextView tvBack, tvErrorPhone, tvErrorRepPass;
    private Button btnSave;
    private LinearLayout layoutThis, layoutOtp;
    private FixSizeForToast fixSizeForToast = new FixSizeForToast(this);
    private FirebaseAuth mAuth;
    private VerifyPhoneNumber mVerifyPhoneNumber;
    private ApiUser apiUser = new ApiUser(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_confirm_forget_password);

        findViewsById();
        mVerifyPhoneNumber = new VerifyPhoneNumber(this,this);
        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("vi");
        mAuth.useAppLanguage();
        layoutOtp.setMinimumHeight(0);

        edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 10) {
                    tvErrorPhone.setText("*Phone number must have 10 characters");
                } else {
                    tvErrorPhone.setText("");
                }
            }
        });

        btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtPhone.getText().length() != 0) {
                    if (tvErrorPhone.getText().length() == 0) {
                        mVerifyPhoneNumber.verifyPhoneNumber(Formater.formatStringToPhone(edtPhone.getText().toString().trim()),mAuth);
                    }
                } else {
                    tvErrorPhone.setText("*Phone number cannot be left blank");
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSave();
            }
        });
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edtRepPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!edtPass.getText().toString().equals(edtRepPass.getText().toString())){
                    tvErrorRepPass.setText("Invalid repeat password ");
                }else {
                    tvErrorRepPass.setText("");
                }
            }
        });
    }

    private void findViewsById() {
        edtPhone = (EditText) findViewById(R.id.edt_phone_forget);
        btnSendOtp = (Button) findViewById(R.id.btn_send_otp);
        edtOtp = (EditText) findViewById(R.id.edt_otp);
        tvBack = (TextView) findViewById(R.id.tvBack);
        btnSave = (Button) findViewById(R.id.btnSave);
        layoutThis = findViewById(R.id.layout_this);
        layoutOtp = findViewById(R.id.layout_otp);
        tvErrorPhone = findViewById(R.id.phone_number_error);
        tvErrorRepPass = findViewById(R.id.tv_repPass_error);
        edtPass = findViewById(R.id.edtNewPassword);
        edtRepPass = findViewById(R.id.edtNewPasswordConfirm);
    }

    private void onClickSave(){
        if (edtOtp.getText().length() == 0) {
            fixSizeForToast.fixSizeToast("OTP cannot be left blank");
        } else {
            mVerifyPhoneNumber.gotoEnterOTP(edtOtp.getText().toString().trim(),mAuth);
        }
    }


    private void updateUIWhileVerifySuccess() {
        if (tvErrorRepPass.getText() != null && tvErrorPhone.getText() != null){
            if(edtPass.getText() == null || edtRepPass.getText() == null){
                fixSizeForToast.fixSizeToast("You cannot leave data blank ");
            }else {
                apiUser.forgetPassword(edtPhone.getText().toString(),edtPass.getText().toString());
            }
        }
    }

    @Override
    public void VerifyPhoneSuccess(String phoneNumber) {
        updateUIWhileVerifySuccess();
    }

    @Override
    public void changePassSuccess() {
        fixSizeForToast.fixSizeToast("Change password success");
        startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
    }

    @Override
    public void changePassFail() {
        fixSizeForToast.fixSizeToast("Your account does not exist`");
    }
}