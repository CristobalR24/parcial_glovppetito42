package com.example.appparcialkquiel_lsaldana_crodriguez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PrincipalActivity extends AppCompatActivity {
    Button b1,b2,b3,b4;
    String TipoRecibido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Intent i = getIntent();
        TipoRecibido=i.getStringExtra("tipoenviado");
        this.InitControles();
        this.ControlBotones();
    }

    private void InitControles(){
        b1=findViewById(R.id.ver_recetas_disponibles);
        b2=findViewById(R.id.ver_recetas_guardadas);
        b3=findViewById(R.id.agregar_recetas);
        b4=findViewById(R.id.anadir_usuarios);
    }

    public void ControlBotones(){
        if(TipoRecibido.equals("administrador"))
            b2.setVisibility(View.GONE);
        else if(TipoRecibido.equals("consumidor")){
            b3.setVisibility(View.GONE);
            b4.setVisibility(View.GONE);
        }
    }

    public void InsertarUsuario(View view) {
        Intent i = new Intent(this.getApplicationContext(),Create_User_Activity.class);
        startActivity(i);
    }
}