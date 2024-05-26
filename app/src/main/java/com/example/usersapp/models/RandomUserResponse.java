package com.example.usersapp.models;

import com.google.gson.annotations.SerializedName;

public class RandomUserResponse {
    @SerializedName("results")
    private RandomUser[] results;

    public RandomUser[] getResults() {
        return results;
    }

    public void setResults(RandomUser[] results) {
        this.results = results;
    }
}
