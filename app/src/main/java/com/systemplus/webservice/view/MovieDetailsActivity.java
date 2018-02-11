package com.systemplus.webservice.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.systemplus.webservice.R;
import com.systemplus.webservice.model.MoviesResponse;

public class MovieDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Intent intent = getIntent();

        MoviesResponse moviesResponse = intent.getParcelableExtra(MainActivity.MOVIE_DETAILS);
        showToast(new Gson().toJson(moviesResponse));

        // This is dummy comment just for testing
    }
}
