package com.example.apptuprosor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ListaCursos extends AppCompatActivity {
    private String titulo;
    private String descripcion;
    private int imgResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cursos);
    }

    public ListaCursos(String titulo, String descripcion, int imgResource){
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imgResource = imgResource;
    }

    public String getTitulo(){
        return titulo;
    }

    public String getDescrpcion(){
        return descripcion;
    }

    public int getImgResource(){
        return imgResource;
    }
}