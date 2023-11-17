package com.gmaxmart.resort1000;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;
import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Bookyourholidayresort  extends AppCompatActivity implements PaymentResultWithDataListener {
    ConstraintLayout backbutton;
    RecyclerView recycler;
    collectionAdapter collectionadapter;
    ArrayList<String> videoid,photo;
    CardView submitbutton;
    ArrayList<String> Collectionid,Collectiontitle,Collectionplace,Collectionpackage,
            Collectionday,Collectionprice,Collectiondiscount,Collectiontypetour;
    Dialog loading;
    TextView place,title,price,discount,days;
    String noofroom ="1";
    View datepicker,datepicker2;
    TextInputLayout tname,temail,tmobile,tdate,tdate2,tdays,tadults,tchildren;
    EditText name,email,mobile,date,date2,day,adults,children;
    private int mYear, mMonth, mDay;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public static final int MAX_FORMAT_LENGTH = 8;
    public static final int MIN_FORMAT_LENGTH = 3;
    private String updatedText;
    private boolean editing;
    String amount="0",adultamount="0",childrenamount="0",totalamount="0",gstamount="0",payamount="0",adultno="0",chilno="0";
    String saveamount="0",saveadultamount="0",savechildrenamount="0",savetotalamount="0",savegstamount="0",savepayamount="0",saveadultno="0",savechilno="0";
    TextView adultamountshow,childrenamountshow,totalamountshow,gstamountshow,payamountshow,saveamountshow;
    Switch partialpayment;
    boolean partial=false;
    Order order; String order_Id="null";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookyourholidayresort);

        backbutton=findViewById(R.id.constraintLayout);
        recycler=findViewById(R.id.topslider);
        place=findViewById(R.id.place);
        title=findViewById(R.id.textView4);
        price=findViewById(R.id.price);
        discount=findViewById(R.id.textView5);
        days=findViewById(R.id.day);
        submitbutton=findViewById(R.id.submitbutton);
        partialpayment=findViewById(R.id.partial);

        datepicker=findViewById(R.id.pickdate);
        datepicker2=findViewById(R.id.pickdatecheck);
        submitbutton=findViewById(R.id.submitbutton);
        tname=findViewById(R.id.textInputLayoutname);
        temail=findViewById(R.id.textInputLayoutemail);
        tmobile=findViewById(R.id.textInputLayoutmobile);
        tdate=findViewById(R.id.textInputLayoutdate);
        tdate2=findViewById(R.id.textInputLayoutdatecheck);
        tdays=findViewById(R.id.textInputLayoutdays);
        tadults=findViewById(R.id.textInputLayoutadults);
        tchildren=findViewById(R.id.textInputLayoutchildren);

        name=findViewById(R.id.name);
        email=findViewById(R.id.emal);
        mobile=findViewById(R.id.mobile);
        date=findViewById(R.id.date);
        date2=findViewById(R.id.datecheck);
        day=findViewById(R.id.days);
        adults=findViewById(R.id.adults);
        children=findViewById(R.id.children);

        adultamountshow=findViewById(R.id.adultamount);
        childrenamountshow=findViewById(R.id.childeramount);
        totalamountshow=findViewById(R.id.totalamount);
        gstamountshow=findViewById(R.id.gstamount);
        payamountshow=findViewById(R.id.payableamount);
        saveamountshow=findViewById(R.id.saveamount);

        loading=new Dialog(this);
        loading.setContentView(R.layout.loadingui);
        loading.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loading.setCancelable(false);

        //   loading.show();

        LinearLayoutManager linearLayout= new LinearLayoutManager(Bookyourholidayresort.this,LinearLayoutManager.HORIZONTAL,false);
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
        collectionadapter = new collectionAdapter(photo);
        recycler.setAdapter(collectionadapter);


        Intent intent = getIntent();
        if (intent != null) {
            String tid = intent.getStringExtra("ID");
            String ttitle = intent.getStringExtra("Title");
            String tplace = intent.getStringExtra("Place");
            String tphoto = intent.getStringExtra("Photo");
            String tday = intent.getStringExtra("Day");
            String tprice = intent.getStringExtra("Price");
            String tdiscount = intent.getStringExtra("Discount");
            String ttypeTour = intent.getStringExtra("TypeTour");

            //    Toast.makeText(this, tprice, Toast.LENGTH_SHORT).show();
            title.setText(tplace);

            price.setText("₹"+tprice+"/-");

            amount = tprice;


        }

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
                        amount=jsonObject1.getString("price");
                        saveamount=jsonObject1.getString("discount");
                        Collectiondiscount.add(jsonObject1.getString("discount"));
                        Collectiontypetour.add(jsonObject1.getString("type_tour"));
                        JSONArray jsonArrayphoto=new JSONArray();
                        jsonArrayphoto=jsonObject1.getJSONArray("photos");
                        //  Toast.makeText(bookyourholiday.this, jsonArrayphoto.toString(), Toast.LENGTH_SHORT).show();
                        for (int i=0;i<jsonArrayphoto.length();i++){
                            JSONObject jsonObject=new JSONObject();
                            jsonObject=jsonArrayphoto.getJSONObject(i);
                            photo.add(jsonObject.getString("photo"));
                        }
                        title.setText(Collectiontitle.get(0));place.setText(Collectionplace.get(0));
                        price.setText("₹"+Collectionprice.get(0)+"/-"); discount.setText("₹"+Collectiondiscount.get(0)+"/-");
                        if(Collectionday.get(0).equals("1")){
                            days.setText(Collectionday.get(0)+"D");
                        }else {
                            int nigh=Integer.valueOf(Collectionday.get(0));
                            nigh=nigh-1;
                            days.setText(String.valueOf(nigh)+"N / "+Collectionday.get(0)+"D");
                        }
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


        name.addTextChangedListener(new TextWatcher() {@Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}@Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {tname.setErrorEnabled(false);}@Override
        public void afterTextChanged(Editable editable) {}});

        email.addTextChangedListener(new TextWatcher() {@Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}@Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {temail.setErrorEnabled(false);}@Override
        public void afterTextChanged(Editable editable) {}});

        mobile.addTextChangedListener(new TextWatcher() {@Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}@Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {tmobile.setErrorEnabled(false);}@Override
        public void afterTextChanged(Editable editable) {}});


        day.addTextChangedListener(new TextWatcher() {@Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}@Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {tdays.setErrorEnabled(false);}@Override
        public void afterTextChanged(Editable editable) {}});

        adults.addTextChangedListener(new TextWatcher() {@Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}@Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
           /* tadults.setErrorEnabled(false);
            int am=Integer.valueOf(amount);
            int saveam=Integer.valueOf(saveamount);
            //adultno=adults.getText().toString().trim();
            if(adults.getText().toString().trim().isEmpty()){
                adultno="0";
                saveadultno="0";
            }
            else {
                adultno=adults.getText().toString().trim();
                saveadultno=adults.getText().toString().trim();
            }
            int adno=Integer.valueOf(adultno);
            int adnoamount=am*adno;
            adultamount=String.valueOf(adnoamount);
            adultamountshow.setText("₹"+adultamount+"/-");
            int chnoamount=Integer.valueOf(chilno);
            chnoamount=chnoamount*am;
            int dis=(chnoamount*60)/100;
            chnoamount=chnoamount-dis;
            childrenamount=String.valueOf(chnoamount);
            childrenamountshow.setText("₹"+childrenamount+"/-");
            int toamount=0;
            int granttotal=adnoamount+chnoamount;
            totalamount=String.valueOf(granttotal);
            totalamountshow.setText("₹"+totalamount+"/-");
            int gstam=Integer.valueOf(totalamount);
            gstam=(gstam*18)/100;
            gstamount=String.valueOf(gstam);
            gstamountshow.setText("₹"+gstamount+"/-");
            int payam=Integer.valueOf(payamount);
            payam=granttotal+gstam;
            payamount=String.valueOf(payam);
            payamountshow.setText("₹"+payamount+"/-");



            int saveadno=Integer.valueOf(saveadultno);
            int saveadnoamount=saveam*saveadno;
            saveadultamount=String.valueOf(adnoamount);
            int savechnoamount=Integer.valueOf(savechilno);
            savechnoamount=savechnoamount*saveam;
            int savedis=(savechnoamount*60)/100;
            savechnoamount=savechnoamount-savedis;
            savechildrenamount=String.valueOf(savechnoamount);
            int savetoamount=0;
            int savegranttotal=saveadnoamount+savechnoamount;
            savetotalamount=String.valueOf(savegranttotal);
            int savegstam=Integer.valueOf(savetotalamount);
            savegstam=(savegstam*18)/100;
            savegstamount=String.valueOf(savegstam);
            int savepayam=Integer.valueOf(savepayamount);
            savepayam=savegranttotal+savegstam;
            savepayamount=String.valueOf(savepayam);
            int save=savepayam-payam;
            String show=String.valueOf(save);
            saveamountshow.setText("You Save ₹"+show+"/-");*/

        }@Override
        public void afterTextChanged(Editable editable) {}});

        children.addTextChangedListener(new TextWatcher() {@Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}@Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
           /* tchildren.setErrorEnabled(false);
            int am=Integer.valueOf(amount);
            int saveam=Integer.valueOf(saveamount);
            //adultno=adults.getText().toString().trim();
            if(children.getText().toString().trim().isEmpty()){
                chilno="0";
                savechilno="0";
            }
            else {
                chilno=children.getText().toString().trim();
                savechilno=children.getText().toString().trim();
            }
            int adno=Integer.valueOf(adultno);
            int adnoamount=am*adno;
            adultamount=String.valueOf(adnoamount);
            adultamountshow.setText("₹"+adultamount+"/-");
            int chnoamount=Integer.valueOf(chilno);
            chnoamount=chnoamount*am;
            int dis=(chnoamount*60)/100;
            chnoamount=chnoamount-dis;
            childrenamount=String.valueOf(chnoamount);
            childrenamountshow.setText("₹"+childrenamount+"/-");
            int toamount=0;
            int granttotal=adnoamount+chnoamount;
            totalamount=String.valueOf(granttotal);
            totalamountshow.setText("₹"+totalamount+"/-");
            int gstam=Integer.valueOf(totalamount);
            gstam=(gstam*18)/100;
            gstamount=String.valueOf(gstam);
            gstamountshow.setText("₹"+gstamount+"/-");
            int payam=Integer.valueOf(payamount);
            payam=granttotal+gstam;
            payamount=String.valueOf(payam);
            payamountshow.setText("₹"+payamount+"/-");

            int saveadno=Integer.valueOf(saveadultno);
            int saveadnoamount=saveam*saveadno;
            saveadultamount=String.valueOf(saveadnoamount);
            int savechnoamount=Integer.valueOf(savechilno);
            savechnoamount=savechnoamount*saveam;
            int savedis=(savechnoamount*60)/100;
            savechnoamount=savechnoamount-savedis;
            savechildrenamount=String.valueOf(savechnoamount);
            int savetoamount=0;
            int savegranttotal=saveadnoamount+savechnoamount;
            savetotalamount=String.valueOf(savegranttotal);
            int savegstam=Integer.valueOf(savetotalamount);
            savegstam=(savegstam*18)/100;
            savegstamount=String.valueOf(savegstam);
            int savepayam=Integer.valueOf(savepayamount);
            savepayam=savegranttotal+savegstam;

            int save=savepayam-payam;
            String show=String.valueOf(save);
            saveamountshow.setText("You Save ₹"+show+"/-");*/


            String checkInStr = date.getText().toString().trim();
            String checkOutStr = date2.getText().toString().trim();
            String noofroom = children.getText().toString().trim();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date checkInDate = dateFormat.parse(checkInStr);
                Date checkOutDate = dateFormat.parse(checkOutStr);

                long differenceInMilliseconds = checkOutDate.getTime() - checkInDate.getTime();
                long differenceInDays = TimeUnit.DAYS.convert(differenceInMilliseconds, TimeUnit.MILLISECONDS);

                int am = Integer.valueOf(amount);
                int room = 0; // Default value

                if (!noofroom.isEmpty()) {
                    room = Integer.valueOf(noofroom);
                }

                int adno = (int) differenceInDays;
                int adnoamount = am * adno * room;
                adultamount = String.valueOf(adnoamount);
                adultamountshow.setText("₹" + adultamount + "/-");

                totalamountshow.setText("₹" + adultamount + "/-");
                int gstam = Integer.valueOf(adultamount);
                gstam = (gstam * 18) / 100;
                gstamount = String.valueOf(gstam);
                gstamountshow.setText("₹" + gstamount + "/-");
                int payam = Integer.valueOf(payamount);
                payam = adnoamount + gstam;
                payamount = String.valueOf(payam);
                payamountshow.setText("₹" + payamount + "/-");

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }@Override
        public void afterTextChanged(Editable editable) {}});

        date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                tdate.setErrorEnabled(false);
                if (text.toString().equals(updatedText) || editing) return;

                String digitsOnly = text.toString().replaceAll("\\D", "");
                int digitLen = digitsOnly.length();

                if (digitLen < MIN_FORMAT_LENGTH || digitLen > MAX_FORMAT_LENGTH) {
                    updatedText = digitsOnly;
                    return;
                }

                if (digitLen <= 4) {
                    String month = digitsOnly.substring(0, 2);
                    String day = digitsOnly.substring(2);

                    updatedText = String.format(Locale.US, "%s/%s", month, day);
                }
                else {
                    String month = digitsOnly.substring(0, 2);
                    String day = digitsOnly.substring(2, 4);
                    String year = digitsOnly.substring(4);

                    updatedText = String.format(Locale.US, "%s/%s/%s", month, day, year);
                }
                if(!date.getText().toString().trim().isEmpty()) {
                    try {
                        String currentdate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                        String depaturedate = date.getText().toString().trim();
                        SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");

                        Date date1 = dates.parse(depaturedate);
                        Date date2 = dates.parse(currentdate);

                        long difference = Math.abs(date1.getTime() - date2.getTime());
                        long differenceDates = difference / (24 * 60 * 60 * 1000);

                        String dayDifference = Long.toString(differenceDates);

                        // Toast.makeText(bookyourholiday.this, dayDifference, Toast.LENGTH_SHORT).show();
                        if(Integer.valueOf(dayDifference)>60){
                            if (partialpayment.isChecked()) {
                                partial = true;
                                // Toast.makeText(bookyourholiday.this, String.valueOf(partial), Toast.LENGTH_SHORT).show();
                            } else {
                                partial = false;
                                //  Toast.makeText(bookyourholiday.this, String.valueOf(partial), Toast.LENGTH_SHORT).show();
                            }

                        }
                        else {
                            partialpayment.setChecked(false);
                            partial = false;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editing) return;

                editing = true;

                editable.clear();
                editable.insert(0, updatedText);

                editing = false;
            }
        });

        date2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String checkInStr = date.getText().toString().trim();
                String checkOutStr = date2.getText().toString().trim();

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date checkInDate = dateFormat.parse(checkInStr);
                    Date checkOutDate = dateFormat.parse(checkOutStr);

                    long differenceInMilliseconds = checkOutDate.getTime() - checkInDate.getTime();
                    long differenceInDays = TimeUnit.DAYS.convert(differenceInMilliseconds, TimeUnit.MILLISECONDS);

                    if (differenceInDays <= 0) {
                        // Show dialog that check-out date cannot be before or the same as the check-in date
                        // Replace the dialog with your implementation
                        // Example:
                        showDialog("Error", "Check-out date cannot be before or the same as check-in date");
                        return; // Return to prevent further execution
                    }

                    System.out.println("Difference in days: " + differenceInDays);

                    int am = Integer.valueOf(amount);

                    int adno = (int) differenceInDays;
                    int adnoamount = am * adno;
                    adultamount = String.valueOf(adnoamount);
                    adultamountshow.setText("₹" + adultamount + "/-");

                    totalamountshow.setText("₹" + adultamount + "/-");
                    int gstam = Integer.valueOf(adultamount);
                    gstam = (gstam * 18) / 100;
                    gstamount = String.valueOf(gstam);
                    gstamountshow.setText("₹" + gstamount + "/-");
                    int payam = Integer.valueOf(payamount);
                    payam = adnoamount + gstam;
                    payamount = String.valueOf(payam);
                    payamountshow.setText("₹" + payamount + "/-");

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });


        MaterialDatePicker datePickr= MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTheme(R.style.MaterialCalendarTheme)
                .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                .build();

        MaterialDatePicker datePickr2= MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTheme(R.style.MaterialCalendarTheme)
                .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                .build();
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickr.show(getSupportFragmentManager(),"MATERIAL_DATE_PICKER");
            }
        });

        datepicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickr2.show(getSupportFragmentManager(),"MATERIAL_DATE_PICKER");
            }
        });
        datePickr.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.setTimeInMillis((Long) selection);
                SimpleDateFormat format = new SimpleDateFormat("dd MM yyyy");
                String formattedDate  = format.format(calendar.getTime());
                date.setText(formattedDate);
                if(!date.getText().toString().trim().isEmpty()) {
                    try {
                        String currentdate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                        String depaturedate = date.getText().toString().trim();
                        SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");

                        Date date1 = dates.parse(depaturedate);
                        Date date2 = dates.parse(currentdate);

                        long difference = Math.abs(date1.getTime() - date2.getTime());
                        long differenceDates = difference / (24 * 60 * 60 * 1000);

                        String dayDifference = Long.toString(differenceDates);

                        // Toast.makeText(bookyourholiday.this, dayDifference, Toast.LENGTH_SHORT).show();
                        if(Integer.valueOf(dayDifference)>60){
                            if (partialpayment.isChecked()) {
                                partial = true;
                                // Toast.makeText(bookyourholiday.this, String.valueOf(partial), Toast.LENGTH_SHORT).show();
                            } else {
                                partial = false;
                                //  Toast.makeText(bookyourholiday.this, String.valueOf(partial), Toast.LENGTH_SHORT).show();
                            }

                        }
                        else {
                            partialpayment.setChecked(false);
                            partial = false;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        datePickr2.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.setTimeInMillis((Long) selection);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate  = format.format(calendar.getTime());
                date2.setText(formattedDate);
                if(!date2.getText().toString().trim().isEmpty()) {
                    try {
                        String currentdate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                        String depaturedate = date2.getText().toString().trim();
                        SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");

                        Date date1 = dates.parse(depaturedate);
                        Date date2 = dates.parse(currentdate);

                        long difference = Math.abs(date1.getTime() - date2.getTime());
                        long differenceDates = difference / (24 * 60 * 60 * 1000);

                        String dayDifference = Long.toString(differenceDates);

                        // Toast.makeText(bookyourholiday.this, dayDifference, Toast.LENGTH_SHORT).show();
                        if(Integer.valueOf(dayDifference)>60){
                            if (partialpayment.isChecked()) {
                                partial = true;
                                // Toast.makeText(bookyourholiday.this, String.valueOf(partial), Toast.LENGTH_SHORT).show();
                            } else {
                                partial = false;
                                //  Toast.makeText(bookyourholiday.this, String.valueOf(partial), Toast.LENGTH_SHORT).show();
                            }

                        }
                        else {
                            partialpayment.setChecked(false);
                            partial = false;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        partialpayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!date.getText().toString().trim().isEmpty()) {
                    try {
                        String currentdate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                        String depaturedate = date.getText().toString().trim();
                        SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");

                        Date date1 = dates.parse(depaturedate);
                        Date date2 = dates.parse(currentdate);

                        long difference = Math.abs(date1.getTime() - date2.getTime());
                        long differenceDates = difference / (24 * 60 * 60 * 1000);

                        String dayDifference = Long.toString(differenceDates);

                        // Toast.makeText(bookyourholiday.this, dayDifference, Toast.LENGTH_SHORT).show();
                        if(Integer.valueOf(dayDifference)>60){
                            if (partialpayment.isChecked()) {
                                partial = true;
                                // Toast.makeText(bookyourholiday.this, String.valueOf(partial), Toast.LENGTH_SHORT).show();
                            } else {
                                partial = false;
                                //  Toast.makeText(bookyourholiday.this, String.valueOf(partial), Toast.LENGTH_SHORT).show();
                            }

                        }
                        else {
                            partialpayment.setChecked(false);
                            partial = false;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    partialpayment.setChecked(false);
                    partial = false;
                }
            }
        });
//        partialpayment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(partialpayment.isChecked()){
//                    partial=true;
//                }
//                else {
//                    partial=false;
//                }
//            }
//        });

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkInStr = date.getText().toString().trim();
                String checkOutStr = date2.getText().toString().trim();

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date checkInDate = dateFormat.parse(checkInStr);
                    Date checkOutDate = dateFormat.parse(checkOutStr);

                    long differenceInMilliseconds = checkOutDate.getTime() - checkInDate.getTime();
                    long differenceInDays = TimeUnit.DAYS.convert(differenceInMilliseconds, TimeUnit.MILLISECONDS);

                    if (differenceInDays <= 0) {
                        // Show dialog that check-out date cannot be before or the same as the check-in date
                        // Replace the dialog with your implementation
                        // Example:
                        showDialog("Error", "Check-out date cannot be before or the same as check-in date");
                        return; // Return to prevent further execution
                    }

                }catch (ParseException e) {
                    e.printStackTrace();
                }

                   /* loading.show();
                    SharedPreferences sharedPreferences = getSharedPreferences("userdetail",MODE_PRIVATE);
                    String id=sharedPreferences.getString("id","no");

                    Map<String,String> param=new HashMap<String,String>();
                 //   param.put("email",email.getText().toString().trim());
                 //   param.put("password",password.getText().toString().trim());

                    param.put("adultAmt", "100");
                    param.put("adults", "1");
                    param.put("childrenAmt", "60");
                    param.put("childrens","1");
                    param.put("gst", "50");
                    param.put("departure_date", "20/11/2022");
                    param.put("name", "sample");
                    param.put("status", "100");
                    param.put("totalAmt", "200");
                    param.put("user_id", id);
                    param.put("userContact", "9027408729");
                    param.put("userEmail", "shivamrajpandita233226@gmail.com");
                    param.put("orderId", "order_Id");
                    param.put("paymentId", "s");
                    param.put("id", Collectionid.get(0));

                OkHttpClient client = new OkHttpClient();
                client = new OkHttpClient.Builder()
                        .connectTimeout(1000, TimeUnit.SECONDS)
                        .writeTimeout(1000, TimeUnit.SECONDS)
                        .readTimeout(3000, TimeUnit.SECONDS)
                        .build();

                Retrofit.Builder builder=new Retrofit.Builder()
                        .baseUrl("https://webtosters.ga/resort/api/")
                        .addConverterFactory(GsonConverterFactory.create()).client(client);
                Retrofit retrofit=builder.build();

                myapi api=retrofit.create(myapi.class);

                Call<Map> call=api.save(param);

                call.enqueue(new Callback<Map>() {
                    @Override
                    public void onResponse(Call<Map> call, retrofit2.Response<Map> response) {
                        loading.dismiss();
                        Toast.makeText(bookyourholiday.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Map> call, Throwable t) {
                        loading.dismiss();
                        Toast.makeText(bookyourholiday.this, t.toString(), Toast.LENGTH_SHORT).show();
                        Log.i("erroe",t.toString());
                    }
                });*/



                boolean error = checkError();
                if (error) {
                    return;
                }
                if(payamount.equals("0")){
                    tadults.setError("1 adult is require");
                }
                else {
                    loading.show();

                    if(partialpayment.isChecked()){
                        partial=true;
                    }
                    else {
                        partial=false;
                    }

                    int amount = Math.round(Float.parseFloat(payamount) * 100);
                    Checkout checkout = new Checkout();
                    //    Checkout.preload(getApplicationContext());
                    //checkout.setKeyID("rzp_test_G2yE8p0cW2SUFn");
                    checkout.setKeyID("rzp_live_FC7WZP2BS6Aogf");
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

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    loading.dismiss();
                                    // JSONObject jsonObject = new JSONObject(String.valueOf(order));
                                    // order_Id = jsonObject.getString("id");
                                    //  Toast.makeText(bookyourholiday.this, order_Id, Toast.LENGTH_SHORT).show();
                                    SharedPreferences sharedPreferences = getSharedPreferences("userdetail",MODE_PRIVATE);
                                    String name=sharedPreferences.getString("name","no");
                                    String gmail=sharedPreferences.getString("email","no");

                                    object.put("name", "CLUB 1000 RESORT");
                                    object.put("description", "Test payment");
                                    object.put("currency", "INR");
                                    object.put("amount", amount);
                                    object.put("accept_partial", partial);
                                    object.put("prefill.contact", mobile.getText().toString().trim());
                                    object.put("prefill.email", gmail);
                                    // object.put("order_id", order_Id);

                                    checkout.open(Bookyourholidayresort.this, object);
                                }
                                catch (Exception e){
                                    loading.dismiss();
                                    Toast.makeText(Bookyourholidayresort.this, "Your Internet is interrupted.", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, 3000);

                    } catch (Exception e) {
                        loading.dismiss();
                        Toast.makeText(Bookyourholidayresort.this, "Your Internet is interrupted.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bookyourholidayresort.super.onBackPressed();
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

    public boolean checkError(){
        boolean error = false;
        if(name.getText().toString().trim().isEmpty()){
            tname.setError("Please fill out this field.");
            error=true;
        } else {
            error = !error ? false : true;
            tname.setErrorEnabled(false);
        }
       /* if(email.getText().toString().trim().isEmpty()){
            temail.setError("Please fill out this field.");
            error=true;
        } else {
            error = !error ? false : true;
            temail.setErrorEnabled(false);
            if(!email.getText().toString().trim().matches(emailPattern)){
                temail.setError("Invalid Email Id");
                error=true;
            } else {
                error = !error ? false : true;
                temail.setErrorEnabled(false);
            }
        }*/
        if(mobile.getText().toString().trim().isEmpty()){
            tmobile.setError("Please fill out this field.");
            error=true;
        } else {
            error = !error ? false : true;
            if(mobile.getText().toString().trim().length()<10){
                tmobile.setError("Mobile No must be 10 digits");
                error=true;
            } else {
                error = !error ? false : true;
                tmobile.setErrorEnabled(false);
            }
        }

        if(date.getText().toString().trim().isEmpty()){
            tdate.setError("Please fill out this field.");
            error=true;
        } else {
            error = !error ? false : true;
            tdate.setErrorEnabled(false);
        }

        if(date2.getText().toString().trim().isEmpty()){
            tdate2.setError("Please fill out this field.");
            error=true;
        } else {
            error = !error ? false : true;
            tdate2.setErrorEnabled(false);
        }
       /* if(day.getText().toString().trim().isEmpty()){
            tdays.setError("Please fill out this field.");
            error=true;
        } else {
            error = !error ? false : true;
            tdays.setErrorEnabled(false);
        }*/
        if(adults.getText().toString().trim().isEmpty()){
            tadults.setError("Please fill out this field.");
            error=true;
        } else {
            error = !error ? false : true;
            tadults.setErrorEnabled(false);
        }
        if(children.getText().toString().trim().isEmpty()){
            tchildren.setError("Please fill out this field.");
            error=true;
        } else {
            error = !error ? false : true;
            tchildren.setErrorEnabled(false);
        }
        return error;
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        // this method is called on payment success.

       /* try{
            AlertDialog.Builder alertDialogBuilder;
            alertDialogBuilder = new AlertDialog.Builder(bookyourholiday.this);
            alertDialogBuilder.setMessage("Payment Successful :\nPayment ID: "+s+"\nPayment Data: "+paymentData.getData()+"\nOrder_id: "+paymentData.getOrderId());
            alertDialogBuilder.show();

        }catch (Exception e){
            e.printStackTrace();
        }*/
        try {
            loading.show();
            SharedPreferences sharedPreferences = getSharedPreferences("userdetail",MODE_PRIVATE);
            String id=sharedPreferences.getString("id","no");
            String gmail=sharedPreferences.getString("email","no");

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
            param.put("userEmail", gmail);
            param.put("orderId", order_Id);
            param.put("paymentId", s);

            //   JSONObject pack = new JSONObject();
            param.put("id", Collectionid.get(0));

            // mainObj.put("paymentData", paymentdata);
            // mainObj.put("pack", pack);


            RequestQueue requestQueue2 = Volley.newRequestQueue(Bookyourholidayresort.this);
            String URL2 = profilecontainer.baseUrl+"/resort/api/save_payment_details.php";

            JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.POST, URL2,new JSONObject(param), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    loading.dismiss();
                    // Toast.makeText(bookyourholiday.this, response.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(Bookyourholidayresort.this, "Payment is successful : " + s, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Bookyourholidayresort.this, MainActivity.class));
                    finishAffinity();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loading.dismiss();
                    Toast.makeText(Bookyourholidayresort.this, "Your Internet is interrupted.", Toast.LENGTH_LONG).show();
                    // Log.i("booking",error.toString());
                }
            });
            requestQueue2.add(jsonObjectRequest2);

        }
        catch (Exception e){
        }
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        // on payment failed.
        AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(Bookyourholidayresort.this);
        alertDialogBuilder.setMessage("Payment Failed :\n"+paymentData);
        alertDialogBuilder.show();
        // Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();
    }

    private void showDialog(String title, String message) {
        // Implement your dialog here (e.g., AlertDialog)
        // Example using AlertDialog:
        AlertDialog.Builder builder = new AlertDialog.Builder(Bookyourholidayresort.this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Handle OK button click if needed
                    }
                })
                .show();
    }


}