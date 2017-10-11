package com.example.android.thecats;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by fbrsw on 11.10.2017.
 */

public interface TheCatApi {
    @GET("/api/images/get")
    Call<List<CatItem>> getData(@Query("num") int count);
}
