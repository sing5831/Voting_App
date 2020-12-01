package com.example.votingapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class BallotActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<BallotModel> arrayList;
    SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_list);
        listView = (ListView) findViewById(R.id.listview);
        //swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.);

        arrayList = new ArrayList<>();
        new fetchData().execute();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new fetchData().execute();
            }
        });
    }

    public class fetchData extends AsyncTask<String, String, String> {

        @Override
        public void onPreExecute() {
            super .onPreExecute();
            swipeRefresh.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... params) {
            arrayList.clear();
            String result = null;
            try {
                URL url = new URL("");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String temp;

                    while ((temp = reader.readLine()) != null) {
                        stringBuilder.append(temp);
                    }
                    result = stringBuilder.toString();
                }else  {
                    result = "error";
                }

            } catch (Exception  e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        public void onPostExecute(String s) {
            super .onPostExecute(s);
            swipeRefresh.setRefreshing(false);
            try {
                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray("data");

                for (int i = 0; i < array.length(); i++) {

                    JSONObject jsonObject = array.getJSONObject(i);
                    String id = jsonObject.getString("id");
                    String first_name = jsonObject.getString("first_name");
                    String last_name = jsonObject.getString("last_name");
                    String email = jsonObject.getString("email");

                    BallotModel model = new BallotModel();
                    model.setId(id);
                    model.setName(first_name + " " + last_name);
                    model.setId(email);
                    arrayList.add(model);
                }
            } catch (JSONException  e) {
                e.printStackTrace();
            }

            CustomAdapter adapter = new CustomAdapter(BallotActivity.this, arrayList);
            listView.setAdapter(adapter);

        }
    }


}
