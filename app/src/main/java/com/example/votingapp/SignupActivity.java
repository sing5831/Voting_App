package com.example.votingapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends Activity {

    EditText fname;
    EditText lname;
    EditText email, passwd, repasswd;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
/*
        fname = findViewById(R.id.first_name);
        lname = findViewById(R.id.last_name);
        email = findViewById(R.id.email_signup);
        passwd = findViewById(R.id.passwd_signup);
        repasswd = findViewById(R.id.repassword);
        register = findViewById(R.id.btn_signup);

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

            }
        });

    }

    public UserRequest_Signup createrequest(){

        UserRequest_Signup userRequest_signup = new UserRequest_Signup();
        userRequest_signup.setFirst_name(fname.getText().toString());
        userRequest_signup.setLast_name(lname.getText().toString());
        userRequest_signup.setEmail(email.getText().toString());

        return userRequest_signup;

    }

    public void saveUser(UserRequest_Signup userRequest_signup){
        Call<UserResponse_Signup> userResponse_signupCall = ApiClient.getUserService().saveUser(userRequest_signup);
        userResponse_signupCall.enqueue(new Callback<UserResponse_Signup>() {
            @Override
            public void onResponse(Call<UserResponse_Signup> call, Response<UserResponse_Signup> response) {
                if(response.isSuccessful()){
                    Toast.makeText(SignupActivity.this,"Save Success", Toast.LENGTH_LONG);
                }
                else{
                    Toast.makeText(SignupActivity.this,"Failed", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<UserResponse_Signup> call, Throwable t) {
                Toast.makeText(SignupActivity.this,"Failed", Toast.LENGTH_LONG);
            }
        });



 */
    }


}
