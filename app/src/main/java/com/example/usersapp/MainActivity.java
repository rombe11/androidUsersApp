package com.example.usersapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.usersapp.models.RandomUser;
import com.example.usersapp.models.RandomUserDao;
import com.example.usersapp.models.RandomUserEntity;
import com.example.usersapp.models.RandomUserResponse;
import com.example.usersapp.models.UserDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView ageTextView;
    private TextView emailTextView;
    private TextView cityTextView;
    private TextView countryTextView;
    private ImageView image;

    private Button nextUserButton;
    private Button addToCollectionButton;
    private Button viewCollectionButton;

    private RandomUser currentUser;
    private List<RandomUser> usersList;
    private RandomUserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        firstNameTextView = findViewById(R.id.firstNameLabel);
        lastNameTextView = findViewById(R.id.lastNameLabel);
        ageTextView = findViewById(R.id.ageLabel);
        emailTextView = findViewById(R.id.emailLabel);
        cityTextView = findViewById(R.id.cityLabel);
        countryTextView = findViewById(R.id.countryLabel);
        image = findViewById(R.id.imageView);

        nextUserButton = findViewById(R.id.btnNextUser);
        addToCollectionButton = findViewById(R.id.btnAddToCollection);
        viewCollectionButton = findViewById(R.id.btnViewCollection);

        usersList = new ArrayList<>();
        userDao = UserDatabase.getInstance(this).randomUserDao();

        fetchRandomUser();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nextUserButton.setOnClickListener(v -> fetchRandomUser());

        addToCollectionButton.setOnClickListener(v -> addToCollection());

        viewCollectionButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UsersActivity.class);
            startActivity(intent);
        });
    }

    private RandomUserEntity createUserEntity(RandomUser user) {
        return new RandomUserEntity(
                user.getGender(),
                user.getName().getFirst(),
                user.getName().getLast(),
                user.getLocation().toString(),
                user.getEmail(),
                user.getPhone(),
                user.getCell(),
                user.getDob().toString(),
                user.getPicture().getLarge(),
                user.getLocation().getCity(),
                user.getLocation().getCountry(),
                user.getDob().getAge()
        );
    }

    private void insertUser(RandomUserEntity userEntity) {
        new Thread(() -> userDao.insert(userEntity)).start();
    }

    private void addToCollection() {
        if (currentUser != null) {
            if (!usersList.contains(currentUser)) {
                usersList.add(currentUser);
                //insert to the database
                RandomUserEntity userEntity = createUserEntity(currentUser);
                insertUser(userEntity);
                Toast.makeText(this, "User added to collection", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "User already exists in the collection", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "User has error details", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchRandomUser() {
        nextUserButton.setEnabled(false);
        addToCollectionButton.setEnabled(false);
        viewCollectionButton.setEnabled(false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://randomuser.me/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService service = retrofit.create(UserService.class);

        Call<RandomUserResponse> call = service.getRandomUser();
        call.enqueue(new Callback<RandomUserResponse>() {
            @Override
            public void onResponse(Call<RandomUserResponse> call, Response<RandomUserResponse> response) {
                if (response.isSuccessful()) {
                    RandomUserResponse randomUserResponse = response.body();
                    if (randomUserResponse != null && randomUserResponse.getResults() != null && randomUserResponse.getResults().length > 0) {
                        RandomUser user = randomUserResponse.getResults()[0];

                        firstNameTextView.setText(user.getName().getFirst());
                        lastNameTextView.setText(user.getName().getLast());
                        ageTextView.setText(String.valueOf(user.getDob().getAge()));
                        emailTextView.setText(user.getEmail());
                        cityTextView.setText(user.getLocation().getCity());
                        countryTextView.setText(user.getLocation().getCountry());

                        String imageUrl = user.getPicture().getLarge();
                        loadImage(imageUrl);

                        currentUser = user;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch random user", Toast.LENGTH_SHORT).show();
                }
                nextUserButton.setEnabled(true);
                addToCollectionButton.setEnabled(true);
                viewCollectionButton.setEnabled(true);
            }

            @Override
            public void onFailure(Call<RandomUserResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to fetch random user: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                setErrorLabels();

                nextUserButton.setEnabled(true);
                addToCollectionButton.setEnabled(true);
                viewCollectionButton.setEnabled(true);
            }
        });
    }

    private void loadImage(String imageUrl) {
        Picasso.get().load(imageUrl).into(image);
    }

    private void setErrorLabels() {
        firstNameTextView.setText(" Error");
        lastNameTextView.setText(" Error");
        ageTextView.setText(" Error");
        emailTextView.setText(" Error");
        cityTextView.setText(" Error");
        countryTextView.setText(" Error");
        String imageUrl = "https://png.pngtree.com/png-vector/20210827/ourmid/pngtree-error-404-page-not-found-png-image_3832696.jpg";
        loadImage(imageUrl);

        currentUser = null;
    }
}
