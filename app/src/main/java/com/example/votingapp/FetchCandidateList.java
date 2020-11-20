package com.example.votingapp;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class FetchCandidateList extends AsyncTask<Void, Void,  Void> {

    String data = "";
    String candidateName = "";

    @Override
    protected Void doInBackground(Void... voids){
        try{
            URL url = new URL("");

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }

            JSONArray JA = new JSONArray(data);
            for(int i=0; i<JA.length(); i++){
                JSONObject JO = (JSONObject) JA.get(i);
                candidateName = "Candidate_Name" + JO.get("name") + "\n";
            }

        }
        catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void avoid){
       super.onPostExecute(avoid);



    }


}

