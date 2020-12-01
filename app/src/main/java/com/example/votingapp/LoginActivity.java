package com.example.votingapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

                if (TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(pin.getText().toString())){
                    Toast.makeText(LoginActivity.this,"All inputs Required", Toast.LENGTH_LONG);
                }
                else{
                    startActivity(new Intent(LoginActivity.this, AgreementActivity.class
                    ));

                    //LoginRequest loginRequest = new LoginRequest();
                    //loginRequest.setEmail(email.getText().toString());
                    //loginRequest.setPin(pin.getText().toString());
                    //loginUser(loginRequest);


                }


            }
        });

    }

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



}

