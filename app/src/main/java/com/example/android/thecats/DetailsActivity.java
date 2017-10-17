package com.example.android.thecats;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.android.thecats.response.Response;

import java.io.IOException;

/**
 * Created by fbrsw on 12.10.2017.
 */

public class DetailsActivity extends AppCompatActivity {
    private String userId = null;
    private String imgId = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_item);

        imgId = getIntent().getStringExtra("id");
        String pos = getIntent().getStringExtra("pos");
        String url = getIntent().getStringExtra("url");
        userId = getIntent().getStringExtra("userId");

        String title = imgId + " " + pos;
        TextView titleTextView = (TextView) findViewById(R.id.image_id);
        titleTextView.setText(title);

        ImageView imageView = (ImageView) findViewById(R.id.image);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);

        Glide
                .with(this)
                .load(url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .error(R.drawable.access_error)
                .into(imageView);
    }

    private class ImageAction extends AsyncTask<Boolean, Void, Void> {

        @Override
        protected Void doInBackground(Boolean... params) {
            retrofit2.Response<com.example.android.thecats.response.Response> response = null;
            try {
                String action = null;
                if (params.length < 1) throw new IOException();
                if (!params[0]) action = "remove";
                response = App.getApi().action(userId, imgId, action).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    private void like(boolean action) {
        ImageAction imageAction = new ImageAction();
        imageAction.execute(action);
    }

    public void action(View view) {
        if (userId == null) {
            Toast.makeText(DetailsActivity.this, "login first", Toast.LENGTH_SHORT).show();
            return;
        }
        if (view.getId() == R.id.like) {
            Toast.makeText(DetailsActivity.this, "like\nadded to favourites", Toast.LENGTH_SHORT).show();
                like(true);
        } else if (view.getId() == R.id.unlike) {
            Toast.makeText(DetailsActivity.this, "unlike\nremoved from favourites", Toast.LENGTH_SHORT).show();
                like(false);
        }
    }
}
