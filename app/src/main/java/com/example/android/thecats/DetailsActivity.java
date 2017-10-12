package com.example.android.thecats;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by fbrsw on 12.10.2017.
 */

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_item);

        String id = getIntent().getStringExtra("id");
        String url = getIntent().getStringExtra("url");

        TextView titleTextView = (TextView) findViewById(R.id.image_id);
        titleTextView.setText(id);

        ImageView imageView = (ImageView) findViewById(R.id.image);

        Glide
                .with(this)
                .load(url)
                .into(imageView);
    }
}
