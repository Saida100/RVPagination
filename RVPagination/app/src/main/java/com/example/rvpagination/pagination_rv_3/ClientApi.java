package com.example.rvpagination.pagination_rv_3;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientApi {
    // http://35.200.174.74/apis/volley_array.json
    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://35.200.174.74/apis/")
                    .build();
        }
        return retrofit;
    }

}
