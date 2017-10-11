package com.example.android.thecats.api;

import com.example.android.thecats.CatItem;
import com.example.android.thecats.GeneralServerResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by fbrsw on 11.10.2017.
 */

public interface TheCatApi {
    @GET("/api/images/get")
    Call<List<GeneralServerResponse>> getData(@Query("num") int count);
}
