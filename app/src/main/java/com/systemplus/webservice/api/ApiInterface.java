package com.systemplus.webservice.api;

import com.systemplus.webservice.model.LoginRequest;
import com.systemplus.webservice.model.LoginResponse;
import com.systemplus.webservice.model.MovieData;
import com.systemplus.webservice.model.MoviesResponse;
import com.systemplus.webservice.model.polylines.PolyLineResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Rizwan on 04-Feb-18.
 */

public interface ApiInterface {

    @GET("movie/top_rated")
    Call<MovieData> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @POST("user/login")
    Call<LoginResponse> requestLogin(@Body LoginRequest loginRequest);

    @GET
    Call<PolyLineResponse> getPolyLine(@Url String url);


}
