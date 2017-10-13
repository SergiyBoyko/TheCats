package com.example.android.thecats;

import android.content.Intent;
import android.content.IntentFilter;
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

    private ConnectionReceiver connectionReceiver = new ConnectionReceiver();
    private boolean connected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.registerReceiver(connectionReceiver, new IntentFilter(
                "android.net.conn.CONNECTIVITY_CHANGE"));

        tryConnect();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(connectionReceiver);
    }

    public void tryConnect() {
        if (Utils.isOnline(this) && !connected) {
            prepareMainActivity();
            connected = true;
        }

    }

    private boolean loadMore(RecyclerViewAdapter adapter, int count) {
        if (!Utils.isOnline(this) || adapter == null) return false;
        ArrayList<Image> createLists = loadFirst(count);
        return adapter.addItems(createLists);
    }

    private void prepareMainActivity() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);

        final RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<Image> createLists = loadFirst(50);
        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(), createLists);
        recyclerView.setAdapter(adapter);

        adapter.setClickListener(new RecyclerClickListener() {
            @Override
            public void onClick(View v) {
                Image img = adapter.getItem(getPos());

                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("id", img.getId() + " " + getPos());
                intent.putExtra("url", img.getUrl());

                //Start details activity
                startActivity(intent);
            }
        });

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                GridLayoutManager layoutManager = ((GridLayoutManager)recyclerView.getLayoutManager());

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    //End of list
                    loadMore(adapter, 50);
                    Toast.makeText(MainActivity.this, "Adding", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private class Task extends AsyncTask<Integer, Void, retrofit2.Response<com.example.android.thecats.Entity.Response>> {

        @Override
        protected retrofit2.Response<com.example.android.thecats.Entity.Response> doInBackground(Integer... integers) {
            retrofit2.Response<com.example.android.thecats.Entity.Response> response = null;
            try {
                if (integers.length < 1) throw new IOException();
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
