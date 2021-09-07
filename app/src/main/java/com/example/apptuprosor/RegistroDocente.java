package com.example.apptuprosor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistroDocente extends AppCompatActivity implements View.OnClickListener{
    EditText dnombres, dcorreo, ddni, dcelular, dpassword;
    Button D_Registrar;
    TextView D_Politica;
    RequestQueue requestQueue;
    private static final String URL_ELIMINAR_USER="http://192.168.232.136/EliminarUser.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_docente);
        dnombres = (EditText) findViewById(R.id.D_nombres);
        dcorreo = (EditText) findViewById(R.id.D_correo);
        ddni = (EditText) findViewById(R.id.D_dni);
        dcelular = (EditText) findViewById(R.id.d_celular);
        dpassword = (EditText) findViewById(R.id.D_password);
        D_Politica = (TextView) findViewById(R.id.D_politica);
        D_Politica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReg = new Intent(RegistroDocente.this, PoliticaDePrivacidad.class);
                RegistroDocente.this.startActivity(intentReg);
            }
        });
        D_Registrar = (Button) findViewById(R.id.Btn_D_registrar);
        D_Registrar.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        if(v == D_Registrar){
            final String nombres = dnombres.getText().toString();
            final String correo = dcorreo.getText().toString();
            final String dni = ddni.getText().toString();
            final String celular = dcelular.getText().toString();
            final String password = dpassword.getText().toString();
            Response.Listener<String> respoListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if(dnombres.getText().toString().equals("")){
                            Toast.makeText(RegistroDocente.this,"Ingresa tu nombre", Toast.LENGTH_SHORT).show();
                        }else if(dcorreo.getText().toString().equals("")){
                            Toast.makeText(RegistroDocente.this,"Ingresa tu correo institucional", Toast.LENGTH_SHORT).show();
                        }else if(ddni.getText().toString().equals("")){
                            Toast.makeText(RegistroDocente.this,"Ingresa tu DNI", Toast.LENGTH_SHORT).show();
                        }else if(dcelular.getText().toString().equals("")){
                            Toast.makeText(RegistroDocente.this,"Ingresa tu número de celular", Toast.LENGTH_SHORT).show();
                        }else if(dpassword.getText().toString().equals("")){
                            Toast.makeText(RegistroDocente.this,"Ingresa una contraseña", Toast.LENGTH_SHORT).show();
                        }else{
                            if(success){
                                EliminarUsuario(URL_ELIMINAR_USER);
                                Toast.makeText(RegistroDocente.this,"Registro exitoso", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegistroDocente.this,LoginDocente.class);
                                RegistroDocente.this.startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegistroDocente.this);
                                builder.setMessage("Error al registrarse, ingrese nuevamente sus datos").setNegativeButton("Retry",null).create().show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            RegistroDocenteRequest registroDocenteRequest = new RegistroDocenteRequest(nombres, correo, dni, celular, password, respoListener);
            RequestQueue queve = Volley.newRequestQueue(RegistroDocente.this);
            queve.add(registroDocenteRequest);
        }
        if(v == D_Politica){
            Intent intentReg = new Intent(RegistroDocente.this, PoliticaDePrivacidad.class);
            RegistroDocente.this.startActivity(intentReg);
        }
    }
    private void EliminarUsuario(String URL_ELIMINAR_USER){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ELIMINAR_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplication(), "VERIFICANDO DATOS", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(),error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> parametros = new HashMap<String,String>();
                parametros.put("correo",dcorreo.getText().toString());
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}