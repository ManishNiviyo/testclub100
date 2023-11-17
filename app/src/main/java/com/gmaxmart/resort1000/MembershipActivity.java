package com.gmaxmart.resort1000;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.gmaxmart.resort1000.adapters.MyListAdapter;
import com.gmaxmart.resort1000.models.resortmodels.MyListData;

public class MembershipActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership2);
        MyListData[] myListData = new MyListData[] {
                new MyListData("Free Membership", R.drawable.freememebership),
                new MyListData("Executive Memebership", R.drawable.executive),
                new MyListData("Premium Membership", R.drawable.premium_memebership),
                new MyListData("Corporate Membership", R.drawable.corporatemembershipimage),


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

        startActivity(new Intent(MembershipActivity.this,MainActivity.class));
    }
}