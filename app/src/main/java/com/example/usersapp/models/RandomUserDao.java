package com.example.usersapp.models;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RandomUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(RandomUserEntity randomUser);

    @Query("SELECT * FROM random_users")
    List<RandomUserEntity> getAllRandomUsers();

}
