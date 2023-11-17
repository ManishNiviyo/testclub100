package com.gmaxmart.resort1000;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class generalquery extends AppCompatActivity {
    ConstraintLayout backbutton;
    View datepicker;
    CardView submitbutton,cencelbutton;
    TextInputLayout tname,temail,tmobile,tdestination,tdate,tdays,tadults,tchildren;
    EditText name,email,mobile,destination,date,days,adults,children;
    private int mYear, mMonth, mDay;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//    private String current = "";
//    private String ddmmyyyy = "DDMMYYYY";
//    private Calendar cal = Calendar.getInstance();
    public static final int MAX_FORMAT_LENGTH = 8;
    public static final int MIN_FORMAT_LENGTH = 3;
    Dialog loading;
    private String updatedText;
    private boolean editing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generalquery);
        backbutton=findViewById(R.id.constraintLayout);
        datepicker=findViewById(R.id.pickdate);
        submitbutton=findViewById(R.id.submitbutton);
        tname=findViewById(R.id.textInputLayoutname);
        temail=findViewById(R.id.textInputLayoutemail);
        tmobile=findViewById(R.id.textInputLayoutmobile);
        tdestination=findViewById(R.id.textInputLayoutdestination);
        tdate=findViewById(R.id.textInputLayoutdate);
        tdays=findViewById(R.id.textInputLayoutdays);
        tadults=findViewById(R.id.textInputLayoutadults);
        tchildren=findViewById(R.id.textInputLayoutchildren);
        cencelbutton=findViewById(R.id.cancelbutton);

        name=findViewById(R.id.name);
        email=findViewById(R.id.emal);
        mobile=findViewById(R.id.mobile);
        destination=findViewById(R.id.destination);
        date=findViewById(R.id.date);
        days=findViewById(R.id.days);
        adults=findViewById(R.id.adults);
        children=findViewById(R.id.children);

        loading=new Dialog(this);
        loading.setContentView(R.layout.loadingui);
        loading.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loading.setCancelable(false);

//        EditText[] editTexts={name,email,mobile,destination,days,adults,children};
//        TextInputLayout[] textInputLayout={tname,temail,tmobile,tdestination,tdays,tadults,tchildren};
//
//        for(int i=0;i<editTexts.length;i++){
//            editTexts[i].addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                   // checkError();
//                    textInputLayout[i].setErrorEnabled(false);
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//
//                }
//            });
//        }

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

        destination.addTextChangedListener(new TextWatcher() {@Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}@Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {tdestination.setErrorEnabled(false);}@Override
        public void afterTextChanged(Editable editable) {}});

        days.addTextChangedListener(new TextWatcher() {@Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}@Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {tdays.setErrorEnabled(false);}@Override
        public void afterTextChanged(Editable editable) {}});

        adults.addTextChangedListener(new TextWatcher() {@Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}@Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {tadults.setErrorEnabled(false);}@Override
        public void afterTextChanged(Editable editable) {}});

        children.addTextChangedListener(new TextWatcher() {@Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}@Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {tchildren.setErrorEnabled(false);}@Override
        public void afterTextChanged(Editable editable) {}});

        date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                tdate.setErrorEnabled(false);
//                if (date.getText().toString().trim().equals(current)) {
//                    return;
//                }
//                String clean = date.getText().toString().replaceAll("[^\\d.]|\\.", "");
//                String cleanC = current.replaceAll("[^\\d.]|\\.", "");
//
//                int cl = clean.length();
//                int sel = cl;
//
//                for (int j = 2; j <= cl && j < 6; j += 2) {
//                    sel++;
//                }
//                if (clean.equals(cleanC)) sel--;
//
//                if (clean.length() < 8){
//                    clean = clean + ddmmyyyy.substring(clean.length());
//                }
//                else{
//                    int day  = Integer.parseInt(clean.substring(0,2));
//                    int mon  = Integer.parseInt(clean.substring(2,4));
//                    int year = Integer.parseInt(clean.substring(4,8));
//
//                    mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
//                    cal.set(Calendar.MONTH, mon-1);
//                    year = (year<1900)?1900:(year>2100)?2100:year;
//                    cal.set(Calendar.YEAR, year);
//
//                    day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
//                    clean = String.format("%02d%02d%02d",day, mon, year);
//
//                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
//                            clean.substring(2, 4),
//                            clean.substring(4, 8));
//
//                    sel = sel < 0 ? 0 : sel;
//                    current = clean;
//                    date.setText(current);
//                    date.setSelection(sel < current.length() ? sel : current.length());
//                }

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
        MaterialDatePicker datePickr= MaterialDatePicker.Builder.datePicker()
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

        datePickr.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.setTimeInMillis((Long) selection);
                SimpleDateFormat format = new SimpleDateFormat("dd MM yyyy");
                String formattedDate  = format.format(calendar.getTime());
                date.setText(formattedDate);
            }
        });

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean error=checkError();
                if(error){
                    return;
                }
                loading.show();

                Map<String,String> param=new HashMap<String,String>();
                param.put("name",name.getText().toString().trim());
                param.put("date",date.getText().toString().trim());
                param.put("days",days.getText().toString().trim());
                param.put("desination",destination.getText().toString().trim());
                param.put("adults",adults.getText().toString().trim());
                param.put("email",email.getText().toString().trim());
                param.put("mobile",mobile.getText().toString().trim());
                param.put("children",children.getText().toString().trim());

                RequestQueue requestQueue2 = Volley.newRequestQueue(generalquery.this);
                String URL2 =profilecontainer.baseUrl+"/resort/api/inquiry_back.php";

                JsonObjectRequest jsonObjectRequest2=new JsonObjectRequest(Request.Method.POST, URL2, new JSONObject(param), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.dismiss();
                       // Toast.makeText(generalquery.this, response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            if(response.getString("status").equals("true")){
                                Toast.makeText(generalquery.this, response.getString("result"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(generalquery.this,MainActivity.class));
                                finishAffinity();
                            }
                        }catch (Exception e){
                            Toast.makeText(generalquery.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(generalquery.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue2.add(jsonObjectRequest2);

            }
        });

        cencelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generalquery.super.onBackPressed();
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generalquery.super.onBackPressed();
            }
        });
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
        if(email.getText().toString().trim().isEmpty()){
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
        }
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
        if(destination.getText().toString().trim().isEmpty()){
            tdestination.setError("Please fill out this field.");
            error=true;
        } else {
            error = !error ? false : true;
            tdestination.setErrorEnabled(false);
        }
        if(date.getText().toString().trim().isEmpty()){
            tdate.setError("Please fill out this field.");
            error=true;
        } else {
            error = !error ? false : true;
            tdate.setErrorEnabled(false);
        }
        if(days.getText().toString().trim().isEmpty()){
            tdays.setError("Please fill out this field.");
            error=true;
        } else {
            error = !error ? false : true;
            tdays.setErrorEnabled(false);
        }
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
}