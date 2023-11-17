package com.gmaxmart.resort1000.models.resortmodels;

import android.util.Log;

import com.gmaxmart.resort1000.adapters.ResortImageGalleryAdapter;
import com.gmaxmart.resort1000.profilecontainer;

import java.util.ArrayList;
import java.io.Serializable;

public class Resort implements Serializable {
    public int id;
    public String name;
    public String city;
    public String state;
    public String country;
    public String address;

    public String getAbout() {
        return about;
    }

    public String about;
    public String location;
    public String policies;
    public String review;
    public String rating;
    public String hotel_star;

    public String getCheckin() {
        return checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public String getChildren() {
        return children;
    }

    public String getLocal_id() {
        return local_id;
    }

    public String getCouple() {
        return couple;
    }

    public String getForeign_guest() {
        return foreign_guest;
    }

    public String checkin;
    public String checkout;
    public String children;
    public String local_id;
    public String couple;
    public String foreign_guest;
    public String date;
    public String status;

    public ArrayList<Amenity> getAmenities() {
        return amenities;
    }

    public ArrayList<Amenity> amenities;
//    public ArrayList<ResortGallery> gallery;
    public String city_name;
    public String state_name;

    public String getPrice() {
        return price;
    }

    public String price;
    public String image;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getCityName() {
        return city_name;
    }
    public
    String getStateName() {
        return state_name;
    }
    public String getImageName() {
        return image;
    }

    public String getImageUrl()
    {
        return profilecontainer.baseUrl+"/resort/admin/hotel_images/"+image;
    }
    public String getAddress() {
        return address;
    }
    public String getLocationIframe() {
        return location;
    }

//    public ArrayList<ResortGallery> getGallery()
//    {
//        Log.d("someasdfasdf",gallery.toString());
//        return gallery;
//    }

}