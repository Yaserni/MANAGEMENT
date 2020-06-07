package com.example.b7sport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Update_Adress extends AppCompatActivity {
    Button mUpdate,mCancel;
    FirebaseDatabase data = FirebaseDatabase.getInstance();
    EditText mNewAddress;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__adress);
        mUpdate = findViewById(R.id.button2);
        mNewAddress = findViewById(R.id.new_address);
        mCancel = findViewById(R.id.cancel_btn);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        final DatabaseReference ref = data.getReference("EDMT_FIREBASE");
        final DatabaseReference ref1 = data.getReference("EDMT_FIREBASE");

        Bundle bundle = getIntent().getExtras();
        final String email = bundle.getString("Address");
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent(getApplicationContext(), Profile.class));
            }
        });
        mUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("Address",mNewAddress.getText().toString().trim());
                fStore.collection("users").document(fAuth.getUid())
                        .update(map);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String new_adsress = mNewAddress.getText().toString().trim();
                        String name1="";
                        String name2;
                        for (DataSnapshot d : dataSnapshot.getChildren())
                        {
                            name2=d.child("email").getValue().toString();
                            if(myequals(email,name2))
                            {
                                name1=d.getKey().toString();
                                ref1.child(name1).child("address").setValue(new_adsress);
                                Toast.makeText(Update_Adress.this, "Address Updated!", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        startActivity(new Intent(getApplicationContext(),Profile.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    public boolean myequals(String str1,String str2){
        if(str1.length()!=str2.length())
            return false;
        else if(str1.length() == str2.length())
        {
            for(int i=0;i<str1.length();i++){
                if(str1.charAt(i) !=  str2.charAt(i))
                    return false;
            }
        }
        return true;
    }
}
