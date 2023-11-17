package com.gmaxmart.resort1000;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gmaxmart.resort1000.adapters.AmenitiesAdapter;
import com.gmaxmart.resort1000.adapters.ResortImageGalleryAdapter;
import com.gmaxmart.resort1000.adapters.RoomsAdapter;
import com.gmaxmart.resort1000.api.ApiClient;
import com.gmaxmart.resort1000.models.resortmodels.Amenity;
import com.gmaxmart.resort1000.models.resortmodels.Resort;
import com.gmaxmart.resort1000.models.resortmodels.ResortRoomResponse;
import com.google.android.gms.maps.MapView;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class resortDetails extends AppCompatActivity {

    protected ViewPager2 recyclerView;
    protected RecyclerView amenitiesRecyclerView, roomTypesRecyclerView;
    ProgressBar galleryProgressBar;
    private TextView resortPlace, resortName, aboutTitle, aboutText, resortPrice, checkInValue, checkOutValue, childrenExtraValue, localIdValue, coupleFriendlyValue, foreignGuestValue;
    private ArrayList<Amenity> amenities;
    private WebView resortLocationWebView;
    private String mapUrl;
    AmenitiesAdapter amenitiesAdapter;
    private Resort resort;
    private View backButton;

    CardView ratingbutton,submitbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resort_details);

        resort = (Resort) getIntent().getSerializableExtra("resort");
        submitbutton=findViewById(R.id.submitbutton);
        initValues();
        setValues();
        setListeners();

        Call<ResortRoomResponse> call  = ApiClient.getInstance().apiInterface().getRooms(String.valueOf(resort.getId()));
        call.enqueue(new Callback<ResortRoomResponse>() {
            @Override
            public void onResponse(Call<ResortRoomResponse> call, Response<ResortRoomResponse> response) {
                ResortImageGalleryAdapter adapter = new ResortImageGalleryAdapter(resortDetails.this,response.body().getRooms().get(0).getGallery());
//                recyclerView.setLayoutManager(new LinearLayoutManager(resortDetails.this,LinearLayoutManager.HORIZONTAL, false));
//                recyclerView.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                recyclerView.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
                recyclerView.setAdapter(adapter);
                recyclerView.setCurrentItem(0);
                galleryProgressBar.setVisibility(View.INVISIBLE);

                RoomsAdapter roomsAdapter = new RoomsAdapter(response.body().getRooms(), resortDetails.this);
                roomTypesRecyclerView.setLayoutManager(new LinearLayoutManager(resortDetails.this));
                roomTypesRecyclerView.setAdapter(roomsAdapter);
            }

            @Override
            public void onFailure(Call<ResortRoomResponse> call, Throwable t) {

            }
        });

//        recyclerView.setLayoutManager(new LinearLayoutManager(resortDetails.this));
//        ResortImageGalleryAdapter resortGalleryAdapter = new ResortImageGalleryAdapter(resortDetails.this,resort.getGallery());
//        recyclerView.setAdapter(resortGalleryAdapter);

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences2 = getSharedPreferences("logindetail",MODE_PRIVATE);
                String login=sharedPreferences2.getString("login","no");
                if(login.equals("yes")){
                    Intent intent = getIntent();
                    if (intent != null) {
                        String id = intent.getStringExtra("ID");
                        String title = intent.getStringExtra("Title");
                        String place = intent.getStringExtra("Place");
                        String photo = intent.getStringExtra("Photo");
                        String day = intent.getStringExtra("Day");
                        String price = intent.getStringExtra("Price");
                        String discount = intent.getStringExtra("Discount");
                        String typeTour = intent.getStringExtra("TypeTour");

                        intent = new Intent(resortDetails.this, Bookyourholidayresort.class);

                        intent.putExtra("ID", resort.id);
                        intent.putExtra("Title", title);
                        intent.putExtra("Place", place);
                        intent.putExtra("Photo", photo);
                        intent.putExtra("Day", day);
                        intent.putExtra("Price", resort.price);
                        intent.putExtra("Discount", discount);
                        intent.putExtra("TypeTour", typeTour);
                        startActivity(intent);

                        // Now you can use these values as needed in your detailresort activity
                    }
                }
                else {
                    startActivity(new Intent(resortDetails.this,login.class));
                }

            }
        });
    }
    protected void initValues(){
        recyclerView = findViewById(R.id.imageGallery);
        amenitiesRecyclerView = findViewById(R.id.hotelAmenitiesRecyclerView);
        roomTypesRecyclerView = findViewById(R.id.roomTypesRecyclerView);
        galleryProgressBar = findViewById(R.id.loadingGallery);
        resortPlace = findViewById(R.id.resortPlace);
        resortName = findViewById(R.id.resortName);
        resortPrice = findViewById(R.id.resortPrice);
        aboutTitle = findViewById(R.id.aboutTitle);
        aboutText = findViewById(R.id.aboutText);
        checkInValue = findViewById(R.id.checkInValue);
        checkOutValue = findViewById(R.id.checkOutValue);
        childrenExtraValue = findViewById(R.id.childrenExtraValue);
        localIdValue = findViewById(R.id.localIdValue);
        coupleFriendlyValue = findViewById(R.id.coupleFriendlyValue);
        foreignGuestValue = findViewById(R.id.foreignGuestValue);
        backButton = findViewById(R.id.backButton);
        amenities = resort.getAmenities();

        amenitiesAdapter = new AmenitiesAdapter(amenities);
        amenitiesRecyclerView.setLayoutManager(new LinearLayoutManager(resortDetails.this));
        amenitiesRecyclerView.setAdapter(amenitiesAdapter);
        amenitiesRecyclerView.setNestedScrollingEnabled(false);

        mapUrl = resort.getLocationIframe();


}
    protected void setValues(){
        resortPlace.setText(resort.getCityName());
        resortName.setText(resort.getName());
        aboutTitle.setText("About "+resort.getName().trim());
        aboutText.setText(Html.fromHtml(resort.getAbout()));
        resortPrice.setText("₹ "+resort.getPrice()+"/-");
        checkInValue.setText(resort.getCheckin());
        checkOutValue.setText(resort.getCheckout());
        childrenExtraValue.setText(resort.getChildren());
        localIdValue.setText(resort.getLocal_id());
        coupleFriendlyValue.setText(resort.getCouple());
        foreignGuestValue.setText(resort.getForeign_guest());
    }

    protected void setListeners()
    {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}