package com.example.b7sport;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText mFullName,mEmail,mPassword,mPhonenumber,mAddress;
    Button mRegisterbtn;
    TextView alreadyRegistered;
    FirebaseAuth fAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseFirestore fStore;
    String UserID;
    public final String TAG = "TAG";

    String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
     Logic l=new Logic();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.fName);
        mEmail = findViewById(R.id.eMail);
        mPassword = findViewById(R.id.passWord);
        mPhonenumber = findViewById(R.id.phoneNumber2);
        mRegisterbtn = findViewById(R.id.Registerbtn);
        alreadyRegistered = findViewById(R.id.alreadyRegistred);
        mAddress = findViewById(R.id.address);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("EDMT_FIREBASE");
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

//        if(fAuth.getCurrentUser()!=null){
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            finish();
//        }

        mRegisterbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

               // Info info = new Info(email,PhoneNumber,Name,password);
            //    databaseReference.push().setValue(info);
                final Intent myIntent = new Intent(view.getContext(),MainActivity.class);
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                final String Name = mFullName.getText().toString().trim();
                final String PhoneNumber = mPhonenumber.getText().toString().trim();
                final String Address = mAddress.getText().toString().trim();

                myIntent.putExtra("email",email);
                if(l.EmailRequired(email)) return;
                if(l.PasswordIsEmpty(password)) return;
                if(l.PasswordLength(password)) return;
                if(l.EmailRegex(email)){
                     mEmail.setError("The Format of the email must be example@example.com");
                    return;
                }
                if(l.CheckName(Name)) return;

                //Info info = new Info(email,PhoneNumber,Name,password,Address,"0","0");

                if(l.EmailRegex(email)) return;
                if(l.CheckName(Name)) return;



                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            Info info = new Info(email,PhoneNumber,Name,password,"0","0");
//                            databaseReference.push().setValue(info);
                            Map<String,Object> map = new HashMap<String,Object>();
                            map.put("email",email);
                            map.put("PhoneNumber",PhoneNumber);
                            map.put("FullName",Name);
                            map.put("password",password);
                            map.put("address",Address);
                            map.put("UserID",0);
                            map.put("flag",0);

                            databaseReference.push().setValue(map);
                            Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                            UserID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentrefernce = fStore.collection("users").document(UserID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("FullName", Name);
                            user.put("Address",Address);
                            user.put("Email", email);
                            user.put("PhoneNumber", PhoneNumber);
                            user.put("Password", password);


                            documentrefernce.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"onSuccess : user Profile is created for" + UserID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: "+ e.toString());
                                }
                            });

                            startActivity(myIntent);
                            finish();
//                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(Register.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        alreadyRegistered.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();

            }
        });

    }


}
