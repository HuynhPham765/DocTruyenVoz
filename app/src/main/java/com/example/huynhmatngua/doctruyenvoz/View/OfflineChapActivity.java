package com.example.huynhmatngua.doctruyenvoz.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.huynhmatngua.doctruyenvoz.Adapter.ChapListViewAdapter;
import com.example.huynhmatngua.doctruyenvoz.Database.DBManager;
import com.example.huynhmatngua.doctruyenvoz.R;

import java.util.ArrayList;

public class OfflineChapActivity extends AppCompatActivity {

    private ListView lvChap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_chap);

        lvChap = findViewById(R.id.lvStoryChap);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        if(name != null) {
            DBManager db = new DBManager(OfflineChapActivity.this);
            ArrayList<String> chapArrayList = db.getChapByName(name);
            ChapListViewAdapter adapter = new ChapListViewAdapter(OfflineChapActivity.this, R.layout.row_item_listview_story, chapArrayList, name);
            lvChap.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_option_activity_offline_chap, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_sort:
                Intent intent = getIntent();
                String name = intent.getStringExtra("name");
                if(name != null) {
                    DBManager db = new DBManager(OfflineChapActivity.this);
                    ArrayList<String> chapArrayList = db.getChapByNameAfterSort(name);
                    ChapListViewAdapter adapter = new ChapListViewAdapter(OfflineChapActivity.this, R.layout.row_item_listview_story, chapArrayList, name);
                    lvChap.setAdapter(adapter);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
