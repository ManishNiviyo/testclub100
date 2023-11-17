package com.gmaxmart.resort1000;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class viewallresort extends AppCompatActivity {
    ConstraintLayout backbutton;
    TextView header;
    ArrayList<String> Collectionid,Collectiontitle,Collectionplace,Collectionphoto,
            Collectionday,Collectionprice,Collectiondiscount,Collectiontypetour;
    collectionAdapter collectionadapter;
    RecyclerView recycler;
    ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewallresort);
        backbutton=findViewById(R.id.constraintLayout);
        header=findViewById(R.id.header);
        recycler=findViewById(R.id.recycler);
        loading=findViewById(R.id.loading);

        loading.setVisibility(View.VISIBLE);

        header.setText(profilecontainer.heading);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
       // LinearLayoutManager linearLayoutcollection= new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false);
        recycler.setLayoutManager(layoutManager);
        Collectionid=new ArrayList<String>();
        Collectiontitle=new ArrayList<String>();
        Collectionplace=new ArrayList<String>();
        Collectionphoto=new ArrayList<String>();
        Collectionday=new ArrayList<String>();
        Collectionprice=new ArrayList<String>();
        Collectiondiscount=new ArrayList<String>();
        Collectiontypetour=new ArrayList<String>();
        collectionadapter =new collectionAdapter(Collectionid);
        recycler.setAdapter(collectionadapter);

        Map<String,String> param=new HashMap<String,String>();
        param.put("tour_type",profilecontainer.tourtypr);
      //  Toast.makeText(this, profilecontainer.tourtypr, Toast.LENGTH_SHORT).show();
        RequestQueue requestQueue2 = Volley.newRequestQueue(this);
        String URL2 =profilecontainer.baseUrl+"/resort/api/same_category.php";

        JsonObjectRequest jsonObjectRequest2=new JsonObjectRequest(Request.Method.POST, URL2, new JSONObject(param), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.setVisibility(View.GONE);
                try {
                    if(response.getString("status").equals("true")){
                       // Toast.makeText(viewallresort.this, response.toString(), Toast.LENGTH_SHORT).show();
                        JSONArray jsonArraycollection=new JSONArray();
                        jsonArraycollection=response.getJSONArray("same_category");
                        for (int i=0;i<jsonArraycollection.length();i++) {
                            JSONObject jsonObject1 = new JSONObject();
                            JSONObject jsonObject2 = new JSONObject();
                            JSONArray jsonArray=new JSONArray();
                            jsonObject1 = jsonArraycollection.getJSONObject(i);
                            jsonArray=jsonObject1.getJSONArray("photos");
                            jsonObject2=jsonArray.getJSONObject(0);
                            Collectionid.add(jsonObject1.getString("id"));
                            Collectiontitle.add(jsonObject1.getString("description"));
                            Collectionplace.add(jsonObject1.getString("place"));
                            Collectionphoto.add(jsonObject2.getString("photo"));
                            Collectionday.add(jsonObject1.getString("day"));
                            Collectionprice.add(jsonObject1.getString("price"));
                            Collectiondiscount.add(jsonObject1.getString("discount"));
                            Collectiontypetour.add(jsonObject1.getString("type_tour"));
                        }
                        collectionadapter.notifyDataSetChanged();

                    }
                }catch (Exception e){
                    Toast.makeText(viewallresort.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(viewallresort.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue2.add(jsonObjectRequest2);


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewallresort.super.onBackPressed();
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allresort, parent, false);
            return new collectionAdapter.collectionViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull collectionAdapter.collectionViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Glide.with(holder.resortimage.getContext()).load(profilecontainer.baseUrl+"/resort/admin/photos/"+Collectionphoto.get(position)).into(holder.resortimage);
            holder.place.setText(Collectionplace.get(position));
            holder.title.setText(Collectiontitle.get(position));
            holder.price.setText("₹"+Collectionprice.get(position)+"/-");
            holder.discount.setText("₹"+Collectiondiscount.get(position)+"/-");
            if(Collectionday.get(position).equals("1")){
                holder.day.setText(Collectionday.get(position)+"D");
            }else {
                int nigh=Integer.valueOf(Collectionday.get(position));
                nigh=nigh-1;
                holder.day.setText(String.valueOf(nigh)+"N / "+Collectionday.get(position)+"D");
            }
            holder.desc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    profilecontainer.place=Collectionplace.get(position);
                    startActivity(new Intent(viewallresort.this,viewplaceresort.class));
                }
            });
        }
        @Override
        public int getItemCount() {
            return datamodel.size();
        }
        public class collectionViewHolder extends RecyclerView.ViewHolder {
            ImageView resortimage;
            TextView place,title,price,discount,day;
            CardView desc;
            public collectionViewHolder(View itemView) {
                super(itemView);
                resortimage=itemView.findViewById(R.id.resortimage);
                place=itemView.findViewById(R.id.textView2);
                title=itemView.findViewById(R.id.textView4);
                price=itemView.findViewById(R.id.price);
                discount=itemView.findViewById(R.id.textView5);
                day=itemView.findViewById(R.id.day);
                desc=itemView.findViewById(R.id.desc);
            }
        }
    }
}