package com.example.appparcialkquiel_lsaldana_crodriguez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appparcialkquiel_lsaldana_crodriguez.Data.ComidasDBProcess;

public class AgregarRecetaActivity extends AppCompatActivity {

    EditText nombre_receta, ingrediente_receta, descripcion;
    Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_receta);
        this.Iniciarlizar_Controles();
    }

    private void Iniciarlizar_Controles()
    {
        nombre_receta = (EditText)findViewById(R.id.nombre_receta);
        ingrediente_receta = (EditText)findViewById(R.id.ingrediente_receta);
        descripcion = (EditText)findViewById(R.id.descripcion);
        guardar = (Button)findViewById(R.id.guardar);
    }

    public void Guardar_Datos(View view) {
        String nombre,ingrediente,preparacion;
        nombre=nombre_receta.getText().toString();
        ingrediente=ingrediente_receta.getText().toString();
        preparacion=descripcion.getText().toString();
        ComidasDBProcess db=new ComidasDBProcess(this.getApplicationContext());

        if(!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(ingrediente) && !TextUtils.isEmpty(preparacion))
        {   if(db.RecetaYaExtiste(nombre,ingrediente,preparacion))
                Toast.makeText(this.getApplicationContext(),"Esta receta ya esta en la base de datos",Toast.LENGTH_LONG).show();
            else
                {db.AgregarReceta(nombre,ingrediente,preparacion);
                 Toast.makeText(this.getApplicationContext(),"La receta fue agregada exitosamente",Toast.LENGTH_LONG).show();
                 nombre_receta.setText("");
                 ingrediente_receta.setText("");
                 descripcion.setText("");}
        }
        else
            Toast.makeText(this.getApplicationContext(),"Debe llenar todos los campos",Toast.LENGTH_LONG).show();
    }

    public void Retornar(View view) { this.finish();}
}