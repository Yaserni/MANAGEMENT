package com.example.b7sport;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class Login extends AppCompatActivity {
    EditText mEmail,mPassword;
    TextView mRegisterActivity,mPasswordRecovery;
    Button mLoginButton;
    FirebaseAuth fAuth;
    ProgressDialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dialog = new ProgressDialog(this);

        mEmail = findViewById(R.id.EmailText);
        mPassword = findViewById(R.id.PasswordText);
        mRegisterActivity = findViewById(R.id.RegisterLink);
        mLoginButton = findViewById(R.id.LoginButton);
        mPasswordRecovery = findViewById(R.id.PasswordRecovery);
        fAuth = FirebaseAuth.getInstance();

        Intent intent1 = new Intent(Login.this,MainActivity.class);
        String emailas  = mEmail.getText().toString();
        intent1.putExtra("emailadd",emailas);

      //  if(fAuth.getCurrentUser()!=null){
       //     startActivity(intent1);
       //     finish();
       // }

        mLoginButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                String email = mEmail.getText().toString().trim();

                String password = mPassword.getText().toString().trim();
                if (EmailisEmpty(email)) return;

                if (PasswordIsEmpty(password)) return;

                dialog.setMessage("Loging in...");
                dialog.show();
                final Intent myIntent = new Intent(view.getContext(),MainActivity.class);
                myIntent.putExtra("emailadd",email);

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required");
                    return;
                }
                if(email.equals("admin")&&password.equals("admin")){
                    Intent intent = new Intent(view.getContext(),adminpage.class);
                    Toast.makeText(Login.this,"Loged in Successfully.",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else{

//                if (password.length() <= 6) {
//                    mPassword.setError("Password Must be longer than 6 chars!");
//                    return;
//                }
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            dialog.dismiss();

                            Toast.makeText(Login.this,"Loged in Successfully.",Toast.LENGTH_SHORT).show();
                            startActivity(myIntent);
                            //                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            dialog.dismiss();
                            Toast.makeText(Login.this,"Error ! " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                }
            }
        });
        mRegisterActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
        mPasswordRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetEmail = new EditText((v.getContext()));
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("שחזור סיסמה");
                passwordResetDialog.setMessage("כתוב את המייל שלך כדי לקבל מייל לשחזור סיסמה.");
                passwordResetDialog.setView(resetEmail);

                passwordResetDialog.setPositiveButton("שלח", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Extracting the email
                        String mail = resetEmail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this, "קישור שחזור סיסמה נשלח למייל!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Error! Reset Link is not sent" + e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("בטל", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Close the dialog

                    }
                });
//                AlertDialog dialog = passwordResetDialog.create();
//                dialog.getWindow().setGravity(Gravity.RIGHT);
//                dialog.show();
                passwordResetDialog.create().show();

            }
        });

    }
    public boolean EmailisEmpty(String email){
        if(TextUtils.isEmpty(email)){
            mEmail.setError("חובה למלות שדה זה");
            return true;
        }
        return false;
    }
    public boolean PasswordIsEmpty(String email){
        if(TextUtils.isEmpty(email)){
            mPassword.setError("חובה למלות שדה זה");
            return true;
        }
        return false;
    }

}
