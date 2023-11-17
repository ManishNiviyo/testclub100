package com.gmaxmart.resort1000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class QuerySharing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_sharing);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(QuerySharing.this,MainActivity.class));
    }
}