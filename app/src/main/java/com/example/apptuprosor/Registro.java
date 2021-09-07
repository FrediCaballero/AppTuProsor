package com.example.apptuprosor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class Registro extends AppCompatActivity implements View.OnClickListener {
    EditText etnombres, etedad, etcorreo, etpassword;
    Button btn_registrar;
    TextView tv_politica;
    private int dia, mes, anio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        etnombres = (EditText) findViewById(R.id.EditT_nombres);
        etedad = (EditText) findViewById(R.id.EditT_edad);
        etedad.setOnClickListener(this);
        etcorreo = (EditText) findViewById(R.id.EditT_correo);
        etpassword = (EditText) findViewById(R.id.EditT_password);
        tv_politica = (TextView) findViewById(R.id.TextV_politica);
        tv_politica.setOnClickListener(this);
        btn_registrar = (Button) findViewById(R.id.Btn_registrar);
        btn_registrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == etedad){
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            anio = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(Registro.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfyear, int dayOfMonth) {
                    etedad.setText(dayOfMonth+"/"+(monthOfyear+1)+"/"+year);
                     }
                }
                ,dia,mes,anio);
                datePickerDialog.show();
        }
        if(v == btn_registrar){
            final String nombres = etnombres.getText().toString();
            final String nacimiento = etedad.getText().toString();
            final String correo = etcorreo.getText().toString();
            final String password = etpassword.getText().toString();
            Response.Listener<String> respoListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if(etnombres.getText().toString().equals("")){
                            Toast.makeText(Registro.this,"Ingresa tu nombre", Toast.LENGTH_SHORT).show();
                        }else if (etedad.getText().toString().equals("")){
                            Toast.makeText(Registro.this,"Ingresa tu fecha de nacimiento", Toast.LENGTH_SHORT).show();
                        }else if(etcorreo.getText().toString().equals("")){
                            Toast.makeText(Registro.this,"Ingresa un correo valido", Toast.LENGTH_SHORT).show();
                        }else if(etpassword.getText().toString().equals("")){
                            Toast.makeText(Registro.this,"Ingresa una contraseña", Toast.LENGTH_SHORT).show();
                        }else{
                            if(success){
                                Toast.makeText(Registro.this,"Registro exitoso", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Registro.this,MainActivity.class);
                                Registro.this.startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(Registro.this);
                                builder.setMessage("Error al registrarse, ingrese nuevamente sus datos").setNegativeButton("Retry",null).create().show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            RegistroRequest registroRequest = new RegistroRequest(nombres, nacimiento, correo, password, respoListener);
            RequestQueue queve = Volley.newRequestQueue(Registro.this);
            queve.add(registroRequest);
        }
        if(v == tv_politica){
            Intent intentPol = new Intent(Registro.this, PoliticaDePrivacidad.class);
            Registro.this.startActivity(intentPol);
        }
    }
}