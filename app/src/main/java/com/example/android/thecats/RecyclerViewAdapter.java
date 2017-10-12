package com.example.android.thecats;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.thecats.Entity.Image;

import java.util.ArrayList;

/**
 * Created by fbrsw on 11.10.2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<Image> galleryList;
    private Context context;

    private View.OnClickListener mClickListener;

    public RecyclerViewAdapter(Context context, ArrayList<Image> galleryList) {
        this.galleryList = galleryList;
        this.context = context;
    }

    public void setClickListener(View.OnClickListener callback) {
        mClickListener = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onClick(view);
            }
        });
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(galleryList.get(position).getId());
        holder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide
                .with(context)
                .load(galleryList.get(position).getUrl())
                .into(holder.img);

    }

    public Image getItem(int pos)  {
        if (galleryList.size() <= pos) {
            System.out.println("errrrrrrrrrrrrrrooorrr " + pos + " " + galleryList.size());
            return null;
        }
        return galleryList.get(pos);
    }

    @Override
    public int getItemCount() {
        if (galleryList == null) return 0;
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
