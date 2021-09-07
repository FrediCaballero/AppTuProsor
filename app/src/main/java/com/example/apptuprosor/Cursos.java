package com.example.apptuprosor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apptuprosor.Cursos;
import com.example.apptuprosor.retrofit_data.RetrofitApiService;
import com.example.apptuprosor.retrofit_data.RetrofitClient;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cursos extends AppCompatActivity implements  SearchView.OnQueryTextListener {
    private RecyclerView rvLista;
    private RecyclerAdapter adapter;
    private List<ItemList> items;
    private SearchView svSearch;
    private RetrofitApiService retrofitApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursos);
        initViews();
        initValues();
        initListener();
    }

    private void initViews(){
        rvLista = findViewById(R.id.rvLista);
        svSearch = findViewById(R.id.svSearch);
    }

    private void initValues(){
        retrofitApiService = RetrofitClient.getApiService();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvLista.setLayoutManager(manager); //indica en que posicion se van a poblar los elementos
        getItemsSQL();
    }

    private void initListener(){
        svSearch.setOnQueryTextListener(this);
    }

    private void getItemsSQL(){
        retrofitApiService.getItems().enqueue(new Callback<List<ItemList>>() {
            @Override
            public void onResponse(Call<List<ItemList>> call, Response<List<ItemList>> response) {
                items = response.body();
                adapter = new RecyclerAdapter(items, Cursos.this);
                rvLista.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ItemList>> call, Throwable t) {
                Toast.makeText(Cursos.this,"ERROR"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filter(newText);
        return false;
    }
}