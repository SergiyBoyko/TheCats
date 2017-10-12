package com.example.android.thecats;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.android.thecats.Entity.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<Image> createLists = loadFirst(50);
        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(), createLists);
        recyclerView.setAdapter(adapter);

        adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = recyclerView.indexOfChild(v);

                Image img = adapter.getItem(pos);

                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("id", img.getId());
                intent.putExtra("url", img.getUrl());

                //Start details activity
                startActivity(intent);
            }
        });

    }

    private class Task extends AsyncTask<Integer, Void, retrofit2.Response<com.example.android.thecats.Entity.Response>> {

        @Override
        protected retrofit2.Response<com.example.android.thecats.Entity.Response> doInBackground(Integer... integers) {
            retrofit2.Response<com.example.android.thecats.Entity.Response> response = null;
            try {
                response = App.getApi().getData(integers[0]).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
    }

    private ArrayList<Image> loadFirst(int count) {
        com.example.android.thecats.Entity.Response response = null;
        Task t = new Task();
        t.execute(count);
        try {
            retrofit2.Response<com.example.android.thecats.Entity.Response> rez = t.get();
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
