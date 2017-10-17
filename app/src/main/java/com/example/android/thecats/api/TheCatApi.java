package com.example.android.thecats.api;

import com.example.android.thecats.response.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by fbrsw on 11.10.2017.
 */

public interface TheCatApi {
    @GET("/api/images/get?api_key=MjMyMzgz&format=xml")
    Call<Response> getData(@Query("results_per_page") int count);

    @GET("/api/images/get?api_key=MjMyMzgz&format=xml")
    Call<Response> getData(@Query("results_per_page") int count, @Query("category") String category);

    @GET("/api/categories/list")
    Call<Response> getCategories();

    @GET("/api/images/getfavourites?api_key=MjMyMzgz&format=xml")
    Call<Response> getFavourites(@Query("sub_id") String subId);

    @GET("/api/images/favourite?api_key=MjMyMzgz&format=xml")
    Call<Response> action(@Query("sub_id") String subId, @Query("image_id") String imageId, @Query("action") String remove);

}
