package com.gmaxmart.resort1000.api;

import com.gmaxmart.resort1000.models.resortmodels.ResortResponseModel;
import com.gmaxmart.resort1000.models.resortmodels.ResortRoomResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface ApiInterface {
    @Headers({"Content-Type:application/json"})
    @GET("resort/api/hotels.php")
    Call<ResortResponseModel> getResorts(@Query("city") String city);

    @Headers({"Content-Type:application/json"})
    @GET("resort/api/hotel_rooms.php")
    Call<ResortRoomResponse> getRooms(@Query("hotel_id") String hotel_id);
}
