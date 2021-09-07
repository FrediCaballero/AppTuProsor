package com.example.apptuprosor;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RegistroDocenteRequest extends StringRequest {
    private static final String REGISTRO_REQUEST_URL="http://192.168.232.136/registroDocente.php";
    private Map<String,String> params;
    public RegistroDocenteRequest(String nombres, String correo, String dni, String celular, String password, Response.Listener<String> listener){
        super(Method.POST, REGISTRO_REQUEST_URL, listener, null);
        params=new HashMap<>();
        params.put("nombres",nombres);
        params.put("correo",correo);
        params.put("dni",dni);
        params.put("celular",celular);
        params.put("password",password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}