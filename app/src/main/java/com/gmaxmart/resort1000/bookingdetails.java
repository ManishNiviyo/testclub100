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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

public class bookingdetails extends AppCompatActivity {
    ConstraintLayout backbutton;
    RecyclerView recycler;
    collectionAdapter collectionadapter;
    ArrayList<String> videoid,photo;
    CardView ratingbutton,submitbutton;
    TextView place,title,payid,orid,adults,childrens,adamount,chamount,total,taxs,rate,cancel;
    Dialog loading,ratingpopupsending;
    BottomSheetDialog ratingpopup;
    View star1,star2,star3,star4,star5;
    View star11,star22,star33,star44,star55;
    EditText feedback;
    String rating="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookingdetails);
        backbutton=findViewById(R.id.constraintLayout);
        recycler=findViewById(R.id.topslider);
        place=findViewById(R.id.place);
        title=findViewById(R.id.textView4);
        ratingbutton=findViewById(R.id.cardView4);
        submitbutton=findViewById(R.id.cardView10);
        payid=findViewById(R.id.paymentid);
        orid=findViewById(R.id.orderid);

        adults=findViewById(R.id.adults);
        childrens=findViewById(R.id.children);
        adamount=findViewById(R.id.adultamount);
        chamount=findViewById(R.id.childrenamount);
        total=findViewById(R.id.totalamount);
        taxs=findViewById(R.id.taxs);
        feedback=findViewById(R.id.editText);

        payid.setText("Payment Id: "+profilecontainer.paymentid);
        orid.setText("Order Id: "+profilecontainer.orderidid);

        adults.setText("Adults: "+profilecontainer.adults);
        childrens.setText("Children: "+profilecontainer.children);
        adamount.setText("Adults Amount: ₹"+profilecontainer.adultsamount+"/-");
        chamount.setText("Children Amount: ₹"+profilecontainer.childrenamount+"/-");
        total.setText("Total Amount: ₹"+profilecontainer.totalamount+"/-");
        taxs.setText("All Tax: ₹"+profilecontainer.tax+"/-");

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

        ratingpopupsending=new Dialog(this);
        ratingpopupsending.setContentView(R.layout.sendingrating);
        ratingpopupsending.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ratingpopupsending.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        star11=ratingpopupsending.findViewById(R.id.star1);
        star22=ratingpopupsending.findViewById(R.id.star2);
        star33=ratingpopupsending.findViewById(R.id.star3);
        star44=ratingpopupsending.findViewById(R.id.star4);
        star55=ratingpopupsending.findViewById(R.id.star5);
        rate=ratingpopupsending.findViewById(R.id.rate);
        cancel=ratingpopupsending.findViewById(R.id.cancel);

        loading.show();

        LinearLayoutManager linearLayout= new LinearLayoutManager(bookingdetails.this,LinearLayoutManager.HORIZONTAL,false);
        recycler.setLayoutManager(linearLayout);
        videoid=new ArrayList<>();
        photo=new ArrayList<>();
        photo=new ArrayList<>();
        collectionadapter =new collectionAdapter(photo);
        recycler.setAdapter(collectionadapter);


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
                        JSONArray jsonArrayphoto=new JSONArray();
                        jsonArrayphoto=jsonObject1.getJSONArray("photos");
                        for (int i=0;i<jsonArrayphoto.length();i++){
                            JSONObject jsonObject=new JSONObject();
                            jsonObject=jsonArrayphoto.getJSONObject(i);
                            photo.add(jsonObject.getString("photo"));
                        }
                        title.setText(jsonObject1.getString("description"));place.setText(jsonObject1.getString("place"));


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
                    Toast.makeText(bookingdetails.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(bookingdetails.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue1.add(jsonObjectRequest1);

        star11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating="1";
                star11.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                star22.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C8C8C8")));
                star33.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C8C8C8")));
                star44.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C8C8C8")));
                star55.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C8C8C8")));
            }
        });
        star22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating="2";
                star11.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                star22.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                star33.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C8C8C8")));
                star44.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C8C8C8")));
                star55.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C8C8C8")));
            }
        });
        star33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating="3";
                star11.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                star22.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                star33.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                star44.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C8C8C8")));
                star55.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C8C8C8")));
            }
        });
        star44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating="4";
                star11.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                star22.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                star33.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                star44.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                star55.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C8C8C8")));
            }
        });
        star55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating="5";
                star11.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                star22.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                star33.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                star44.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                star55.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
            }
        });

        ratingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingpopup.show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingpopupsending.dismiss();
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.show();

                SharedPreferences sharedPreferences = getSharedPreferences("userdetail",MODE_PRIVATE);
                String id=sharedPreferences.getString("id","no");

                Map<String,String> param=new HashMap<String,String>();
                param.put("user_id",id);
                param.put("package_id",profilecontainer.id);
                param.put("package_type",profilecontainer.tourtypr);
                param.put("rating",rating);
                param.put("feedback",feedback.getText().toString().trim());

                RequestQueue requestQueue2 = Volley.newRequestQueue(bookingdetails.this);
                String URL2 =profilecontainer.baseUrl+"/resort/api/user_rating.php";

                JsonObjectRequest jsonObjectRequest2=new JsonObjectRequest(Request.Method.POST, URL2, new JSONObject(param), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.dismiss();
                        Toast.makeText(bookingdetails.this, "successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(bookingdetails.this,MainActivity.class));
                        finishAffinity();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(bookingdetails.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue2.add(jsonObjectRequest2);

            }
        });

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!feedback.getText().toString().trim().isEmpty()){
                    ratingpopupsending.show();
                }
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookingdetails.super.onBackPressed();
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
}