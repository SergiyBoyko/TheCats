package com.example.android.thecats;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.android.thecats.api.TheCatApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.ResponseBody;
import retrofit2.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<Image> createLists = loadFirst(50);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(), createLists);
        recyclerView.setAdapter(adapter);

    }

    private class Task extends AsyncTask<Integer, Void, retrofit2.Response<Response>> {

        @Override
        protected retrofit2.Response<Response> doInBackground(Integer... integers) {
            retrofit2.Response<Response> response = null;
            try {
                response = App.getApi().getData(integers[0]).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
    }

    private ArrayList<Image> loadFirst(int count) {
        Response response = null;
        Task t = new Task();
        t.execute(count);
        try {
            retrofit2.Response<Response> rez = t.get();
            if (rez == null) Toast.makeText(this, "Error loading", Toast.LENGTH_LONG).show();
            else {
                Toast.makeText(this, "Loaded", Toast.LENGTH_LONG).show();
                response = rez.body();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        ArrayList<Image> images = new ArrayList<>();
        if (response != null)
            images.addAll(response.getData().getImages().getImages());

        return images;
    }

}
