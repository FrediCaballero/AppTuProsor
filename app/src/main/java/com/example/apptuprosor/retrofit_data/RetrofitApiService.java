package com.example.apptuprosor.retrofit_data;

import com.example.apptuprosor.ItemList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitApiService {
    @GET("ConsultaItem.php") //Insertar base de datos remota ejemplos items.php
    Call<List<ItemList>> getItems();
}
