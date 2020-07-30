package com.example.huynhmatngua.doctruyenvoz.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.huynhmatngua.doctruyenvoz.Database.DBManager;
import com.example.huynhmatngua.doctruyenvoz.R;

public class OfflineBodyActivity extends AppCompatActivity {

    private TextView tvBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_body);

        tvBody = findViewById(R.id.tvBody);
        Intent intent = getIntent();
        String chap = intent.getStringExtra("chap");
        String name = intent.getStringExtra("name");
        DBManager db = new DBManager(OfflineBodyActivity.this);
        String body = db.getBodybyNameChap(name, chap);

        tvBody.setText(body);
    }
}
