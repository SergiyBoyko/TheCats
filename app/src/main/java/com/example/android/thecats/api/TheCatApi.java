package com.example.android.thecats.api;

import com.example.android.thecats.Entity.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by fbrsw on 11.10.2017.
 */

public interface TheCatApi {
    @GET("/api/images/get?api_key=MjMyMzgz&format=xml")
    Call<Response> getData(@Query("results_per_page") int count);
}
