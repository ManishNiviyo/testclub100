package com.gmaxmart.resort1000;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.gmaxmart.resort1000.adapters.MyListAdapter;
import com.gmaxmart.resort1000.models.resortmodels.MyListData;

public class TourBookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_booking);
        MyListData[] myListData = new MyListData[] {
                new MyListData("Domestic Tour", R.drawable.tajmahal),
                new MyListData("International Tour", R.drawable.newyork),
                new MyListData("Pilgrim Tour", R.drawable.kashi_vishwanathtemple),
                new MyListData("Business Tour", R.drawable.corportaetour),


        };

      /*  RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewresort);
        MyListAdapter adapter = new MyListAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(TourBookingActivity.this,MainActivity.class));
    }
}