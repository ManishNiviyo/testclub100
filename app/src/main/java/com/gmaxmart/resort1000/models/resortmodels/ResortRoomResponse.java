package com.gmaxmart.resort1000.models.resortmodels;

import java.util.ArrayList;

public class ResortRoomResponse {
    public boolean status;
    public ArrayList<Room> rooms;

    public boolean getStatus() {
        return status;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

}
