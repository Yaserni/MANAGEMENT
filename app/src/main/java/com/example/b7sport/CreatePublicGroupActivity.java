package com.example.b7sport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.app.TimePickerDialog;
import android.app.DialogFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class CreatePublicGroupActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{
    DatabaseReference firebaseDatabase;

    public TextView textid, textName, textType, textStreet,textNeighborh,textActivity,textLighting,textSportType,secretcode;//I dont know if I must add the lat and lon
    Button selctgrbtn,starthour,endhour;
    TextView secretTextView;
    EditText group_p_number,group_name;
    TextView textView ;
    RadioButton privateG;
    RadioButton publicG;
    Arena arena;
    int id;
    final FirebaseDatabase data = FirebaseDatabase.getInstance();

    ArrayList<helper> helpers;
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


        starthour = findViewById(R.id.starthour);
        endhour = findViewById(R.id.endhour);
        Date d = new Date();
        starthour.setText( "00:00");
        endhour.setText( "00:00");
        starthour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView=starthour;
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getFragmentManager(), "time picker");
            }
        });

        endhour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView=endhour;
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getFragmentManager(), "time picker");
            }
        });
    getDataFromFireBaseidstarth();
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
                    g.setStarthour(starthour.getText().toString());
                    g.setEndhour(endhour.getText().toString());
                    if(!checkhour(g.getStarthour(),g.getEndhour()))
                    {
                        selctgrbtn.setError("או שהמגרש שמור בשעות אלה או שיש שגיאה בנתונים שהכנסת!!");
                        return;
                    }



                    firebaseDatabase.push().setValue(g);
                    Toast.makeText(CreatePublicGroupActivity.this, "Data inserted successfully", Toast.LENGTH_LONG).show();
                    Intent intent =new Intent(getApplicationContext(),RecyclerViewGroup.class);
                    startActivity(intent);
                }
//                else startActivity();
            }
        });

    }
    private void getDataFromFireBaseidstarth() {
        final ProgressDialog progressDialog = new ProgressDialog(this);

        final DatabaseReference ref = data.getReference("Groups");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int arenaid;
                String starth, endh;
                helpers = new ArrayList<>();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    if(d.getKey().equals("id")) break;

                    arenaid = Integer.parseInt(d.child("arenaid").getValue().toString());
                    starth = d.child("starthour").getValue().toString();
                    endh = d.child("endhour").getValue().toString();
                    if(arenaid==arena.getId())
                    {
                        helpers.add(new helper(starth,endh,arenaid));
                    }
                }

                int []a=new int[2];
        //        parsehour("12:30",a);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

public boolean checkhour(String starth,String endh)
{
    int []sth=new int[2];//start hour
    int []enh=new int[2];//end hour
    parsehour(starth,sth);
    parsehour(endh,enh);
//    if(sth[0]>enh[0] ||(sth[0]==enh[0] && sth[1]>=enh[1]))
    int otherstartm,startm=calcminutes(sth);
    int otherendm,endm =calcminutes(enh);
    if(startm >= endm)
    {
        Toast.makeText(CreatePublicGroupActivity.this, "שעת התחלה חייבת להיות לפני שעת סיום", Toast.LENGTH_LONG).show();
        return false;
    }
    int []sth1=new int[2];//start hour
    int []enh1=new int[2];//end hour

//if(helpers!=null)
    helper h ;
    for (int i=0; i< helpers.size();i++)
    {
        h=helpers.get(i);
        parsehour(h.starthour,sth1);
        parsehour(h.endhour,enh1);

        otherstartm = calcminutes(sth1);
        otherendm = calcminutes(enh1);
        if(((startm>otherstartm && startm< otherendm ) || (endm>otherstartm && endm < otherendm)))
        {
            //error try another time this arena already reserved
            Toast.makeText(CreatePublicGroupActivity.this, "בזמן זה שבחרת קיימת קבוצה אחרת תנשה זמן אחר", Toast.LENGTH_LONG).show();
            return false;
        }
    }
    return true;
}


public int calcminutes(int[] a)
{
    return a[0]*60+a[1];
}
public int[] parsehour(String sh,int hour[])
{

    String []s =sh.split(":");
    hour[0]=Integer.parseInt(s[0]);
    hour[1]=Integer.parseInt(s[1]);

    return hour;
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

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String h="";
        String m="";
        if(hourOfDay<10)
            h="0";
        h+=Integer.toString( hourOfDay);
        if(minute<10)
            m="0";
        m+=Integer.toString( minute);
        textView.setText(h + ":" + m);
    }
}


    class helper
    {
       public String starthour, endhour;
       public int arenaid;

        public helper(String starthour, String endhour, int arenaid) {
            this.starthour = starthour;
            this.endhour = endhour;
            this.arenaid = arenaid;
        }
    }