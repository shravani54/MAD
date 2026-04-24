package com.example.aichatbot;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    @POST("chat/completions")
    Call<ResponseBody> getResponse(
            @Header("Authorization") String auth,
            @Body RequestBody body
    );
}