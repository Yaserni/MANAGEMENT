package com.example.b7sport;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;


public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.ViewHolder> implements Filterable {

    Dialog mydialog;
    private Context context;
    private List<String> list;
    private List<String> fulllist;
    static int id;

    public EmailAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;


    }

    @NonNull
    @Override
    public EmailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.single_email, parent, false);

//        mydialog=new Dialog(context);
//        mydialog.setContentView(R.layout.dialog_user);
//        TextView name= mydialog.findViewById(R.id.dialog_name);
//        TextView phone= mydialog.findViewById(R.id.dialog_phone);


        return new EmailAdapter.ViewHolder(v);
    }
    static user selecteduser;
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.email.setText(list.get(position));
        user u;
        selecteduser=new user(list.get(position));
        for(int i=0 ;i<ShowParticipants.users.size();i++) {
            u=ShowParticipants.users.get(i);
            if (u.userEmail.equals(list.get(position)))
                selecteduser = u;

        }
        holder.name.setText(selecteduser.FullName);

        holder.linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(context,OtherUserProfile.class);
                context.startActivity(intent);

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
    private Filter arenaFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(fulllist);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (String item : fulllist) {
                    if (item.toLowerCase().contains(filterPattern)) {
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
    public void setfullValue(ArrayList<String> groundList)
    {
        fulllist=new ArrayList<>(groundList);
    }





    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView email;
        public TextView name;
        LinearLayout linear1;
        public Dialog mydDialog;

        public ViewHolder(View itemView) {
            super(itemView);
            linear1 = itemView.findViewById(R.id.linear3);
            email = itemView.findViewById(R.id.single_email);
            name = itemView.findViewById(R.id.single_email_name);


        }
    }

}
//package com.example.b7sport;
//
//public class EmailAdapter {
//}




