package com.example.b7sport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class welcome_pag extends AppCompatActivity {
    final FirebaseDatabase data = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_pag);

        final DatabaseReference ref = data.getReference("EDMT_FIREBASE");
            ref.child("test").setValue("2");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                     String name2;
                     String glag;

                for (DataSnapshot d : dataSnapshot.getChildren())
                {
                    name2=d.child("email").getValue().toString();

                    if(name2.equals(Login.Email) )
                    {
                        startActivity(new Intent(getApplicationContext(),Login.class));

                    }

                }

                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });





    }
}
