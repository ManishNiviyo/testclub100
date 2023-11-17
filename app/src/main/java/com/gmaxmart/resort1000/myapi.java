package com.gmaxmart.resort1000;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface myapi {
    @Headers({"Content-Type:application/json"})
    @POST("save_payment_details.php")
    Call<Map> save(@Body Map bon);
}
