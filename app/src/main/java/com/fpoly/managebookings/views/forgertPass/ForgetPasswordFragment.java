package com.fpoly.managebookings.views.forgertPass;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.views.login.LoginActivity;


public class ForgetPasswordFragment extends Fragment {
    private EditText edtNewPassword;
    private EditText edtNewPasswordConfirm;
    private TextView tvBack;
    private Button btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.forget_password, container, false);
        findViewsById(view);
        Bundle bundle = this.getArguments();
        String phoneNumber = bundle.getString("PHONENUMBER");

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    private void findViewsById(View view){
        edtNewPassword = (EditText) view.findViewById(R.id.edtNewPassword);
        edtNewPasswordConfirm = (EditText) view.findViewById(R.id.edtNewPasswordConfirm);
        tvBack = (TextView) view.findViewById(R.id.tvBack);
        btnSave = (Button) view.findViewById(R.id.btnSave);
    }
}