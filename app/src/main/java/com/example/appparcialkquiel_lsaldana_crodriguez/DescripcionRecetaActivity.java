package com.example.appparcialkquiel_lsaldana_crodriguez;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;

public class DescripcionRecetaActivity extends AppCompatActivity {
    TextView nombre,ingredientes,preparacion;
    ImageView imagen;
    String _imagen, _nombre,_ingrediente,_preparacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_receta);
        this.InitControles();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Bundle i= getIntent().getExtras();
        _imagen=i.getString("imagen");
        _nombre=i.getString("titulo");
        _ingrediente=i.getString("ingrediente");
        _preparacion=i.getString("preparacion");

        this.MostrarDatos();
    }

    void InitControles(){
        imagen=findViewById(R.id.foto_receta2);
        nombre=findViewById(R.id.nombre_receta2);
        ingredientes=findViewById(R.id.ingrediente_receta2);
        preparacion=findViewById(R.id.descripcion2);
    }

    void MostrarDatos(){
        nombre.setText(_nombre);
        ingredientes.setText(_ingrediente);
        preparacion.setText(_preparacion);

        try {
            URL url = new URL(_imagen);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            imagen.setImageBitmap(bmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Retornar(View view) {this.finish();}
}