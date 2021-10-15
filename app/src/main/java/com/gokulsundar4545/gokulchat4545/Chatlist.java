package com.gokulsundar4545.gokulchat4545;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chatlist extends AppCompatActivity {


    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    TextView name;
    FirebaseAuth mAuth;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    CircleImageView profile_home;

    androidx.appcompat.widget.Toolbar  toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);


        name=findViewById(R.id.loginname);
        profile_home=findViewById(R.id.profile_image);
        toolbar=findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);


        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("users");
        String Userid=firebaseUser.getUid();


        databaseReference.child(Userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               UserClass Userclass=snapshot.getValue(UserClass.class);
                if (Userclass !=null)
                {
                    String firstname=Userclass.getUsername();
                    name.setText(firstname);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






//to dispaly User Profile


        databaseReference=FirebaseDatabase.getInstance().getReference().child("users");
        storageReference=firebaseStorage.getInstance().getReference().child("Profilepics");

        databaseReference.child(Userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()&& snapshot.getChildrenCount()>0)
                {
                    if (snapshot.hasChild("image"))
                    {
                        String image=snapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(profile_home);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.LogOut:
                Logout();
                break;

            case R.id.Editprofile:

                Intent intent = new Intent(Chatlist.this, Editprofile.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Logout() {
        mAuth.signOut();
        Intent intent = new Intent(Chatlist.this, Login.class);
        startActivity(intent);
        finish();

    }


}










