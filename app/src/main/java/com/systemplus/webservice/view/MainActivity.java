package com.systemplus.webservice.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.systemplus.webservice.R;
import com.systemplus.webservice.adapter.MovieClassAdapter;
import com.systemplus.webservice.api.ApiClient;
import com.systemplus.webservice.api.ApiInterface;
import com.systemplus.webservice.model.MovieData;
import com.systemplus.webservice.model.MoviesResponse;
import com.systemplus.webservice.model.Result;
import com.systemplus.webservice.util.RecyclerItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    public static final String MOVIE_DETAILS = "MOVIE_DETAILS";
    RecyclerView moviesList;

    private static final String API_KEY = "7e8f60e325cd06e164799af1e317d7a7";
    private MovieClassAdapter mMovieClassAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        moviesList = findViewById(R.id.moviesList);

        moviesList.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        showProgressDialog();
                        Result result = mMovieClassAdapter.getItem(position);
                        Call<MoviesResponse> call = apiService.getMovieDetails(result.getId(), API_KEY);
                        call.enqueue(new Callback<MoviesResponse>() {
                            @Override
                            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                                hidProgressDialog();
                                MoviesResponse moviesResponse = response.body();
                                Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
                                intent.putExtra(MOVIE_DETAILS, moviesResponse);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                                hidProgressDialog();

                            }
                        });


                    }
                })
        );

        Call<MovieData> call = apiService.getTopRatedMovies(API_KEY);

        showProgressDialog();
        call.enqueue(new Callback<MovieData>() {
            @Override
            public void onResponse(Call<MovieData> call, Response<MovieData> response) {
                hidProgressDialog();
                MovieData movieData = response.body();
                List<Result> moviesResult = movieData.getResults();

                mMovieClassAdapter = new MovieClassAdapter(moviesResult, MainActivity.this);
                moviesList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                moviesList.setAdapter(mMovieClassAdapter);

            }

            @Override
            public void onFailure(Call<MovieData> call, Throwable t) {
                hidProgressDialog();
                showToast(t.getMessage());
            }
        });
    }
}
