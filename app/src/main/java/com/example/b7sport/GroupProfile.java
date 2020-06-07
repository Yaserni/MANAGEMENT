package com.example.b7sport;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class GroupProfile extends AppCompatActivity {
    public TextView textid, textName, textType, textStreet,textNeighborh,textActivity,textLighting,textSportType,groupname ,numberofplayers,isprivate;//I dont know if I must add the lat and lon
    Button mJoinGroup,mCancelJpinGroup;
    String userID1;
    FirebaseDatabase firebaseDatabase;
    static  Group selectedgl;
    static String key;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    Button showpart,showmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_profile);
        firebaseDatabase = FirebaseDatabase.getInstance();

/*
        Bundle bundle = getIntent().getExtras();
        userID1 = bundle.getString("emailadd");
        final Intent myIntent = new Intent(GroupProfile.this, MainActivity.class);
        myIntent.putExtra("emailadd", userID1);
*/

        textName =findViewById(R.id.gr_name3);
//        linear1 = findViewById(R.id.linear3);

        textName =findViewById(R.id.gr_name3);

        textType = findViewById(R.id.gr_type3);
        textStreet = findViewById(R.id.gr_street3);
        textNeighborh =findViewById(R.id.gr_gr_neighbor3);
        textActivity = findViewById(R.id.gr_activity3);
        textSportType =findViewById(R.id.gr_sporttype3);
        textLighting = findViewById(R.id.gr_lighting3);
        textid = findViewById(R.id.gr_id3);
        groupname = findViewById(R.id.sg_grname3);
        numberofplayers = findViewById(R.id.sg_playersnumber3);
        isprivate = findViewById(R.id.sg_isprivate3);

        mJoinGroup = findViewById(R.id.JoinpGroup);
        showpart =findViewById(R.id.showParticipants);
        showmap =findViewById(R.id.showmap);

        textid.setText(String.valueOf(GroupAdapter.selected_group.getArenaid()));
        textName.setText("שם מגרש : " + GroupAdapter.selected_group.getArenaname());
        textType.setText("סוג מגרש : " +String.valueOf(GroupAdapter.selected_group.getArenatype()));
        textStreet.setText("כביש : " +String.valueOf(GroupAdapter.selected_group.getArenastreet()));
        textNeighborh.setText("שכונה : " +GroupAdapter.selected_group.getArenaneighbor());
        textActivity.setText("פעילות : " +GroupAdapter.selected_group.getArenaactivity());
        textLighting.setText("תאורה : " +GroupAdapter.selected_group.getArenalighing());
        textSportType.setText("סוג ספורט : " +GroupAdapter.selected_group.getArenasport_type());
        groupname.setText("שם קבוצה: " + GroupAdapter.selected_group.getGroupname());
        numberofplayers.setText("מספר שחקנים בקבוצה: " + GroupAdapter.selected_group.getPlayersnumber());


        final Intent myIntent = new Intent(GroupProfile.this, RecyclerViewGroup.class);
        myIntent.putExtra("emailadd", MainActivity.emailID);

        boolean privateGroup = GroupAdapter.selected_group.isIsprivate();
        if(privateGroup)
            isprivate.setText("קבוצה פרטית");
        else
            isprivate.setText("קבוצה ציבורית");
        String groupp = GroupAdapter.selected_group.getNodeKey().toString();
        final int numofPlayers = GroupAdapter.selected_group.getPlayersnumber();
        String GroupPath = "Groups" +"/"+ groupp;
        databaseReference = firebaseDatabase.getReference(GroupPath);
        databaseReference1 = firebaseDatabase.getReference(GroupPath +"/Participants");


        mCancelJpinGroup=findViewById(R.id.CancelJoin);
        mJoinGroup = findViewById(R.id.JoinpGroup);

        mCancelJpinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(myIntent);
                finish();
            }
        });

        showpart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ShowParticipants.class));
            }
        });


        showmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ArenaMapsActivity.class));
            }
        });

        mJoinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String groupid = String.valueOf(GroupAdapter.selected_group.getArenaid());
                final Map<String, Object> map = new HashMap<String, Object>();
                map.put("UserEmail", MainActivity.emailID);

                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data1 : dataSnapshot.getChildren()) {
                            if (MainActivity.emailID.equals(data1.child("UserEmail").getValue().toString())) {
                                Toast.makeText(GroupProfile.this, "Already in Group!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (numofPlayers == (int)data1.getChildrenCount()) {
                                Toast.makeText(GroupProfile.this, "Group is full!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        }
                        if(GroupAdapter.selected_group.isIsprivate()){
                            //  if(true){

                            final String groupPassword = GroupAdapter.selected_group.getSecretcode();

                            final EditText resetEmail = new EditText((v.getContext()));
                            resetEmail.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            //     resetEmail.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                            passwordResetDialog.setTitle("Group Password");
                            passwordResetDialog.setMessage("Password of the group");
                            passwordResetDialog.setView(resetEmail);

                            passwordResetDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Extracting the email
                                    String mail = resetEmail.getText().toString();
                                    try{
                                        if(mail.equals(groupPassword)){
                                            Toast.makeText(GroupProfile.this, "Currect Password!", Toast.LENGTH_SHORT).show();
                                            databaseReference.child("Participants").push().setValue(map);
                                            Toast.makeText(GroupProfile.this, "Joined to Group!", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(GroupProfile.this, "Wronge Password!", Toast.LENGTH_SHORT).show();
                                        }
                                    }catch(Exception ex){
                                        ex.printStackTrace();
                                    }

                                }
                            });
                            passwordResetDialog.setNegativeButton("בטל", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Close the dialog

                                }
                            });

                            passwordResetDialog.create().show();
                        }else{
                            databaseReference.child("Participants").push().setValue(map);
                            Toast.makeText(GroupProfile.this, "Joined to Group!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

/*
                mCancelJpinGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  startActivity(myIntent);
                    }
                });
*/
            }
        });
    }


}
