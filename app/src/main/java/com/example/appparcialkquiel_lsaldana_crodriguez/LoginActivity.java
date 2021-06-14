package com.example.appparcialkquiel_lsaldana_crodriguez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appparcialkquiel_lsaldana_crodriguez.Data.ComidasDBProcess;
import com.example.appparcialkquiel_lsaldana_crodriguez.Entidades.Usuario;

public class LoginActivity extends AppCompatActivity {
    EditText User,Contra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.InitControles();
    }

    private void InitControles(){
        User=findViewById(R.id.lblusuario);
        Contra=findViewById(R.id.lblpassword);
    }

    public void ValidarUsuario(View v){ //cambiar nombre a LoginUsuario
        try{
        String UserT,ContraT,TipoT;
        UserT=User.getText().toString();
        ContraT= Contra.getText().toString();

        ComidasDBProcess dbProcess= new ComidasDBProcess(this.getApplicationContext());
        Usuario Ingresado = new Usuario("",UserT,ContraT,"");

        if(dbProcess.ValidarUsuario(Ingresado)) {
            TipoT = dbProcess.ObtenerTipo(Ingresado);
            if (TipoT.equals("administrador")) {
                Intent i = new Intent(this.getApplicationContext(),PrincipalActivity.class);
                i.putExtra("tipoenviado",TipoT);
                startActivity(i);
            } else if (TipoT.equals("consumidor")) {
                Intent i = new Intent(this.getApplicationContext(),PrincipalActivity.class);
                i.putExtra("tipoenviado",TipoT);
                startActivity(i);
            }
        }
        else
            Toast.makeText(this.getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
        }
        catch (Exception x){
            Toast.makeText(this.getApplicationContext(), "Error:"+x, Toast.LENGTH_LONG).show();
        }
    }

}