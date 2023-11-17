package com.gmaxmart.resort1000.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gmaxmart.resort1000.R;
import com.gmaxmart.resort1000.models.resortmodels.Room;

import java.util.ArrayList;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.ViewHolder> {

    private ArrayList<Room> rooms;
    private Context ctx;
    public RoomsAdapter(ArrayList<Room> rooms, Context ctx) {
        this.rooms = rooms;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Room room = rooms.get(position);
        TextView quantityTextView = holder.roomQuantity;
        String roomPrice = room.getPrice();

        holder.roomQuantityDecrementButton.setAlpha(0.5f);
        holder.roomPrice.setText("₹ "+roomPrice+"/-");
        try{
            Glide.with(holder.itemView.getContext()).load(room.getGallery().get(0).getImageUrl()).into(holder.roomImage);
        } catch (ArrayIndexOutOfBoundsException e) {

        }

        holder.roomQuantityIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(quantityTextView.getText().toString().trim());
                try{
                    if(quantity>=10){
                        holder.roomQuantityIncrementButton.setAlpha(0.5f);
                        Toast.makeText(ctx, "You can add maximum of 10 rooms", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if((quantity+1)==10){
                        holder.roomQuantityIncrementButton.setAlpha(0.5f);
                    }
                    holder.roomQuantityDecrementButton.setAlpha(1);
                    quantityTextView.setText(String.valueOf(quantity+1));
                }catch (Exception e) {
                    Toast.makeText(ctx, "Something Went Wrong please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.roomQuantityDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(quantityTextView.getText().toString().trim());
                try{
                    int newQuantity = quantity-1;
                    holder.roomQuantityIncrementButton.setAlpha(1);
                    if(quantity<=0){
                        holder.roomQuantityDecrementButton.setAlpha(0.5f);
                        return;
                    }
                    if(newQuantity==0){
                        holder.roomQuantityDecrementButton.setAlpha(0.5f);
                    }
                    quantityTextView.setText(String.valueOf(newQuantity));
                }catch (Exception e) {
                    Toast.makeText(ctx, "Something Went Wrong please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView roomQuantityIncrementButton, roomQuantityDecrementButton, roomQuantity, roomPrice;
        ImageView roomImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomQuantityIncrementButton = itemView.findViewById(R.id.roomQuantityIncrement);
            roomQuantityDecrementButton = itemView.findViewById(R.id.roomQuantityDecrement);
            roomQuantity = itemView.findViewById(R.id.roomQuantity);
            roomImage = itemView.findViewById(R.id.roomImage);
            roomPrice = itemView.findViewById(R.id.roomPrice);
        }
    }
}
