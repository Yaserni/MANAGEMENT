package com.example.b7sport;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Card extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        ArrayList<report_info> item=new ArrayList<report_info>();
        item.add( new report_info("good","see"));
        item.add( new report_info("sww","swewqewqewqew"));
        adpter_report adp=new  adpter_report(item);
        ListView ls=(ListView)findViewById(R.id.list_v);
        ls.setAdapter(adp);
    }
        class adpter_report extends BaseAdapter
        {
             ArrayList<report_info> item=null;
             public adpter_report(ArrayList<report_info> item)
             {
                 this.item=item;
             }
            @Override
            public int getCount() {
                return  item.size();
            }

            @Override
            public Object getItem(int position) {
               return  item.get(position) ;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater linflater=getLayoutInflater();
                View view=linflater.inflate(R.layout.activity_show_report,null);
                TextView txtemail=(TextView) view.findViewById(R.id.email);
                TextView txtdec=(TextView)view.findViewById(R.id.desc);
                txtemail.setText(item.get(position).email);
                txtdec.setText(item.get(position).text);
             return  view;
            }
        }

}
