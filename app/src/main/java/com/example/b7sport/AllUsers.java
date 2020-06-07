package com.example.b7sport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllUsers extends AppCompatActivity {

    Button mBackbtn;
    //Recycler View
    RecyclerView mRecyclerView;
    List<InfoFromDataBase> usersinfo = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore fStore;
    UsersAdapter adapeter;
    ProgressDialog pd;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);


        pd = new ProgressDialog(this);

        mBackbtn = findViewById(R.id.backbtn);
        //Intialazing
        mRecyclerView= findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        show();


        mBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), adminpage.class));
                finish();
            }
        });


    }

    private void showdata() {
        fStore = FirebaseFirestore.getInstance();

        pd.setTitle("טוען נתונים...");
        pd.show();
        pd.setCancelable(false);
        fStore.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        try{
                        pd.dismiss();
                        for (DocumentSnapshot doc : task.getResult()) {
                            InfoFromDataBase info = new InfoFromDataBase(doc.getString("Email"),
                                    doc.getString("PhoneNumber"),
                                    doc.getString("FullName"),
                                    doc.getString("Adress"));
                            usersinfo.add(info);
                        }
                        adapeter = new UsersAdapter(AllUsers.this, usersinfo);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        mRecyclerView.setAdapter(adapeter);
                        }catch(Exception e){

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(AllUsers.this, "Error Loading the Info!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void show() {
        pd.setTitle("טוען נתונים...");
        pd.show();
        pd.setCancelable(false);

        reference = FirebaseDatabase.getInstance().getReference().child("EDMT_FIREBASE");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    InfoFromDataBase data1 = data.getValue(InfoFromDataBase.class);
                    usersinfo.add(data1);
                }

                adapeter = new UsersAdapter(AllUsers.this, usersinfo);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                mRecyclerView.setAdapter(adapeter);
                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AllUsers.this, "Error Loading the Info!", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
