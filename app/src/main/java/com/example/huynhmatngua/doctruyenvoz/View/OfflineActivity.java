package com.example.huynhmatngua.doctruyenvoz.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhmatngua.doctruyenvoz.Adapter.StoryListViewAdapter;
import com.example.huynhmatngua.doctruyenvoz.Database.DBManager;
import com.example.huynhmatngua.doctruyenvoz.InternetConnection;
import com.example.huynhmatngua.doctruyenvoz.Object.Story;
import com.example.huynhmatngua.doctruyenvoz.R;

import java.util.ArrayList;

public class OfflineActivity extends AppCompatActivity {

    private ListView lvStoryDownload;
    private TextView tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);

        lvStoryDownload = findViewById(R.id.lvStoryDownload);
        tvShow = findViewById(R.id.tvShow);

        DBManager db = new DBManager(OfflineActivity.this);
        ArrayList<String> nameArrayList = db.getAllNameOfStory();
        if(nameArrayList == null){
            lvStoryDownload.setVisibility(View.GONE);
        }
        else {
            tvShow.setVisibility(View.GONE);
            StoryListViewAdapter adapter = new StoryListViewAdapter(OfflineActivity.this, R.layout.row_item_listview_story, nameArrayList);
            lvStoryDownload.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_truyen_offline:
                if (InternetConnection.checkConnection(OfflineActivity.this)) {
                    Intent intent = new Intent(OfflineActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Vui lòng bật kết nối mạng hoặc wifi", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
