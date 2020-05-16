package com.example.b7sport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Mesg extends AppCompatActivity {
    EditText mesg;
    Button Send;
    final FirebaseDatabase data = FirebaseDatabase.getInstance();
    final DatabaseReference ref = data.getReference("Message");
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        mesg=findViewById(R.id.M);
        Send=findViewById(R.id.send);

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg= mesg.getText().toString();
                ref.setValue(msg);
                startActivity(new Intent(getApplicationContext(),adminpage.class));


            }
        });

















    }



}
