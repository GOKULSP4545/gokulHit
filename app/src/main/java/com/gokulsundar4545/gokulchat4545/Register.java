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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    TextView Allreadybtn;
    private EditText inputUserName1, inputEmail1, inputPassWord1, inputConformPassWord1,PhoneNo1;
    Button btnRegister;
    FirebaseAuth mAuth;
    ProgressDialog mLoadindBar;

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("User");


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user=mAuth.getCurrentUser();
        if (user!=null) {
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }


    }







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        Allreadybtn = findViewById(R.id.AlreadyYouHaveAnAccount);
        inputUserName1 = findViewById(R.id.inputName);
        inputEmail1 = findViewById(R.id.inputEmail);
        inputPassWord1 = findViewById(R.id.inputPassWord);
        inputConformPassWord1 = findViewById(R.id.inputConformPassWord);
        PhoneNo1=findViewById(R.id.PhoneNo);
        mAuth = FirebaseAuth.getInstance();
        mLoadindBar = new ProgressDialog(Register.this);
        btnRegister = findViewById(R.id.btnRegistration);

        //==========================================================================================================

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


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials();

            }


        });


        Allreadybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);

            }
        });
    }


    private void checkCredentials() {

        String Username = inputUserName1.getText().toString();
        String Email = inputEmail1.getText().toString();
        String Password = inputPassWord1.getText().toString();
        String ConformPassWord = inputConformPassWord1.getText().toString();
        String PhoneNumber = PhoneNo1.getText().toString();

        if (Username.isEmpty() || Username.length() < 7) {
            showError(inputUserName1, "User name is invalid!");
        } else if (Email.isEmpty() || !Email.contains("@")) {
            showError(inputEmail1, "Email is invalid");
        } else if (Password.isEmpty() || Password.length() < 7) {
            showError(inputPassWord1, "Enter a Strong password");
        } else if (ConformPassWord.isEmpty() || !ConformPassWord.equals(Password)) {
            showError(inputConformPassWord1, "password does not match!");
        } else if (PhoneNumber.isEmpty() || PhoneNumber.length() < 10 || PhoneNumber.length() > 10) {
            showError(PhoneNo1, "Mobile  is invalid");
        } else {
            mLoadindBar.setTitle("Registration");
            mLoadindBar.setMessage("Please Waite,While check Your Credentials");
            mLoadindBar.setCanceledOnTouchOutside(false);
            mLoadindBar.show();


            mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        final String uid = currentUser.getUid();
                        UserClass UserClass = new UserClass(inputUserName1.getText().toString(), inputEmail1.getText().toString(), inputPassWord1.getText().toString(), inputConformPassWord1.getText().toString(), PhoneNo1.getText().toString());
                        databaseReference.child(uid).setValue(UserClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Intent intent = new Intent(Register.this, Chatlist.class);
                                    startActivity(intent);
                                    finish();

                                } else if (task.getException() != null) {
                                    mLoadindBar.dismiss();
                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }

                            }
                        });
                    } else if (task.getException() != null) {
                        mLoadindBar.dismiss();

                        Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            });


        }
    }


    private void showError (EditText input, String s){
        input.setError(s);
        input.requestFocus();



    }
}