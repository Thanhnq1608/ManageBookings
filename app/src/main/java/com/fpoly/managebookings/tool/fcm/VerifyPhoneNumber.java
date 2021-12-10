package com.fpoly.managebookings.tool.fcm;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.fpoly.managebookings.tool.FixSizeForToast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneNumber {
    private FixSizeForToast fixSizeForToast;
    private Activity activity;
    private VerifySuccessInterface mVerifySuccessInterface;
    private String verificationId;

    public interface VerifySuccessInterface {
        void VerifyPhoneSuccess(String phoneNumber);
    }

    public VerifyPhoneNumber(Activity activity, VerifySuccessInterface mVerifySuccessInterface) {
        this.activity = activity;
        this.mVerifySuccessInterface = mVerifySuccessInterface;
        fixSizeForToast = new FixSizeForToast(activity);
    }

    public void gotoEnterOTP(String otp, FirebaseAuth mAuth) {
        if (verificationId != null) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
            signInWithPhoneAuthCredential(credential, mAuth);
        } else {
            fixSizeForToast.fixSizeToast("Please press send otp button before press confirm button!");
        }
    }

    public void verifyPhoneNumber(String phoneNumber, FirebaseAuth mAuth) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(activity) // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                signInWithPhoneAuthCredential(phoneAuthCredential, mAuth);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                fixSizeForToast.fixSizeToast("Verification Failed");
                                Log.e("Verification Failed", "" + e.getMessage());
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(verificationID, forceResendingToken);
                                verificationId = verificationID;
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential, FirebaseAuth mAuth) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(activity.getClass().getName(), "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
//                            updateUIWhileVerifySuccess(user.getPhoneNumber());
                            mVerifySuccessInterface.VerifyPhoneSuccess(user.getPhoneNumber());
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(activity.getClass().getName(), "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                fixSizeForToast.fixSizeToast("The verify code entered was invalid");
                            }
                        }
                    }
                });
    }
}
