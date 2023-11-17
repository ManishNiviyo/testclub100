package com.gmaxmart.resort1000.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gmaxmart.resort1000.R;
import com.gmaxmart.resort1000.models.resortmodels.RoomGallery;

import java.util.ArrayList;

public class ResortImageGalleryAdapter extends RecyclerView.Adapter<ResortImageGalleryAdapter.viewHolder> {

    Context ctx;
    ArrayList<RoomGallery> images;

    public ResortImageGalleryAdapter(Context ctx, ArrayList<RoomGallery> images)
    {
        this.images = images;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.imageslider, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        RoomGallery rg = images.get(position);
        Glide.with(holder.sliderimage.getContext()).load(rg.getImageUrl()).into(holder.sliderimage);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        ImageView sliderimage;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            sliderimage = itemView.findViewById(R.id.sliderimage);
        }
    }
}
