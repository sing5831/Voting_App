package com.example.votingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class AgreementActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);

        Button btn =(Button)findViewById( R.id.btn_ballot);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCandidateListActivity();
            }
        });

        CheckBox checkBox= ( CheckBox ) findViewById( R.id.agree);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    btn.setEnabled(true);
                }
                else{
                    btn.setEnabled(false);
                }
            }
        });

    }

    public void openCandidateListActivity(){
        Intent intent = new Intent(this, CandidateListActivity.class);
        startActivity(intent);
    }

}
