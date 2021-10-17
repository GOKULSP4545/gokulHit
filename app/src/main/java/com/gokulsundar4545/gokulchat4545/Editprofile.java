package com.gokulsundar4545.gokulchat4545;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

public class Editprofile extends AppCompatActivity {

    private de.hdodenhof.circleimageview.CircleImageView ProfileImageView;
    private Button CloseButton,SaveButton;
    private TextView ProfileChangeBtn;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    private Uri imageUri;
    private String myUri ="";
    private StorageTask uploadTask;
    private StorageReference storageProfilePicsRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);


        mAuth=FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("User");
        storageProfilePicsRef= FirebaseStorage.getInstance().getReference().child("Profile Pic");


        ProfileImageView=findViewById(R.id.profile_image);
        CloseButton=findViewById(R.id.btnClose);
        SaveButton=findViewById(R.id.btnSave);
        ProfileChangeBtn=findViewById(R.id.change_profile_btn);

        //Close bUtton ->Back to the mainprofile

        CloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Editprofile.this,Chatlist.class));
            }
        });

        //UploadeProfileImage
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadProfileImage();
            }
        });

        ProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setAspectRatio(1,1).start(Editprofile.this);
            }
        });


        getUserinfo();

    }

    private void getUserinfo() {

        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount() >0){

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
    protected void onActivityResult(int requestCode, int resultCode,   Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK && data!=null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri=result.getUri();
            ProfileImageView.setImageURI(imageUri);

        }else
        {
            Toast.makeText(this, "Error , Try again ", Toast.LENGTH_SHORT).show();
        }
    }

    private void UploadProfileImage() {

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.setMessage("Please wait ,While We are setting your data");
        progressDialog.show();

        if (imageUri != null)
        {
            final StorageReference fileRef=storageProfilePicsRef
                    .child(mAuth.getCurrentUser().getUid()+".jpg");

            uploadTask=fileRef.putFile(imageUri);


            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful())
                    {
                        Uri downloadUrl =task.getResult();
                        myUri = downloadUrl.toString();

                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("image",myUri);
                        databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);
                        progressDialog.dismiss();
                    }
                }
            });
        }
        else {
            progressDialog.dismiss();
            Toast.makeText(this, "Please select an Image to Update", Toast.LENGTH_SHORT).show();
        }

    }





}


