package com.example.votingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class CandidateListActivity extends AppCompatActivity {

    String candidates[] = new String[]{"Donald Trump", "Joe Biden", "Kamala Harris", "Bernie Sanders"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_list);

        ListView listView = (ListView) findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, candidates);
        listView.setAdapter(adapter);

        Button btnSubmitVote = (Button) findViewById(R.id.btn_submit);
        btnSubmitVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConfirmationActivity();
            }
        });


    }

    public void openConfirmationActivity(){
        Intent intent = new Intent(this, ConfirmationActivity.class);
        startActivity(intent);
    }

}
