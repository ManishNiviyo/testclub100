package com.gmaxmart.resort1000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class PrMarketingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pr_marketing);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(PrMarketingActivity.this,MainActivity.class));
    }
}