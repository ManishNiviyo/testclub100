package com.gmaxmart.resort1000;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.gmaxmart.resort1000.adapters.MyListAdapter;
import com.gmaxmart.resort1000.models.resortmodels.MyListData;

public class ResortBookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resort_booking);
        MyListData[] myListData = new MyListData[] {
                new MyListData("Kumarakom Lake Resort", R.drawable.resort4),
                new MyListData("Om Sai Beach Huts", R.drawable.resort),
                new MyListData("Ahilya By The Sea", R.drawable.resort2),
                new MyListData("Andores Resort And Spa", R.drawable.resort3),


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

        startActivity(new Intent(ResortBookingActivity.this,MainActivity.class));
    }
}