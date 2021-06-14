package com.example.appparcialkquiel_lsaldana_crodriguez.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appparcialkquiel_lsaldana_crodriguez.Entidades.Receta;
import com.example.appparcialkquiel_lsaldana_crodriguez.R;

import java.util.ArrayList;
import java.util.List;

public class RecetasListViewAdapter  extends ArrayAdapter<Receta>
{

    private List<Receta> opciones = new ArrayList<>();

    public RecetasListViewAdapter(Context context, List<Receta> datos){
        super(context, R.layout.listview_layout_template, datos);

        opciones= datos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.listview_layout_template, null);

        ImageView lblfoto = item.findViewById(R.id.lblFotoRec);
        lblfoto.setImageResource(Integer.parseInt(opciones.get(position).getImagen()));

        TextView lbltitulo = item.findViewById(R.id.lblNomRec);
        lbltitulo.setText(opciones.get(position).getTitulo());

        ImageView lblicono = item.findViewById(R.id.lblicono);
        lblicono.setImageResource(Integer.parseInt(opciones.get(position).getImagen()));
        return(item);
    }
}