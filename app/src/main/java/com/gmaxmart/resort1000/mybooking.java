package com.gmaxmart.resort1000;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class mybooking extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ConstraintLayout showdra;
    ProgressBar loading;
    CardView loginbutton;
    TextView logintext;
    TextView headername,headertitle;
    ArrayList<String> Collectionid,Collectiontitle,Collectionplace,Collectionphoto,
            Collectionday,Collectionprice,Collectiondiscount,Collectiontypetour
            ,collectionpurchasedate,collectiondepartdate,packageid
            ,packagetour,paymentid,orderid,adults,childrens,adamount,chamount,tax,total;
    collectionAdapter collectionadapter;
    RecyclerView recycler;
    String showservies="no";
    CardView menbookingcard,manuservices;
    ConstraintLayout manuhome,manucontact,manuabout,manuagent,manubooking,manumembership,manuproprty;
    LinearLayout menushowservices;
    View showserviceimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybooking);

        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        drawerLayout = findViewById(R.id.drawerLayout);
        showdra = findViewById(R.id.showdr);
        loading = findViewById(R.id.loading);
        recycler=findViewById(R.id.recycler);
        loading.setVisibility(View.VISIBLE);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        Collectionid=new ArrayList<String>();
        Collectiontitle=new ArrayList<String>();
        Collectionplace=new ArrayList<String>();
        Collectionphoto=new ArrayList<String>();
        Collectionday=new ArrayList<String>();
        Collectionprice=new ArrayList<String>();
        Collectiondiscount=new ArrayList<String>();
        Collectiontypetour=new ArrayList<String>();
        collectionpurchasedate=new ArrayList<String>();
        collectiondepartdate=new ArrayList<String>();
        packageid=new ArrayList<String>();
        packagetour=new ArrayList<String>();
        paymentid=new ArrayList<String>();
        orderid=new ArrayList<String>();
        adults=new ArrayList<String>();
        childrens=new ArrayList<String>();
        adamount=new ArrayList<String>();
        chamount=new ArrayList<String>();
        tax=new ArrayList<String>();
        total=new ArrayList<String>();
        collectionadapter =new collectionAdapter(Collectionid);
        recycler.setAdapter(collectionadapter);

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
        SharedPreferences sharedPreferences = getSharedPreferences("userdetail",MODE_PRIVATE);
        String name=sharedPreferences.getString("name","no");
        String gmail=sharedPreferences.getString("email","no");
        String id=sharedPreferences.getString("id","no");

        if(login.equals("yes")){
            logintext.setText("Logout");
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
        String URL2 =profilecontainer.baseUrl+"/resort/api/user_orders.php?uid="+id;

        JsonObjectRequest jsonObjectRequest2=new JsonObjectRequest(Request.Method.POST, URL2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.setVisibility(View.GONE);
                try {
                    if(!response.getString("order").equals("null")){
                        JSONArray jsonArraycollection=new JSONArray();
                        jsonArraycollection=response.getJSONArray("order");
                        for (int i=0;i<jsonArraycollection.length();i++) {
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1 = jsonArraycollection.getJSONObject(i);
                            Collectionid.add(jsonObject1.getString("id"));
                            collectiondepartdate.add(jsonObject1.getString("departure_date"));
                            collectionpurchasedate.add(jsonObject1.getString("timestamp"));
                            paymentid.add(jsonObject1.getString("payment_id"));
                            orderid.add(jsonObject1.getString("order_id"));
                            JSONObject jsonObject=new JSONObject();
                            jsonObject=jsonObject1.getJSONObject("package");
                            Collectionplace.add(jsonObject.getString("place"));
                            Collectiontitle.add(jsonObject.getString("description"));
                            packageid.add(jsonObject.getString("id"));
                            packagetour.add(jsonObject.getString("type_tour"));
                            JSONArray jsonArray=new JSONArray();
                            jsonArray=jsonObject.getJSONArray("images");
                            Collectionphoto.add(jsonArray.getString(0));
                            adults.add(jsonObject1.getString("adult"));
                            childrens.add(jsonObject1.getString("child"));
                            adamount.add(jsonObject1.getString("adult_amt"));
                            chamount.add(jsonObject1.getString("children_amt"));
                            tax.add(jsonObject1.getString("tax"));
                            total.add(jsonObject1.getString("amount"));
                        }
                        collectionadapter.notifyDataSetChanged();

                    }
                }catch (Exception e){
                    Toast.makeText(mybooking.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mybooking.this, error.toString(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), "Logout Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(mybooking.this,MainActivity.class));
                    finishAffinity();
                }
            }
        });

        showdra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        manubooking.setBackgroundResource(R.drawable.menucolor);
        manuhome.setBackgroundColor(Color.WHITE);
        manucontact.setBackgroundColor(Color.WHITE);
        manuabout.setBackgroundColor(Color.WHITE);
        manuagent.setBackgroundColor(Color.WHITE);
        //manubooking.setBackgroundColor(Color.WHITE);
        manumembership.setBackgroundColor(Color.WHITE);
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
                manubooking.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finishAffinity();
            }
        });
        manucontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manucontact.setBackgroundResource(R.drawable.menucolor);
                manubooking.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(),contactus.class));
                finishAffinity();
            }
        });
        manuabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manuabout.setBackgroundResource(R.drawable.menucolor);
                manubooking.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(),aboutus.class));
                finishAffinity();
            }
        });
        manuagent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manuagent.setBackgroundResource(R.drawable.menucolor);
                manubooking.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(),agentpartner.class));
                finishAffinity();
            }
        });
        manubooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(GravityCompat.START);

            }
        });
        manumembership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manumembership.setBackgroundResource(R.drawable.menucolor);
                manubooking.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(),membership.class));
                finishAffinity();
            }
        });
        manuproprty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manuproprty.setBackgroundResource(R.drawable.menucolor);
                manubooking.setBackgroundColor(Color.WHITE);
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
            startActivity(new Intent(mybooking.this,MainActivity.class));
            finishAffinity();
            // overridePendingTransition(0,0);
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderui, parent, false);
            return new collectionAdapter.collectionViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull collectionAdapter.collectionViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Glide.with(holder.imageView.getContext()).load(profilecontainer.baseUrl+"/resort/admin/photos/"+Collectionphoto.get(position)).into(holder.imageView);
            holder.place.setText(Collectionplace.get(position));
            holder.des.setText(Collectiontitle.get(position));
            holder.purchase.setText("Purchase Date: "+collectionpurchasedate.get(position));
            holder.departure.setText("Departure Date: "+collectiondepartdate.get(position));

            holder.detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    profilecontainer.id=packageid.get(position);
                    profilecontainer.tourtypr=packagetour.get(position);
                    profilecontainer.paymentid=paymentid.get(position);
                    profilecontainer.orderidid=orderid.get(position);

                    profilecontainer.adults=adults.get(position);
                    profilecontainer.children=childrens.get(position);
                    profilecontainer.adultsamount=adamount.get(position);
                    profilecontainer.childrenamount=chamount.get(position);
                    profilecontainer.totalamount=total.get(position);
                    profilecontainer.tax=tax.get(position);

                    startActivity(new Intent(mybooking.this,bookingdetails.class));
                }
            });

        }
        @Override
        public int getItemCount() {
            return datamodel.size();
        }
        public class collectionViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView place,des,purchase,departure;
            CardView detail;
            public collectionViewHolder(View itemView) {
                super(itemView);
                imageView=itemView.findViewById(R.id.packageimage);
                place=itemView.findViewById(R.id.textView2);
                des=itemView.findViewById(R.id.textView4);
                purchase=itemView.findViewById(R.id.textView3);
                departure=itemView.findViewById(R.id.textView5);
                detail=itemView.findViewById(R.id.detail);
            }
        }
    }
}