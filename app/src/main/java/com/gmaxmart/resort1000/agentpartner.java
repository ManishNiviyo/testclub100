package com.gmaxmart.resort1000;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class agentpartner extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ConstraintLayout showdra;
    CardView submitbutton;
    TextInputLayout tname,temail,tmobile,tresort,trooms,twebsite,taddress,tcity;
    EditText name,email,mobile,resort,rooms,website,address,city;
    Dialog loading;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
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
        setContentView(R.layout.activity_agentpartner);

        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        drawerLayout = findViewById(R.id.drawerLayout);
        showdra = findViewById(R.id.showdr);
        submitbutton=findViewById(R.id.submitbutton);
        tname=findViewById(R.id.textInputLayoutname);
        temail=findViewById(R.id.textInputLayoutemail);
        tmobile=findViewById(R.id.textInputLayoutmobile);
        tresort=findViewById(R.id.textInputLayoutResort);
        trooms=findViewById(R.id.textInputLayoutrooms);
        twebsite=findViewById(R.id.textInputLayoutwebsite);
        taddress=findViewById(R.id.textInputLayoutaddress);
        tcity=findViewById(R.id.textInputLayoutcity);

        name=findViewById(R.id.name);
        email=findViewById(R.id.emal);
        mobile=findViewById(R.id.mobile);
        resort=findViewById(R.id.Resort);
        rooms=findViewById(R.id.rooms);
        website=findViewById(R.id.adults);
        address=findViewById(R.id.address);
        city=findViewById(R.id.city);

        loading=new Dialog(this);
        loading.setContentView(R.layout.loadingui);
        loading.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loading.setCancelable(false);

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

        resort.addTextChangedListener(new TextWatcher() {@Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}@Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {tresort.setErrorEnabled(false);}@Override
        public void afterTextChanged(Editable editable) {}});

        rooms.addTextChangedListener(new TextWatcher() {@Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}@Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {trooms.setErrorEnabled(false);}@Override
        public void afterTextChanged(Editable editable) {}});

        address.addTextChangedListener(new TextWatcher() {@Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}@Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {taddress.setErrorEnabled(false);}@Override
        public void afterTextChanged(Editable editable) {}});

        city.addTextChangedListener(new TextWatcher() {@Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}@Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {tcity.setErrorEnabled(false);}@Override
        public void afterTextChanged(Editable editable) {}});

        website.addTextChangedListener(new TextWatcher() {@Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}@Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {twebsite.setErrorEnabled(false);}@Override
        public void afterTextChanged(Editable editable) {}});


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

        manuagent.setBackgroundResource(R.drawable.menucolor);
        manuhome.setBackgroundColor(Color.WHITE);
        manucontact.setBackgroundColor(Color.WHITE);
        manuabout.setBackgroundColor(Color.WHITE);
       // manuagent.setBackgroundColor(Color.WHITE);
        manubooking.setBackgroundColor(Color.WHITE);
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
                manuagent.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finishAffinity();
            }
        });
        manucontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manucontact.setBackgroundResource(R.drawable.menucolor);
                manuagent.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(),contactus.class));
                finishAffinity();
            }
        });
        manuabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manuabout.setBackgroundResource(R.drawable.menucolor);
                manuagent.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(),aboutus.class));
                finishAffinity();
            }
        });
        manuagent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(GravityCompat.START);

            }
        });
        manubooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manubooking.setBackgroundResource(R.drawable.menucolor);
                manuagent.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(),mybooking.class));
                finishAffinity();
            }
        });
        manumembership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manumembership.setBackgroundResource(R.drawable.menucolor);
                manuagent.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(),membership.class));
                finishAffinity();
            }
        });
        manuproprty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manuproprty.setBackgroundResource(R.drawable.menucolor);
                manuagent.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(getApplicationContext(),listyourproperty.class));
                finishAffinity();
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
                param.put("mobile",mobile.getText().toString().trim());
                param.put("email",email.getText().toString().trim());
                param.put("enquiry",city.getText().toString().trim());
                param.put("website",rooms.getText().toString().trim());
                param.put("company_name",resort.getText().toString().trim());
                param.put("gst",address.getText().toString().trim());
                param.put("city_id",website.getText().toString().trim());

                RequestQueue requestQueue2 = Volley.newRequestQueue(agentpartner.this);
                String URL2 =profilecontainer.baseUrl+"/resort/api/agent.php";

                JsonObjectRequest jsonObjectRequest2=new JsonObjectRequest(Request.Method.POST, URL2, new JSONObject(param), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.dismiss();
                        // Toast.makeText(generalquery.this, response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            if(response.getString("status").equals("true")){
                                Toast.makeText(agentpartner.this, response.getString("result"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(agentpartner.this,MainActivity.class));
                                finishAffinity();
                            }
                        }catch (Exception e){
                            Toast.makeText(agentpartner.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(agentpartner.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue2.add(jsonObjectRequest2);
            }
        });
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            startActivity(new Intent(agentpartner.this,MainActivity.class));
            finishAffinity();
           // overridePendingTransition(0,0);
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
        if(resort.getText().toString().trim().isEmpty()){
            tresort.setError("Please fill out this field.");
            error=true;
        } else {
            error = !error ? false : true;
            tresort.setErrorEnabled(false);
        }
        if(rooms.getText().toString().trim().isEmpty()){
            trooms.setError("Please fill out this field.");
            error=true;
        } else {
            error = !error ? false : true;
            trooms.setErrorEnabled(false);
        }
        if(website.getText().toString().trim().isEmpty()){
            twebsite.setError("Please fill out this field.");
            error=true;
        } else {
            error = !error ? false : true;
            twebsite.setErrorEnabled(false);
        }
        if(address.getText().toString().trim().isEmpty()){
            taddress.setError("Please fill out this field.");
            error=true;
        } else {
            error = !error ? false : true;
            taddress.setErrorEnabled(false);
        }
        if(city.getText().toString().trim().isEmpty()){
            tcity.setError("Please fill out this field.");
            error=true;
        } else {
            error = !error ? false : true;
            tcity.setErrorEnabled(false);
        }
        return error;
    }
}