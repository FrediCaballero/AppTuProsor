package com.example.apptuprosor;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RegistroCursoRequest extends StringRequest {
    private static final String REGISTRO_REQUEST_URL="http://192.168.232.136/RegistroCurso.php";
    private Map<String,String> params;
    public RegistroCursoRequest(String titulo, String descripcion, String fecha, String hora, String enlace, String imgResource, Response.Listener<String> listener){
        super(Method.POST, REGISTRO_REQUEST_URL, listener, null);
        params=new HashMap<>();
        params.put("titulo",titulo);
        params.put("descripcion",descripcion);
        params.put("fecha",fecha);
        params.put("hora",hora);
        params.put("enlace",enlace);
        params.put("imgResource",imgResource);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}