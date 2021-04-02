package com.example.votingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.text.TextUtils.isEmpty;


public class LoginActivity extends AppCompatActivity {

    EditText email, pin;
    Button btnLogin, btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email_login);
        pin = findViewById(R.id.pin);
        btnLogin = findViewById(R.id.btn_login);
        btnSignup = (findViewById(R.id.btn_signup));

        String username, passwd;
        username = email.getText().toString();
        passwd = pin.getText().toString();

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






                    startActivity(new Intent(LoginActivity.this, AgreementActivity.class
                    ));
                }
            }
        });




    }

    public class fetchData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
           // arrayList.clear();
            String result = null;
            try {
                URL url = new URL("https://mobilevotingapp.azurewebsites.net/api/login");
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

            try {
                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray("data");

                for (int i = 0; i < array.length(); i++) {

                    JSONObject jsonObject = array.getJSONObject(i);
                    int id = jsonObject.getInt("UserId");
                    String first_name = jsonObject.getString("FirstName");
                    String last_name = jsonObject.getString("last_name");
                    String email = jsonObject.getString("Email");

                    UserModel model = new UserModel();
                    model.setId(id);
                    model.setFirst_name(first_name);
                    model.setEmail(email);


                   // arrayList.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //CustomAdapter adapter = new CustomAdapter(BallotActivity.this, arrayList);
            //listView.setAdapter(adapter);

        }
    }



    public void checkUsername() {
        /*
        		if (isEmpty(firstName)) {
			Toast t = Toast.makeText(this, "You must enter first name to register!", Toast.LENGTH_SHORT);
			t.show();
		}

        boolean isEmail(EditText text) {
            CharSequence email = text.getText().toString();
            return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        }

        boolean isEmpty(EditText text) {
            CharSequence str = text.getText().toString();
            return TextUtils.isEmpty(str);
        }
        boolean isValid = true;
        if (isEmpty(email.getText())) {
            email.setError("You must enter username to login!");
            isValid = false;
        } else {
            if (!isEmail(email)) {
                email.setError("Enter valid email!");
                isValid = false;
            }
        }

        if (isEmpty(pin.getText())) {
            pin.setError("You must enter password to login!");
            isValid = false;
        } else {
            if (pin.getText().toString().length() < 4) {
                pin.setError("Password must be at least 4 chars long!");
                isValid = false;
            }
        }

        //check email and password
        //IMPORTANT: here should be call to backend or safer function for local check; For example simple check is cool
        //For example simple check is cool
        if (isValid) {
            String usernameValue = email.getText().toString();
            String passwordValue = pin.getText().toString();
            if (usernameValue.equals("test@test.com") && passwordValue.equals("password1234")) {
                //everything checked we open new activity
                Intent i = new Intent(LoginActivity.this, AgreementActivity.class);
                startActivity(i);
                //we close this activity
                this.finish();
            } else {
                Toast t = Toast.makeText(this, "Wrong email or password!", Toast.LENGTH_SHORT);
                t.show();
            }
        }

         */

    }

    /*
    public void loginUser(LoginRequest loginRequest){
        Call<LoginResponse> loginResponseCall = ApiClient.getUserService().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    LoginResponse loginResponse = response.body();
                    startActivity(new Intent(LoginActivity.this, LoginActivity.class).putExtra("data", loginResponse));
                    finish();
                }
                else{
                    Toast.makeText(LoginActivity.this,"Error occurred", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Failed", Toast.LENGTH_LONG);
            }
        });
    }
*/


}

