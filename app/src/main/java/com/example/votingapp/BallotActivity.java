package com.example.votingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BallotActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<BallotModel> arrayList;
    int selectedItem = -1;
    UserModel usermodel = new UserModel();

    @Override
    public void onBackPressed() {
        startActivity(new Intent(BallotActivity.this, LoginActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_list);
        listView = (ListView) findViewById(R.id.listview);

       //. usermodel.setEmail("nik@abc");
        String username = getIntent().getStringExtra("Email");
        usermodel.setEmail(username);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = position;
                AsyncT asyncT = new AsyncT();
                asyncT.execute();

                openConfirmationActivity();
            }
        });

        arrayList = new ArrayList<>();
        new fetchData().execute();
    }
    public void openConfirmationActivity(){
        Intent intent = new Intent(this, ConfirmationActivity.class);
        startActivity(intent);
    }

    public class fetchData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            arrayList.clear();
            String result = null;
            try {
                URL url = new URL("https://mobilevotingapp.azurewebsites.net/api/vote");
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
                    Log.d("doInBackgroundCandidateList: ",result);
                }else  {
                    result = "error";
                }

            } catch (Exception  e) {
                e.printStackTrace();
                Log.d("doInBackground: ",e.getMessage());
            }
            return result;
        }

        @Override
        public void onPostExecute(String s) {
            super .onPostExecute(s);

            try {
              //  JSONObject object = new JSONObject(s);
                JSONArray array = new JSONArray(s);
                Log.d( "onPostExecute: ","" + array.length());
             //   JSONArray array = object.getJSONArray(data);

                for (int i = 0; i < array.length(); i++) {

                    JSONObject jsonObject = array.getJSONObject(i);
                    String id = jsonObject.getString("CandidateId");
                    String first_name = jsonObject.getString("CandidateName");
                    //String last_name = jsonObject.getString("last_name");
                    //String email = jsonObject.getString("email");

                    BallotModel model = new BallotModel();
                    model.setId(id);
                    model.setName(first_name );
                    //model.setEmail(email);
                    arrayList.add(model);
                }
            } catch (JSONException  e) {
                e.printStackTrace();
                Log.d( "onPostExecute: ", e.getMessage());
            }

            CustomAdapter adapter = new CustomAdapter(BallotActivity.this, arrayList);
            listView.setAdapter(adapter);

        }
    }

    class AsyncT extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {

            AsyncMethodVote();
            return null;
        }

    }

    private void AsyncMethodVote() {
        String uemail = usermodel.getEmail();

        BallotModel model = arrayList.get(selectedItem);
        String cid= model.getId();
        String cname = model.getName();
        int vote = 1;

        try {
            URL url = new URL("https://mobilevotingapp.azurewebsites.net/api/vote"); //Enter URL here
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
            httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
            httpURLConnection.connect();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("CandidateId", cid);
            jsonObject.put("CandidateName", cname);
            jsonObject.put("Votes", vote);
            jsonObject.put("UserEmail", uemail);

            Log.d("AsyncMethod: ",jsonObject.toString());

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(jsonObject.toString());
            wr.flush();
            wr.close();

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
