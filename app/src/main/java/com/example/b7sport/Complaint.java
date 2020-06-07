package com.example.b7sport;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class Complaint extends AppCompatActivity {
    EditText mEntireMsg;
    Button mSendmsg,mCancelbtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        mEntireMsg = findViewById(R.id.entire_msg);
        mSendmsg = findViewById(R.id.send_btn);
        mCancelbtn = findViewById(R.id.cancelsend_btn);


        Bundle bundle = getIntent().getExtras();

        final String userID1 = bundle.getString("emailadd");

        final Intent intent1 = new Intent(Complaint.this,MainActivity.class);
        intent1.putExtra("emailadd",userID1);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Complains");


        mSendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                String messsage = mEntireMsg.getText().toString().trim();
                if(TextUtils.isEmpty(messsage)){
                    mEntireMsg.setError("הודעה ריקה,נא לכתוב משהו!");
                    return;
                }
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("Message",messsage);
                map.put("Email",userID1);

                databaseReference.push().setValue(map);
                Toast.makeText(Complaint.this,"תלונה נשלחה בהצלחה!",Toast.LENGTH_SHORT).show();
                startActivity(intent1);
                //finish();
                }catch(Exception e){
                    Toast.makeText(Complaint.this,"",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mCancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
                finish();
            }
        });

    }
}
