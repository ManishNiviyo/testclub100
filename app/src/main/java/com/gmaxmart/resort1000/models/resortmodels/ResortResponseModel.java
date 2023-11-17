package com.gmaxmart.resort1000.models.resortmodels;

import java.util.ArrayList;

public class ResortResponseModel {

    public boolean status;
    public ArrayList<Resort> resorts;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<Resort> getResorts() {
        return resorts;
    }

    public void setResorts(ArrayList<Resort> resorts) {
        this.resorts = resorts;
    }


}
