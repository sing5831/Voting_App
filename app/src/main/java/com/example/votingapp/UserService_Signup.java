package com.example.votingapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService_Signup {

    @POST("users/")
    Call<UserResponse_Signup>saveUser(@Body UserRequest_Signup userRequest_signup);

}
