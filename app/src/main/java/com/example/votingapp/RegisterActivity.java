package com.example.votingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class RegisterActivity extends AppCompatActivity {

    TextView content;
    EditText fname;
    EditText lname;
    EditText email, add, dofb, d_license;
    Button register;
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
        register = findViewById(R.id.btn_next);

        register.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v)
            {
                try{

                    // CALL GetText method to make post method call
                    GetText();
                }
                catch(Exception ex)
                {
                    content.setText(" url exeption! " );
                }
            }
        });
    }

    // Create GetText Metod
    public  void  GetText()  throws UnsupportedEncodingException
    {
        // Get user defined values
        First_Name = fname.getText().toString();
        Last_Name = lname.getText().toString();
        Email   = email.getText().toString();
        DOB   = dofb.getText().toString();
        Address   = add.getText().toString();
        License = d_license.getText().toString();

        // Create data variable for sent values to server

        String data = URLEncoder.encode("fname", "UTF-8")
                + "=" + URLEncoder.encode(First_Name, "UTF-8");
        data += "&" + URLEncoder.encode("lname", "UTF-8") + "="
                + URLEncoder.encode(Last_Name, "UTF-8");

        data += "&" + URLEncoder.encode("email", "UTF-8") + "="
                + URLEncoder.encode(Email, "UTF-8");

        data += "&" + URLEncoder.encode("dob", "UTF-8")
                + "=" + URLEncoder.encode(DOB, "UTF-8");
        data += "&" + URLEncoder.encode("license", "UTF-8") + "="
                + URLEncoder.encode(License, "UTF-8");

        data += "&" + URLEncoder.encode("add", "UTF-8")
                + "=" + URLEncoder.encode(Address, "UTF-8");

        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL("");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
        }
        catch(Exception ex)
        {

        }
        finally
        {
            try
            {

                reader.close();
            }

            catch(Exception ex) {}
        }

        // Show response on activity
        content.setText( text  );

    }

}




