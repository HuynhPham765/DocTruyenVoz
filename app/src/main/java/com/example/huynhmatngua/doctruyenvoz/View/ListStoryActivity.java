package com.example.huynhmatngua.doctruyenvoz.View;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ListStoryActivity extends AppCompatActivity {

    private ArrayList<Story> storyArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_story);

        if (!InternetConnection.checkConnection(ListStoryActivity.this)) {
            Intent intent = new Intent(ListStoryActivity.this, OfflineActivity.class);
            startActivity(intent);
        }

        Intent intent = getIntent();
        ArrayList<String> arrURL = intent.getStringArrayListExtra("url");
        String singleURL = intent.getStringExtra("SingleURL");
        String path;
        if(arrURL != null){
            for(int i=0; i<arrURL.size(); i++){
                path = arrURL.get(i);
                if(path != null) {
                    URL url = null;
                    try {
                        url = new URL(path);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    GetStoryFromURL getStoryFromURL = new GetStoryFromURL(url);
                    getStoryFromURL.execute();
                }
            }
            Intent intent1 = new Intent(ListStoryActivity.this, MainActivity.class);
            intent.putExtra("Success", true);
            startActivity(intent1);
        } else {
            if(singleURL != null){
                path = singleURL;
                if(path != null) {
                    URL url = null;
                    try {
                        url = new URL(path);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    GetStoryFromURL getStoryFromURL = new GetStoryFromURL(url);
                    getStoryFromURL.execute();
                }
            }
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
                Element elementHeader = Jsoup.parse(document.toString()).getElementsByClass("post-header").first();
                String nameAndChap = elementHeader.getElementsByClass("post-title entry-title").first().text();
                if(!nameAndChap.contains("-")){
                    nameAndChap = nameAndChap+"-Chap 1";
                }
                String[] nameChap = nameAndChap.split("-");
                String name = nameChap[0];
                String chap = nameChap[1];
                Element element = Jsoup.parse(document.toString()).getElementById("post_content");
                String allDocument = element.text();
                Elements listChap = element.getElementsByTag("h2");

                String chapCurrent="", chapNext="", body = "";

                DBManager db = new DBManager(ListStoryActivity.this);
                if(listChap.size() > 0) {
                    for (int i = 0; i < listChap.size(); i++) {
                        Element story = listChap.get(i);
                        chapCurrent = story.getElementsByTag("h2").text();
                        if (i < listChap.size() - 1) {
                            chapNext = listChap.get(i + 1).getElementsByTag("h2").text();

                            int startChapCurrent = allDocument.indexOf(chapCurrent);
                            int endChapCurrent = startChapCurrent + chapCurrent.length();
                            int startChapNext = allDocument.indexOf(chapNext);
                            body = allDocument.substring(endChapCurrent, startChapNext);
                            Story story1 = new Story(name, chapCurrent, body);
                            db.addStory(story1);

                        } else {
                            int startChapCurrent = allDocument.indexOf(chapCurrent);
                            int endChapCurrent = startChapCurrent + chapCurrent.length();
                            body = allDocument.substring(endChapCurrent, allDocument.length());

                            Story story1 = new Story(name, chapCurrent, body);
                            db.addStory(story1);
                        }
                    }
                }
                else {
                    body = allDocument;
                    Story story = new Story(name, chap, body);
                    db.addStory(story);
                }
            }
            else {
                Toast.makeText(ListStoryActivity.this, "Xảy ra lỗi khi tải truyện", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
