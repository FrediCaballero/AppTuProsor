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
import android.widget.Toast;

public class Docentes extends AppCompatActivity {
    ImageButton RegistroCurso, VerCursos, IbtnYoutube, IbtnGithub, IbtnFacebook;
    Button DcerrarSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docentes);
        RegistroCurso = (ImageButton) findViewById(R.id.Ibtn_RCursos);
        DcerrarSesion = (Button) findViewById(R.id.btn_DCerrar);
        RegistroCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReg = new Intent(Docentes.this, RegistroCursos.class);
                Docentes.this.startActivity(intentReg);
            }
        });
        DcerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Docentes.this);
                builder.setMessage("Desea cerrar Sesion?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Docentes.this,"Cerrando sesión", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Docentes.this, LoginDocente.class);
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
        IbtnYoutube = (ImageButton) findViewById(R.id.Ibtn_youtube);
        IbtnYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCur = new Intent(Docentes.this, Youtube.class);
                Docentes.this.startActivity(intentCur);
            }
        });
        IbtnFacebook = (ImageButton) findViewById(R.id.Ibtn_facebook);
        IbtnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCur = new Intent(Docentes.this, Facebook.class);
                Docentes.this.startActivity(intentCur);
            }
        });
        IbtnGithub = (ImageButton) findViewById(R.id.Ibtn_github);
        IbtnGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCur = new Intent(Docentes.this, Github.class);
                Docentes.this.startActivity(intentCur);
            }
        });
        VerCursos  = (ImageButton) findViewById(R.id.Ibtn_VerCursos);
        VerCursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCur = new Intent(Docentes.this, Cursos.class);
                Docentes.this.startActivity(intentCur);
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == event.KEYCODE_BACK){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Desea cerrar Sesion?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Docentes.this, LoginDocente.class);
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
}