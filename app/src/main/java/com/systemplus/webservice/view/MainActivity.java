package com.systemplus.webservice.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.systemplus.webservice.R;
import com.systemplus.webservice.api.ApiClient;
import com.systemplus.webservice.api.ApiInterface;
import com.systemplus.webservice.model.MovieData;
import com.systemplus.webservice.model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = "7e8f60e325cd06e164799af1e317d7a7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MovieData> call = apiService.getTopRatedMovies(API_KEY);

        call.enqueue(new Callback<MovieData>() {
            @Override
            public void onResponse(Call<MovieData> call, Response<MovieData> response) {
                MovieData movieData = response.body();
                List<Result> moviesResult = movieData.getResults();
                showToast(new Gson().toJson(movieData).toString());

            }

            @Override
            public void onFailure(Call<MovieData> call, Throwable t) {
                showToast(t.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
