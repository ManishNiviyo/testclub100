package com.gmaxmart.resort1000.adapters;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gmaxmart.resort1000.R;
import com.gmaxmart.resort1000.models.resortmodels.Amenity;

import java.util.ArrayList;
import java.util.Locale;

public class AmenitiesAdapter extends RecyclerView.Adapter<AmenitiesAdapter.viewHolder> {

    ArrayList<Amenity> amenities;
    public AmenitiesAdapter(ArrayList<Amenity> amenities)
    {
        this.amenities = amenities;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotelamanitiesitem,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Amenity amenity  = amenities.get(position);
        String amenityName = amenity.getAmenities();
        holder.amenityText.setText(amenityName);

        ImageView icon = holder.amenityIcon;
        String amenityNameInLC = amenityName.toLowerCase();
        if(amenityNameInLC.contains("wifi")){
            icon.setImageResource(R.drawable.wifi);
        } else if(amenityNameInLC.contains("security")) {
            icon.setImageResource(R.drawable.video);
        } else if(amenityNameInLC.contains("lcd")) {
            icon.setImageResource(R.drawable.monitor);
        } else if(amenityNameInLC.contains("card")){
            icon.setImageResource(R.drawable.credit_card);
        }
    }

    @Override
    public int getItemCount() {
        return amenities.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        TextView amenityText;
        ImageView amenityIcon;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            amenityText = itemView.findViewById(R.id.amenityText);
            amenityIcon = itemView.findViewById(R.id.amenityIcon);
        }
    }
}
