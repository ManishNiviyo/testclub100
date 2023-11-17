package com.gmaxmart.resort1000.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gmaxmart.resort1000.R;
import com.gmaxmart.resort1000.models.resortmodels.Resort;
import com.gmaxmart.resort1000.models.resortmodels.ResortResponseModel;
import com.gmaxmart.resort1000.profilecontainer;
import com.gmaxmart.resort1000.resortDetails;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OutResortAdapter extends RecyclerView.Adapter<OutResortAdapter.viewHolder> {

    Context ctx;
    ResortResponseModel list;

    public OutResortAdapter(Context ctx, ResortResponseModel list)
    {
        this.list = list;
        this.ctx = ctx;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.resort_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Resort resort = list.getResorts().get(position);
        int resortId = resort.getId();
        String resortName = resort.getName();
        String location = resort.getCityName() + "," + resort.getStateName();

        holder.resortName.setText(resortName);
        holder.resortLocation.setText(location);
        holder.resortprice.setText(resort.getPrice());
        Glide.with(holder.resortImage.getContext()).load(resort.getImageUrl()).into(holder.resortImage);

        holder.bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(holder.resortImage,"imageTransition");
                pairs[1] = new Pair<View, String>(holder.resortName,"nameTransition");

                Intent intent = new Intent(ctx, resortDetails.class);
                intent.putExtra("resort",resort);

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) ctx,pairs);

                ctx.startActivity(intent, options.toBundle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.getResorts().size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView resortName,resortLocation,resortprice;
        ImageView resortImage;
        Button bookNowButton;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            resortName = itemView.findViewById(R.id.resortName);
            resortprice = itemView.findViewById(R.id.resort_price);
            resortLocation = itemView.findViewById(R.id.resortLocation);
            resortImage = itemView.findViewById(R.id.resortImage);
            bookNowButton = itemView.findViewById(R.id.bookNowButton);
        }
    }
}
