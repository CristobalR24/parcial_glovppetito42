package com.example.appparcialkquiel_lsaldana_crodriguez.Data;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.appparcialkquiel_lsaldana_crodriguez.Entidades.Receta;
import com.example.appparcialkquiel_lsaldana_crodriguez.Entidades.Usuario;
import com.example.appparcialkquiel_lsaldana_crodriguez.R;

import java.util.ArrayList;
import java.util.List;

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
        }
    }
    catch(Exception x){}
    }

    public Boolean LoginUsuario(Usuario user){ //valida que el usuario y la contraseña coincidan
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

    public String ObtenerNombre(Usuario user){
        String NombreRequerido=" ";
        try{
            SQLiteDatabase db=_db.getReadableDatabase();
            if(db!=null){
                String[] Campos = new String[]{"Nombre"};
                String[] arg = new String[]{user.getCorreo()};
                Cursor cursor = db.query("Usuarios",Campos,"Correo=?",arg,null,null,null);
                if(cursor.moveToFirst())
                    NombreRequerido=cursor.getString(0);
                db.close();
            }
        }
        catch(Exception x){}
        return NombreRequerido;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<Receta> ObtenerRecetas(){
        SQLiteDatabase db=_db.getReadableDatabase();
        try{List<Receta> lstrec = new ArrayList<Receta>();
            if(db!=null){
                String[] Campos = new String[]{"Imagen","Titulo","Ingredientes","Preparacion"};
                Cursor cursor = db.query("Recetas",Campos,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        Receta receta = new Receta(
                                cursor.getString(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3)
                        );
                        lstrec.add(receta);
                    }while(cursor.moveToNext());
                }
                return lstrec;
            }
        }
        catch(Exception x){}
        finally { db.close();}
        return null;
    }

    public void BorrarReceta(int RecetaID){
        SQLiteDatabase db=_db.getWritableDatabase();
        try{
            if(db!=null){
                db.delete("Recetas","ID_rec="+RecetaID,null);
                db.delete("Recetas_Save","ID_rec="+RecetaID,null);
            }
        }
        catch(Exception x){}
        finally {db.close();}
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public int ObtenerIdReceta(Receta rec){
        int IdRequerido=0;
        SQLiteDatabase db=_db.getReadableDatabase();
        try{
            if(db!=null){
                String[] Campos = new String[]{"ID_rec"};
                String[] arg = new String[]{rec.getTitulo(), rec.getIngredientes(), rec.getPreparacion()};
                Cursor cursor = db.query("Recetas",Campos,"Titulo=? AND Ingredientes=? AND Preparacion=?",arg,null,null,null);
                if(cursor.moveToFirst())
                    IdRequerido=cursor.getInt(0);
                return IdRequerido;
            }
        }
        catch(Exception x){}
        finally {db.close();}
        return IdRequerido;
    }

    public int ObtenerIdUsuario(String correo){
        int IdUser=0;
        SQLiteDatabase db=_db.getReadableDatabase();
        try{
            if(db!=null){
                String[] Campos = new String[]{"ID_user"};
                String[] arg = new String[]{correo};
                Cursor cursor = db.query("Usuarios",Campos,"Correo=?",arg,null,null,null);
                if(cursor.moveToFirst())
                    IdUser=cursor.getInt(0);

                db.close();
            }
        }
        catch(Exception x){}
        finally {db.close();}
        return IdUser;
    }

    public Boolean RecetaYaGuardada(int IDreceta,int IDuser){ //valida que el correo no exista ya, cada uno debe ser unico
        SQLiteDatabase db=_db.getReadableDatabase();
        try{
            if(db != null){
                String[] Campos = new String[]{"ID_rec"};
                Cursor cursor = db.query("Recetas_Save",Campos,"ID_rec="+IDreceta+" AND ID_user="+IDuser,null,null,null,null);
                if(cursor.moveToFirst())
                {return true;}
            }
        }
        catch(Exception x){throw x;}
        finally { db.close();}
        return false;
    }

    public boolean GuardarReceta(int IdReceta, int IdUser){
        SQLiteDatabase db=_db.getWritableDatabase();
        try{
            if(db!=null){
                 ContentValues datos = new ContentValues();
                 datos.put("ID_user",IdUser);
                 datos.put("ID_rec",IdReceta);
                 datos.put("Marcado",0);
                 db.insert("Recetas_Save",null,datos);
                return true;
            }
        }
        catch(Exception x){}
        finally {db.close();}
        return false;
    }

    public List<Receta> ObtenerRecetasGuardadas(int iduser){
        try{List<Receta> lstrec = new ArrayList<Receta>();
            SQLiteDatabase db=_db.getReadableDatabase();
            if(db!=null){
                String[] Campos = new String[]{"Imagen","Titulo","Ingredientes","Preparacion"};
                //aqui implementamos una forma primitiva de query para simular un join, pues de otro modo usariamos rawQuery
                Cursor cursor = db.query("Recetas_Save,Recetas",Campos,"Recetas_Save.ID_rec=Recetas.ID_rec AND ID_user="+iduser,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        Receta receta = new Receta(
                                cursor.getString(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3)
                        );
                        lstrec.add(receta);
                    }while(cursor.moveToNext());
                }
             return lstrec;
            }
        }
        catch(Exception x){}
        finally { _db.close();}
        return null;
    }

    public void LikeReceta(int IdReceta, int IdUser,int marcado){
        SQLiteDatabase db=_db.getWritableDatabase();
        try{
            if(db!=null){
                ContentValues datos = new ContentValues();
                datos.put("Marcado",marcado);
                db.update("Recetas_Save",datos,"ID_user="+IdUser+" AND ID_rec="+IdReceta,null);
            }
        }
        catch(Exception x){}
        finally {db.close();}
    }

    public boolean isRecetaLiked(int IdReceta,int IdUser){
        SQLiteDatabase db=_db.getReadableDatabase();
        try{
            if(db!=null){
                String[] Campos = new String[]{"ID_rec"};
                Cursor cursor = db.query("Recetas_Save",Campos,"ID_rec="+IdReceta+" AND ID_user="+IdUser+" AND Marcado=1",null,null,null,null);
                if(cursor.moveToFirst())
                {return true;}
            }
        }
        catch(Exception x){}
        finally {db.close();}
        return false;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public void AgregarReceta(String foto, String nombre, String ingrediente,String preparacion) {
        SQLiteDatabase sq = _db.getWritableDatabase();
        if (sq != null){
            try{
                ContentValues valores = new ContentValues();
                valores.put("Imagen", foto);
                valores.put("Titulo",nombre);
                valores.put("Ingredientes",ingrediente);
                valores.put("Preparacion",preparacion);

                sq.insert("Recetas",null,valores);

            } catch (Exception e){ }
            finally {sq.close();}
        }

    }

    public boolean RecetaYaExtiste(String nombre, String ingrediente, String preparacion){
        SQLiteDatabase sq = _db.getReadableDatabase();
        try{
        if(sq!=null){
            String[] Campos=new String[]{"ID_rec"};
            String[] arg= new String[]{nombre,ingrediente,preparacion};
            Cursor cursor=sq.query("Recetas",Campos,"Titulo=? AND Ingredientes=? AND Preparacion=?",arg,null,null,null);
            if(cursor.moveToFirst()){
                return true;
            }
        }
        }
        catch(Exception e){

        }
        finally {sq.close();}
        return false;
    }

}
