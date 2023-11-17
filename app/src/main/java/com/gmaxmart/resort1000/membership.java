package com.gmaxmart.resort1000;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class membership extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ConstraintLayout showdra;
    RecyclerView recyclerView;
    ArrayList<String> memberid,membertitle,memberdiscount,memberprice,memberdescription,membertype,membervalidity;
    collectionAdapter collectionadapter;
    ProgressBar loading;
    CardView loginbutton;
    TextView logintext;
    TextView headername,headertitle;
    TabLayout tabLayout;
    ViewPager viewPager;
    String showservies="no";
    CardView menbookingcard,manuservices;
    ConstraintLayout manuhome,manucontact,manuabout,manuagent,manubooking,manumembership,manuproprty;
    LinearLayout menushowservices;
    View showserviceimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);

        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        drawerLayout = findViewById(R.id.drawerLayout);
        showdra = findViewById(R.id.showdr);
        recyclerView = findViewById(R.id.recyler);
        loading = findViewById(R.id.loading);
        tabLayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.pageviewer);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        memberid=new ArrayList<String>();
        membertitle=new ArrayList<String>();
        memberdiscount=new ArrayList<String>();
        memberprice=new ArrayList<String>();
        memberdescription=new ArrayList<String>();
        membertype=new ArrayList<String>();
        membervalidity=new ArrayList<String>();
        collectionadapter =new collectionAdapter(memberid);
        recyclerView.setAdapter(collectionadapter);

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

        tabLayout.setupWithViewPager(viewPager);
        VPadaptor vPadaptor=new VPadaptor(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(vPadaptor);

        vPadaptor.addfram(new Fragment_1(),"Regular");
        vPadaptor.addfram(new Fragment_2(),"Agent");
        vPadaptor.notifyDataSetChanged();

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

        RequestQueue requestQueue2 = Volley.newRequestQueue(this);
        String URL2 =profilecontainer.baseUrl+"/resort/api/membership.php";

        JsonObjectRequest jsonObjectRequest2=new JsonObjectRequest(Request.Method.POST, URL2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.setVisibility(View.GONE);
                try {
                    if(response.getString("status").equals("true")){
                        JSONArray jsonArraycollection=new JSONArray();
                        jsonArraycollection=response.getJSONArray("membership_list");
                        for (int i=0;i<jsonArraycollection.length();i++) {
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1 = jsonArraycollection.getJSONObject(i);
                            memberid.add(jsonObject1.getString("id"));
                            membertitle.add(jsonObject1.getString("title"));
                            membertype.add(jsonObject1.getString("type"));
                            membervalidity.add(jsonObject1.getString("validity"));
                            memberprice.add(jsonObject1.getString("price"));
                            memberdiscount.add(jsonObject1.getString("discount"));
                            memberdescription.add(jsonObject1.getString("description"));
                        }
                        collectionadapter.notifyDataSetChanged();

                    }
                }catch (Exception e){
                    //Toast.makeText(membership.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(membership.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue2.add(jsonObjectRequest2);


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

        manumembership.setBackgroundResource(R.drawable.menucolor);
        manuhome.setBackgroundColor(Color.WHITE);
        manucontact.setBackgroundColor(Color.WHITE);
        manuabout.setBackgroundColor(Color.WHITE);
        manuagent.setBackgroundColor(Color.WHITE);
        manubooking.setBackgroundColor(Color.WHITE);
        //manumembership.setBackgroundColor(Color.WHITE);
        manuproprty.setBackgroundColor(Color.WHITE);
       // menushowservices.setVisibility(View.GONE);

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
                manumembership.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finishAffinity();
            }
        });
        manucontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manucontact.setBackgroundResource(R.drawable.menucolor);
                manumembership.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(),contactus.class));
                finishAffinity();
            }
        });
        manuabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manuabout.setBackgroundResource(R.drawable.menucolor);
                manumembership.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(),aboutus.class));
                finishAffinity();
            }
        });
        manuagent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manuagent.setBackgroundResource(R.drawable.menucolor);
                manumembership.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(),agentpartner.class));
                finishAffinity();
            }
        });
        manubooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manubooking.setBackgroundResource(R.drawable.menucolor);
                manumembership.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(),mybooking.class));
                finishAffinity();
            }
        });
        manumembership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(GravityCompat.START);

            }
        });
        manuproprty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manuproprty.setBackgroundResource(R.drawable.menucolor);
                manumembership.setBackgroundColor(Color.WHITE);
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
            startActivity(new Intent(membership.this,MainActivity.class));
            finishAffinity();
            overridePendingTransition(0,0);
        }
    }
    public class collectionAdapter extends RecyclerView.Adapter<collectionAdapter.collectionViewHolder> {
        private ArrayList<String> datamodel;
        public collectionAdapter() {}
        public collectionAdapter(ArrayList<String> datamodel) {
            this.datamodel = datamodel;
        }
        @NonNull
        @Override
        public collectionAdapter.collectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.membership, parent, false);
            return new collectionAdapter.collectionViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull collectionAdapter.collectionViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.type.setText(membertype.get(position));
            holder.discount.setText(memberdiscount.get(position)+" % on all packages");
            holder.description.setText(Html.fromHtml(memberdescription.get(position)));
            holder.price.setText("₹"+memberprice.get(position)+"/-");
            holder.month.setText(membervalidity.get(position));
            holder.buybutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        @Override
        public int getItemCount() {
            return datamodel.size();
        }
        public class collectionViewHolder extends RecyclerView.ViewHolder {
            TextView type,discount,description,price,month;
            CardView buybutton;
            public collectionViewHolder(View itemView) {
                super(itemView);
                type=itemView.findViewById(R.id.type);
                discount=itemView.findViewById(R.id.title);
                description=itemView.findViewById(R.id.desc);
                price=itemView.findViewById(R.id.price);
                month=itemView.findViewById(R.id.month);
                buybutton=itemView.findViewById(R.id.buybutton);
            }
        }
    }
}