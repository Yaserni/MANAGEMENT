package com.example.b7sport;

import android.app.ListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<ViewHolder>{
    MainActivity listActivity;
    List<InfoFromDataBase> infolist;

    public UsersAdapter(MainActivity listActivity,List<InfoFromDataBase> infolist){
        this.infolist = infolist;
        this.listActivity = listActivity;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycler,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.fullname.setText(infolist.get(position).getFullName());
        holder.phonenumber.setText(infolist.get(position).getPhoneNumber());
        holder.email.setText(infolist.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return infolist.size();
    }
}