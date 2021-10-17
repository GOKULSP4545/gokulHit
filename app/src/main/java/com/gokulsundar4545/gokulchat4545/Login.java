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


        //============================================================================================================

        ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        if (networkInfo==null||!networkInfo.isConnected()||!networkInfo.isAvailable())
        {
            Dialog dialog=new Dialog(this);
            dialog.setContentView(R.layout.inter_alart);
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().getAttributes().windowAnimations= android.R.style.Animation_Dialog;
            Button Button;
            Button = dialog.findViewById(R.id.Button);
            Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recreate();

                }
            });

            dialog.show();
        }
        //=============================================================================================================




        ForgetPassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ResetPassword.class);
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


    private void checkCredentials () {


        String Email = edtUserEamil.getText().toString();
        String password = edtUserPassword.getText().toString();


        if (Email.isEmpty() || !Email.contains("@")) {
            showError(edtUserEamil, "Email is inValid");
        } else if (password.isEmpty() || password.length() < 7) { ;
            showError(edtUserPassword, "passWord in must be 7Characters");
        } else {
            mLoadingBar.setTitle("Login");
            mLoadingBar.setMessage("Please Waite,While check Your Credentials");
            mLoadingBar.show();

            mAuth.signInWithEmailAndPassword(Email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Login.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                        mLoadingBar.dismiss();



                        Intent intent=new Intent(Login.this,Chatlist.class);
                        startActivity(intent);
                        finish();


                    }
                    else {
                        mLoadingBar.dismiss();
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
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

