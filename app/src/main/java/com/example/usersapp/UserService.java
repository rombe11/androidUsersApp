package com.example.usersapp;

import com.example.usersapp.models.RandomUserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
public interface UserService {

    @GET("api")
    Call<RandomUserResponse> getRandomUser();
}
