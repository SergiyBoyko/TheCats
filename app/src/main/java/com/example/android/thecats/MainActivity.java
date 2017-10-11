package com.example.android.thecats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<CatItem> createLists = prepareData();
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(), createLists);
        recyclerView.setAdapter(adapter);

    }

    private ArrayList<CatItem> prepareData() {
        return null;
    }

    private ArrayList<CatItem> prepareTestData() {
        ArrayList<CatItem> catItems = new ArrayList<>();
        String url = "http://24.media.tumblr.com/tumblr_m1wva7zKq41qlluv1o1_500.jpg";
        String title = "img1";
        catItems.add(new CatItem(title, url));
        url = "http://25.media.tumblr.com/tumblr_lw2frlbLzM1qzv52ko1_1280.jpg";
        title = "img2";
        catItems.add(new CatItem(title, url));
        url = "http://25.media.tumblr.com/tumblr_m04876cqrm1qzex9io1_500.jpg";
        title = "img3";
        catItems.add(new CatItem(title, url));
        url = "http://25.media.tumblr.com/tumblr_lmyopf9bNq1qhwmnpo1_1280.jpg";
        title = "img4";
        catItems.add(new CatItem(title, url));
        url = "http://24.media.tumblr.com/tumblr_m2iwnmLOWB1rnzhbzo1_500.jpg";
        title = "img5";
        catItems.add(new CatItem(title, url));
        url = "http://24.media.tumblr.com/tumblr_m1wva7zKq41qlluv1o1_500.jpg";
        title = "img1";
        catItems.add(new CatItem(title, url));
        url = "http://25.media.tumblr.com/tumblr_lw2frlbLzM1qzv52ko1_1280.jpg";
        title = "img2";
        catItems.add(new CatItem(title, url));
        url = "http://25.media.tumblr.com/tumblr_m04876cqrm1qzex9io1_500.jpg";
        title = "img3";
        catItems.add(new CatItem(title, url));
        url = "http://25.media.tumblr.com/tumblr_lmyopf9bNq1qhwmnpo1_1280.jpg";
        title = "img4";
        catItems.add(new CatItem(title, url));
        url = "http://24.media.tumblr.com/tumblr_m2iwnmLOWB1rnzhbzo1_500.jpg";
        title = "img5";
        catItems.add(new CatItem(title, url));
        url = "http://24.media.tumblr.com/tumblr_m1wva7zKq41qlluv1o1_500.jpg";
        title = "img1";
        catItems.add(new CatItem(title, url));
        url = "http://25.media.tumblr.com/tumblr_lw2frlbLzM1qzv52ko1_1280.jpg";
        title = "img2";
        catItems.add(new CatItem(title, url));
        url = "http://25.media.tumblr.com/tumblr_m04876cqrm1qzex9io1_500.jpg";
        title = "img3";
        catItems.add(new CatItem(title, url));
        url = "http://25.media.tumblr.com/tumblr_lmyopf9bNq1qhwmnpo1_1280.jpg";
        title = "img4";
        catItems.add(new CatItem(title, url));
        url = "http://24.media.tumblr.com/tumblr_m2iwnmLOWB1rnzhbzo1_500.jpg";
        title = "img5";
        catItems.add(new CatItem(title, url));
        return catItems;
    }
}
