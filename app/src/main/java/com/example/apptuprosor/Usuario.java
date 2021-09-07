package com.example.apptuprosor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Usuario extends AppCompatActivity {
    TextView tv_nombre;
    ImageButton ibtn_cursos, ibtn_profesores, ibtn_youtube, itbn_github;
    Button btn_D_registro, btn_salir;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == event.KEYCODE_BACK){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Desea cerrar Sesion?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Usuario.this, MainActivity.class);
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
        setContentView(R.layout.activity_usuario);
        tv_nombre = (TextView) findViewById(R.id.TextV_nombres);
        Intent intent = getIntent();
        String nombres = intent.getStringExtra("nombres");

        tv_nombre.setText(nombres);

        ibtn_cursos = (ImageButton) findViewById(R.id.Ibtn_cursos);
        ibtn_profesores = (ImageButton) findViewById(R.id.Ibtn_profesores);
        ibtn_cursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCur = new Intent(Usuario.this, Cursos.class);
                Usuario.this.startActivity(intentCur);
            }
        });
        ibtn_profesores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPro = new Intent(Usuario.this, Profesores.class);
                Usuario.this.startActivity(intentPro);
            }
        });
        btn_D_registro = (Button) findViewById(R.id.Btn_Docente);
        btn_D_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCur = new Intent(Usuario.this, RegistroDocente.class);
                Usuario.this.startActivity(intentCur);
            }
        });
        btn_salir = (Button) findViewById(R.id.btn_salir);
        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Usuario.this);
                    builder.setMessage("Desea cerrar Sesion?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Usuario.this,"cerrando sesión", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Usuario.this, MainActivity.class);
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
        });
        ibtn_youtube = (ImageButton) findViewById(R.id.ibtn_youtube);
        ibtn_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCur = new Intent(Usuario.this, Youtube.class);
                Usuario.this.startActivity(intentCur);
            }
        });
        itbn_github = (ImageButton) findViewById(R.id.ibtn_github);
        itbn_github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCur = new Intent(Usuario.this, Github.class);
                Usuario.this.startActivity(intentCur);
            }
        });
    }
}