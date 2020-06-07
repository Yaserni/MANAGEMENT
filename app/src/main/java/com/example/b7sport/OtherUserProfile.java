package com.example.b7sport;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;




import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b7sport.Update_Adress;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class OtherUserProfile extends AppCompatActivity {
    private TextView mName,mEmail,mPhonenumber,mAddress;
    Button mUpdateAdrressbtn,mUploadProfilePic;
    private FirebaseDatabase database;
    private DatabaseReference UserRef;
    FirebaseFirestore fStore;
    ImageView mProfilePictore;
    StorageReference storageReference;
    // static String photoProvider = MainActivity.emailID;
    FirebaseAuth fAuth;
    private static final String USERS = "EDMT_FIREBASE";
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);

        Bundle bundle = getIntent().getExtras();

        // final String userID1 = bundle.getString("emailadd");

        final String userID1 = EmailAdapter.selecteduser.userEmail;
        fAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);

        final Intent myIntent = new Intent(OtherUserProfile.this,MainActivity.class);
        final Intent Address_intent = new Intent(OtherUserProfile.this, Update_Adress.class);

        myIntent.putExtra("emailadd",userID1);
        mUpdateAdrressbtn = findViewById(R.id.update_address);
        mName = findViewById(R.id.FullName1);
        mEmail = findViewById(R.id.Email1);
        mPhonenumber = findViewById(R.id.PhoneNumber1);
        mAddress = findViewById(R.id.Address1);
        mUploadProfilePic = findViewById(R.id.ProfilePictureBTN);

        database = FirebaseDatabase.getInstance();
        UserRef = database.getReference(USERS);
        mProfilePictore = findViewById(R.id.ProfileImage);
        //Init Database
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        pd.setTitle("טוען נתונים...") ;
        pd.show();
        pd.setCancelable(false);
        show(userID1);


        mUpdateAdrressbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(Address_intent);
            }
        });
        mUploadProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    Intent openGallaryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //    startActivityForResult(openGallaryIntent,1000);
                Intent choosePictureIntent = new Intent(Intent.ACTION_GET_CONTENT);
                choosePictureIntent.setType("image/*");
                startActivityForResult(choosePictureIntent, 1);
            }
        });


//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setSelectedItemId(R.id.profile);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.home:
//                        startActivity(myIntent);
//                        finish();
//                        overridePendingTransition(0,0);
//                        return true;
////                    case R.id.games:
////                        startActivity(new Intent(getApplicationContext(),About.class));
////                        overridePendingTransition(0,0);
////                        return true;
//                    case R.id.profile:
//                        return true;
//                }
//                return false;
//            }
//        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode==RESULT_OK){
            Uri imageUri = data.getData();
            //  mProfilePictore.setImageURI(imageUri);

            uploadProfilePhoto(imageUri);
        }
    }

    public void uploadProfilePhoto(Uri imageUri){
        final StorageReference fileRef = storageReference.child(fAuth.getCurrentUser().getUid());
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //     Toast.makeText(Profile.this,"תמונה הועליתה בהצלחה!",Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(mProfilePictore);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OtherUserProfile.this,"Faild for some reason!",Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void show(final String email) {
        StorageReference profileRef = storageReference.child(fAuth.getCurrentUser().getUid());
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(mProfilePictore);
            }
        });


        UserRef = FirebaseDatabase.getInstance().getReference("EDMT_FIREBASE");
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data1:dataSnapshot.getChildren())
                    if(email.equals( data1.child("email").getValue().toString())){
                        mName.setText(data1.child("FullName").getValue().toString());
                        mEmail.setText(data1.child("email").getValue().toString());
                        mPhonenumber.setText(data1.child("PhoneNumber").getValue().toString());
                        mAddress.setText(data1.child("address").getValue().toString());
                        pd.dismiss();
                        break;
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
