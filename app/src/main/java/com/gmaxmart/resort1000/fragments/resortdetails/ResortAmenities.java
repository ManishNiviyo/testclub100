package com.gmaxmart.resort1000.fragments.resortdetails;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmaxmart.resort1000.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResortAmenities#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResortAmenities extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resort_amenities, container, false);
    }
    public static ResortAmenities newInstance(String text) {

        ResortAmenities f = new ResortAmenities();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}