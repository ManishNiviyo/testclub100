package com.gmaxmart.resort1000;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gmaxmart.resort1000.adapters.OutResortAdapter;
import com.gmaxmart.resort1000.api.ApiClient;
import com.gmaxmart.resort1000.models.resortmodels.ResortResponseModel;
import com.gmaxmart.resort1000.models.resortmodels.ResortResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class resortsList extends AppCompatActivity {

    private RecyclerView resortListRecyclerView;
    private ProgressBar loadingListResorts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resorts_list);

        Intent i = getIntent();
        String city = i.getExtras().getString("city");

        resortListRecyclerView = findViewById(R.id.resortListRecyclerView);
        loadingListResorts = findViewById(R.id.loadingListResorts);

        try{
            Call<ResortResponseModel> call = ApiClient.getInstance().apiInterface().getResorts(city);
            call.enqueue(new Callback<ResortResponseModel>() {
                @Override
                public void onResponse(Call<ResortResponseModel> call, Response<ResortResponseModel> response) {
                    OutResortAdapter adapter = new OutResortAdapter(resortsList.this,response.body());
                    resortListRecyclerView.setLayoutManager(new LinearLayoutManager(resortsList.this));
                    resortListRecyclerView.setAdapter(adapter);
                    loadingListResorts.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<ResortResponseModel> call, Throwable t) {
                    Log.d("somethingnn",t.getLocalizedMessage());
                }
            });
        } catch (Exception e) {
            Log.d("somethingnn", e.getLocalizedMessage());
        }
    }
}