package com.fpoly.managebookings;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_quen_mk extends AppCompatActivity {
    EditText  etPassword, etRepeatPassword;
    final int MIN_PASSWORD_LENGTH = 6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);
        viewInitializations();
    }

    void viewInitializations() {

        etPassword = findViewById(R.id.edtNewPassword);
        etRepeatPassword = findViewById(R.id.edtNewPasswordConfirm);

        // To show back button in actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // Checking if the input in form is valid
    boolean validateInput() {

        if (etPassword.getText().toString().equals("")) {
            etPassword.setError("Please Enter Password");
            return false;
        }
        if (etRepeatPassword.getText().toString().equals("")) {
            etRepeatPassword.setError("Please Enter Repeat Password");
            return false;
        }

        // checking minimum password Length
        if (etPassword.getText().length() < MIN_PASSWORD_LENGTH) {
            etPassword.setError("Password Length must be more than " + MIN_PASSWORD_LENGTH + "characters");
            return false;
        }

        // Checking if repeat password is same
        if (!etPassword.getText().toString().equals(etRepeatPassword.getText().toString())) {
            etRepeatPassword.setError("Password does not match");
            return false;
        }
        return true;
    }


    // Hook Click Event

    public void performResetPassword (View v) {
        if (validateInput()) {

            // Input is valid, here send data to your server

            String password = etPassword.getText().toString();
            String repeatPassword = etRepeatPassword.getText().toString();

            Toast.makeText(this,"Password Reset Successfully",Toast.LENGTH_SHORT).show();
            // Here you can call you API

        }
    }

}
