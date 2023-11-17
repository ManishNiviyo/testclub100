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
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class registration extends AppCompatActivity {
    ConstraintLayout backbutton;
    EditText name,email,monbile,company,gst,pass,conpass;
    TextInputLayout tname,temail,tmonbile,tcompany,tgst,tpass,tconpass;
    CardView reg;
    Switch agrnt;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Dialog loading;
    String agent="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        backbutton=findViewById(R.id.constraintLayout);
        reg=findViewById(R.id.submitbutton);
        agrnt=findViewById(R.id.agent);
        name=findViewById(R.id.name);
        email=findViewById(R.id.emal);
        monbile=findViewById(R.id.mobile);
        company=findViewById(R.id.company);
        gst=findViewById(R.id.gst);
        pass=findViewById(R.id.password);
        conpass=findViewById(R.id.passwordconfirm);

        tname=findViewById(R.id.textInputLayoutname);
        temail=findViewById(R.id.textInputLayoutemail);
        tmonbile=findViewById(R.id.textInputLayoutmobile);
        tcompany=findViewById(R.id.textInputLayoutcompany);
        tgst=findViewById(R.id.textInputLayoutgsrt);
        tpass=findViewById(R.id.textInputLayoutpassword);
        tconpass=findViewById(R.id.textInputLayoutpasswordconfirm);

        loading=new Dialog(this);
        loading.setContentView(R.layout.loadingui);
        loading.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loading.setCancelable(false);

        agrnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(agrnt.isChecked()){
                    agent="yes";
                    tcompany.setVisibility(View.VISIBLE);
                    tgst.setVisibility(View.VISIBLE);
                    tcompany.setErrorEnabled(false);
                    tgst.setErrorEnabled(false);
                }
                else {
                    agent="";
                    tcompany.setVisibility(View.GONE);
                    tgst.setVisibility(View.GONE);
                }
            }
        });

        name.addTextChangedListener(new TextWatcher() {@Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}@Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {tname.setErrorEnabled(false);}@Override
            public void afterTextChanged(Editable editable) {}
        });
        email.addTextChangedListener(new TextWatcher() {@Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}@Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {temail.setErrorEnabled(false);}@Override
        public void afterTextChanged(Editable editable) {}
        });
        monbile.addTextChangedListener(new TextWatcher() {@Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}@Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {tmonbile.setErrorEnabled(false);}@Override
        public void afterTextChanged(Editable editable) {}
        });
        company.addTextChangedListener(new TextWatcher() {@Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}@Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {tcompany.setErrorEnabled(false);}@Override
        public void afterTextChanged(Editable editable) {}
        });
        gst.addTextChangedListener(new TextWatcher() {@Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}@Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {tgst.setErrorEnabled(false);}@Override
        public void afterTextChanged(Editable editable) {}
        });
        pass.addTextChangedListener(new TextWatcher() {@Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}@Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {tpass.setErrorEnabled(false);}@Override
        public void afterTextChanged(Editable editable) {}
        });
        conpass.addTextChangedListener(new TextWatcher() {@Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}@Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {tconpass.setErrorEnabled(false);}@Override
        public void afterTextChanged(Editable editable) {}
        });


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean error=checkError();
                if(error){
                    return;
                }
                if(!pass.getText().toString().trim().equals(conpass.getText().toString().trim())){
                    tconpass.setError("Confirm Password is not matching.");
                }
                else {
                    loading.show();
                    Map<String,String> param=new HashMap<String,String>();
                    param.put("email",email.getText().toString().trim());
                    param.put("password",pass.getText().toString().trim());
                    param.put("name",name.getText().toString().trim());
                    param.put("company_name",company.getText().toString().trim());
                    param.put("gstin",gst.getText().toString().trim());
                    param.put("mobile",monbile.getText().toString().trim());
                    param.put("isagent",agent);

                    RequestQueue requestQueue2 = Volley.newRequestQueue(registration.this);
                    String URL2 =profilecontainer.baseUrl+"/resort/api/singnup.php";

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
                                    myEdit.putString("email", email.getText().toString().trim());
                                    myEdit.putString("password", pass.getText().toString().trim());
                                    myEdit.putString("name", name.getText().toString().trim());
                                    JSONArray jsonArray=new JSONArray();
                                    jsonArray=response.getJSONArray("id");
                                    myEdit.putString("id", jsonArray.getString(0));
                                    myEdit2.putString("login","yes");
                                    myEdit.commit();
                                    myEdit2.commit();
                                    Toast.makeText(registration.this, response.getString("result"), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(registration.this,MainActivity.class));
                                    finishAffinity();
                                }
                                else {
                                    Toast.makeText(registration.this, response.getString("result"), Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
                                Toast.makeText(registration.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loading.dismiss();
                            Toast.makeText(registration.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    requestQueue2.add(jsonObjectRequest2);
                }
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registration.super.onBackPressed();
            }
        });
    }

    public boolean checkError() {
        boolean error = false;
        if (name.getText().toString().trim().isEmpty()) {
            tname.setError("Please fill out this field.");
            error = true;
        } else {
            error = !error ? false : true;
            tname.setErrorEnabled(false);
        }
        if (email.getText().toString().trim().isEmpty()) {
            temail.setError("Please fill out this field.");
            error = true;
        } else {
            error = !error ? false : true;
            temail.setErrorEnabled(false);
            if (!email.getText().toString().trim().matches(emailPattern)) {
                temail.setError("Invalid Email Id");
                error = true;
            } else {
                error = !error ? false : true;
                temail.setErrorEnabled(false);
            }
        }
        if (monbile.getText().toString().trim().isEmpty()) {
            tmonbile.setError("Please fill out this field.");
            error = true;
        } else {
            error = !error ? false : true;
            if (monbile.getText().toString().trim().length() < 10) {
                tmonbile.setError("Mobile No must be 10 digits");
                error = true;
            } else {
                error = !error ? false : true;
                tmonbile.setErrorEnabled(false);
            }
        }
        if (pass.getText().toString().trim().isEmpty()) {
            tpass.setError("Please fill out this field.");
            error = true;
        } else {
            error = !error ? false : true;
            if (pass.getText().toString().trim().length() < 4) {
                tpass.setError("Password is too short");
                error = true;
            } else {
                error = !error ? false : true;
                tpass.setErrorEnabled(false);
            }
        }
        if (conpass.getText().toString().trim().isEmpty()) {
            tconpass.setError("Please fill out this field.");
            error = true;
        } else {
            error = !error ? false : true;
            tconpass.setErrorEnabled(false);
        }

        if (agent.equals("yes")) {

            if (company.getText().toString().trim().isEmpty()) {
                tcompany.setError("Please fill out this field.");
                error = true;
            } else {
                error = !error ? false : true;
                tcompany.setErrorEnabled(false);
            }
            if (gst.getText().toString().trim().isEmpty()) {
                tgst.setError("Please fill out this field.");
                error = true;
            } else {
                error = !error ? false : true;
                tgst.setErrorEnabled(false);
            }
        }


        return error;
    }
}