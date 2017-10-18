package com.example.android.thecats;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.android.thecats.response.Image;

import java.util.ArrayList;

/**
 * Created by fbrsw on 11.10.2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<Image> galleryList;
    private Context context;

    private RecyclerClickListener mClickListener;

    public RecyclerViewAdapter(Context context, ArrayList<Image> galleryList) {
        this.galleryList = galleryList;
        this.context = context;
    }

    public void setClickListener(RecyclerClickListener callback) {
        mClickListener = callback;
    }

    public void setGalleryList(ArrayList<Image> galleryList) {
        this.galleryList = galleryList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.show(view, holder.getPosition());
            }
        });
        holder.setIsRecyclable(false);
        return holder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.title.setText(galleryList.get(position).getId());
        holder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Glide
                .with(context)
                .load(galleryList.get(position).getUrl())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
//                        Toast.makeText(context, "error " + holder.getItemId() + " pos " + position, Toast.LENGTH_LONG).show();
                        if (galleryList.size() > position) {
                            galleryList.remove(position);
                            notifyDataSetChanged();
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .error(R.drawable.access_error)
                .into(holder.img);

    }

    public boolean addItems(ArrayList<Image> newItems) {
        if (newItems != null) {
            galleryList.addAll(newItems);
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    public Image getItem(int pos) {
        if (galleryList.size() <= pos) {
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
        private ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            img = (ImageView) itemView.findViewById(R.id.img);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
        }
    }
}
