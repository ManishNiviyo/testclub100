package com.gmaxmart.resort1000.models.resortmodels;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Room {
    public int id;
    public int hotel_id;
    public String room_type;

    public String getPrice() {
        return price;
    }

    public String price;
    public String rooms;
    public String date;
    public String status;
    public String book_status;
    public String city;
    public String  state;
    public String hotel_name;
    public ArrayList<RoomGallery> gallery;
    public ArrayList<Amenity> amenities;

    public ArrayList<RoomGallery> getGallery()
    {
        return gallery;
    }
}
