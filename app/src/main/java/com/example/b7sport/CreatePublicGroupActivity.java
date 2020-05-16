package com.example.b7sport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class CreatePublicGroupActivity extends AppCompatActivity {
    DatabaseReference firebaseDatabase;

    public TextView textid, textName, textType, textStreet,textNeighborh,textActivity,textLighting,textSportType,secretcode;//I dont know if I must add the lat and lon
    Button selctgrbtn;
    TextView secretTextView;
    EditText group_p_number,group_name;

    RadioButton privateG;
    RadioButton publicG;
    Arena arena;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_public_group);


        textName = findViewById(R.id.gr_name1);
        textType = findViewById(R.id.gr_type1);
        textStreet = findViewById(R.id.gr_street1);
        textNeighborh =findViewById(R.id.gr_gr_neighbor1);
        textActivity = findViewById(R.id.gr_activity1);
        textSportType = findViewById(R.id.gr_sporttype1);
        textLighting = findViewById(R.id.gr_lighting1);
        textid= findViewById(R.id.gr_id1);
        selctgrbtn= findViewById(R.id.createg1);
        group_name = findViewById(R.id.group_name);
        group_p_number = findViewById(R.id.players_number);
        privateG= findViewById(R.id.radioprivate);
        publicG= findViewById(R.id.radiopublic);
        secretcode=findViewById(R.id.secretcode);
        secretTextView= findViewById(R.id.secrettext);

        //put the values ...
        privateG.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked) {
                    secretcode.setVisibility(View.INVISIBLE);
                    secretTextView.setVisibility(View.INVISIBLE);
                }
                else{
                    secretcode.setVisibility(View.VISIBLE);
                    secretTextView.setVisibility(View.VISIBLE);
                }

            }
        });
        //put the values ...


        arena= RecyclerViewArena.groundList.get(ArenaAdapter.id);
        textName.setText("שם מגרש : " + arena.getName());
        textType.setText("סוג מגרש : " +String.valueOf(arena.getType()));
        textStreet.setText("כביש : " +String.valueOf(arena.getStreet()));
        textNeighborh.setText("שכונה : " +arena.getNeighbor());
        textActivity.setText("פעילות : " +arena.getActivity());
        textLighting.setText("תאורה : " +arena.getLighing());
        textSportType.setText("סוג ספורט : " +arena.getSport_type());
        textid.setVisibility(View.INVISIBLE);


        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Groups");

        selctgrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name =group_name.getText().toString();
                String n = group_p_number.getText().toString();
                if(CheckGrName(name)==true && CheckNumber(n)==true) {
                    getId();
                    int number = Integer.parseInt( group_p_number.getText().toString());
                    boolean isPrivate=!(publicG.isChecked());
                    Group g = Group.makeGroup(name, Integer.toString(id) , number, isPrivate, arena);

                    //                    synchronized (firebaseDatabase) {}
                    if(isPrivate)
                        g.setSecretcode(secretcode.getText().toString());
                    else g.setSecretcode("");

                    firebaseDatabase.push().setValue(g);
                    Toast.makeText(CreatePublicGroupActivity.this, "Data inserted successfully", Toast.LENGTH_LONG).show();
                    Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
//                else startActivity();
            }
        });

    }
    public boolean CheckGrName(String name)
    {
        if(name.equals("") || name == null)
        {
            group_name.setError("חובה למלות שדה זה");
            return false;
        }
        else
        {
            return true;
        }

    }

    public boolean CheckNumber(String num)
    {
        if(!num.equals("") && num !=null)
        {
            int n = Integer.parseInt(num);
            if(n > 0)
                return true;
            else
            {
                group_p_number.setError("מספר שחקנים חייב להיות גדול מאפס");
                return false;
            }
        }
        else {
            group_p_number.setError("חייב למלא השדה הזה ");
            return false;
        }

    }

    public void getId()
    {
        final FirebaseDatabase data = FirebaseDatabase.getInstance();
        //final DatabaseReference ref1 = data.getReference("Groups");
        final DatabaseReference ref = data.getReference("Groups/id");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    id = Integer.parseInt(dataSnapshot.getValue().toString());




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        ref.setValue(Integer.toString(id+1));

    }

}


