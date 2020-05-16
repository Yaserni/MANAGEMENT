package com.example.b7sport;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class GroupAdapter extends  RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    public AppCompatActivity z = new AppCompatActivity();
    private Context context;
    private List<Group> list;
    private Group group;
    static int id;
    static Group selected_group;

    public GroupAdapter(Context context, List<Group> list) {
        this.context = context;
        this.list = list;


    }

    @NonNull
    @Override
    public GroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.activity_singlegroup, parent, false);
        return new GroupAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        group = list.get(position);

        holder.textid.setText(String.valueOf(group.getArenaid()));
        holder.textName.setText("שם מגרש : " + group.getArenaname());
        holder.textType.setText("סוג מגרש : " +String.valueOf(group.getArenatype()));
        holder.textStreet.setText("כביש : " +String.valueOf(group.getArenastreet()));
        holder.textNeighborh.setText("שכונה : " +group.getArenaneighbor());
        holder.textActivity.setText("פעילות : " +group.getArenaactivity());
        holder.textLighting.setText("תאורה : " +group.getArenalighing());
        holder.textSportType.setText("סוג ספורט : " +group.getArenasport_type());
        holder.groupname.setText("שם קבוצה: " + group.getGroupname());
        holder.numberofplayers.setText("מספר שחקנים בקבוצה: " + group.getPlayersnumber());

        if(group.isIsprivate())
            holder.isprivate.setText("קבוצה פרטית");
        else
            holder.isprivate.setText("קבוצה ציבורית");

        holder.linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x =Integer.parseInt(holder.textid.getText().toString());
                Toast.makeText(context, "Item Number "+x +" selected..", Toast.LENGTH_SHORT).show();
                id=x;
                selected_group=group;
                Intent intent  = new Intent(context,GroupProfile.class);
                context.startActivity(intent);

            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textid, textName, textType, textStreet,textNeighborh,textActivity,textLighting,textSportType,groupname ,numberofplayers, isprivate;//I dont know if I must add the lat and lon
        LinearLayout linear1;

        public ViewHolder(View itemView ) {
            super(itemView);

                textName = itemView.findViewById(R.id.gr_name2);
                linear1 = itemView.findViewById(R.id.linear2);
                textType = itemView.findViewById(R.id.gr_type2);
                textStreet = itemView.findViewById(R.id.gr_street2);
                textNeighborh = itemView.findViewById(R.id.gr_gr_neighbor2);
                textActivity = itemView.findViewById(R.id.gr_activity2);
                textSportType = itemView.findViewById(R.id.gr_sporttype2);
                textLighting = itemView.findViewById(R.id.gr_lighting2);
                textid = itemView.findViewById(R.id.gr_id2);
                groupname = itemView.findViewById(R.id.sg_grname);
                numberofplayers = itemView.findViewById(R.id.sg_playersnumber);
                isprivate=itemView.findViewById(R.id.sg_isprivate);

        }
    }

}
//package com.example.b7sport;
//
//public class GroupAdapter {
//}



