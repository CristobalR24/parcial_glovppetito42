package com.example.appparcialkquiel_lsaldana_crodriguez;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appparcialkquiel_lsaldana_crodriguez.Data.ComidasDBProcess;
import com.example.appparcialkquiel_lsaldana_crodriguez.Entidades.Usuario;

public class PrincipalActivity extends AppCompatActivity {
    Button b1,b2,b3,b4;
    TextView mensaje,mensaje2;
    String TipoRecibido,CorreoRecibido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Intent i = getIntent();
        TipoRecibido=i.getStringExtra("tipoenviado");
        CorreoRecibido=i.getStringExtra("correoenviado");
        this.InitControles();
        this.ControlBotones();
        this.MensajeBienvenida();
    }

    @Override
    public void onBackPressed() { /*no hacer nada*/}

    private void InitControles(){
        b1=findViewById(R.id.ver_recetas_disponibles);
        b2=findViewById(R.id.ver_recetas_guardadas);
        b3=findViewById(R.id.agregar_recetas);
        b4=findViewById(R.id.anadir_usuarios);
        mensaje=findViewById(R.id.mensajebienvenida);
        mensaje2=findViewById(R.id.mensajenombreuser);
    }

    public void ControlBotones(){
        if(TipoRecibido.equals("administrador"))
            b2.setVisibility(View.GONE);

        else if(TipoRecibido.equals("consumidor")){
            b3.setVisibility(View.GONE);
            b4.setVisibility(View.GONE);
        }
    }

   // @SuppressLint("SetTextI18n")
    public void MensajeBienvenida(){
        String nom;
        ComidasDBProcess dbProcess= new ComidasDBProcess(this.getApplicationContext());
        Usuario Ingresado = new Usuario("",CorreoRecibido,"",TipoRecibido);
        nom=dbProcess.ObtenerNombre(Ingresado);
        mensaje.setText("Bienvenido "+TipoRecibido+": ");
        mensaje2.setText(nom);
    }

    public void InsertarUsuario(View view) {
        Intent i = new Intent(this.getApplicationContext(),Create_User_Activity.class);
        startActivity(i);
    }

    public void VerRecetas(View view) {
        Intent i = new Intent(this.getApplicationContext(),ListaRecetaActivity.class);
        i.putExtra("correoenviado",CorreoRecibido);
        i.putExtra("tipoenviado",TipoRecibido);
        startActivity(i);
    }

    public void VerGuardadas(View view) {
        Intent i = new Intent(this.getApplicationContext(),RecetaGuardadaActivity.class);
        i.putExtra("correoenviado",CorreoRecibido);
        //i.putExtra("tipoenviado",TipoRecibido);
        startActivity(i);
    }

    public void Salir(View view) {
        this.finish();
    }
}