package com.example.appparcialkquiel_lsaldana_crodriguez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appparcialkquiel_lsaldana_crodriguez.Adaptadores.RecetasListViewAdapter;
import com.example.appparcialkquiel_lsaldana_crodriguez.Data.ComidasDBProcess;
import com.example.appparcialkquiel_lsaldana_crodriguez.Entidades.Receta;

import java.util.List;

public class RecetaGuardadaActivity extends AppCompatActivity {
    ListView lstguardadas;
    String CorreoRecibido;
    ImageView ListaVacia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta_guardada);
        Intent i = getIntent();
        CorreoRecibido=i.getStringExtra("correoenviado");
        this.InitControles();
        this.MostrarRecetasGuardadas();
        registerForContextMenu(lstguardadas);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        Receta rec= (Receta)lstguardadas.getItemAtPosition(info.position);
        menu.setHeaderTitle(rec.getTitulo());
        getMenuInflater().inflate(R.menu.context_menu_2,menu);

        //Logica de visibilidad de opciones para el menu contextual 2
        int IDrec,IDuser;

        ComidasDBProcess dbProcess= new ComidasDBProcess(this.getApplicationContext());
        IDrec = dbProcess.ObtenerIdReceta(rec);
        IDuser = dbProcess.ObtenerIdUsuario(CorreoRecibido);

        MenuItem anadir= menu.findItem(R.id.mrecetalike);
        MenuItem eliminar = menu.findItem(R.id.mrecetadislike);

        if(dbProcess.isLikeReceta(IDrec,IDuser))
            eliminar.setVisible(true);
        else
            anadir.setVisible(true);

    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        ComidasDBProcess dbProcess= new ComidasDBProcess(this.getApplicationContext());
        Receta rec= (Receta)lstguardadas.getItemAtPosition(info.position);
        int IDrec,IDuser;
        IDrec = dbProcess.ObtenerIdReceta(rec);
        IDuser = dbProcess.ObtenerIdUsuario(CorreoRecibido);
        switch (item.getItemId()){
            case R.id.mrecetalike:
                dbProcess.LikeReceta(IDrec,IDuser,1);
                this.MostrarRecetasGuardadas();
                return true;

            case R.id.mrecetadislike:
                dbProcess.LikeReceta(IDrec,IDuser,0);
                this.MostrarRecetasGuardadas();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
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
            RecetasListViewAdapter adapter = new RecetasListViewAdapter(this.getApplicationContext(), lstrec,id_user);
            lstguardadas.setAdapter(adapter);}
            else
                ListaVacia.setVisibility(View.VISIBLE);

        }
        catch (Exception e){ Toast.makeText(this.getApplicationContext(),"Error: "+e, Toast.LENGTH_LONG).show(); }
    }

    public void Favorito(View v){
        Toast.makeText(this.getApplicationContext(),"Auch",Toast.LENGTH_LONG).show();
    }
}