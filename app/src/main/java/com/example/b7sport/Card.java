package com.example.b7sport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Card extends AppCompatActivity {

    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    static List<report_info> groundList;
    private RecyclerView.Adapter adapter;
    final FirebaseDatabase data = FirebaseDatabase.getInstance();
    //    private ArenaAdapter adapter;
    //https://www.beer-sheva.muni.il/OpenData/Lists/Packages/CustomDispForm.aspx?ID=149
    //private String url = "https://br7ckan.blob.core.windows.net/ckanstorage-prod/resources/58f26a74-af55-4823-81d8-17715883acc6/sport.json?sr=b&sp=r&sig=wdKzFn2eHjYtVpUH6y4bJHdouqty%2B%2BdUfUgQNCQIeDs%3D&sv=2017-04-17&se=2020-05-12T14%3A30%3A13Z";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        mList = findViewById(R.id.repo_list);

        groundList = new ArrayList<>();
//        adapter = new ArenaAdapter(getApplicationContext(),groundList);
        adapter = new repadapter(this ,groundList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);
        getDataFromFireBase();


    }


    private void getDataFromFireBase()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        final DatabaseReference ref = data.getReference("Complains");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                report_info rep;

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    rep= new report_info(d.child("Email").getValue().toString(),d.child("Message").getValue().toString());

                    //                arena.setId(id);


                    groundList.add(rep);
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
