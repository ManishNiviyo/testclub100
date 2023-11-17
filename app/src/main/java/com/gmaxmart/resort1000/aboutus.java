package com.gmaxmart.resort1000;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

public class aboutus extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ConstraintLayout showdra;
    TextView aboutus;
    ProgressBar loading;
    CardView loginbutton;
    TextView logintext;
    TextView headername,headertitle;
    String showservies="no";
    CardView menbookingcard,manuservices;
    ConstraintLayout manuhome,manucontact,manuabout,manuagent,manubooking,manumembership,manuproprty;
    LinearLayout menushowservices;
    View showserviceimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        drawerLayout = findViewById(R.id.drawerLayout);
        showdra = findViewById(R.id.showdr);
        aboutus = findViewById(R.id.aboutustext);
        loading = findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);

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

        SharedPreferences sharedPreferences2 = getSharedPreferences("logindetail",MODE_PRIVATE);
        String login=sharedPreferences2.getString("login","no");
        if(login.equals("yes")){
            logintext.setText("Logout");
            SharedPreferences sharedPreferences = getSharedPreferences("userdetail",MODE_PRIVATE);
            String name=sharedPreferences.getString("name","no");
            String gmail=sharedPreferences.getString("email","no");

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
            headername.setText("Hi, "+sb.toString());headertitle.setText(gmail);
            menbookingcard.setVisibility(View.VISIBLE);
        }
        else {
            logintext.setText("Login");
            headername.setText("Club 1000 Resort");headertitle.setText("Enjoy your life");
            menbookingcard.setVisibility(View.GONE);
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL =profilecontainer.baseUrl+"/resort/api/about_us.php";

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.setVisibility(View.GONE);
                try {
                    if(response.getString("status").equals("true")){
                        aboutus.setText(Html.fromHtml(response.getString("about_us")));
                    }
                }catch (Exception e){
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);


        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(logintext.getText().toString().trim().equals("Login")){
                    startActivity(new Intent(getApplicationContext(),login.class));
                }
                else {
                    SharedPreferences sharedPreferences2 = getSharedPreferences("logindetail",MODE_PRIVATE);
                    SharedPreferences.Editor myEdit2 = sharedPreferences2.edit();
                    myEdit2.putString("login","no");
                    myEdit2.commit();
                    logintext.setText("Login");
                    headername.setText("Club 1000 Resort");headertitle.setText("Enjoy your life");
                    // Menu nav_Menu = navigationView.getMenu();
                    // nav_Menu.findItem(R.id.nav_booking).setVisible(false);
                    menbookingcard.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Logout Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        showdra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        manuabout.setBackgroundResource(R.drawable.menucolor);
        manuhome.setBackgroundColor(Color.WHITE);
        manucontact.setBackgroundColor(Color.WHITE);
        //manuabout.setBackgroundColor(Color.WHITE);
        manuagent.setBackgroundColor(Color.WHITE);
        manubooking.setBackgroundColor(Color.WHITE);
        manumembership.setBackgroundColor(Color.WHITE);
        manuproprty.setBackgroundColor(Color.WHITE);
        //menushowservices.setVisibility(View.GONE);

        manuservices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(showservies.equals("yes")){
                    showservies="no";
                    menushowservices.setVisibility(View.VISIBLE);
                    showserviceimage.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
                }
                else {
                    showservies="yes";
                    menushowservices.setVisibility(View.GONE);
                    showserviceimage.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
                }
            }
        });

        manuhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manuhome.setBackgroundResource(R.drawable.menucolor);
                manuabout.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finishAffinity();
            }
        });
        manucontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manucontact.setBackgroundResource(R.drawable.menucolor);
                manuabout.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(),contactus.class));
                finishAffinity();
            }
        });
        manuabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        manuagent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manuagent.setBackgroundResource(R.drawable.menucolor);
                manuabout.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(),agentpartner.class));
                finishAffinity();
            }
        });
        manubooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manubooking.setBackgroundResource(R.drawable.menucolor);
                manuabout.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(),mybooking.class));
                finishAffinity();
            }
        });
        manumembership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manumembership.setBackgroundResource(R.drawable.menucolor);
                manuabout.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(),membership.class));
                finishAffinity();
            }
        });
        manuproprty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manuproprty.setBackgroundResource(R.drawable.menucolor);
                manuabout.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(),listyourproperty.class));
                finishAffinity();
            }
        });
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            startActivity(new Intent(aboutus.this,MainActivity.class));
            finishAffinity();
           // overridePendingTransition(0,0);
        }
    }
}