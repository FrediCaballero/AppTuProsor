package com.example.apptuprosor.retrofit_data;

import com.example.apptuprosor.retrofit_data.RetrofitApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit=null;
    private static final String URL_BASE = "http://192.168.232.136/";
    public static RetrofitApiService getApiService(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(URL_BASE).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(RetrofitApiService.class);
    }
}
