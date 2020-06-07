package com.example.b7sport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import com.example.b7sport.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.PrintStreamPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.PrintStream;
import java.time.Clock;

public class ChangePassword extends AppCompatActivity {
    EditText NewPass, ConfirmNewPass;
    Button mBack, mChangePass;
    FirebaseAuth auth;
    Login login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        NewPass = findViewById(R.id.newpass);
        ConfirmNewPass = findViewById(R.id.confirmnewpass);
        mBack = findViewById(R.id.Back);
        mChangePass = findViewById(R.id.changePsswordbtn);
        auth = FirebaseAuth.getInstance();

        //Back to the main page
        mBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        mChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String newp = NewPass.getText().toString();
                boolean flag = true;// true if the new password is ok else false
                String cnewp = ConfirmNewPass.getText().toString();

                if (newp.length() <= 6) {
                    flag = false;
                    NewPass.setError("על הסיסמה להיות לפחות 7 אותיות");
                }

                if (!newp.equals(cnewp)) {
                    flag = false;
                    ConfirmNewPass.setError("הסיסמאות איןן זהות..");
                }
                if (flag) {
                    if (user != null) {
                        user.updatePassword(NewPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ChangePassword.this, "Your password has been changed.." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    System.out.println("error");
                                    Toast.makeText(ChangePassword.this, "Your password could not be changed..", Toast.LENGTH_LONG);
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(ChangePassword.this, "סיסמה לא השתנה נסה שוב!!", Toast.LENGTH_LONG);
                }
            }
        }
        );
    }

    public boolean checknewp(String cnewp)
    {
        String newp = "asd123";
        return newp.equals(cnewp);
    }
}
