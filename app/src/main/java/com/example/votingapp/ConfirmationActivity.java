package com.example.votingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class ConfirmationActivity extends AppCompatActivity {

    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        logout = findViewById(R.id.btn_logout);



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ConfirmationActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ConfirmationActivity.this, LoginActivity.class));
    }

}
