package com.gmaxmart.resort1000;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    private static final int MAX_COUNTER = 1000;
    private static final long DELAY = (long) 0.01; // Milliseconds delay between increments
    private int currentCounter = 0;
    private TextView counterhappyguest;
    private TextView countergeneralmember;
    private TextView countercorporatemember;
    private TextView countertagent;
    private TextView counterTagency;
    private TextView countercities;
    private Handler handler;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ConstraintLayout showdra, call, add, whatsapp, message;
    CardView loginbutton;
    TextView logintext;
    RecyclerView recyclerView;
    ContactsAdapter adapter;
    ArrayList<String> videoid, title;

    CardView Collectionheader, Toursheader, InternationalToursheader, Weddingheader, Waterparkheader, Businessheader, Pilgrimheader;
    TextView Collectionheadertext, Toursheadertext, InternationalToursheadertext, Weddingheadertext, Waterparkheadertext, Businessheadertext, Pilgrimheadertext;
    RecyclerView Collectionrecycler, Toursrecycler, InternationalToursrecycler, Weddingrecycler, Waterparkrecycler, Businessrecycler, Pilgrimrecycler;

    ArrayList<String> collectionId, collectionPhoto, collectionText;

    ArrayList<String> Toursid, Tourstitle, Toursplace, Toursphoto,
            Toursday, Toursprice, Toursdiscount, Tourstypetour;

    ArrayList<String> Internationalid, Internationaltitle, Internationalplace, Internationalphoto,
            Internationalday, Internationalprice, Internationaldiscount, Internationaltypetour;

    ArrayList<String> Weddingid, Weddingtitle, Weddingplace, Weddingphoto,
            Weddingday, Weddingprice, Weddingdiscount, Weddingtypetour;

    ArrayList<String> Waterparkid, Waterparktitle, Waterparkplace, Waterparkphoto,
            Waterparkday, Waterparkprice, Waterparkdiscount, Waterparktypetour;

    ArrayList<String> Businessid, Businesstitle, Businessplace, Businessphoto,
            Businessday, Businessprice, Businessdiscount, Businesstypetour;

    ArrayList<String> Pilgrimid, Pilgrimtitle, Pilgrimplace, Pilgrimphoto,
            Pilgrimday, Pilgrimprice, Pilgrimdiscount, Pilgrimtypetour;

    collectionAdapter collectionadapter;
    ToursAdapter Toursnadapter;
    InternationalAdapter Internationaladapter;
    WeddingAdapter Weddingadapter;
    WaterparkAdapter Waterparkadapter;
    BusinessAdapter Businessadapter;
    PilgrimAdapter Pilgrimadapter;
    ProgressBar loading;
    LinearLayout ourservices;
    TextView headername, headertitle;
    String showservies = "no";
    CardView menbookingcard, manuservices, resortbooking, tourbooking, mebership, querysharing,otaservice, prmarketing,tafranchise;
    ConstraintLayout manuhome, manucontact, manuabout, manuagent, manubooking, manumembership, manuproprty;
    LinearLayout menushowservices;
    View showserviceimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        counterhappyguest = findViewById(R.id.happyguest_count);
        countergeneralmember= findViewById(R.id.generalmember_count);

        countercorporatemember= findViewById(R.id.corporatemember_count);
        countertagent= findViewById(R.id.tagent_count);

        counterTagency= findViewById(R.id.tagency_count);
        countercities= findViewById(R.id.cities_count);


        handler = new Handler();

        // Start the counter
        startCounter(counterhappyguest, 5786); // Set max value for happyguest_count
        startCounter(  countergeneralmember, 1287); // Set max value for generalmember_count
        startCounter(countercorporatemember, 1440);

        startCounter(countertagent, 1565); // Set max value for happyguest_count
        startCounter(   counterTagency, 400); // Set max value for generalmember_count
        startCounter(countercities, 10);

        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        drawerLayout = findViewById(R.id.drawerLayout);
        showdra = findViewById(R.id.showdr);
        call = findViewById(R.id.call);
        add = findViewById(R.id.add);
        whatsapp = findViewById(R.id.whatsapp);
        message = findViewById(R.id.message);
        recyclerView = findViewById(R.id.toprecycler);
        loading = findViewById(R.id.loading);
        ourservices = findViewById(R.id.servicesheader);
        loading.setVisibility(View.VISIBLE);
        ourservices.setVisibility(View.GONE);

        View header = navigationView.getHeaderView(0);
        loginbutton = header.findViewById(R.id.login);
        logintext = header.findViewById(R.id.logintext);
        headername = header.findViewById(R.id.textView);
        headertitle = header.findViewById(R.id.gmail);

        menbookingcard = header.findViewById(R.id.cardView14);
        manuhome = header.findViewById(R.id.homecolor);
        manucontact = header.findViewById(R.id.contactcolor);
        manuabout = header.findViewById(R.id.aboutuscolor);
        manuagent = header.findViewById(R.id.agentcolor);
        manubooking = header.findViewById(R.id.yourbookingcolor);
        manumembership = header.findViewById(R.id.membershipcolor);
        manuproprty = header.findViewById(R.id.listpropertycolor);
        manuservices = header.findViewById(R.id.cardView17);
        menushowservices = header.findViewById(R.id.servicesshow);
        showserviceimage = header.findViewById(R.id.image);
        resortbooking = header.findViewById(R.id.resort_booking);
        tourbooking = header.findViewById(R.id.tour_booking);
        mebership = header.findViewById(R.id.membership);
        querysharing = header.findViewById(R.id.query_sharing);
        otaservice = header.findViewById(R.id.otaservice);
     prmarketing = header.findViewById(R.id.pr_marketing);
    tafranchise = header.findViewById(R.id.ta_franchise);


        Collectionheader = findViewById(R.id.Collectionheader);
        Toursheader = findViewById(R.id.Toursheader);
        InternationalToursheader = findViewById(R.id.InternationalToursheader);
        Weddingheader = findViewById(R.id.Weddingheader);
        Waterparkheader = findViewById(R.id.Waterparkheader);
        Businessheader = findViewById(R.id.Businessheader);
        Pilgrimheader = findViewById(R.id.Pilgrimheader);

        Collectionheadertext = findViewById(R.id.Collectionheadertext);
        Toursheadertext = findViewById(R.id.Toursheadertext);
        InternationalToursheadertext = findViewById(R.id.InternationalToursheadertext);
        Weddingheadertext = findViewById(R.id.Weddingheadertext);
        Waterparkheadertext = findViewById(R.id.Waterparkheadertext);
        Businessheadertext = findViewById(R.id.Businessheadertext);
        Pilgrimheadertext = findViewById(R.id.Pilgrimheadertext);

        Collectionrecycler = findViewById(R.id.Collectionrecycler);
        Toursrecycler = findViewById(R.id.Toursrecycler);
        InternationalToursrecycler = findViewById(R.id.InternationalToursrecycler);
        Weddingrecycler = findViewById(R.id.Weddingrecycler);
        Waterparkrecycler = findViewById(R.id.Waterparkrecycler);
        Businessrecycler = findViewById(R.id.Businessrecycler);
        Pilgrimrecycler = findViewById(R.id.Pilgrimrecycler);

        Collectionheader.setVisibility(View.GONE);
        Toursheader.setVisibility(View.GONE);
        InternationalToursheader.setVisibility(View.GONE);
        Weddingheader.setVisibility(View.GONE);
        Waterparkheader.setVisibility(View.GONE);
        Businessheader.setVisibility(View.GONE);
        Pilgrimheader.setVisibility(View.GONE);

        LinearLayoutManager linearLayout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayout);
        videoid = new ArrayList<String>();
        title = new ArrayList<String>();
        adapter = new ContactsAdapter(videoid);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutcollection = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        Collectionrecycler.setLayoutManager(linearLayoutcollection);
        collectionId = new ArrayList<String>();
        collectionPhoto = new ArrayList<String>();
        collectionText = new ArrayList<String>();

        collectionadapter = new collectionAdapter(collectionId);
        Collectionrecycler.setAdapter(collectionadapter);

        LinearLayoutManager linearLayoutTours = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        Toursrecycler.setLayoutManager(linearLayoutTours);
        Toursid = new ArrayList<String>();
        Tourstitle = new ArrayList<String>();
        Toursplace = new ArrayList<String>();
        Toursphoto = new ArrayList<String>();
        Toursday = new ArrayList<String>();
        Toursprice = new ArrayList<String>();
        Toursdiscount = new ArrayList<String>();
        Tourstypetour = new ArrayList<String>();
        Toursnadapter = new ToursAdapter(Toursid);
        Toursrecycler.setAdapter(Toursnadapter);

        LinearLayoutManager linearLayoutInternational = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        InternationalToursrecycler.setLayoutManager(linearLayoutInternational);
        Internationalid = new ArrayList<String>();
        Internationaltitle = new ArrayList<String>();
        Internationalplace = new ArrayList<String>();
        Internationalphoto = new ArrayList<String>();
        Internationalday = new ArrayList<String>();
        Internationalprice = new ArrayList<String>();
        Internationaldiscount = new ArrayList<String>();
        Internationaltypetour = new ArrayList<String>();
        Internationaladapter = new InternationalAdapter(Internationalid);
        InternationalToursrecycler.setAdapter(Internationaladapter);

        LinearLayoutManager linearLayoutWedding = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        Weddingrecycler.setLayoutManager(linearLayoutWedding);
        Weddingid = new ArrayList<String>();
        Weddingtitle = new ArrayList<String>();
        Weddingplace = new ArrayList<String>();
        Weddingphoto = new ArrayList<String>();
        Weddingday = new ArrayList<String>();
        Weddingprice = new ArrayList<String>();
        Weddingdiscount = new ArrayList<String>();
        Weddingtypetour = new ArrayList<String>();
        Weddingadapter = new WeddingAdapter(Weddingid);
        Weddingrecycler.setAdapter(Weddingadapter);

        LinearLayoutManager linearWaterpark = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        Waterparkrecycler.setLayoutManager(linearWaterpark);
        Waterparkid = new ArrayList<String>();
        Waterparktitle = new ArrayList<String>();
        Waterparkplace = new ArrayList<String>();
        Waterparkphoto = new ArrayList<String>();
        Waterparkday = new ArrayList<String>();
        Waterparkprice = new ArrayList<String>();
        Waterparkdiscount = new ArrayList<String>();
        Waterparktypetour = new ArrayList<String>();
        Waterparkadapter = new WaterparkAdapter(Waterparkid);
        Waterparkrecycler.setAdapter(Waterparkadapter);

        LinearLayoutManager linearBusiness = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        Businessrecycler.setLayoutManager(linearBusiness);
        Businessid = new ArrayList<String>();
        Businesstitle = new ArrayList<String>();
        Businessplace = new ArrayList<String>();
        Businessphoto = new ArrayList<String>();
        Businessday = new ArrayList<String>();
        Businessprice = new ArrayList<String>();
        Businessdiscount = new ArrayList<String>();
        Businesstypetour = new ArrayList<String>();
        Businessadapter = new BusinessAdapter(Businessid);
        Businessrecycler.setAdapter(Businessadapter);

        LinearLayoutManager linearPilgrim = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        Pilgrimrecycler.setLayoutManager(linearPilgrim);
        Pilgrimid = new ArrayList<String>();
        Pilgrimtitle = new ArrayList<String>();
        Pilgrimplace = new ArrayList<String>();
        Pilgrimphoto = new ArrayList<String>();
        Pilgrimday = new ArrayList<String>();
        Pilgrimprice = new ArrayList<String>();
        Pilgrimdiscount = new ArrayList<String>();
        Pilgrimtypetour = new ArrayList<String>();
        Pilgrimadapter = new PilgrimAdapter(Pilgrimid);
        Pilgrimrecycler.setAdapter(Pilgrimadapter);


        SharedPreferences sharedPreferences2 = getSharedPreferences("logindetail", MODE_PRIVATE);
        String login = sharedPreferences2.getString("login", "no");
        if (login.equals("yes")) {
            logintext.setText("Logout");
            SharedPreferences sharedPreferences = getSharedPreferences("userdetail", MODE_PRIVATE);
            String name = sharedPreferences.getString("name", "no");
            String gmail = sharedPreferences.getString("email", "no");

            String[] splits = name.toLowerCase().split(" ");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < splits.length; i++) {
                String eachWord = splits[i];
                if (i > 0 && eachWord.length() > 0) {
                    sb.append(" ");
                }
                String cap = eachWord.substring(0, 1).toUpperCase()
                        + eachWord.substring(1);
                sb.append(cap);
            }
            headername.setText("Hi, " + sb.toString());
            headertitle.setText(gmail);
            menbookingcard.setVisibility(View.VISIBLE);
        } else {
            logintext.setText("Login");
            headername.setText("Club 1000 Resort");
            headertitle.setText("Enjoy your life");
            menbookingcard.setVisibility(View.GONE);
        }

//        LinearSnapHelper snapHelper=new LinearSnapHelper();
//        snapHelper.attachToRecyclerView(recyclerView);
//
//        LinearSnapHelper snapHelpercollection=new LinearSnapHelper();
//        snapHelpercollection.attachToRecyclerView(Collectionrecycler);
//
//        LinearSnapHelper snapHelperTours=new LinearSnapHelper();
//        snapHelperTours.attachToRecyclerView(Toursrecycler);
//
//        LinearSnapHelper snapHelperInternational=new LinearSnapHelper();
//        snapHelperInternational.attachToRecyclerView(InternationalToursrecycler);
//
//        LinearSnapHelper snapHelperWedding=new LinearSnapHelper();
//        snapHelperWedding.attachToRecyclerView(Weddingrecycler);
//
//        LinearSnapHelper snapHelperWaterpark=new LinearSnapHelper();
//        snapHelperWaterpark.attachToRecyclerView(Waterparkrecycler);
//
//        LinearSnapHelper snapHelperBusiness=new LinearSnapHelper();
//        snapHelperBusiness.attachToRecyclerView(Businessrecycler);
//
//        LinearSnapHelper snapHelperPilgrim=new LinearSnapHelper();
//        snapHelperPilgrim.attachToRecyclerView(Pilgrimrecycler);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (linearLayout.findLastCompletelyVisibleItemPosition() < (adapter.getItemCount() - 1)) {
                    linearLayout.smoothScrollToPosition(recyclerView, new RecyclerView.State(), linearLayout.findLastCompletelyVisibleItemPosition() + 1);
                } else {
                    linearLayout.smoothScrollToPosition(recyclerView, new RecyclerView.State(), 0);
                }
            }
        }, 0, 3000);

        Timer timercollection = new Timer();
        timercollection.schedule(new TimerTask() {
            @Override
            public void run() {
                if (linearLayoutcollection.findLastCompletelyVisibleItemPosition() < (collectionadapter.getItemCount() - 1)) {
                    linearLayoutcollection.smoothScrollToPosition(Collectionrecycler, new RecyclerView.State(), linearLayoutcollection.findLastCompletelyVisibleItemPosition() + 1);
                } else {
                    linearLayoutcollection.smoothScrollToPosition(Collectionrecycler, new RecyclerView.State(), 0);
                }
            }
        }, 0, 3000);

        Timer timerTours = new Timer();
        timerTours.schedule(new TimerTask() {
            @Override
            public void run() {
                if (linearLayoutTours.findLastCompletelyVisibleItemPosition() < (Toursnadapter.getItemCount() - 1)) {
                    linearLayoutTours.smoothScrollToPosition(Toursrecycler, new RecyclerView.State(), linearLayoutTours.findLastCompletelyVisibleItemPosition() + 1);
                } else {
                    linearLayoutTours.smoothScrollToPosition(Toursrecycler, new RecyclerView.State(), 0);
                }
            }
        }, 0, 3000);

        Timer timerInternational = new Timer();
        timerInternational.schedule(new TimerTask() {
            @Override
            public void run() {
                if (linearLayoutInternational.findLastCompletelyVisibleItemPosition() < (Internationaladapter.getItemCount() - 1)) {
                    linearLayoutInternational.smoothScrollToPosition(InternationalToursrecycler, new RecyclerView.State(), linearLayoutInternational.findLastCompletelyVisibleItemPosition() + 1);
                } else {
                    linearLayoutInternational.smoothScrollToPosition(InternationalToursrecycler, new RecyclerView.State(), 0);
                }
            }
        }, 0, 3000);

        Timer timerWedding = new Timer();
        timerWedding.schedule(new TimerTask() {
            @Override
            public void run() {
                if (linearLayoutWedding.findLastCompletelyVisibleItemPosition() < (Weddingadapter.getItemCount() - 1)) {
                    linearLayoutWedding.smoothScrollToPosition(Weddingrecycler, new RecyclerView.State(), linearLayoutWedding.findLastCompletelyVisibleItemPosition() + 1);
                } else {
                    linearLayoutWedding.smoothScrollToPosition(Weddingrecycler, new RecyclerView.State(), 0);
                }
            }
        }, 0, 3000);

        Timer timerWaterpark = new Timer();
        timerWaterpark.schedule(new TimerTask() {
            @Override
            public void run() {
                if (linearWaterpark.findLastCompletelyVisibleItemPosition() < (Waterparkadapter.getItemCount() - 1)) {
                    linearWaterpark.smoothScrollToPosition(Waterparkrecycler, new RecyclerView.State(), linearWaterpark.findLastCompletelyVisibleItemPosition() + 1);
                } else {
                    linearWaterpark.smoothScrollToPosition(Waterparkrecycler, new RecyclerView.State(), 0);
                }
            }
        }, 0, 3000);

        Timer timerBusiness = new Timer();
        timerBusiness.schedule(new TimerTask() {
            @Override
            public void run() {
                if (linearBusiness.findLastCompletelyVisibleItemPosition() < (Businessadapter.getItemCount() - 1)) {
                    linearBusiness.smoothScrollToPosition(Businessrecycler, new RecyclerView.State(), linearBusiness.findLastCompletelyVisibleItemPosition() + 1);
                } else {
                    linearBusiness.smoothScrollToPosition(Businessrecycler, new RecyclerView.State(), 0);
                }
            }
        }, 0, 3000);

        Timer timerPilgrim = new Timer();
        timerPilgrim.schedule(new TimerTask() {
            @Override
            public void run() {
                if (linearPilgrim.findLastCompletelyVisibleItemPosition() < (Pilgrimadapter.getItemCount() - 1)) {
                    linearPilgrim.smoothScrollToPosition(Pilgrimrecycler, new RecyclerView.State(), linearPilgrim.findLastCompletelyVisibleItemPosition() + 1);
                } else {
                    linearPilgrim.smoothScrollToPosition(Pilgrimrecycler, new RecyclerView.State(), 0);
                }
            }
        }, 0, 3000);


        loading.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = profilecontainer.baseUrl + "/resort/api/top_sliders.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("status").equals("true")) {
                        JSONArray jsonArray = new JSONArray();
                        jsonArray = response.getJSONArray("top_slider");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1 = jsonArray.getJSONObject(i);
                            videoid.add(jsonObject1.getString("image"));
                            title.add(jsonObject1.getString("title"));
                        }
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);


        RequestQueue requestQueue2 = Volley.newRequestQueue(this);
        String URL2 = profilecontainer.baseUrl + "/resort/api/all_resort_new.php";

        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, URL2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.setVisibility(View.GONE);
                try {
                    if (response.getString("status").equals("true")) {
                        JSONObject jsonObject = new JSONObject();
                        JSONArray jsonname = new JSONArray();
                        JSONArray jsonArraycollection = new JSONArray();
                        JSONArray jsonArrayTours = new JSONArray();
                        JSONArray jsonArrayInternational = new JSONArray();
                        JSONArray jsonArrayWedding = new JSONArray();
                        JSONArray jsonArrayWaterpark = new JSONArray();
                        JSONArray jsonArrayBusiness = new JSONArray();
                        JSONArray jsonArrayPilgrim = new JSONArray();

                        jsonObject = response.getJSONObject("resort");
                        jsonname = response.getJSONArray("name_type");
                        for (int i = 0; i < jsonname.length(); i++) {
                            if (i == 0) {
                                Collectionheader.setVisibility(View.VISIBLE);
                                Collectionheadertext.setText(jsonname.getString(i));
                            }
                            if (i == 1) {
                                Toursheader.setVisibility(View.VISIBLE);
                                Toursheadertext.setText(jsonname.getString(i));
                            }
                            if (i == 2) {
                                InternationalToursheader.setVisibility(View.VISIBLE);
                                InternationalToursheadertext.setText(jsonname.getString(i));
                            }
                            if (i == 3) {
                                Weddingheader.setVisibility(View.VISIBLE);
                                Weddingheadertext.setText(jsonname.getString(i));
                            }
                            if (i == 4) {
                                Waterparkheader.setVisibility(View.VISIBLE);
                                Waterparkheadertext.setText(jsonname.getString(i));
                            }
                            if (i == 5) {
                                Businessheader.setVisibility(View.VISIBLE);
                                Businessheadertext.setText(jsonname.getString(i));
                            }
                            if (i == 6) {
                                Pilgrimheader.setVisibility(View.VISIBLE);
                                Pilgrimheadertext.setText(jsonname.getString(i));
                            }
                        }
                        ourservices.setVisibility(View.GONE);
                        jsonArraycollection = jsonObject.getJSONArray("resort_city");
//                            collectionId.add("1");
//                            collectionPhoto.add("image");
//                            collectionText.add("awfasdf");
                        for (int i = 0; i < jsonArraycollection.length(); i++) {
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1 = jsonArraycollection.getJSONObject(i);
                            collectionId.add(jsonObject1.getString("id"));
                            collectionPhoto.add(jsonObject1.getString("city_image"));
                            collectionText.add(jsonObject1.getString("city"));
                        }
                        jsonArrayTours = jsonObject.getJSONArray("domestic_tour");
                        for (int i = 0; i < jsonArrayTours.length(); i++) {
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1 = jsonArrayTours.getJSONObject(i);
                            Toursid.add(jsonObject1.getString("id"));
                            Tourstitle.add(jsonObject1.getString("description"));
                            Toursplace.add(jsonObject1.getString("place"));
                            Toursphoto.add(jsonObject1.getString("photo"));
                            Toursday.add(jsonObject1.getString("day"));
                            Toursprice.add(jsonObject1.getString("price"));
                            Toursdiscount.add(jsonObject1.getString("discount"));
                            Tourstypetour.add(jsonObject1.getString("type_tour"));
                        }
                        jsonArrayInternational = jsonObject.getJSONArray("international_tour");
                        for (int i = 0; i < jsonArrayInternational.length(); i++) {
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1 = jsonArrayInternational.getJSONObject(i);
                            Internationalid.add(jsonObject1.getString("id"));
                            Internationaltitle.add(jsonObject1.getString("description"));
                            Internationalplace.add(jsonObject1.getString("place"));
                            Internationalphoto.add(jsonObject1.getString("photo"));
                            Internationalday.add(jsonObject1.getString("day"));
                            Internationalprice.add(jsonObject1.getString("price"));
                            Internationaldiscount.add(jsonObject1.getString("discount"));
                            Internationaltypetour.add(jsonObject1.getString("type_tour"));
                        }
                        jsonArrayWedding = jsonObject.getJSONArray("destination_wedding");
                        for (int i = 0; i < jsonArrayWedding.length(); i++) {
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1 = jsonArrayWedding.getJSONObject(i);
                            Weddingid.add(jsonObject1.getString("id"));
                            Weddingtitle.add(jsonObject1.getString("title"));
                            Weddingplace.add(jsonObject1.getString("place"));
                            Weddingphoto.add(jsonObject1.getString("photo"));
                            Weddingday.add(jsonObject1.getString("day"));
                            Weddingprice.add(jsonObject1.getString("price"));
                            Weddingdiscount.add(jsonObject1.getString("discount"));
                            Weddingtypetour.add(jsonObject1.getString("type_tour"));
                        }
                        jsonArrayWaterpark = jsonObject.getJSONArray("day_outing");
                        for (int i = 0; i < jsonArrayWaterpark.length(); i++) {
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1 = jsonArrayWaterpark.getJSONObject(i);
                            Waterparkid.add(jsonObject1.getString("id"));
                            Waterparktitle.add(jsonObject1.getString("description"));
                            Waterparkplace.add(jsonObject1.getString("place"));
                            Waterparkphoto.add(jsonObject1.getString("photo"));
                            Waterparkday.add(jsonObject1.getString("day"));
                            Waterparkprice.add(jsonObject1.getString("price"));
                            Waterparkdiscount.add(jsonObject1.getString("discount"));
                            Waterparktypetour.add(jsonObject1.getString("type_tour"));
                        }
                        jsonArrayBusiness = jsonObject.getJSONArray("corporate");
                        for (int i = 0; i < jsonArrayBusiness.length(); i++) {
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1 = jsonArrayBusiness.getJSONObject(i);
                            Businessid.add(jsonObject1.getString("id"));
                            Businesstitle.add(jsonObject1.getString("description"));
                            Businessplace.add(jsonObject1.getString("place"));
                            Businessphoto.add(jsonObject1.getString("photo"));
                            Businessday.add(jsonObject1.getString("day"));
                            Businessprice.add(jsonObject1.getString("price"));
                            Businessdiscount.add(jsonObject1.getString("discount"));
                            Businesstypetour.add(jsonObject1.getString("type_tour"));
                        }
                        jsonArrayPilgrim = jsonObject.getJSONArray("pilgrim");
                        for (int i = 0; i < jsonArrayPilgrim.length(); i++) {
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1 = jsonArrayPilgrim.getJSONObject(i);
                            Pilgrimid.add(jsonObject1.getString("id"));
                            Pilgrimtitle.add(jsonObject1.getString("description"));
                            Pilgrimplace.add(jsonObject1.getString("place"));
                            Pilgrimphoto.add(jsonObject1.getString("photo"));
                            Pilgrimday.add(jsonObject1.getString("day"));
                            Pilgrimprice.add(jsonObject1.getString("price"));
                            Pilgrimdiscount.add(jsonObject1.getString("discount"));
                            Pilgrimtypetour.add(jsonObject1.getString("type_tour"));
                        }
                        Internationaladapter.notifyDataSetChanged();
                        collectionadapter.notifyDataSetChanged();
                        Toursnadapter.notifyDataSetChanged();
                        Weddingadapter.notifyDataSetChanged();
                        Waterparkadapter.notifyDataSetChanged();
                        Businessadapter.notifyDataSetChanged();
                        Pilgrimadapter.notifyDataSetChanged();

                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue2.add(jsonObjectRequest2);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (logintext.getText().toString().trim().equals("Login")) {
                    startActivity(new Intent(MainActivity.this, login.class));
                } else {
                    SharedPreferences sharedPreferences2 = getSharedPreferences("logindetail", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit2 = sharedPreferences2.edit();
                    myEdit2.putString("login", "no");
                    myEdit2.commit();
                    logintext.setText("Login");
                    headername.setText("Club 1000 Resort");
                    headertitle.setText("Enjoy your life");
                    // Menu nav_Menu = navigationView.getMenu();
                    // nav_Menu.findItem(R.id.nav_booking).setVisible(false);
                    menbookingcard.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Collectionheader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilecontainer.tourtypr = "our_collection";
                profilecontainer.place = "no";
                profilecontainer.heading = "Resort Collection";
                startActivity(new Intent(MainActivity.this, viewallresort.class));
            }
        });
        Toursheader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilecontainer.tourtypr = "domestic_tour";
                profilecontainer.place = "no";
                profilecontainer.heading = "Domestic Tours";
                startActivity(new Intent(MainActivity.this, viewallresort.class));
            }
        });
        InternationalToursheader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilecontainer.tourtypr = "international_tour";
                profilecontainer.place = "no";
                profilecontainer.heading = "International Tours";
                startActivity(new Intent(MainActivity.this, viewallresort.class));
            }
        });
        Weddingheader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilecontainer.tourtypr = "destination_wedding";
                profilecontainer.place = "no";
                profilecontainer.heading = "Destination Wedding's";
                startActivity(new Intent(MainActivity.this, viewallresort.class));
            }
        });
        Waterparkheader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilecontainer.tourtypr = "day_outing";
                profilecontainer.place = "no";
                profilecontainer.heading = "Day Outing / Waterpark / Adventure";
                startActivity(new Intent(MainActivity.this, viewallresort.class));
            }
        });
        Businessheader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilecontainer.tourtypr = "corporate";
                profilecontainer.place = "no";
                profilecontainer.heading = "Corporate / Business Spot";
                startActivity(new Intent(MainActivity.this, viewallresort.class));
            }
        });
        Pilgrimheader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilecontainer.tourtypr = "pilgrim";
                profilecontainer.place = "no";
                profilecontainer.heading = "Pilgrim Tour";
                startActivity(new Intent(MainActivity.this, viewallresort.class));
            }
        });


        showdra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, generalquery.class));
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:7862831321"));
                startActivity(intent);
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("smsto:7862831321");
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                startActivity(intent);
            }
        });
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=7862831321&text=")));
            }
        });

        manuhome.setBackgroundResource(R.drawable.menucolor);
        manucontact.setBackgroundColor(Color.WHITE);
        manuabout.setBackgroundColor(Color.WHITE);
        manuagent.setBackgroundColor(Color.WHITE);
        manubooking.setBackgroundColor(Color.WHITE);
        manumembership.setBackgroundColor(Color.WHITE);
        manuproprty.setBackgroundColor(Color.WHITE);
        //  menushowservices.setVisibility(View.GONE);

        manuservices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showservies.equals("yes")) {
                    showservies = "no";
                    menushowservices.setVisibility(View.VISIBLE);
                    showserviceimage.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
                } else {
                    showservies = "yes";
                    menushowservices.setVisibility(View.GONE);
                    showserviceimage.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
                }
            }
        });

        manuhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        manucontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manucontact.setBackgroundResource(R.drawable.menucolor);
                manuhome.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(MainActivity.this, contactus.class));
                finishAffinity();
            }
        });
        manuabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manuabout.setBackgroundResource(R.drawable.menucolor);
                manuhome.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(MainActivity.this, aboutus.class));
                finishAffinity();
            }
        });
        manuagent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manuagent.setBackgroundResource(R.drawable.menucolor);
                manuhome.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(MainActivity.this, agentpartner.class));
                finishAffinity();
            }
        });
        manubooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manubooking.setBackgroundResource(R.drawable.menucolor);
                manuhome.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(MainActivity.this, mybooking.class));
                finishAffinity();
            }
        });
        manumembership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manumembership.setBackgroundResource(R.drawable.menucolor);
                manuhome.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(MainActivity.this, membership.class));
                finishAffinity();
            }
        });


        resortbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resortbooking.setBackgroundResource(R.drawable.menucolor);
                resortbooking.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(MainActivity.this, ResortBookingActivity.class));
                finishAffinity();
            }
        });


        tourbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tourbooking.setBackgroundResource(R.drawable.menucolor);
                tourbooking.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(MainActivity.this, TourBookingActivity.class));
                finishAffinity();
            }
        });


        mebership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mebership.setBackgroundResource(R.drawable.menucolor);
                mebership.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(MainActivity.this, MembershipActivity.class));
                finishAffinity();
            }
        });


        querysharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                querysharing.setBackgroundResource(R.drawable.menucolor);
                querysharing.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(MainActivity.this, QuerySharing.class));
                finishAffinity();
            }
        });


    otaservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otaservice.setBackgroundResource(R.drawable.menucolor);
               otaservice.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(MainActivity.this, OtaActivity.class));
                finishAffinity();
            }
        });

      prmarketing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            prmarketing.setBackgroundResource(R.drawable.menucolor);
            prmarketing.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(MainActivity.this, PrMarketingActivity.class));
                finishAffinity();
            }
        });

     tafranchise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               tafranchise.setBackgroundResource(R.drawable.menucolor);
              tafranchise.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(MainActivity.this, TafranchiseActivity.class));
                finishAffinity();
            }
        });
        manuproprty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manuproprty.setBackgroundResource(R.drawable.menucolor);
                manuhome.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(MainActivity.this, listyourproperty.class));
                finishAffinity();
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void startCounter(final TextView textView, final int maxValue) {
        handler.postDelayed(new Runnable() {
            int currentValue = 0;

            @Override
            public void run() {
                if (currentValue <= maxValue) {
                    textView.setText("+" + String.valueOf(currentValue));
                    currentValue++;
                    handler.postDelayed(this, DELAY);
                }
            }
        }, DELAY);
    }

    public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
        private ArrayList<String> datamodel;

        public ContactsAdapter() {
        }

        public ContactsAdapter(ArrayList<String> datamodel) {
            this.datamodel = datamodel;
        }

        @NonNull
        @Override
        public ContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topslider, parent, false);
            return new ContactsAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ContactsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Glide.with(holder.topslider.getContext()).load(profilecontainer.baseUrl + "/resort/images/" + videoid.get(position)).into(holder.topslider);
            holder.slidertitle.setText(title.get(position));
            holder.topslider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, generalquery.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return datamodel.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView topslider;
            TextView slidertitle;

            public ViewHolder(View itemView) {
                super(itemView);
                topslider = itemView.findViewById(R.id.sliderimage);
                slidertitle = itemView.findViewById(R.id.slidertitle);
            }
        }
    }

    public class collectionAdapter extends RecyclerView.Adapter<collectionAdapter.collectionViewHolder> {
        private ArrayList<String> datamodel;

        public collectionAdapter() {
        }

        public collectionAdapter(ArrayList<String> datamodel) {
            this.datamodel = datamodel;
        }

        @NonNull
        @Override
        public collectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resortcirclecard, parent, false);
            return new collectionViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull collectionViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Glide.with(holder.circleImage.getContext()).load(profilecontainer.baseUrl + "/resort/admin/city_image/" + collectionPhoto.get(position)).into(holder.circleImage);
            holder.circleText.setText(collectionText.get(position));
            holder.circleCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, resortsList.class);
                    i.putExtra("city", collectionText.get(position));
                    startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return datamodel.size();
        }

        public class collectionViewHolder extends RecyclerView.ViewHolder {
            ImageView circleImage;
            TextView circleText;
            CardView circleCard;

            public collectionViewHolder(View itemView) {
                super(itemView);
                circleImage = itemView.findViewById(R.id.collectionCardImage);
                circleText = itemView.findViewById(R.id.collectionCardText);
                circleCard = itemView.findViewById(R.id.collectionCard);
            }
        }
    }

    public class ToursAdapter extends RecyclerView.Adapter<ToursAdapter.ToursViewHolder> {
        private ArrayList<String> datamodel;

        public ToursAdapter() {
        }

        public ToursAdapter(ArrayList<String> datamodel) {
            this.datamodel = datamodel;
        }

        @NonNull
        @Override
        public ToursAdapter.ToursViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resortdetailcard, parent, false);
            return new ToursAdapter.ToursViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ToursAdapter.ToursViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Glide.with(holder.resortimage.getContext()).load(profilecontainer.baseUrl + "/resort/admin/photos/" + Toursphoto.get(position)).into(holder.resortimage);
            holder.place.setText(Toursplace.get(position));
            holder.title.setText(Tourstitle.get(position));
            holder.price.setText("₹" + Toursprice.get(position) + "/-");
            holder.discount.setText("₹" + Toursdiscount.get(position) + "/-");
            if (Toursday.get(position).equals("1")) {
                holder.day.setText(Toursday.get(position) + "D");
            } else {
                int nigh = Integer.valueOf(Toursday.get(position));
                nigh = nigh - 1;
                holder.day.setText(String.valueOf(nigh) + "N / " + Toursday.get(position) + "D");
            }
            holder.desc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    profilecontainer.tourtypr = Tourstypetour.get(position);
                    profilecontainer.place = Toursplace.get(position);
                    startActivity(new Intent(MainActivity.this, viewplaceresort.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return datamodel.size();
        }

        public class ToursViewHolder extends RecyclerView.ViewHolder {
            ImageView resortimage;
            TextView place, title, price, discount, day;
            CardView desc;

            public ToursViewHolder(View itemView) {
                super(itemView);
                resortimage = itemView.findViewById(R.id.resortimage);
                place = itemView.findViewById(R.id.textView2);
                title = itemView.findViewById(R.id.textView4);
                price = itemView.findViewById(R.id.price);
                discount = itemView.findViewById(R.id.textView5);
                day = itemView.findViewById(R.id.day);
                desc = itemView.findViewById(R.id.desc);
            }
        }
    }

    public class InternationalAdapter extends RecyclerView.Adapter<InternationalAdapter.InternationalViewHolder> {
        private ArrayList<String> datamodel;

        public InternationalAdapter() {
        }

        public InternationalAdapter(ArrayList<String> datamodel) {
            this.datamodel = datamodel;
        }

        @NonNull
        @Override
        public InternationalAdapter.InternationalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resortdetailcard, parent, false);
            return new InternationalAdapter.InternationalViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull InternationalAdapter.InternationalViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Glide.with(holder.resortimage.getContext()).load(profilecontainer.baseUrl + "/resort/admin/photos/" + Internationalphoto.get(position)).into(holder.resortimage);
            holder.place.setText(Internationalplace.get(position));
            holder.title.setText(Internationaltitle.get(position));
            holder.price.setText("₹" + Internationalprice.get(position) + "/-");
            holder.discount.setText("₹" + Internationaldiscount.get(position) + "/-");
            if (Internationalday.get(position).equals("1")) {
                holder.day.setText(Internationalday.get(position) + "D");
            } else {
                int nigh = Integer.valueOf(Internationalday.get(position));
                nigh = nigh - 1;
                holder.day.setText(String.valueOf(nigh) + "N / " + Internationalday.get(position) + "D");
            }
            holder.desc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    profilecontainer.tourtypr = Internationaltypetour.get(position);
                    profilecontainer.place = Internationalplace.get(position);
                    startActivity(new Intent(MainActivity.this, viewplaceresort.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return datamodel.size();
        }

        public class InternationalViewHolder extends RecyclerView.ViewHolder {
            ImageView resortimage;
            TextView place, title, price, discount, day;
            CardView desc;

            public InternationalViewHolder(View itemView) {
                super(itemView);
                resortimage = itemView.findViewById(R.id.resortimage);
                place = itemView.findViewById(R.id.textView2);
                title = itemView.findViewById(R.id.textView4);
                price = itemView.findViewById(R.id.price);
                discount = itemView.findViewById(R.id.textView5);
                day = itemView.findViewById(R.id.day);
                desc = itemView.findViewById(R.id.desc);
            }
        }
    }


    public class WeddingAdapter extends RecyclerView.Adapter<WeddingAdapter.WeddingViewHolder> {
        private ArrayList<String> datamodel;

        public WeddingAdapter() {
        }

        public WeddingAdapter(ArrayList<String> datamodel) {
            this.datamodel = datamodel;
        }

        @NonNull
        @Override
        public WeddingAdapter.WeddingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resortdetailcard, parent, false);
            return new WeddingAdapter.WeddingViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull WeddingAdapter.WeddingViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Glide.with(holder.resortimage.getContext()).load(profilecontainer.baseUrl + "/resort/admin/photos/" + Weddingphoto.get(position)).into(holder.resortimage);
            holder.place.setText(Weddingplace.get(position));
            holder.title.setText(Weddingtitle.get(position));
            holder.price.setText("₹" + Weddingprice.get(position) + "/-");
            holder.discount.setText("₹" + Weddingdiscount.get(position) + "/-");
            if (Weddingday.get(position).equals("1")) {
                holder.day.setText(Weddingday.get(position) + "D");
            } else {
                int nigh = Integer.valueOf(Weddingday.get(position));
                nigh = nigh - 1;
                holder.day.setText(String.valueOf(nigh) + "N / " + Weddingday.get(position) + "D");
            }
            holder.desc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    profilecontainer.tourtypr = Weddingtypetour.get(position);
                    profilecontainer.place = Weddingplace.get(position);
                    startActivity(new Intent(MainActivity.this, viewplaceresort.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return datamodel.size();
        }

        public class WeddingViewHolder extends RecyclerView.ViewHolder {
            ImageView resortimage;
            TextView place, title, price, discount, day;
            CardView desc;

            public WeddingViewHolder(View itemView) {
                super(itemView);
                resortimage = itemView.findViewById(R.id.resortimage);
                place = itemView.findViewById(R.id.textView2);
                title = itemView.findViewById(R.id.textView4);
                price = itemView.findViewById(R.id.price);
                discount = itemView.findViewById(R.id.textView5);
                day = itemView.findViewById(R.id.day);
                desc = itemView.findViewById(R.id.desc);
            }
        }
    }

    public class WaterparkAdapter extends RecyclerView.Adapter<WaterparkAdapter.WaterparkViewHolder> {
        private ArrayList<String> datamodel;

        public WaterparkAdapter() {
        }

        public WaterparkAdapter(ArrayList<String> datamodel) {
            this.datamodel = datamodel;
        }

        @NonNull
        @Override
        public WaterparkAdapter.WaterparkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resortdetailcard, parent, false);
            return new WaterparkAdapter.WaterparkViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull WaterparkAdapter.WaterparkViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Glide.with(holder.resortimage.getContext()).load(profilecontainer.baseUrl + "/resort/admin/photos/" + Waterparkphoto.get(position)).into(holder.resortimage);
            holder.place.setText(Waterparkplace.get(position));
            holder.title.setText(Waterparktitle.get(position));
            holder.price.setText("₹" + Waterparkprice.get(position) + "/-");
            holder.discount.setText("₹" + Waterparkdiscount.get(position) + "/-");
            if (Waterparkday.get(position).equals("1")) {
                holder.day.setText(Waterparkday.get(position) + "D");
            } else {
                int nigh = Integer.valueOf(Waterparkday.get(position));
                nigh = nigh - 1;
                holder.day.setText(String.valueOf(nigh) + "N / " + Waterparkday.get(position) + "D");
            }
            holder.desc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    profilecontainer.tourtypr = Waterparktypetour.get(position);
                    profilecontainer.place = Waterparkplace.get(position);
                    startActivity(new Intent(MainActivity.this, viewplaceresort.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return datamodel.size();
        }

        public class WaterparkViewHolder extends RecyclerView.ViewHolder {
            ImageView resortimage;
            TextView place, title, price, discount, day;
            CardView desc;

            public WaterparkViewHolder(View itemView) {
                super(itemView);
                resortimage = itemView.findViewById(R.id.resortimage);
                place = itemView.findViewById(R.id.textView2);
                title = itemView.findViewById(R.id.textView4);
                price = itemView.findViewById(R.id.price);
                discount = itemView.findViewById(R.id.textView5);
                day = itemView.findViewById(R.id.day);
                desc = itemView.findViewById(R.id.desc);
            }
        }
    }


    public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.BusinessViewHolder> {
        private ArrayList<String> datamodel;

        public BusinessAdapter() {
        }

        public BusinessAdapter(ArrayList<String> datamodel) {
            this.datamodel = datamodel;
        }

        @NonNull
        @Override
        public BusinessAdapter.BusinessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resortdetailcard, parent, false);
            return new BusinessAdapter.BusinessViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BusinessAdapter.BusinessViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Glide.with(holder.resortimage.getContext()).load(profilecontainer.baseUrl + "/resort/admin/photos/" + Businessphoto.get(position)).into(holder.resortimage);
            holder.place.setText(Businessplace.get(position));
            holder.title.setText(Businesstitle.get(position));
            holder.price.setText("₹" + Businessprice.get(position) + "/-");
            holder.discount.setText("₹" + Businessdiscount.get(position) + "/-");
            if (Businessday.get(position).equals("1")) {
                holder.day.setText(Businessday.get(position) + "D");
            } else {
                int nigh = Integer.valueOf(Businessday.get(position));
                nigh = nigh - 1;
                holder.day.setText(String.valueOf(nigh) + "N / " + Businessday.get(position) + "D");
            }
            holder.desc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    profilecontainer.tourtypr = Businesstypetour.get(position);
                    profilecontainer.place = Businessplace.get(position);
                    startActivity(new Intent(MainActivity.this, viewplaceresort.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return datamodel.size();
        }

        public class BusinessViewHolder extends RecyclerView.ViewHolder {
            ImageView resortimage;
            TextView place, title, price, discount, day;
            CardView desc;

            public BusinessViewHolder(View itemView) {
                super(itemView);
                resortimage = itemView.findViewById(R.id.resortimage);
                place = itemView.findViewById(R.id.textView2);
                title = itemView.findViewById(R.id.textView4);
                price = itemView.findViewById(R.id.price);
                discount = itemView.findViewById(R.id.textView5);
                day = itemView.findViewById(R.id.day);
                desc = itemView.findViewById(R.id.desc);
            }
        }
    }


    public class PilgrimAdapter extends RecyclerView.Adapter<PilgrimAdapter.PilgrimViewHolder> {
        private ArrayList<String> datamodel;

        public PilgrimAdapter() {
        }

        public PilgrimAdapter(ArrayList<String> datamodel) {
            this.datamodel = datamodel;
        }

        @NonNull
        @Override
        public PilgrimAdapter.PilgrimViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resortdetailcard, parent, false);
            return new PilgrimAdapter.PilgrimViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PilgrimAdapter.PilgrimViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Glide.with(holder.resortimage.getContext()).load(profilecontainer.baseUrl + "/resort/admin/photos/" + Pilgrimphoto.get(position)).into(holder.resortimage);
            holder.place.setText(Pilgrimplace.get(position));
            holder.title.setText(Pilgrimtitle.get(position));
            holder.price.setText("₹" + Pilgrimprice.get(position) + "/-");
            holder.discount.setText("₹" + Pilgrimdiscount.get(position) + "/-");
            if (Pilgrimday.get(position).equals("1")) {
                holder.day.setText(Pilgrimday.get(position) + "D");
            } else {
                int nigh = Integer.valueOf(Pilgrimday.get(position));
                nigh = nigh - 1;
                holder.day.setText(String.valueOf(nigh) + "N / " + Pilgrimday.get(position) + "D");
            }
            holder.desc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    profilecontainer.tourtypr = Pilgrimtypetour.get(position);
                    profilecontainer.place = Pilgrimplace.get(position);
                    startActivity(new Intent(MainActivity.this, viewplaceresort.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return datamodel.size();
        }

        public class PilgrimViewHolder extends RecyclerView.ViewHolder {
            ImageView resortimage;
            TextView place, title, price, discount, day;
            CardView desc;

            public PilgrimViewHolder(View itemView) {
                super(itemView);
                resortimage = itemView.findViewById(R.id.resortimage);
                place = itemView.findViewById(R.id.textView2);
                title = itemView.findViewById(R.id.textView4);
                price = itemView.findViewById(R.id.price);
                discount = itemView.findViewById(R.id.textView5);
                day = itemView.findViewById(R.id.day);
                desc = itemView.findViewById(R.id.desc);
            }
        }




    }

}