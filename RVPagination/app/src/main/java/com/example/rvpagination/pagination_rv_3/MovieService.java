package com.example.rvpagination.pagination_rv_3;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieService {

    // http://35.200.174.74/apis/volley_array.json
    @GET("volley_array.json")
    Call<List<Movie>> getMovies();
}
