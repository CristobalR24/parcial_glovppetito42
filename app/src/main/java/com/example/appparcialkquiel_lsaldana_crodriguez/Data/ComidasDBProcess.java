package com.example.appparcialkquiel_lsaldana_crodriguez.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appparcialkquiel_lsaldana_crodriguez.Entidades.Usuario;
import com.example.appparcialkquiel_lsaldana_crodriguez.R;

public class ComidasDBProcess {
    ComidasDBHelper _db;

    public ComidasDBProcess(Context context){
        _db = new ComidasDBHelper(context,"Comidas",null, R.integer.Version);
    }

    public Boolean CorreoExiste(Usuario user){ //valida que el correo no exista ya, cada uno debe ser unico
        try{
            SQLiteDatabase db=_db.getReadableDatabase();
            if(db != null){
                String[] Campos = new String[]{"Correo"};
                String[] arg = new String[]{user.getCorreo()};
                Cursor cursor = db.query("Usuarios",Campos,"Correo=?",arg,null,null,null);
                if(cursor.moveToFirst())
                {return true;}
            }
        }
        catch(Exception x){}
        return false;
    }

    public void GuardarUsuario(Usuario user){
    try{
        SQLiteDatabase db=_db.getWritableDatabase();
        if(db!=null){
        ContentValues datos = new ContentValues();
        datos.put("Nombre",user.getNombre());
        datos.put("Correo",user.getCorreo());
        datos.put("Pass",user.getPassword());
        datos.put("Tipo",user.getTipo());
        db.insert("Usuarios",null,datos);

        db.close();
      //  return true;
        }
    }
    catch(Exception x){}
    //return false;
    }

    public Boolean ValidarUsuario(Usuario user){ //valida que el usuario y la contraseña coincidan
        try{
            SQLiteDatabase db=_db.getReadableDatabase();
            if(db != null){
                String[] Campos = new String[]{"Correo","Pass"};
                String[] arg = new String[]{user.getCorreo(),user.getPassword()};
                Cursor cursor = db.query("Usuarios",Campos,"Correo=? AND Pass=?",arg,null,null,null);
                if(cursor.moveToFirst())
                {return true;}
            }
        }
        catch(Exception x){}
        return false;
    }

    public String ObtenerTipo(Usuario user){
        String TipoRequerido=" ";
        try{
            SQLiteDatabase db=_db.getReadableDatabase();
            if(db!=null){
                String[] Campos = new String[]{"Tipo"};
                String[] arg = new String[]{user.getCorreo(),user.getPassword()};
                Cursor cursor = db.query("Usuarios",Campos,"Correo=? AND Pass=?",arg,null,null,null);
                if(cursor.moveToFirst())
                    TipoRequerido=cursor.getString(0);
                db.close();
            }
        }
        catch(Exception x){}
        return TipoRequerido;
    }


}
