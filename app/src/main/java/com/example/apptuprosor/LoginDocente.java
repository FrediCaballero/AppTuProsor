package com.example.apptuprosor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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

public class LoginDocente extends AppCompatActivity {
    EditText et_correo, et_password;
    Button btn_log;
    TextView tv_restablecer;
    private static final String FORGOT_PASSWORD_DOCENTE_URL = "http://192.168.232.136/forgotDocente.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_docente);
        et_correo = (EditText) findViewById(R.id.D_Lcorreo);
        et_password = (EditText) findViewById(R.id.D_Lpassword);
        btn_log = (Button) findViewById(R.id.Btn_Diniciar);
        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String correo = et_correo.getText().toString();
                final String password = et_password.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                String nombres = jsonResponse.getString("nombres");
                                String dni = jsonResponse.getString("dni");

                                Intent intent = new Intent(LoginDocente.this, Docentes.class);
                                intent.putExtra("nombres",nombres);
                                intent.putExtra("correo",correo);
                                intent.putExtra("dni",dni);
                                LoginDocente.this.startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginDocente.this);
                                builder.setMessage("Correo o contrase??a incorrectas").setNegativeButton("Retry",null).create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginDocenteRequest loginDocenteRequest = new LoginDocenteRequest(correo, password, responseListener);
                RequestQueue queve = Volley.newRequestQueue(LoginDocente.this);
                queve.add(loginDocenteRequest);
            }
        });
        tv_restablecer = (TextView) findViewById(R.id.D_restablecer);
        tv_restablecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserForgotPasswordWithEmail();
            }
        });
    }
    private void UserForgotPasswordWithEmail() {
        View forgot_password_layout = LayoutInflater.from(this).inflate(R.layout.activity_forgot_password, null);
        final EditText forgot_email = forgot_password_layout.findViewById(R.id.forgot_email);
        Button btnForgotPass = forgot_password_layout.findViewById(R.id.forgot_password_button);
        Button btnCancelar = forgot_password_layout.findViewById(R.id.forgot_cancelar);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Restablecer Contrase??a");
        builder.setView(forgot_password_layout);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String correo = forgot_email.getText().toString().trim();
                if(correo.isEmpty()){
                    Toast.makeText(LoginDocente.this,"Ingrese su correo", Toast.LENGTH_SHORT).show();
                }else{
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.FORGOT_PASSWORD_DOCENTE_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject object = new JSONObject(response);
                                String mail = object.getString("mail");
                                if(correo.equals("send")){
                                    Toast.makeText(LoginDocente.this,"Correo no enviado", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(LoginDocente.this,"Correo enviado", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LoginDocente.this,"Correo ingresado no se encuentra registrado", Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> forgotparams = new HashMap<>();
                            forgotparams.put("correo",correo);
                            return forgotparams;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(LoginDocente.this);
                    requestQueue.add(stringRequest);
                }
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginDocente.this,"Cancelando", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}