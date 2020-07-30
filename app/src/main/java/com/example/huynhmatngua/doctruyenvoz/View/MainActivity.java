package com.example.huynhmatngua.doctruyenvoz.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huynhmatngua.doctruyenvoz.Database.DBManager;
import com.example.huynhmatngua.doctruyenvoz.InternetConnection;
import com.example.huynhmatngua.doctruyenvoz.Object.Story;
import com.example.huynhmatngua.doctruyenvoz.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private EditText edtURL;
    private Button btn;
    private ArrayList<Story> storyArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!InternetConnection.checkConnection(MainActivity.this)) {
            Intent intent = new Intent(MainActivity.this, OfflineActivity.class);
            startActivity(intent);
        }

        Intent intent = getIntent();
        boolean success = intent.getBooleanExtra("success", false);
        if(success){
            Toast.makeText(getApplicationContext(), "Đã tải toàn bộ truyện", Toast.LENGTH_SHORT).show();
            edtURL.setText("");
        }

        edtURL = findViewById(R.id.edtURL);
        btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!InternetConnection.checkConnection(MainActivity.this)) {
                    Intent intent = new Intent(MainActivity.this, OfflineActivity.class);
                    startActivity(intent);
                }
                String path;
                if(edtURL.getText().toString() != null) {
                    path = edtURL.getText().toString();
                }
                else {
                    path = "https://www.doctruyenvoz.com/2015/03/dong-doi-noi-troi-chap-76-100.html";
                }
                URL url = null;
                try {
                    url = new URL(path);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                GetStoryFromURL getStoryFromURL = new GetStoryFromURL(url);
                getStoryFromURL.execute();
            }
        });
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
                Intent intent = new Intent(MainActivity.this, OfflineActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class GetStoryFromURL extends AsyncTask<Void, Void, Document>{

        private URL url;

        public GetStoryFromURL(URL url) {
            this.url = url;
            storyArrayList = new ArrayList<>();
        }

        @Override
        protected Document doInBackground(Void... voids) {
            try {
                Document elements = Jsoup.connect(url.toString()).get();
                return elements;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }


        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);
            if (document != null) {
                Elements elements = Jsoup.parse(document.toString()).getElementsByClass("danhsach");

                if(elements.size() != 0) {
                    for (int j = 0; j < elements.size(); j++) {
                        Element element = elements.get(j);
                        String dsc = element.getElementsByClass("danhsachchap active").text();
                        if (dsc != null && dsc.equals("DANH SÁCH CHAP")) {
                            Elements listChap = element.getElementsByTag("a");

                            String chapCurrent = "";
                            ArrayList<String> stringArrayList = new ArrayList<>();
                            for (int i = 0; i < listChap.size(); i++) {
                                Element chap = listChap.get(i);
                                chapCurrent = chap.attr("href");
                                stringArrayList.add(chapCurrent);
                            }
                            Intent intent = new Intent(MainActivity.this, ListStoryActivity.class);
                            intent.putExtra("url", stringArrayList);
                            startActivity(intent);
                        }
                    }
                }
                else {
                    Intent intent1 = new Intent(MainActivity.this, ListStoryActivity.class);
                    String newURL = url.toString();
                    intent1.putExtra("SingleURL", newURL);
                    startActivity(intent1);
                }
            }
            else {
                Toast.makeText(MainActivity.this, "Xảy ra lỗi khi tải truyện", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
