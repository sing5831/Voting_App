package com.example.votingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class LoginActivity extends AppCompatActivity {

    EditText email, pin;
    Button btnLogin, btnSignup;
    String username, passwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email_login);
        pin = findViewById(R.id.pin);
        btnLogin = findViewById(R.id.btn_login);
        btnSignup = (findViewById(R.id.btn_signup));



        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class
                ));
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isEmpty(email.getText().toString()) || isEmpty(pin.getText().toString())){
                    Toast t = Toast.makeText(LoginActivity.this, "All inputs required", Toast.LENGTH_LONG);
                    t.show();

                }
                else{
                    AsyncTLogin async = new AsyncTLogin();
                    async.execute();
                }
            }
        });




    }

    class AsyncTLogin extends AsyncTask<Void,Void,String> {

        @Override
        protected String doInBackground(Void... params) {

            String resp = AsyncMethodLogin();
            return resp;
        }


        @Override
        protected void onPostExecute(String s) {
            Log.d( "Display s:", s);
            if(s.isEmpty()){
                Log.d( "onPostExecute: ", "Empty Response from server");
            }
            else{
                if (s.equals("\"Voted\"")){

                    startActivity(new Intent(LoginActivity.this, StatusVotedActivity.class));
                }
                else if(s.equals("\"Success\"")){

                    Intent intent = new Intent(LoginActivity.this, AgreementActivity.class);
                    intent.putExtra("Email", username);
                    startActivity(intent);
                }
                else{
                    Toast t = Toast.makeText(LoginActivity.this, "Incorrect Username or Password", Toast.LENGTH_LONG);
                    t.show();
                }
            }
        }
    }

    private String AsyncMethodLogin() {

        username = email.getText().toString();
        passwd = pin.getText().toString();

       // String email = "From Mobile";
       // String pass = "Navneet24";
        String resp = "";

        try {
            URL url = new URL("https://mobilevotingapp.azurewebsites.net/api/login"); //Enter URL here
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
            httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
            httpURLConnection.connect();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Email", username);
            jsonObject.put("User_Password", passwd);

            Log.d("AsyncMethodLogin: ",jsonObject.toString());

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(jsonObject.toString());
          //  wr.flush();
           // wr.close();

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
                resp = response.toString();
                Log.d("AsyncMethodLoginResponse:",resp);
/*
                if(resp.equals("Voted")){

                    startActivity(new Intent(LoginActivity.this, AgreementActivity.class));
                }
                else{
                    Toast t = Toast.makeText(LoginActivity.this, "No Response from server", Toast.LENGTH_LONG);
                    t.show();
                }
*/
            }

            wr.flush();
            wr.close();
            Log.d("AsyncMethodLoginResponse1:",resp);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resp;
    }
}

