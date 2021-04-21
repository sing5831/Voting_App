package com.example.votingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.text.TextUtils.isEmpty;

public class RegisterActivity extends AppCompatActivity {

    TextView content;
    EditText fname;
    EditText lname;
    EditText email, add, dofb, d_license;
    Button reg;
    String First_Name, Last_Name, Email, Address, DOB, License;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        content    =  findViewById( R.id.content );
        fname = findViewById(R.id.first_name);
        lname = findViewById(R.id.last_name);
        email = findViewById(R.id.email_signup);
        add = findViewById(R.id.address);
        dofb = findViewById(R.id.dob);
        d_license = findViewById(R.id.license);
        reg = findViewById(R.id.btn_nxt);



        reg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                if (isEmpty(email.getText().toString()) || isEmpty(fname.getText().toString()) || isEmpty(lname.getText().toString()) ||
                isEmpty(add.getText().toString()) || isEmpty(dofb.getText().toString()) || isEmpty(d_license.getText().toString()))
                {
                    Toast t = Toast.makeText(RegisterActivity.this, "All inputs required", Toast.LENGTH_LONG);
                    t.show();

                }
                else{
                    try{

                        AsyncT asyncT = new AsyncT();
                        asyncT.execute();
                        Toast t = Toast.makeText(RegisterActivity.this, "Sign Up Successful", Toast.LENGTH_LONG);
                        t.show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                    }
                    catch(Exception ex)
                    {
                        content.setText("url exeption!" );
                    }

                }

            }
        });

    }

    // Create GetText Metod
    class AsyncT extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {

            AsyncMethod();
            return null;
        }

    }

    private void AsyncMethod() {
        First_Name = fname.getText().toString();
        Last_Name = lname.getText().toString();
        DOB = dofb.getText().toString();
        Email = email.getText().toString();
        License = d_license.getText().toString();
        Address = add.getText().toString();
        try {
            URL url = new URL("https://mobilevotingapp.azurewebsites.net/api/user"); //Enter URL here
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
            httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
            httpURLConnection.connect();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("FirstName", First_Name);
            jsonObject.put("LastName", Last_Name);
            jsonObject.put("Email", Email);
            jsonObject.put("DOB", DOB);
            jsonObject.put("License", License);
            jsonObject.put("User_Password", Address);
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




