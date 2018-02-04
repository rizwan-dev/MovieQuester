package com.systemplus.webservice.api;

import com.systemplus.webservice.model.MovieData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Rizwan on 04-Feb-18.
 */

public interface ApiInterface {

    @GET("movie/top_rated")
    Call<MovieData> getTopRatedMovies(@Query("api_key") String apiKey);
}
