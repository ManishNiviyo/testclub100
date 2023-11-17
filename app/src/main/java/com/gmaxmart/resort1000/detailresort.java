package com.gmaxmart.resort1000;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class detailresort extends AppCompatActivity {
    ConstraintLayout backbutton;
    RecyclerView recycler,daysrecycler;
    collectionAdapter collectionadapter;
    dayAdapter dayadapter;
    ArrayList<String> videoid,photo;
    CardView ratingbutton,submitbutton;
    ArrayList<String> Collectionid,Collectiontitle,Collectionplace,Collectionpackage,
            Collectionday,Collectionprice,Collectiondiscount,Collectiontypetour;

    ArrayList<String> dayid,dayno,daytitle,caydisc;
    Dialog loading;
    BottomSheetDialog ratingpopup;
    TextView tplace,ttitle,tprice,tdiscount,tpackag,tdays;

    View star1,star2,star3,star4,star5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailresort);
        backbutton=findViewById(R.id.constraintLayout);
        recycler=findViewById(R.id.topslider);
        daysrecycler=findViewById(R.id.daysrecycler);
        tplace=findViewById(R.id.place);
        ttitle=findViewById(R.id.tour_title);
        tprice=findViewById(R.id.price);
        tdiscount=findViewById(R.id.textView5);
        tpackag=findViewById(R.id.textView7);
        tdays=findViewById(R.id.day);
        ratingbutton=findViewById(R.id.cardView4);
        submitbutton=findViewById(R.id.submitbutton);

        loading=new Dialog(this);
        loading.setContentView(R.layout.loadingui);
        loading.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loading.setCancelable(false);

        ratingpopup=new BottomSheetDialog(this);
        ratingpopup.setContentView(R.layout.ratingui);
        star1=ratingpopup.findViewById(R.id.star1);
        star2=ratingpopup.findViewById(R.id.star2);
        star3=ratingpopup.findViewById(R.id.star3);
        star4=ratingpopup.findViewById(R.id.star4);
        star5=ratingpopup.findViewById(R.id.star5);


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

            Toast.makeText(this, price, Toast.LENGTH_SHORT).show();

            ttitle.setText(place);
            tprice.setText("₹"+price+"/-");
            tpackag.setText(typeTour);

            // Now you can use these values as needed in your detailresort activity
        }

        loading.show();

        LinearLayoutManager linearLayout= new LinearLayoutManager(detailresort.this,LinearLayoutManager.HORIZONTAL,false);
        recycler.setLayoutManager(linearLayout);
        videoid=new ArrayList<>();
        photo=new ArrayList<>();
        Collectionid=new ArrayList<>();
        Collectiontitle=new ArrayList<>();
        Collectionplace=new ArrayList<>();
        Collectionpackage=new ArrayList<>();
        Collectionday=new ArrayList<>();
        Collectionprice=new ArrayList<>();
        Collectiondiscount=new ArrayList<>();
        Collectiontypetour=new ArrayList<>();
        photo=new ArrayList<>();
        collectionadapter =new collectionAdapter(photo);
        recycler.setAdapter(collectionadapter);

        daysrecycler.setLayoutManager(new LinearLayoutManager(this));
        dayid=new ArrayList<>();
        dayno=new ArrayList<>();
        daytitle=new ArrayList<>();
        caydisc=new ArrayList<>();
        dayadapter =new dayAdapter(dayid);
        daysrecycler.setAdapter(dayadapter);


        LinearSnapHelper snapHelper=new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recycler);

        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(linearLayout.findLastCompletelyVisibleItemPosition()<(collectionadapter.getItemCount()-1)){
                    linearLayout.smoothScrollToPosition(recycler,new RecyclerView.State(),linearLayout.findLastCompletelyVisibleItemPosition()+1);
                }
                else {linearLayout.smoothScrollToPosition(recycler,new RecyclerView.State(),0);
                }}},0,3000);

        Map<String,String> param=new HashMap<String,String>();
        param.put("id",profilecontainer.id);

        RequestQueue requestQueue2 = Volley.newRequestQueue(this);
        String URL2 =profilecontainer.baseUrl+"/resort/api/single_tour.php";

        JsonObjectRequest jsonObjectRequest2=new JsonObjectRequest(Request.Method.POST, URL2, new JSONObject(param), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();
                try {
                    if(response.getString("status").equals("true")){
                        JSONArray jsonArraycollection=new JSONArray();
                        jsonArraycollection=response.getJSONArray("single_tour");
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1=jsonArraycollection.getJSONObject(0);
                        Collectionid.add(jsonObject1.getString("id"));
                        Collectiontitle.add(jsonObject1.getString("description"));
                        Collectionplace.add(jsonObject1.getString("place"));
                        Collectionpackage.add(jsonObject1.getString("package"));
                        Collectionday.add(jsonObject1.getString("day"));
                        Collectionprice.add(jsonObject1.getString("price"));
                        Collectiondiscount.add(jsonObject1.getString("discount"));
                        Collectiontypetour.add(jsonObject1.getString("type_tour"));
                        JSONArray jsonArrayphoto=new JSONArray();
                        jsonArrayphoto=jsonObject1.getJSONArray("photos");
                        for (int i=0;i<jsonArrayphoto.length();i++){
                            JSONObject jsonObject=new JSONObject();
                            jsonObject=jsonArrayphoto.getJSONObject(i);
                            photo.add(jsonObject.getString("photo"));
                        }
                        ttitle.setText(Collectiontitle.get(0));tplace.setText(Collectionplace.get(0));
                        tprice.setText("₹"+Collectionprice.get(0)+"/-"); tdiscount.setText("₹"+Collectiondiscount.get(0)+"/-");
                        tpackag.setText(Html.fromHtml(Collectionpackage.get(0)));
                        if(Collectionday.get(0).equals("1")){
                            tdays.setText(Collectionday.get(0)+"D");
                        }else {
                            int nigh=Integer.valueOf(Collectionday.get(0));
                            nigh=nigh-1;
                            tdays.setText(String.valueOf(nigh)+"N / "+Collectionday.get(0)+"D");
                        }
                        JSONArray jsonArrayday=new JSONArray();
                        jsonArrayday=jsonObject1.getJSONArray("day_details");
                        for (int i=0;i<jsonArrayday.length();i++){
                            JSONObject jsonObject=new JSONObject();
                            jsonObject=jsonArrayday.getJSONObject(i);
                            dayid.add(jsonObject.getString("id"));
                            dayno.add(jsonObject.getString("td_day"));
                            daytitle.add(jsonObject.getString("title"));
                            caydisc.add(jsonObject.getString("description"));
                        }
                        dayadapter.notifyDataSetChanged();
                        collectionadapter.notifyDataSetChanged();

                    }
                }catch (Exception e){
                    //Toast.makeText(detailresort.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(detailresort.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue2.add(jsonObjectRequest2);

        Map<String,String> param1=new HashMap<String,String>();
        param1.put("package_id",profilecontainer.id);
        param1.put("package_type",profilecontainer.tourtypr);

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        String URL1 =profilecontainer.baseUrl+"/resort/api/all_rating.php";

        JsonObjectRequest jsonObjectRequest1=new JsonObjectRequest(Request.Method.POST, URL1, new JSONObject(param1), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();
                try {
                    if(response.getString("status").equals("true")){
                        JSONArray jsonArraycollection=new JSONArray();
                        jsonArraycollection=response.getJSONArray("details");
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1=jsonArraycollection.getJSONObject(0);

                        String rating=jsonObject1.getString("rating");
                        int rate=Integer.valueOf(rating);

                        if(rate>=1&&rate<2){
                            star1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                        }
                        if(rate>=2&&rate<3){
                            star1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                            star2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                        }
                        if(rate>=3&&rate<4){
                            star1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                            star2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                            star3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                        }
                        if(rate>=4&&rate<5){
                            star1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                            star2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                            star3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                            star4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                        }
                        if(rate>=5){
                            star1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                            star2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                            star3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                            star4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                            star5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                        }

                    }
                }catch (Exception e){
                    Toast.makeText(detailresort.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(detailresort.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue1.add(jsonObjectRequest1);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailresort.super.onBackPressed();
            }
        });

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

                 intent = new Intent(detailresort.this, bookyourholiday.class);
                        intent.putExtra("ID", id);
                        intent.putExtra("Title", title);
                        intent.putExtra("Place", place);
                        intent.putExtra("Photo", photo);
                        intent.putExtra("Day", day);
                        intent.putExtra("Price", price);
                        intent.putExtra("Discount", discount);
                        intent.putExtra("TypeTour", typeTour);

                        startActivity(intent);

                        // Now you can use these values as needed in your detailresort activity
                    }
                }
                else {
                    startActivity(new Intent(detailresort.this,login.class));
                }

            }
        });

        ratingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingpopup.show();
            }
        });
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imageslider, parent, false);
            return new collectionAdapter.collectionViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull collectionAdapter.collectionViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Log.d("somethingggg",profilecontainer.baseUrl+"/resort/admin/photos/"+photo.get(position));
            Glide.with(holder.imageView.getContext()).load(profilecontainer.baseUrl+"/resort/admin/photos/"+photo.get(position)).into(holder.imageView);

        }
        @Override
        public int getItemCount() {
            return datamodel.size();
        }
        public class collectionViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            public collectionViewHolder(View itemView) {
                super(itemView);
                imageView=itemView.findViewById(R.id.sliderimage);
            }
        }
    }

    public class dayAdapter extends RecyclerView.Adapter<dayAdapter.dayViewHolder> {
        private ArrayList<String> datamodel;
        public dayAdapter() {}
        public dayAdapter(ArrayList<String> datamodel) {
            this.datamodel = datamodel;
        }
        @NonNull
        @Override
        public dayAdapter.dayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daysdetail, parent, false);
            return new dayAdapter.dayViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull dayAdapter.dayViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.tile.setText("Day "+dayno.get(position)+" : "+daytitle.get(position));
            holder.dis.setText(caydisc.get(position));
            holder.show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(holder.vis==0){
                        holder.vis=1;
                        holder.dis.setVisibility(View.VISIBLE);
                        holder.show.setBackgroundResource(R.drawable.ic_baseline_arrow_circle_up_24);
                    }
                    else {
                        holder.vis=0;
                        holder.dis.setVisibility(View.GONE);
                        holder.show.setBackgroundResource(R.drawable.ic_baseline_arrow_circle_down_24);
                    }
                }
            });
            holder.tile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(holder.vis==0){
                        holder.vis=1;
                        holder.dis.setVisibility(View.VISIBLE);
                        holder.show.setBackgroundResource(R.drawable.ic_baseline_arrow_circle_up_24);
                    }
                    else {
                        holder.vis=0;
                        holder.dis.setVisibility(View.GONE);
                        holder.show.setBackgroundResource(R.drawable.ic_baseline_arrow_circle_down_24);
                    }
                }
            });
        }
        @Override
        public int getItemCount() {
            return datamodel.size();
        }
        public class dayViewHolder extends RecyclerView.ViewHolder {
            TextView tile,dis;
            View show;
            int vis=0;
            public dayViewHolder(View itemView) {
                super(itemView);
                tile=itemView.findViewById(R.id.textView11);
                dis=itemView.findViewById(R.id.des);
                show=itemView.findViewById(R.id.show);
            }
        }
    }
}