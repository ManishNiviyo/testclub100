package com.gmaxmart.resort1000;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class Fragment_2 extends Fragment implements PaymentResultWithDataListener {
    View view;
    RecyclerView recyclerView;
    ArrayList<String> memberid,membertitle,memberdiscount,memberprice,memberdescription,membertype,membervalidity;
    collectionAdapter collectionadapter;
    ProgressBar loading;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_2, container, false);

        recyclerView = view.findViewById(R.id.recyler);
        loading = view.findViewById(R.id.loading);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        memberid=new ArrayList<String>();
        membertitle=new ArrayList<String>();
        memberdiscount=new ArrayList<String>();
        memberprice=new ArrayList<String>();
        memberdescription=new ArrayList<String>();
        membertype=new ArrayList<String>();
        membervalidity=new ArrayList<String>();
        collectionadapter =new collectionAdapter(memberid);
        recyclerView.setAdapter(collectionadapter);

        RequestQueue requestQueue2 = Volley.newRequestQueue(getContext());
        String URL2 =profilecontainer.baseUrl+"/resort/api/membership.php";

        JsonObjectRequest jsonObjectRequest2=new JsonObjectRequest(Request.Method.POST, URL2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.setVisibility(View.GONE);
                try {
                    if(response.getString("status").equals("true")){
                        JSONArray jsonArraycollection=new JSONArray();
                        jsonArraycollection=response.getJSONArray("membership_list");
                        if (jsonArraycollection.length()>0){


                            //for (int i=0;i<jsonArraycollection.length();i++) {
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1 = jsonArraycollection.getJSONObject(0);
                            memberid.add(jsonObject1.getString("id"));
                            membertitle.add(jsonObject1.getString("title"));
                            membertype.add(jsonObject1.getString("type"));
                            membervalidity.add(jsonObject1.getString("validity"));
                            memberprice.add(jsonObject1.getString("price"));
                            memberdiscount.add(jsonObject1.getString("discount"));
                            memberdescription.add(jsonObject1.getString("description"));
                            //  }
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


        return view;
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
            holder.type.setText(membertitle.get(position));
            holder.discount.setText(memberdiscount.get(position)+"% on all packages");
            holder.description.setText(Html.fromHtml(memberdescription.get(position)));
            holder.price.setText("₹"+memberprice.get(position)+"/-");
            holder.month.setText(membervalidity.get(position));
            holder.buybutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
// loading.show();
                    int amount = Math.round(Float.parseFloat(memberprice.get(position)) * 100);
                    Checkout checkout = new Checkout();
                    //   Checkout.preload(getApplicationContext());
                    checkout.setKeyID("rzp_test_G2yE8p0cW2SUFn");
                    // checkout.setKeyID("rzp_live_FC7WZP2BS6Aogf");
                    checkout.setImage(R.drawable.logo);
                    JSONObject object = new JSONObject();

                    try {
                       /* Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try  {
                                    RazorpayClient razorpay = new RazorpayClient("rzp_live_FC7WZP2BS6Aogf", "0C73rKrztUYlAA0xuvotWVaJ");

                                    JSONObject orderRequest = new JSONObject();
                                    orderRequest.put("amount",amount);
                                    orderRequest.put("currency","INR");
                                    order = razorpay.orders.create(orderRequest);
                                } catch (Exception e) {
                                   // Toast.makeText(bookyourholiday.this, e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        thread.start();*/

                        // final Handler handler = new Handler();
                        //   handler.postDelayed(new Runnable() {
                        //   @Override
                        //   public void run() {
                        try {
                            //   loading.dismiss();
                            // JSONObject jsonObject = new JSONObject(String.valueOf(order));
                            // order_Id = jsonObject.getString("id");
                            //  Toast.makeText(bookyourholiday.this, order_Id, Toast.LENGTH_SHORT).show();

                            object.put("name", "CLUB 1000 RESORT");
                            object.put("description", "Test payment");
                            object.put("currency", "INR");
                            object.put("amount", amount);
                            // object.put("order_id", order_Id);

                            checkout.open((Activity) getContext(), object);
                        }
                        catch (Exception e){
                            //  loading.dismiss();
                            Toast.makeText((Activity) getContext(), "Your Internet is interrupted.", Toast.LENGTH_SHORT).show();
                        }

                        //  }
                        //   }, 3000);

                    } catch (Exception e) {
                        // loading.dismiss();
                        Toast.makeText((Activity) getContext(), "Your Internet is interrupted.", Toast.LENGTH_SHORT).show();
                    }
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

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        // this method is called on payment success.

        try{
            AlertDialog.Builder alertDialogBuilder;
            alertDialogBuilder = new AlertDialog.Builder(getContext());
            alertDialogBuilder.setMessage("Payment Successful :\nPayment ID: "+s+"\nPayment Data: "+paymentData.getData()+"\nOrder_id: "+paymentData.getOrderId());
            alertDialogBuilder.show();

        }catch (Exception e){
            e.printStackTrace();
        }
       /* try {
            loading.show();
            SharedPreferences sharedPreferences = getSharedPreferences("userdetail",MODE_PRIVATE);
            String id=sharedPreferences.getString("id","no");

            Map<String,String> param=new HashMap<String,String>();
            param.put("adultAmt", adultamount);
            param.put("adults", adults.getText().toString().trim());
            param.put("childrenAmt", childrenamount);
            param.put("childrens", children.getText().toString().trim());
            param.put("gst", gstamount);
            param.put("departure_date", date.getText().toString().trim());
            param.put("name", name.getText().toString().trim());
            param.put("status", "100");
            param.put("totalAmt", payamount);
            param.put("user_id", id);

            // JSONObject paymentdata = new JSONObject();
            param.put("userContact", mobile.getText().toString().trim());
            param.put("userEmail", email.getText().toString().trim());
            param.put("orderId", order_Id);
            param.put("paymentId", s);

            //   JSONObject pack = new JSONObject();
            param.put("id", Collectionid.get(0));

            // mainObj.put("paymentData", paymentdata);
            // mainObj.put("pack", pack);


            RequestQueue requestQueue2 = Volley.newRequestQueue(bookyourholiday.this);
            String URL2 = "https://webtosters.ga/resort/api/save_payment_details.php";

            JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.POST, URL2,new JSONObject(param), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    loading.dismiss();
                    Toast.makeText(bookyourholiday.this, response.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(bookyourholiday.this, "Payment is successful : " + s, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(bookyourholiday.this, MainActivity.class));
                    finishAffinity();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loading.dismiss();
                    Toast.makeText(bookyourholiday.this, "Your Internet is interrupted.", Toast.LENGTH_LONG).show();
                    // Log.i("booking",error.toString());
                }
            });
            requestQueue2.add(jsonObjectRequest2);

        }
        catch (Exception e){
        }*/
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        // on payment failed.
        AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("Payment Failed :\n"+paymentData);
        alertDialogBuilder.show();
        // Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();
    }
}