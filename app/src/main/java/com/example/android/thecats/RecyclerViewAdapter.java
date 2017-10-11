package com.example.android.thecats;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by fbrsw on 11.10.2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<CatItem> galleryList;
    private Context context;

    public RecyclerViewAdapter(Context context, ArrayList<CatItem> galleryList) {
        this.galleryList = galleryList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(galleryList.get(position).getImageTitle());
        holder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide
                .with(context)
                .load(galleryList.get(position).getImageUrl())
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
