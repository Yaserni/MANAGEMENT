package com.example.b7sport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.BadParcelableException;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewGroup extends AppCompatActivity {
    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    static List<Group> groupList;
    private RecyclerView.Adapter adapter;
    final FirebaseDatabase data = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_group);
        mList = findViewById(R.id.grouplist);

        groupList = new ArrayList<>();
//        adapter = new ArenaAdapter(getApplicationContext(),groundList);
        adapter = new GroupAdapter(this ,groupList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);
        getDataFromFireBase();
    }




    private void getDataFromFireBase() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        final DatabaseReference ref = data.getReference("Groups");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Arena arena;
                Group group;
                int id, grid;
                String name, grname;
                String type;
                String street;
                Double housenumber;
                int playernum;
                String neighbor;
                String activity, sportType, lighting;
                boolean isPrivate;
                double lat, lon;
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    if(d.getKey().equals("id")) break;
                    arena = new Arena(Integer.parseInt(d.child("arenaid").getValue().toString()));
                    name = d.child("arenaname").getValue().toString();
                    neighbor = d.child("arenaneighbor").getValue().toString();
                    type = d.child("arenatype").getValue().toString();
                    activity = d.child("arenaname").getValue().toString();
                    street = d.child("arenastreet").getValue().toString();
                    lat = Double.parseDouble(d.child("arenalat").getValue().toString());
                    lon = Double.parseDouble(d.child("arenalon").getValue().toString());
                    lighting = d.child("arenalighing").getValue().toString();


                    arena.setName(d.child("arenaname").getValue().toString());
                    sportType = d.child("arenasport_type").getValue().toString();
                    housenumber = Double.parseDouble(d.child("arenahousenumber").getValue().toString());
                    grname = d.child("groupname").getValue().toString();
                    grid = Integer.parseInt(d.child("groupid").getValue().toString());
                    playernum = Integer.parseInt(d.child("playersnumber").getValue().toString());
                    isPrivate = Boolean.getBoolean(d.child("isprivate").getValue().toString());
                    //                arena.setId(id);

                    arena.setName(name);
                    arena.setType(type);
                    arena.setStreet(street);
                    arena.setNeighbor(neighbor);
                    arena.setHousenumber(housenumber);
                    arena.setLighing(activity);
                    arena.setSport_type(sportType);
                    arena.setLat(lat);
                    arena.setLon(lon);
                    arena.setActivity(activity);
                    group = Group.makeGroup(grname, d.child("groupid").getValue().toString(), playernum, isPrivate, arena);

                    groupList.add(group);
                }

                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
}
