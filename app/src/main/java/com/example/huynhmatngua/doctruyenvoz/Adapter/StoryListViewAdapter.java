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
import com.example.huynhmatngua.doctruyenvoz.View.OfflineChapActivity;

import java.util.ArrayList;

public class StoryListViewAdapter extends ArrayAdapter<String> {

    private Context context;
    private int resource;
    private ArrayList<String> storyArrayList;

    public StoryListViewAdapter( Context context, int resource,  ArrayList<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.storyArrayList = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        }

        final String name = storyArrayList.get(position);
        final TextView tvName = convertView.findViewById(R.id.tvNameOfStory);
        tvName.setText(name);
        SharedPreferences share = context.getSharedPreferences("StoryCurrent", Context.MODE_PRIVATE);
        int current = share.getInt("Story", -1);
        if(current != -1 && current == position){
            tvName.setTextColor(Color.parseColor("#00FF00"));
        }
        else {
            tvName.setTextColor(Color.parseColor("#000000"));
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences share = context.getSharedPreferences("StoryCurrent", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = share.edit();
                editor.clear();
                editor.commit();
                editor.putInt("Story", position);
                editor.commit();

                Intent intent = new Intent(context, OfflineChapActivity.class);
                intent.putExtra("name", name);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
