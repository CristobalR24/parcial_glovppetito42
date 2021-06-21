package com.example.appparcialkquiel_lsaldana_crodriguez.Adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appparcialkquiel_lsaldana_crodriguez.Data.ComidasDBProcess;
import com.example.appparcialkquiel_lsaldana_crodriguez.Entidades.Receta;
import com.example.appparcialkquiel_lsaldana_crodriguez.R;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecetasListViewAdapter  extends ArrayAdapter<Receta>
{
    int id_user;
    private List<Receta> recetas = new ArrayList<>();

    public RecetasListViewAdapter(Context context, List<Receta> datos,int id_user){
        super(context, R.layout.listview_layout_template, datos);
        this.id_user=id_user;
        recetas= datos;
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        int id_rec;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.listview_layout_template, null);

        ImageView lblfoto = item.findViewById(R.id.lblFotoRec); //imagen de la receta

        TextView lbltitulo = item.findViewById(R.id.lblNomRec);
        lbltitulo.setText(recetas.get(position).getTitulo());

        ImageView lblicono = item.findViewById(R.id.lblicono); //icono de fav

        try {
            URL url = new URL(recetas.get(position).getImagen());
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            lblfoto.setImageBitmap(bmp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ComidasDBProcess dbProcess=new ComidasDBProcess(this.getContext());

        Receta rec= getItem(position);
        id_rec=dbProcess.ObtenerIdReceta(rec);
        if(dbProcess.isRecetaLiked(id_rec,id_user))
            lblicono.setVisibility(View.VISIBLE);

        return(item);
    }


}