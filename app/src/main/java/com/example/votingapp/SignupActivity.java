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

public class SignupActivity extends AppCompatActivity {

    EditText fname;
    EditText lname;
    EditText email, add, dofb, d_license;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);



        fname = findViewById(R.id.first_name);
        lname = findViewById(R.id.last_name);
        email = findViewById(R.id.email_signup);
        add = findViewById(R.id.address);
        dofb = findViewById(R.id.dob);
        d_license = findViewById(R.id.license);
        register = findViewById(R.id.btn_next);

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if(TextUtils.isEmpty(fname.getText().toString()) || TextUtils.isEmpty(lname.getText().toString()) || TextUtils.isEmpty(email.getText().toString()) ||
                        TextUtils.isEmpty(add.getText().toString()) || TextUtils.isEmpty(dofb.getText().toString()) || TextUtils.isEmpty(d_license.getText().toString())){

                    Toast.makeText(SignupActivity.this,"All inputs Required", Toast.LENGTH_LONG);
                }
                else{
                    UserRequest_Signup userRequest_signup = new UserRequest_Signup();
                    userRequest_signup.setFirst_name(fname.getText().toString());
                    userRequest_signup.setLast_name(lname.getText().toString());
                    userRequest_signup.setEmail(email.getText().toString());
                    userRequest_signup.setAddress(add.getText().toString());
                    userRequest_signup.setDate_of_birth(dofb.getText().toString());
                    userRequest_signup.setDriving_license(d_license.getText().toString());

                    saveUser(userRequest_signup);
                }

            }
        });

    }
/*
    public UserRequest_Signup createrequest(){

        UserRequest_Signup userRequest_signup = new UserRequest_Signup();
        userRequest_signup.setFirst_name(fname.getText().toString());
        userRequest_signup.setLast_name(lname.getText().toString());
        userRequest_signup.setEmail(email.getText().toString());
        userRequest_signup.setAddress(add.getText().toString());
        userRequest_signup.setDate_of_birth(dofb.getText().toString());
        userRequest_signup.setDriving_license(d_license.getText().toString());

        return userRequest_signup;

    }

 */

    public void saveUser(UserRequest_Signup userRequest_signup){
        Call<UserResponse_Signup> userResponse_signupCall = ApiClient.getUserService().saveUser(userRequest_signup);
        userResponse_signupCall.enqueue(new Callback<UserResponse_Signup>() {
            @Override
            public void onResponse(Call<UserResponse_Signup> call, Response<UserResponse_Signup> response) {
                if(response.isSuccessful()){
                    Toast.makeText(SignupActivity.this,"Save Success", Toast.LENGTH_LONG);
                    startActivity(new Intent(SignupActivity.this, UploadPhoto.class));
                    finish();
                }
                else{
                    Toast.makeText(SignupActivity.this,"Unable to Register User", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<UserResponse_Signup> call, Throwable t) {
                Toast.makeText(SignupActivity.this,"Failed", Toast.LENGTH_LONG);
            }
        });

    }


}
