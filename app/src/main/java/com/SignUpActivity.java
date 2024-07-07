package com;

import static com.myfitbuddy.R.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.myfitbuddy.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextSignUpName, editTextSignUpEmail, editTextSignUpDOB, editTextSignUpMobile, editTextSignUpPwd, editTextSignUpConfirmPwd;
    private ProgressBar progressBar;
    private RadioGroup radioGroupSignupGend;
    private RadioButton radioButtonSignupSelectedGend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setTitle("Sign Up");
        Toast.makeText(SignUpActivity.this, "You can sign up now", Toast.LENGTH_LONG).show();

        editTextSignUpName = findViewById(R.id.edit_text_signup_name);
        editTextSignUpEmail = findViewById(R.id.edit_text_signup_email);
        editTextSignUpDOB = findViewById(R.id.edit_text_signup_dob);
        editTextSignUpMobile = findViewById(R.id.edit_text_signup_mobile);
        editTextSignUpPwd = findViewById(R.id.edit_text_signup_password);
        editTextSignUpConfirmPwd = findViewById(R.id.edit_text_signup_confirm_password);

        progressBar = findViewById(R.id.progressBar);

        radioGroupSignupGend = findViewById(R.id.radio_group_signup_gend);
        radioGroupSignupGend.clearCheck();

        Button buttonSignup = findViewById(R.id.button_signup);
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedGendId = radioGroupSignupGend.getCheckedRadioButtonId();
                radioButtonSignupSelectedGend = findViewById(selectedGendId);

                String textFullName = editTextSignUpName.getText().toString();
                String textEmail = editTextSignUpEmail.getText().toString();
                String textDoB = editTextSignUpDOB.getText().toString();
                String textMobile = editTextSignUpMobile.getText().toString();
                String textPwd = editTextSignUpPwd.getText().toString();
                String textConfirmPwd = editTextSignUpConfirmPwd.getText().toString();
                String textGend;

                if (TextUtils.isEmpty(textFullName)) {
                    Toast.makeText(SignUpActivity.this, "Please Enter Your Full Name", Toast.LENGTH_LONG).show();
                    editTextSignUpName.setError("Full name is required");
                    editTextSignUpName.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(SignUpActivity.this, "Please Enter Your Email", Toast.LENGTH_LONG).show();
                    editTextSignUpEmail.setError("Email is required");
                    editTextSignUpEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(SignUpActivity.this, "Please Re-Enter Your Email", Toast.LENGTH_LONG).show();
                    editTextSignUpEmail.setError("Valid email is required");
                    editTextSignUpEmail.requestFocus();
                } else if (TextUtils.isEmpty(textDoB)) {
                    Toast.makeText(SignUpActivity.this, "Please Enter Your Date of Birth", Toast.LENGTH_LONG).show();
                    editTextSignUpDOB.setError("Date of birth is required");
                    editTextSignUpDOB.requestFocus();
                } else if (radioGroupSignupGend.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(SignUpActivity.this, "Please Select a Gender to Continue", Toast.LENGTH_LONG).show();
                    radioButtonSignupSelectedGend.setError("Gender selection is required");
                    radioButtonSignupSelectedGend.requestFocus();
                } else if (TextUtils.isEmpty(textMobile)) {
                    Toast.makeText(SignUpActivity.this, "Please Enter Your Mobile Number", Toast.LENGTH_LONG).show();
                    editTextSignUpMobile.setError("Mobile number is required");
                    editTextSignUpMobile.requestFocus();
                } else if (textMobile.length() != 10) {
                    Toast.makeText(SignUpActivity.this, "Please Re-Enter Your Mobile Number", Toast.LENGTH_LONG).show();
                    editTextSignUpMobile.setError("Mobile number should be 10 digits long");
                    editTextSignUpMobile.requestFocus();
                } else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(SignUpActivity.this, "Please Enter Your Password", Toast.LENGTH_LONG).show();
                    editTextSignUpPwd.setError("Password is required");
                    editTextSignUpPwd.requestFocus();
                } else if (textPwd.length() < 6) {
                    Toast.makeText(SignUpActivity.this, "Please Re-Enter Your Password", Toast.LENGTH_LONG).show();
                    editTextSignUpPwd.setError("Password too weak");
                    editTextSignUpPwd.requestFocus();
                } else if (TextUtils.isEmpty(textConfirmPwd)) {
                    Toast.makeText(SignUpActivity.this, "Please Confirm Your Password", Toast.LENGTH_LONG).show();
                    editTextSignUpConfirmPwd.setError("Password confirmation is required");
                    editTextSignUpConfirmPwd.requestFocus();
                } else if (!textPwd.equals(textConfirmPwd)) {
                    Toast.makeText(SignUpActivity.this, "Please Confirm Your Password Correctly", Toast.LENGTH_LONG).show();
                    editTextSignUpConfirmPwd.setError("Password confirmation does not match");
                    editTextSignUpConfirmPwd.requestFocus();
                    editTextSignUpConfirmPwd.clearComposingText();
                } else {
                    textGend = radioButtonSignupSelectedGend.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(textFullName, textEmail, textDoB, textGend, textMobile, textPwd);
                }
            }
        });
    }

    private void registerUser(String textFullName, String textEmail, String textDoB, String textGend, String textMobile, String textPwd) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail, textPwd).addOnCompleteListener(SignUpActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "User Signed Up Successfully", Toast.LENGTH_LONG).show();
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            firebaseUser.sendEmailVerification();
                            /*
                            Intent intent = new Intent(SignUpActivity.this, UserProfileActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                            */
                        } else {
                            Toast.makeText(SignUpActivity.this, "Sign Up Failed", Toast.LENGTH_LONG).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}