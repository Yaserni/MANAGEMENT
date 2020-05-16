package com.example.b7sport;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.jar.JarEntry;

public class RecyclerViewArena extends AppCompatActivity {
    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    static List<Arena> groundList;
//    private RecyclerView.Adapter adapter;
    private ArenaAdapter adapter;
    final FirebaseDatabase data = FirebaseDatabase.getInstance();
    //    private ArenaAdapter adapter;
    //https://www.beer-sheva.muni.il/OpenData/Lists/Packages/CustomDispForm.aspx?ID=149
    private String url = "https://br7ckan.blob.core.windows.net/ckanstorage-prod/resources/58f26a74-af55-4823-81d8-17715883acc6/sport.json?sr=b&sp=r&sig=wdKzFn2eHjYtVpUH6y4bJHdouqty%2B%2BdUfUgQNCQIeDs%3D&sv=2017-04-17&se=2020-05-12T14%3A30%3A13Z";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_arena);
        mList = findViewById(R.id.main_list);

        groundList = new ArrayList<>();
//        adapter = new ArenaAdapter(getApplicationContext(),groundList);
        adapter = new ArenaAdapter(this, groundList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);
        getDataFromFireBase();


    }

    private void getDataFromJSON() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Arena arena = new Arena(i);
                        arena.setName(jsonObject.getString("Name"));
                        arena.setType(jsonObject.getString("Type"));
                        arena.setStreet(jsonObject.getString("street"));
                        arena.setNeighbor(jsonObject.getString("neighborho"));
                        arena.setHousenumber(jsonObject.getDouble("HouseNumbe"));
                        arena.setLighing(jsonObject.getString("lighting"));
                        arena.setSport_type(jsonObject.getString("SportType"));
                        arena.setLat(jsonObject.getDouble("lat"));
                        arena.setLon(jsonObject.getDouble("lon"));
                        arena.setActivity(jsonObject.getString("Activity"));
                        arena.setId(i);

                        groundList.add(arena);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    } //


    private void getDataFromFireBase() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        final DatabaseReference ref = data.getReference("sport");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Arena arena;
                int id;
                String name;
                String type;
                String street;
                Double housenumber;
                String neighbor;
                String activity, sportType, lighting;
                double lat, lon;
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    arena = new Arena(Integer.parseInt(d.getKey()));
                    name = d.child("Name").getValue().toString();
                    neighbor = d.child("neighborho").getValue().toString();
                    type = d.child("Type").getValue().toString();
                    activity = d.child("Activity").getValue().toString();
                    street = d.child("street").getValue().toString();
                    lat = Double.parseDouble(d.child("lat").getValue().toString());
                    lon = Double.parseDouble(d.child("lon").getValue().toString());
                    lighting = d.child("lighting").getValue().toString();

                    arena.setName(d.child("Name").getValue().toString());
                    sportType = d.child("SportType").getValue().toString();
                    housenumber = Double.parseDouble(d.child("HouseNumbe").getValue().toString());

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
                    groundList.add(arena);
                }

                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.arena_menu,menu);

        MenuItem SearchItem = menu.findItem(R.id.action_search);
        SearchView searchView =(SearchView) SearchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

}