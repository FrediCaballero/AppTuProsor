package com.example.apptuprosor;

public class ItemList {
    private String titulo;
    private String descripcion;
    private String fecha;
    private String hora;
    private String enlace;
    private String imgResource;


    public ItemList(String titulo, String fecha, String enlace, String descripcion, String imgResource){
        this.titulo = titulo;
        this.fecha = fecha;
        this.hora = titulo;
        this.enlace = enlace;
        this.descripcion = descripcion;
        this.imgResource = imgResource;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getFecha(){
        return fecha;
    }

    public String getHora(){
        return hora;
    }

    public String getEnlace(){
        return enlace;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getImgResource() {
        return imgResource;
    }
}
