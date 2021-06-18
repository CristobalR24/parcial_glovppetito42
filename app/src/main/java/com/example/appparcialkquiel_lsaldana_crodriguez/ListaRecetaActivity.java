package com.example.appparcialkquiel_lsaldana_crodriguez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appparcialkquiel_lsaldana_crodriguez.Adaptadores.RecetasListViewAdapter;
import com.example.appparcialkquiel_lsaldana_crodriguez.Data.ComidasDBProcess;
import com.example.appparcialkquiel_lsaldana_crodriguez.Entidades.Receta;

import java.util.ArrayList;
import java.util.List;

public class ListaRecetaActivity extends AppCompatActivity {
    ListView Recipes;
    String TipoRecibido,CorreoRecibido;
    ImageView ListaVacia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_receta);
        Intent i = getIntent();
        TipoRecibido=i.getStringExtra("tipoenviado");
        CorreoRecibido=i.getStringExtra("correoenviado");
        this.InitControles();
        this.MostrarRecetas();
        registerForContextMenu(Recipes);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        Receta rec= (Receta)Recipes.getItemAtPosition(info.position);
        menu.setHeaderTitle(rec.getTitulo());
        getMenuInflater().inflate(R.menu.context_menu_1,menu);

        // a partir de aqui se controlara la visibilidad de las opciones dependiendo del usuario
        MenuItem eliminar = menu.findItem(R.id.meliminareceta);

        if(TipoRecibido.equals("administrador"))
            eliminar.setVisible(true);
        else
        { if(TipoRecibido.equals("consumidor"))
           {MenuItem ver = menu.findItem(R.id.mrecetacompleta);
            MenuItem guardar = menu.findItem(R.id.mguardarreceta);
            ver.setVisible(true);
            guardar.setVisible(true); }
        }
    }


    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        ComidasDBProcess dbProcess= new ComidasDBProcess(this.getApplicationContext());
        Receta rec= (Receta)Recipes.getItemAtPosition(info.position);
        int IDrec,IDuser;
        switch (item.getItemId()){
            case R.id.mrecetacompleta:
                Intent i = new Intent(this.getApplicationContext(),DescripcionRecetaActivity.class);
                Bundle b=new Bundle();
                b.putString("titulo",rec.getTitulo());
                b.putString("ingrediente",rec.getIngredientes());
                b.putString("preparacion",rec.getPreparacion());
                i.putExtras(b);
                startActivity(i);
                return true;
            case R.id.mguardarreceta:
                        IDrec = dbProcess.ObtenerIdReceta(rec);
                        IDuser = dbProcess.ObtenerIdUsuario(CorreoRecibido);
                        if(dbProcess.RecetaYaGuardada(IDrec,IDuser))
                            Toast.makeText(this.getApplicationContext(),"Ya guardaste esta receta",Toast.LENGTH_LONG).show();
                        else if(dbProcess.GuardarReceta(IDrec,IDuser))
                            Toast.makeText(this.getApplicationContext(),"Receta guardada",Toast.LENGTH_LONG).show();
                return true;
            case R.id.meliminareceta:
                        IDrec = dbProcess.ObtenerIdReceta(rec);
                        dbProcess.BorrarReceta(IDrec);
                        Toast.makeText(this.getApplicationContext(),"Receta eliminada",Toast.LENGTH_LONG).show();
                        this.MostrarRecetas();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void InitControles(){
        Recipes = findViewById(R.id.lstRecetas);
        ListaVacia=findViewById(R.id.advertencia);}


    private void MostrarRecetas(){
        try{
        ComidasDBProcess dbProcess= new ComidasDBProcess(this.getApplicationContext());

        List<Receta> lstrec = dbProcess.ObtenerRecetas();

        if(!lstrec.isEmpty()){
        RecetasListViewAdapter adapter = new RecetasListViewAdapter(this.getApplicationContext(), lstrec,-1);
        Recipes.setAdapter(adapter);}
        else
            ListaVacia.setVisibility(View.VISIBLE);
        }
        catch (Exception e){
            Toast.makeText(this.getApplicationContext(),"Error: "+e, Toast.LENGTH_LONG).show();
        }
    }

    public void Retornar(View view) {this.finish();}
}