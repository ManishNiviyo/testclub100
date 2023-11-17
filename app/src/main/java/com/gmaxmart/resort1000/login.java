package com.gmaxmart.resort1000;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    ConstraintLayout backbutton;
    TextInputLayout temail,tpassword;
    EditText email,password;
    CardView loginbutton,registration;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Dialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        backbutton=findViewById(R.id.constraintLayout);
        temail=findViewById(R.id.textInputLayoutemail);
        tpassword=findViewById(R.id.textInputLayoutpassword);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        loginbutton=findViewById(R.id.login);
        registration=findViewById(R.id.create);

        loading=new Dialog(this);
        loading.setContentView(R.layout.loadingui);
        loading.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loading.setCancelable(false);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean error=checkError();
                if(error){
                    return;
                }
                loading.show();
                Map<String,String> param=new HashMap<String,String>();
                param.put("email",email.getText().toString().trim());
                param.put("password",password.getText().toString().trim());

                RequestQueue requestQueue2 = Volley.newRequestQueue(login.this);
                String URL2 =profilecontainer.baseUrl+"/resort/api/login.php";

                JsonObjectRequest jsonObjectRequest2=new JsonObjectRequest(Request.Method.POST, URL2, new JSONObject(param), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.dismiss();
                        // Toast.makeText(generalquery.this, response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            if(response.getString("status").equals("true")){
                                SharedPreferences sharedPreferences = getSharedPreferences("userdetail",MODE_PRIVATE);
                                SharedPreferences sharedPreferences2 = getSharedPreferences("logindetail",MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                SharedPreferences.Editor myEdit2 = sharedPreferences2.edit();
                                JSONObject jsonObject=new JSONObject();
                                jsonObject=response.getJSONObject("profile");
                                myEdit.putString("email", email.getText().toString().trim());
                                myEdit.putString("password", password.getText().toString().trim());
                                myEdit.putString("name", jsonObject.getString("name"));
                                myEdit.putString("id", jsonObject.getString("id"));
                                myEdit2.putString("login","yes");
                                myEdit.commit();
                                myEdit2.commit();
                                Toast.makeText(login.this, response.getString("result"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(login.this,MainActivity.class));
                                finishAffinity();
                            }
                            else {
                                Toast.makeText(login.this, response.getString("result"), Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(login.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(login.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue2.add(jsonObjectRequest2);
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this,registration.class));
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}@Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {temail.setErrorEnabled(false);}@Override
            public void afterTextChanged(Editable editable) {}
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}@Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {tpassword.setErrorEnabled(false);}@Override
            public void afterTextChanged(Editable editable) {}
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.super.onBackPressed();
            }
        });
    }
    public boolean checkError(){
        boolean error = false;
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
        if(password.getText().toString().trim().isEmpty()){
            tpassword.setError("Please fill out this field.");
            error=true;
        } else {
            error = !error ? false : true;
            if(password.getText().toString().trim().length()<4){
                tpassword.setError("Password is too short");
                error=true;
            } else {
                error = !error ? false : true;
                tpassword.setErrorEnabled(false);
            }
        }

        return error;
    }
}