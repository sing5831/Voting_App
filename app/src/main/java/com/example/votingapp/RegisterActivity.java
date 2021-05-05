package com.example.votingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
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
    EditText email, add, dofb, d_license;
    Button reg;
    String Email, Password,  VoterId;
    int PIN;

    Enc encryption = new Enc();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        content = findViewById(R.id.content);

        email = findViewById(R.id.email_signup);
        add = findViewById(R.id.address);
     //   dofb = findViewById(R.id.dob);
        d_license = findViewById(R.id.license);
        reg = findViewById(R.id.btn_nxt);


        reg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                if (isEmpty(email.getText().toString()) ||
                isEmpty(add.getText().toString()) || isEmpty(d_license.getText().toString()))
                {
                    Toast t = Toast.makeText(RegisterActivity.this, "All inputs required", Toast.LENGTH_LONG);
                    t.show();
                }
                else{
                    try{

                        AsyncT asyncT = new AsyncT();
                        asyncT.execute();
                        Toast t = Toast.makeText(RegisterActivity.this, "Device Registered Successfully!", Toast.LENGTH_LONG);
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
            try {
                AsyncMethod();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }



    }

    private void AsyncMethod() throws Exception {

       // VoterId = dofb.getText().toString();
        Email = email.getText().toString();
        String e_email;
        e_email = encryption.Encrypt(Email, "Password");
        PIN = Integer.valueOf(d_license.getText().toString());
        Password = add.getText().toString();
        String Pass;
        Pass = encryption.Encrypt(Password, "Password");
        String e_did;
        e_did = encryption.Encrypt(getId(), "Password");
        Log.d("EncryptedPassword", Password);
        try {
            URL url = new URL("https://mobilevotingapp.azurewebsites.net/api/register"); //Enter URL here
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
            httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
            httpURLConnection.connect();

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("Email", e_email);
       //     jsonObject.put("VoterId", VoterId);
            jsonObject.put("PIN", PIN);

            jsonObject.put("User_Password", Pass);
            jsonObject.put("DeviceId", e_did);
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

    public String getId(){
        //TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        return android_id;
    }

}




