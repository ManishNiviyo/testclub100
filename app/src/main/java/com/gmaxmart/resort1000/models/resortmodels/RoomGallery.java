package com.gmaxmart.resort1000.models.resortmodels;

import com.gmaxmart.resort1000.profilecontainer;

public class RoomGallery {
    public int id;
    public String room_id;
    public String image;

    public String getImageUrl()
    {
        return profilecontainer.baseUrl+"/resort/admin/rooms_images/"+image;
    }
}
