package com.example.apptuprosor;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ExecutorDelivery;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;

public class RegistroCursos extends AppCompatActivity {
    EditText CTitulo, CDescripcion, CFecha, CHora, CEnlace;
    Button RegistroCurso, SelectImg;
    ImageView Imagen;
    File fileImagen;
    Bitmap bitmap;
    ProgressDialog progreso;
    private int dia, mes, anio, hora, minutos;
    private String path;
    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";//Directorio principal
    private static final String CARPETA_IMAGEN = "imagenes";
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL+CARPETA_IMAGEN;
    private static final int COD_FOTO=20;
    private static final int COD_SELECCIONA=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_cursos);
        CTitulo = (EditText) findViewById(R.id.EditT_titulo);
        CDescripcion =(EditText) findViewById(R.id.EditT_descripcion);
        CFecha = (EditText) findViewById(R.id.EditT_C_fecha);
        CHora = (EditText) findViewById(R.id.EditT_Hora);
        CEnlace = (EditText) findViewById(R.id.EditT_Enlace);
        RegistroCurso = (Button) findViewById(R.id.Btn_C_registrar);
        SelectImg = (Button) findViewById(R.id.btnImagen);
        Imagen = (ImageView) findViewById(R.id.IV_Curso);
        CFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                anio = c.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegistroCursos.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfyear, int dayOfMonth) {
                        CFecha.setText(dayOfMonth+"/"+(monthOfyear+1)+"/"+year);
                    }
                }
                        ,dia,mes,anio);
                datePickerDialog.show();
            }
        });
        CHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                hora = c.get(Calendar.HOUR_OF_DAY);
                minutos = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(RegistroCursos.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        CHora.setText(hourOfDay+":"+minute);
                    }
                }, hora, minutos,false);
                timePickerDialog.show();
            }
        });
        SelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogOpciones();
            }
        });
        RegistroCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String titulo = CTitulo.getText().toString();
                final String descripcion = CDescripcion.getText().toString();
                final String fecha = CFecha.getText().toString();
                final String hora = CHora.getText().toString();
                final String enlace = CEnlace.getText().toString();
                final String imgResource = convertirImgString(bitmap);
                Response.Listener<String> respoListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(CTitulo.getText().toString().equals("")){
                                Toast.makeText(RegistroCursos.this,"Ingresa un título", Toast.LENGTH_SHORT).show();
                            }else if(CDescripcion.getText().toString().equals("")){
                                Toast.makeText(RegistroCursos.this,"Ingresa una descripción del curso", Toast.LENGTH_SHORT).show();
                            }else if(CFecha.getText().toString().equals("")){
                                Toast.makeText(RegistroCursos.this,"Ingresa una fecha de inicio", Toast.LENGTH_SHORT).show();
                            }else if(CHora.getText().toString().equals("")){
                                Toast.makeText(RegistroCursos.this,"Ingresa una hora de inicio", Toast.LENGTH_SHORT).show();
                            }else if(CEnlace.getText().toString().equals("")){
                                Toast.makeText(RegistroCursos.this,"Ingresa un tu enlace de reunión", Toast.LENGTH_SHORT).show();
                            }else if(Imagen.toString().equals("")){
                                Toast.makeText(RegistroCursos.this,"Ingresa la imagen de tu reunión", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                if(success){
                                    Toast.makeText(RegistroCursos.this,"Registro del curso exitoso", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegistroCursos.this,Docentes.class);
                                    RegistroCursos.this.startActivity(intent);
                                }else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistroCursos.this);
                                    builder.setMessage("Error al registrarse, ingrese nuevamente los datos").setNegativeButton("Retry",null).create().show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegistroCursoRequest registroCursoRequest = new RegistroCursoRequest(titulo, descripcion, fecha, hora, enlace, imgResource, respoListener);
                RequestQueue queve = Volley.newRequestQueue(RegistroCursos.this);
                queve.add(registroCursoRequest);
            }
        });
    }
    private void mostrarDialogOpciones(){
        final CharSequence[] opciones = {"Tomar Foto","Elegir de Galeria","Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(RegistroCursos.this);
        builder.setTitle("Elegir una opcion");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(opciones[which].equals("Tomar Foto")){
                    abrirCamara();
                }else{
                    if(opciones[which].equals("Elegir de Galeria")){
                        abrirGaleria();
                    }else{
                        dialog.dismiss();
                    }
                }
            }
        });
        builder.show();
    }

    private void abrirGaleria(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"SELECCIONA"),COD_SELECCIONA);
    }

    private void abrirCamara(){
        File miFile = new File(Environment.getExternalStorageDirectory(),DIRECTORIO_IMAGEN);
        boolean isCreada = miFile.exists();
        if(isCreada==false){
            isCreada=miFile.mkdir();
        }
        if(isCreada==true){
            Long consecutivo = System.currentTimeMillis()/1000;
            String nombre = consecutivo.toString()+".jpg";
            path=Environment.getExternalStorageDirectory()+File.separator+DIRECTORIO_IMAGEN+File.separator+nombre;
            fileImagen=new File(path);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(fileImagen));
            startActivityForResult(intent,COD_FOTO);;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case COD_SELECCIONA:
                Uri miPath = data.getData();
                Imagen.setImageURI(miPath);
                try {
                    bitmap=MediaStore.Images.Media.getBitmap(RegistroCursos.this.getContentResolver(),miPath);
                    Imagen.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case COD_FOTO:
                MediaScannerConnection.scanFile(RegistroCursos.this, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("Path",""+path);
                    }
                });
                bitmap = BitmapFactory.decodeFile(path);
                Imagen.setImageBitmap(bitmap);
                break;
        }
    }

    private String convertirImgString(Bitmap bitmap) {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagenByte = array.toByteArray();
        String imagenString = Base64.encodeToString(imagenByte, Base64.DEFAULT);
        return imagenString;
    }
}

