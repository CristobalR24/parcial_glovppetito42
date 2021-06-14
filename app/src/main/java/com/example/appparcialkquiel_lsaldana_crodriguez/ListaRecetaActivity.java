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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_receta);
        Intent i = getIntent();
        TipoRecibido=i.getStringExtra("tipoenviado2");
        CorreoRecibido=i.getStringExtra("correoenviado2");
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

        MenuItem eliminar = menu.findItem(R.id.meliminareceta);

        // a partir de aqui se controlara la visibilidad de las opciones dependiendo del usuario
        if(TipoRecibido.equals("administrador"))
            eliminar.setVisible(true);
        else
        { if(TipoRecibido.equals("consumidor"))
        { MenuItem ver = menu.findItem(R.id.mrecetacompleta);
            MenuItem guardar = menu.findItem(R.id.mguardarreceta);
            ver.setVisible(true);
            guardar.setVisible(true);
        }
        }
    }


    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.mrecetacompleta:
                return true;
            case R.id.mguardarreceta:
                return true;
            case R.id.meliminareceta:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void InitControles(){
        Recipes = findViewById(R.id.lstRecetas); }

    /*private void AgregarNuevoElemento(){
        List<Receta> opciones = this.GetElementsToListViewTemplate();
        opciones.add(new Receta(String.valueOf(R.drawable.ic_launcher_background), "Randall", "Asi es","5 s"));

        RecetasListViewAdapter adapter = new RecetasListViewAdapter(this, opciones);
        Recipes.setAdapter(adapter);
    }

    private List<Receta> GetElementsToListViewTemplate() {
        List<Receta> opciones = new ArrayList<>();

        opciones.add(new Receta(String.valueOf(R.drawable.ic_launcher_background), "Receta 1", "",""));
        opciones.add(new Receta(String.valueOf(R.drawable.ic_launcher_background), "Receta 2","",""));

        return  opciones;
    }
*/
    private void MostrarRecetas(){
        try{
        ComidasDBProcess dbProcess= new ComidasDBProcess(this.getApplicationContext());

        List<Receta> lstrec = dbProcess.ObtenerRecetas();

        RecetasListViewAdapter adapter = new RecetasListViewAdapter(this.getApplicationContext(), lstrec);
        Recipes.setAdapter(adapter);}
        catch (Exception e){
            Toast.makeText(this.getApplicationContext(),"Error: "+e, Toast.LENGTH_LONG).show();
        }

    }
}