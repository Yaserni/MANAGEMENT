package com.example.b7sport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Profile extends AppCompatActivity {
    private TextView mName,mEmail,mPhonenumber;
    private FirebaseDatabase database;
    private DatabaseReference UserRef;
    FirebaseFirestore fStore;


    private static final String USERS = "EDMT_FIREBASE";
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle bundle = getIntent().getExtras();
        final String userID1 = bundle.getString("emailadd");


        pd = new ProgressDialog(this);

        final Intent myIntent = new Intent(Profile.this,MainActivity.class);
        myIntent.putExtra("emailadd",userID1);

        mName = findViewById(R.id.FullName1);
        mEmail = findViewById(R.id.Email1);
        mPhonenumber = findViewById(R.id.PhoneNumber1);


        database = FirebaseDatabase.getInstance();
        UserRef = database.getReference(USERS);

        //Init Database
        fStore = FirebaseFirestore.getInstance();

        pd.setTitle("טוען נתונים...") ;
        pd.show();
        pd.setCancelable(false);
        //
        //
        /*
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(ds.child("Email").getValue().equals(userID1)){
                        mName.setText(ds.child("FullName").getValue(String.class));
                        mEmail.setText(ds.child("Email").getValue(String.class));
                        mPhonenumber.setText(ds.child("PhoneNumber").getValue(String.class));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        fStore.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //pd.dismiss();
                        for(DocumentSnapshot doc: task.getResult()){
                            String dbuser = doc.getString("Email");
                            if(dbuser.equals(userID1)){
                                mName.setText(doc.getString("FullName"));
                                mEmail.setText(doc.getString("Email"));
                                mPhonenumber.setText(doc.getString("PhoneNumber"));
                                pd.dismiss();
                                break;
                            }
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(Profile.this,"Error Loading the Info!",Toast.LENGTH_SHORT).show();
                    }
                }) ;
        //
        //

      //showdata();
        /*database = FirebaseDatabase.getInstance();
        UserRef = database.getReference(USERS);
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(ds.child("email").getValue().equals(userID1)){
                        mName.setText(ds.child("name").getValue(String.class));
                        mEmail.setText(ds.child("email").getValue(String.class));
                        mPhonenumber.setText(ds.child("phoneNumber").getValue(String.class));
                        pd.dismiss();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(myIntent);
                        overridePendingTransition(0,0);
                        return true;
//                    case R.id.games:
//                        startActivity(new Intent(getApplicationContext(),About.class));
//                        overridePendingTransition(0,0);
//                        return true;
                    case R.id.profile:
                        return true;
                }
                return false;
            }
        });


    }

    /*private void showdata() {

        fStore.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        pd.dismiss();
                        for(DocumentSnapshot doc: task.getResult()){
                            String dbuser = doc.getString("Email");
                            if(userID1.equals(dbuser)){
                                mName.setText(doc.getString("FullName"));
                                mEmail.setText(doc.getString("Email"));
                                mPhonenumber.setText(doc.getString("PhoneNumber"));
                                pd.dismiss();

                            }
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(Profile.this,"Error Loading the Info!",Toast.LENGTH_SHORT).show();
                    }
                }) ;
    }*/
}
