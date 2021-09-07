package com.example.apptuprosor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
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

public class MainActivity extends AppCompatActivity {
    TextView tv_registrar, tv_restablecer;
    Button btn_log, btn_D_log;
    EditText et_correo, et_password;
    private static final String FORGOT_PASSWORD_URL = "http://192.168.232.136/forgot.php";

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == event.KEYCODE_BACK){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Desea salir?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_correo = (EditText) findViewById(R.id.EditT_Lcorreo);
        et_password = (EditText) findViewById(R.id.EditT_Lpassword);
        tv_registrar =(TextView) findViewById(R.id.tv_registrar);
        tv_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReg = new Intent(MainActivity.this, Registro.class);
                MainActivity.this.startActivity(intentReg);
            }
        });
        tv_restablecer = (TextView) findViewById(R.id.tv_restablecer);
        tv_restablecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserForgotPasswordWithEmail();
            }
        });
        btn_log = (Button) findViewById(R.id.Btn_iniciar);
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
                                Toast.makeText(MainActivity.this,"Iniciando sesión Usuario", Toast.LENGTH_SHORT).show();
                                String nombres = jsonResponse.getString("nombres");
                                String nacimiento = jsonResponse.getString("nacimiento");

                                Intent intent = new Intent(MainActivity.this,Usuario.class);
                                intent.putExtra("nombres",nombres);
                                intent.putExtra("nacimiento",nacimiento);
                                intent.putExtra("correo",correo);

                                MainActivity.this.startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("Correo o contraseña incorrectas").setNegativeButton("Retry",null).create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(correo, password, responseListener);
                RequestQueue queve = Volley.newRequestQueue(MainActivity.this);
                queve.add(loginRequest);
            }
        });
        btn_D_log = (Button) findViewById(R.id.D_iniciar);
        btn_D_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReg = new Intent(MainActivity.this, LoginDocente.class);
                MainActivity.this.startActivity(intentReg);
            }
        });
    }
    private void UserForgotPasswordWithEmail() {
        View forgot_password_layout = LayoutInflater.from(this).inflate(R.layout.activity_forgot_password, null);
        final EditText forgot_email = forgot_password_layout.findViewById(R.id.forgot_email);
        Button btnForgotPass = forgot_password_layout.findViewById(R.id.forgot_password_button);
        Button btnCancelar = forgot_password_layout.findViewById(R.id.forgot_cancelar);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Restablecer Contraseña");
        builder.setView(forgot_password_layout);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String correo = forgot_email.getText().toString().trim();
                if(correo.isEmpty()){
                    Toast.makeText(MainActivity.this,"Ingrese su correo", Toast.LENGTH_SHORT).show();
                }else{
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.FORGOT_PASSWORD_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject object = new JSONObject(response);
                                String mail = object.getString("mail");
                                if(correo.equals("send")){
                                    Toast.makeText(MainActivity.this,"Correo no enviado", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(MainActivity.this,"Correo enviado", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this,"El correo ingresado no se encuentra registrado", Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> forgotparams = new HashMap<>();
                            forgotparams.put("correo",correo);
                            return forgotparams;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                    requestQueue.add(stringRequest);
                }
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Cancelando", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}