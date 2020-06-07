package com.example.b7sport;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class repadapter extends  RecyclerView.Adapter<repadapter.ViewHolder> {
    public AppCompatActivity z= new AppCompatActivity();
    private Context context;
    private List<report_info> list;
    private report_info arena;
    static int id;


    public repadapter(Context context, List<report_info> list) {
        this.context = context;
        this.list = list;


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_show_report, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        arena = list.get(position);
        holder.textemail.setText(String.valueOf(arena.email));
        holder.texttxt.setText(arena.text);

        holder.linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(context,Reply_to_messages.class);
                intent.putExtra("email",String.valueOf(arena.email));
                context.startActivity(intent);

            }
        });



    }




    @Override
    public int getItemCount() {
        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textemail, texttxt;
        LinearLayout linear1;

        public ViewHolder(View itemView ) {
            super(itemView);

            textemail = itemView.findViewById(R.id.email);
            linear1 = itemView.findViewById(R.id.linear1);
            texttxt = itemView.findViewById(R.id.desc);


        }
    }

}
