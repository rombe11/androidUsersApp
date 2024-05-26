package com.example.usersapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usersapp.models.RandomUserEntity;
import com.example.usersapp.models.UserDatabase;

import java.util.List;

public class UsersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private UserDatabase db;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        db = UserDatabase.getInstance(this);

        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(UsersActivity.this, MainActivity.class);
            startActivity(intent);
        });

        new Thread(() -> {
            List<RandomUserEntity> users = db.randomUserDao().getAllRandomUsers();
            runOnUiThread(() -> {
                userAdapter = new UserAdapter(UsersActivity.this, users);
                recyclerView.setAdapter(userAdapter);
            });
        }).start();
    }
}
