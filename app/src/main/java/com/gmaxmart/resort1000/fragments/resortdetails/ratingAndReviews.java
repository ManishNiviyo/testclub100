package com.gmaxmart.resort1000.fragments.resortdetails;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmaxmart.resort1000.R;

public class ratingAndReviews extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rating_and_reviews, container, false);
    }
    public static ratingAndReviews newInstance(String text) {

        ratingAndReviews f = new ratingAndReviews();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}