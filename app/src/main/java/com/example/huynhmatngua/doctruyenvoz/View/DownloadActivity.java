package com.example.huynhmatngua.doctruyenvoz.View;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.huynhmatngua.doctruyenvoz.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;

public class DownloadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

    }

    private class DownloadStory extends AsyncTask<Void, Void, Document> {

        private URL url;

        public DownloadStory(URL url) {
            this.url = url;
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
                Element element = Jsoup.parse(document.toString()).select("select.dropdown-select").first();

            }
            else {
                Toast.makeText(DownloadActivity.this, "Get Data Fail", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
