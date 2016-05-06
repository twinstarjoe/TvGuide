package com.twinstarjoe.tvguide.tvguide;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TrendingShowsActivity extends AppCompatActivity {
    private static final String TAG = "TrendingShowsActivity";
    private List<demographics> demographicData;

    static ArrayList<HashMap<String, String>> ShowList;
    String[] showNames;
    String[] airdate;
    int resID;
    int[] drawableids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending_shows);

        DemoFetcher fetcher = new DemoFetcher();
        fetcher.execute();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Add title
        setTitle("              TRENDING SHOWS");

        // Add names, airdates and resource ids for the imageViews
        showNames = new String[]{""};
        airdate = new String[]{""};
        drawableids = new int[]{0};
    }

    private void setShowList() {
        ShowList = new ArrayList<HashMap<String, String>>();
        ListView listview = (ListView) findViewById(R.id.listview);
//        for (demographics demos : TrendingShowsActivity.this.demographicData) {
//            Toast.makeText(TrendingShowsActivity.this, demos.name, Toast.LENGTH_SHORT).show();
//        }

        for (demographics demos : TrendingShowsActivity.this.demographicData) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("NAME", demos.name);
            map.put("AIRDATE", demos.air);
            map.put("IMAGEVIEWID", demos.img);
            ShowList.add(map);
        }
        String[] from = new String[]{"NAME", "AIRDATE", "IMAGEVIEWID"};
        int[] to = new int[]{R.id.txtTitle, R.id.txtAirDate, R.id.show_image};

        CustomTrendAdapter adapter = new CustomTrendAdapter(this,
                ShowList, R.layout.tvshow_item, from, to);
        listview.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trending_shows, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void handleDemoList(List<demographics> demos) {
        this.demographicData = demos;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                for (demographics demos : TrendingShowsActivity.this.demographicData) {
//                    Toast.makeText(TrendingShowsActivity.this, demos.name, Toast.LENGTH_SHORT).show();
//                }
                setShowList();
            }
        });
    }

    private void failedLoadingDemos() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TrendingShowsActivity.this, "Failed to load Posts. Have a look at LogCat.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class DemoFetcher extends AsyncTask<Void, Void, String> {
        private static final String TAG = "DemoFetcher";
        public static final String SERVER_URL = "http://www.felipesilveira.com.br/tvguide/showtrends.php";

        @Override
        protected String doInBackground(Void... params) {
            try {
                //Create an HTTP client
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(SERVER_URL);

                //Perform the request and check the status code
                HttpResponse response = client.execute(post);
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == 200) {

                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();

                    try {
                        //Read the server response and attempt to parse it as JSON
                        Reader reader = new InputStreamReader(content);

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                        Gson gson = gsonBuilder.create();
                        List<demographics> demographicData = new ArrayList<demographics>();
                        demographicData = Arrays.asList(gson.fromJson(reader, demographics[].class));
                        content.close();

                        handleDemoList(demographicData);
                    } catch (Exception ex) {
                        Log.e(TAG, "Failed to parse JSON due to: " + ex);
                        failedLoadingDemos();
                    }
                } else {
                    Log.e(TAG, "Server responded with status code: " + statusLine.getStatusCode());
                    failedLoadingDemos();
                }
            } catch (Exception ex) {
                Log.e(TAG, "Failed to send HTTP POST request due to: " + ex);
                failedLoadingDemos();
            }
            return null;
        }
    }
}

