package com.gokulsundar4545.gokulchat4545;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;

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
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;
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

    private androidx.appcompat.widget.Toolbar  toolbar;
    private de.hdodenhof.circleimageview.CircleImageView ProfileImageView;
    private TextView txtName;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    //==================================================================================================================
    //Fragement
    androidx.viewpager.widget.ViewPager ViewPager;
    com.google.android.material.tabs.TabLayout tablayout;
    PageAdapter pageAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);


        txtName=findViewById(R.id.loginname);
        ProfileImageView=findViewById(R.id.dp);

        tablayout=findViewById(R.id.tablayout) ;
        ViewPager=findViewById(R.id.Viewpage);
        toolbar=findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);

        tablayout.addTab(tablayout.newTab().setText("CHATS"));
        tablayout.addTab(tablayout.newTab().setText("STATUS"));
        tablayout.addTab(tablayout.newTab().setText("CALL"));

        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        PageAdapter pageAdapter=new PageAdapter(getSupportFragmentManager(),tablayout.getTabCount());

        ViewPager.setAdapter(pageAdapter);
        ViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));

        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                ViewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        mAuth=FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("User");

        //==================================================================================================================

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
        //=======================================================================================================================


        getUserinfo();










    }


//================================================================================================================================

    //to dispaly User Profile

    private void getUserinfo() {

        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount() >0){

                    String name=dataSnapshot.child("username").getValue().toString();
                    txtName.setText(name);


                    if(dataSnapshot.hasChild("image"))
                    {

                        String image=dataSnapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(ProfileImageView);
                    }
                }

            }

            @Override
            public void onCancelled( DatabaseError error) {

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










