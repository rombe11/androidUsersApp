package com.example.usersapp.models;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {RandomUserEntity.class}, version = 2)
public abstract class UserDatabase extends RoomDatabase {
    private static UserDatabase instance;
    public abstract RandomUserDao randomUserDao();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Example migration logic
            database.execSQL("ALTER TABLE random_users ADD COLUMN city TEXT");
            database.execSQL("ALTER TABLE random_users ADD COLUMN country TEXT");
            database.execSQL("ALTER TABLE random_users ADD COLUMN age INTEGER");
        }
    };

    public static synchronized UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class, "user_database")
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return instance;
    }
}
