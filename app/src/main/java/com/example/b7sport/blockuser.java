package com.example.b7sport;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.awt.font.TextAttribute;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class blockuser extends AppCompatActivity {

    EditText Name;
    Button but_block;
    Button btn_delete;
    Button btn_unblock;
    String Name1;
    final FirebaseDatabase data = FirebaseDatabase.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blockuser);

        Name=findViewById(R.id.nblock);
        but_block=findViewById(R.id.Bblock);
        btn_delete=findViewById(R.id.deltebtn);
        btn_unblock=findViewById(R.id.Unblock);
        final DatabaseReference ref = data.getReference("EDMT_FIREBASE");
        final DatabaseReference ref1 = data.getReference("EDMT_FIREBASE");


        but_block.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Name1=Name.getText().toString().trim();
                        String name1="";
                        String name2;
                        int flag=0;
                        for (DataSnapshot d : dataSnapshot.getChildren())
                        {
                            name2=d.child("email").getValue().toString();

                            if(name2.equals(Name1) )
                            {
                                flag=1;
                                name1=d.getKey().toString();
                                ref1.child(name1).child("flag").setValue("1");
                                Toast.makeText(blockuser.this, "User Blocked!", Toast.LENGTH_SHORT).show();
                            }

                        }

                        startActivity(new Intent(getApplicationContext(),adminpage.class));
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }


                });





            }


        });




        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Name1=Name.getText().toString().trim();
                        String name1="";
                        String name2;
                        int flag=0;
                        for (DataSnapshot d : dataSnapshot.getChildren())
                        {
                            name2=d.child("email").getValue().toString();

                            if(name2.equals(Name1) )
                            {
                                flag=1;
                                name1=d.getKey().toString();

                            }

                        }
                        if(flag==1) {

                            ref1.child(name1).removeValue();


                        }

                        else
                            Log.d("","falied");


                        startActivity(new Intent(getApplicationContext(),adminpage.class));


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }


                });






            }
        });


        btn_unblock.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Name1=Name.getText().toString().trim();
                        String name1="";
                        String name2;
                        int flag=0;
                        for (DataSnapshot d : dataSnapshot.getChildren())
                        {
                            name2=d.child("email").getValue().toString();

                            if(name2.equals(Name1) )
                            {
                                name1=d.getKey().toString();
                                ref1.child(name1).child("flag").setValue("0");
                                Toast.makeText(blockuser.this, "User UNBlocked!", Toast.LENGTH_SHORT).show();
                            }

                        }


                        startActivity(new Intent(getApplicationContext(),adminpage.class));
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }


                });





            }



        });


    }





}
