package com.example.votingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StatusVotedActivity extends AppCompatActivity {

    Button logout1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_voted);

        logout1 = findViewById(R.id.btnlogout1);

        logout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmationActivity.this);
                builder.setTitle("Confirmation PopUp!").
                        setMessage("You sure, that you want to logout?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(getApplicationContext(),
                                        LoginActivity.class);
                                startActivity(i);
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder.create();
                alert11.show();
*/
                startActivity(new Intent(StatusVotedActivity.this, LoginActivity.class));
            }
        });
    }

}
