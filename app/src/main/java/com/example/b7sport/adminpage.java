package com.example.b7sport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class adminpage extends AppCompatActivity {

    Button b ;
    Button mesg;
    Button re;
    Button sH;
    Button logout;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpage);
        b= findViewById(R.id.button);
        mesg=findViewById(R.id.M);
        re=findViewById(R.id.Report);
        sH=findViewById(R.id.show_group);
        logout=findViewById(R.id.Log_out);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),blockuser.class));
            }
        });



        mesg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getApplicationContext(), Mesg.class));
            }
        });

        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Card.class));

            }
        });

        sH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Cardadmin.class));
            }
        });


       logout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),Login.class));

           }
       });
    }
}
