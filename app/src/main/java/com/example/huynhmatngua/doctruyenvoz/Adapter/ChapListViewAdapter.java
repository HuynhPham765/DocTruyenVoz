package com.example.huynhmatngua.doctruyenvoz.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhmatngua.doctruyenvoz.View.DownloadActivity;
import com.example.huynhmatngua.doctruyenvoz.Object.Story;
import com.example.huynhmatngua.doctruyenvoz.R;
import com.example.huynhmatngua.doctruyenvoz.View.OfflineBodyActivity;
import com.example.huynhmatngua.doctruyenvoz.View.OfflineChapActivity;

import java.util.ArrayList;

public class ChapListViewAdapter extends ArrayAdapter<String> {

    private Context context;
    private int resource;
    private ArrayList<String> storyArrayList;
    private String name;

    public ChapListViewAdapter( Context context, int resource,  ArrayList<String> objects, String name) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.storyArrayList = objects;
        this.name = name;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        }

        final String chap = storyArrayList.get(position);
        final TextView tvName = convertView.findViewById(R.id.tvNameOfStory);
        tvName.setText(chap);

        SharedPreferences share = context.getSharedPreferences("ChapCurrent", Context.MODE_PRIVATE);
        int current = share.getInt("Chap", -1);
        if(current != -1 && current == position){
            tvName.setTextColor(Color.parseColor("#00FF00"));
        }
        else {
            tvName.setTextColor(Color.parseColor("#000000"));
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences share = context.getSharedPreferences("ChapCurrent", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = share.edit();
                editor.clear();
                editor.commit();
                editor.putInt("Chap", position);
                editor.commit();

                Intent intent = new Intent(context, OfflineBodyActivity.class);
                intent.putExtra("chap", chap);
                intent.putExtra("name", name);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
