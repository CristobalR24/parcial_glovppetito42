package com.example.appparcialkquiel_lsaldana_crodriguez;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DescripcionRecetaActivity extends AppCompatActivity {
    TextView nombre,ingredientes,preparacion;
    String _nombre,_ingrediente,_preparacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_receta);
        this.InitControles();

        Bundle i= getIntent().getExtras();
        _nombre=i.getString("titulo");
        _ingrediente=i.getString("ingrediente");
        _preparacion=i.getString("preparacion");

        this.MostrarDatos();
    }

    void InitControles(){
        nombre=findViewById(R.id.nombre_receta2);
        ingredientes=findViewById(R.id.ingrediente_receta2);
        preparacion=findViewById(R.id.descripcion2);
    }

    void MostrarDatos(){
        nombre.setText(_nombre);
        ingredientes.setText(_ingrediente);
        preparacion.setText(_preparacion);
    }

    public void Retornar(View view) {this.finish();}
}