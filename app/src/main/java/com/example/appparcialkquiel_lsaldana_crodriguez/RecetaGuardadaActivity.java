package com.example.appparcialkquiel_lsaldana_crodriguez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appparcialkquiel_lsaldana_crodriguez.Adaptadores.RecetasListViewAdapter;
import com.example.appparcialkquiel_lsaldana_crodriguez.Data.ComidasDBProcess;
import com.example.appparcialkquiel_lsaldana_crodriguez.Entidades.Receta;

import java.util.List;

public class RecetaGuardadaActivity extends AppCompatActivity {
    ListView lstguardadas;
    String TipoRecibido,CorreoRecibido;
    ImageView ListaVacia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta_guardada);
        Intent i = getIntent();
        TipoRecibido=i.getStringExtra("tipoenviado");
        CorreoRecibido=i.getStringExtra("correoenviado");
        this.InitControles();
        this.MostrarRecetasGuardadas();

    }

    private void InitControles(){
        lstguardadas=findViewById(R.id.lstRecetasGuardadas);
        ListaVacia=findViewById(R.id.advertencia2);
    }

    private void MostrarRecetasGuardadas(){
        try{int id_user;
            ComidasDBProcess dbProcess= new ComidasDBProcess(this.getApplicationContext());
            id_user=dbProcess.ObtenerIdUsuario(CorreoRecibido);
            List<Receta> lstrec = dbProcess.ObtenerRecetasGuardadas(id_user);
            if(!lstrec.isEmpty()){
            RecetasListViewAdapter adapter = new RecetasListViewAdapter(this.getApplicationContext(), lstrec);
            lstguardadas.setAdapter(adapter);}
            else
                ListaVacia.setVisibility(View.VISIBLE);

        }
        catch (Exception e){ Toast.makeText(this.getApplicationContext(),"Error: "+e, Toast.LENGTH_LONG).show(); }
    }
}