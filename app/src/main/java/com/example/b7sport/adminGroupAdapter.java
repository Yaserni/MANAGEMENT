package com.example.b7sport;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class adminGroupAdapter extends  RecyclerView.Adapter<adminGroupAdapter.ViewHolder> implements Filterable {

    public AppCompatActivity z = new AppCompatActivity();
    private Context context;
    private ArrayList<Group> list;
    private ArrayList<Group> fulllist;
    final FirebaseDatabase data = FirebaseDatabase.getInstance();
    final DatabaseReference ref = data.getReference("Groups");


    private Group group;
    static int id;
    static Group selected_group;

    public adminGroupAdapter(Context context, ArrayList<Group> list) {
        this.context = context;
        this.list = list;
        fulllist = new ArrayList<>(list);


    }

    @NonNull
    @Override
    public adminGroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.activity_single_admingroup, parent, false);
        return new adminGroupAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        group = list.get(position);

        holder.textid.setText(String.valueOf(group.getArenaid()));
        holder.textName.setText("שם מגרש : " + group.getArenaname());
        holder.textType.setText("סוג מגרש : " + String.valueOf(group.getArenatype()));
        holder.textStreet.setText("כביש : " + String.valueOf(group.getArenastreet()));
        holder.textNeighborh.setText("שכונה : " + group.getArenaneighbor());
        holder.textActivity.setText("פעילות : " + group.getArenaactivity());
        holder.textLighting.setText("תאורה : " + group.getArenalighing());
        holder.textSportType.setText("סוג ספורט : " + group.getArenasport_type());
        holder.groupname.setText("שם קבוצה: " + group.getGroupname());
        holder.numberofplayers.setText("מספר שחקנים בקבוצה: " + group.getPlayersnumber());

        if (group.isIsprivate())
            holder.isprivate.setText("קבוצה פרטית");
        else
            holder.isprivate.setText("קבוצה ציבורית");

        holder.linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group = list.get(position);
                delte(group.key);
                Log.v("INfo",String.valueOf(position));

            }
        });

    }





    @Override
    public int getItemCount() {
        return list.size();

    }


    @Override
    public Filter getFilter() {
        return arenaFilter;
    }

    private Filter arenaFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Group> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(fulllist);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Group item : fulllist) {
                    if (item.getGroupname().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public void setfullValue(ArrayList<Group> groundList) {
        fulllist = new ArrayList<>(groundList);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textid, textName, textType, textStreet, textNeighborh, textActivity, textLighting, textSportType, groupname, numberofplayers, isprivate;//I dont know if I must add the lat and lon
        LinearLayout linear1;

        public ViewHolder(View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.dgr_name2);
            linear1 = itemView.findViewById(R.id.dlinear2);
            textType = itemView.findViewById(R.id.dgr_type2);
            textStreet = itemView.findViewById(R.id.dgr_street2);
            textNeighborh = itemView.findViewById(R.id.dgr_gr_neighbor2);
            textActivity = itemView.findViewById(R.id.dgr_activity2);
            textSportType = itemView.findViewById(R.id.dgr_sporttype2);
            textLighting = itemView.findViewById(R.id.dgr_lighting2);
            textid = itemView.findViewById(R.id.dgr_id2);
            groupname = itemView.findViewById(R.id.dsg_grname);
            numberofplayers = itemView.findViewById(R.id.dsg_playersnumber);
            isprivate = itemView.findViewById(R.id.dsg_isprivate);

        }
    }

    public  void delte (final String key)
    {

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot d : dataSnapshot.getChildren())
                {
                    if(d.getKey().toString().equals(key))
                    {
                        ref.child(d.getKey()).removeValue();

                    }



                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });








    }


}