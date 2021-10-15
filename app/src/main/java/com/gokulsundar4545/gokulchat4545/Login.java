package com.gokulsundar4545.gokulchat4545;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {


    TextView signbtn, ForgetPassWord;
    EditText edtUserEamil, edtUserPassword;
    Button Login;
    private FirebaseAuth mAuth;
    ProgressDialog mLoadingBar;
    ProgressBar progress_Bar;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(getApplicationContext(), Chatlist.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        signbtn = findViewById(R.id.signUp);
        edtUserEamil = findViewById(R.id.inputEmail);
        edtUserPassword = findViewById(R.id.inputPassWord);
        Login = findViewById(R.id.Login);
        mLoadingBar = new ProgressDialog(Login.this);
        mAuth = FirebaseAuth.getInstance();
        ForgetPassWord = findViewById(R.id.Forgot);
        progress_Bar = findViewById(R.id.progressBar);




        ForgetPassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgotPasswort.class);
                startActivity(intent);

            }
        });


        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);

            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials();

            }
        });


    }


    private void checkCredentials() {


        String Email = edtUserEamil.getText().toString();
        String Password = edtUserEamil.getText().toString();


        if (Email.isEmpty() || !Email.contains("@")) {
            showError(edtUserEamil, "Email is invalid");
        } else if (Password.isEmpty()){
            showError(edtUserPassword, "Enter a Strong password");
        } else {
            mLoadingBar.setTitle("Login");
            mLoadingBar.setMessage("Please Wait,While check Your Credentials");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(Login.this, Chatlist.class);
                        startActivity(intent);
                        finish();


                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        mLoadingBar.dismiss();
                    }

                }
            });

        }
    }


    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();


    }

}

