package com.example.b7sport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    private TextView mName,mEmail,mPhonenumber,mAddress;
    Button mUpdateAdrressbtn;
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
        final Intent Address_intent = new Intent(Profile.this,Update_Adress.class);

        myIntent.putExtra("emailadd",userID1);
        mUpdateAdrressbtn = findViewById(R.id.update_address);
        mName = findViewById(R.id.FullName1);
        mEmail = findViewById(R.id.Email1);
        mPhonenumber = findViewById(R.id.PhoneNumber1);
        mAddress = findViewById(R.id.Address1);


        database = FirebaseDatabase.getInstance();
        UserRef = database.getReference(USERS);

        //Init Database
        fStore = FirebaseFirestore.getInstance();

        pd.setTitle("טוען נתונים...") ;
        pd.show();
        pd.setCancelable(false);
        //show(userID1);




        fStore.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot doc: task.getResult()){
                            String dbuser = doc.getString("Email");
                            if(dbuser.equals(userID1)){
                                mName.setText(doc.getString("FullName"));
                                mEmail.setText(doc.getString("Email"));
                                mPhonenumber.setText(doc.getString("PhoneNumber"));
                                mAddress.setText(doc.getString("Address"));
                                Address_intent.putExtra("Address",doc.getString("Email"));
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
                        Toast.makeText(Profile.this,"שגיאה בטעינת נתונים!",Toast.LENGTH_SHORT).show();
                    }
                }) ;

        mUpdateAdrressbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(Address_intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(myIntent);
                        finish();
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
    public void show(final String email) {


        UserRef = FirebaseDatabase.getInstance().getReference().child("EDMT_FIREBASE");
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data1:dataSnapshot.getChildren())
                    if(email == data1.child("email").getValue().toString()){
                        mName.setText(data1.child("FullName").getValue().toString());
                        mEmail.setText(data1.child("email").getValue().toString());
                        mPhonenumber.setText(data1.child("phoneNumber").getValue().toString());
                        mAddress.setText(data1.child("address").getValue().toString());
                        pd.dismiss();
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
