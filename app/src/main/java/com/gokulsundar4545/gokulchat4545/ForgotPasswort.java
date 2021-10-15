package com.gokulsundar4545.gokulchat4545;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswort extends AppCompatActivity {

    private EditText Email1;
    private Button Reset_PassWord;
    ProgressBar progress_Bar;
    FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_passwort);

        Email1 = findViewById(R.id.Email);
        Reset_PassWord = findViewById(R.id.Reset_PassWord);
        progress_Bar=findViewById(R.id.progressBar);

        Auth = FirebaseAuth.getInstance();


        Reset_PassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPassWord();
            }
        });


    }

    private void ResetPassWord() {

        String UserEmail = Email1.getText().toString().trim();

        if (UserEmail.isEmpty()) {
            Email1.setError("Email is Requried!");
            Email1.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(UserEmail).matches()) {
            Email1.setError("Please Provide Valid Email");
            Email1.requestFocus();
            return;
        }
        progress_Bar.setVisibility(View.VISIBLE);

        Auth.sendPasswordResetEmail(UserEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPasswort.this, "Check Your Email to reset your PassWord", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ForgotPasswort.this, "Try again!,Something Went Wrong", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

}



