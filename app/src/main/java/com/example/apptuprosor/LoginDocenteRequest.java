package com.example.apptuprosor;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginDocenteRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL="http://192.168.232.136/loginDocente.php";
    private Map<String,String> params;
    public LoginDocenteRequest(String correo, String password, Response.Listener<String> listener){
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params=new HashMap<>();
        params.put("correo",correo);
        params.put("password",password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
