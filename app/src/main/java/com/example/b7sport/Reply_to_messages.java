package com.example.b7sport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Reply_to_messages extends AppCompatActivity {


    EditText inP;
    // TextView te;
    Button sendB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_to_messages);

        final String  email=getIntent().getExtras().getString("email");
        //String email="a";
        inP=findViewById(R.id.t);
        sendB=findViewById(R.id.S_B);

        final FirebaseDatabase data = FirebaseDatabase.getInstance();
        final DatabaseReference ref = data.getReference("Complains_ANS");

         sendB.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Ans_body info=new Ans_body(email,inP.getText().toString());
                 ref.push().setValue(info);
                 startActivity(new Intent(getApplicationContext(),adminpage.class));

             }
         });











    }
}
