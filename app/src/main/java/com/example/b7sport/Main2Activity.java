package com.example.b7sport;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private ExampleAdapter adapter;
    private List<ExampleItem> exampleList;
    RecyclerViewArena viewArena= new RecyclerViewArena();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        fillExampleList();
        setUpRecyclerView();
    }

    private void fillExampleList() {

        exampleList = new ArrayList<>();
        exampleList.add(new ExampleItem( "One", "Ten"));
        exampleList.add(new ExampleItem( "Two", "Eleven"));
        exampleList.add(new ExampleItem( "Three", "Twelve"));
        exampleList.add(new ExampleItem( "Four", "Thirteen"));
        exampleList.add(new ExampleItem( "Five", "Fourteen"));
        exampleList.add(new ExampleItem( "Six", "Fifteen"));
        exampleList.add(new ExampleItem( "Seven", "Sixteen"));
        exampleList.add(new ExampleItem( "Eight", "Seventeen"));
        exampleList.add(new ExampleItem( "Nine", "Eighteen"));
        for(Arena arena : RecyclerViewArena.groundList)
        {
            exampleList.add(new ExampleItem(arena.getName(),arena.getActivity()));
        }

    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new ExampleAdapter(exampleList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.arena_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        android.widget.SearchView searchView = (SearchView) searchItem.getActionView();
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