package com.gmaxmart.resort1000.fragments.resortdetails;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ResortDetailsFragmentsAdapter extends FragmentStateAdapter {

    public ArrayList<String>  tabs;
    public ResortDetailsFragmentsAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, @NotNull int resortId) {
        super(fragmentManager, lifecycle);

        tabs = new ArrayList<>();

        tabs.add("SELECT ROOM");
        tabs.add("ABOUT RESORT");
        tabs.add("RESORT AMENITIES");
        tabs.add("RATING & REVIEWS");
        tabs.add("RESORT LOCATION");
        tabs.add("RESORT POLICIES");
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new selectRoom();
            case 1: return new aboutResort();
            case 2: return ResortAmenities.newInstance("something");
            case 3: return ratingAndReviews.newInstance("something");
            case 4: return new ResortLocation().newInstance("something");
            case 5: return ResortPolicies.newInstance("something");
            default: return ResortPolicies.newInstance("something");
        }
    }

    @Override
    public int getItemCount() {
        return tabs.size();
    }
}
