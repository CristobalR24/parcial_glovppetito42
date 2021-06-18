package com.example.appparcialkquiel_lsaldana_crodriguez.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ComidasDBHelper extends SQLiteOpenHelper {

    String NuevaTabla1 = "CREATE TABLE Usuarios(ID_user INTEGER PRIMARY KEY AUTOINCREMENT,Nombre TEXT, Correo TEXT UNIQUE, Pass TEXT, Tipo TEXT)";
    String NuevaTabla2 = "CREATE TABLE Recetas(ID_rec INTEGER PRIMARY KEY AUTOINCREMENT,Imagen TEXT,Titulo TEXT, Ingredientes TEXT, Preparacion TEXT)";
    String NuevaTabla3 = "CREATE TABLE Recetas_Save(ID_user,ID_rec,Marcado INTEGER)";
    String PrimeraVez = "INSERT INTO Usuarios(Nombre,Correo,Pass,Tipo) VALUES('Registrador','admin','123','administrador')";

    public ComidasDBHelper(Context context, String dbnombre, SQLiteDatabase.CursorFactory cursor, int dbVersion){
        super(context,dbnombre,cursor,dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NuevaTabla1);
        db.execSQL(NuevaTabla2);
        db.execSQL(NuevaTabla3);
        db.execSQL(PrimeraVez); //inserta por primera vez un usuario administrador inicial
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
