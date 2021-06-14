package com.example.appparcialkquiel_lsaldana_crodriguez;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.appparcialkquiel_lsaldana_crodriguez.Data.ComidasDBProcess;
import com.example.appparcialkquiel_lsaldana_crodriguez.Entidades.Usuario;

public class Create_User_Activity extends AppCompatActivity {
    EditText NuevoNombre,NuevoCorreo,NuevoPass;
    RadioGroup TipoUsuario;
    RadioButton opcion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        this.InitControles();
    }

    private void InitControles(){
        NuevoNombre=findViewById(R.id.lblnombre);
        NuevoCorreo=findViewById(R.id.lblcorreo);
        NuevoPass=findViewById(R.id.lblpass);
        TipoUsuario=findViewById(R.id.lblTipoUsuario);
    }

    public void InsertarUsuario(View v){
        String i_correo, i_contrasena,i_nombre,i_Tipo;
        i_nombre=NuevoNombre.getText().toString();
        i_correo=NuevoCorreo.getText().toString();
        i_contrasena=NuevoPass.getText().toString();
        i_Tipo="";

        ComidasDBProcess dbProcess= new ComidasDBProcess(this.getApplicationContext());
        Usuario Ingresado = new Usuario(i_nombre,i_correo,i_contrasena,"");//objeto usuario temporal

        if(!TextUtils.isEmpty(i_correo) && !TextUtils.isEmpty(i_contrasena) && !TextUtils.isEmpty(i_nombre)){
            if(!dbProcess.CorreoExiste(Ingresado)){//si el correo no existe en la bd significa que esta disponible
                        switch(TipoUsuario.getCheckedRadioButtonId()){
                            case R.id.lblUserAd:i_Tipo="administrador";
                                                break;
                            case R.id.lblUserCo:i_Tipo="consumidor";
                                                break;
                            default: Toast.makeText(this.getApplicationContext(),"Debe seleccionar un tipo de usuario",Toast.LENGTH_LONG).show();
                        }
                if(i_Tipo.equals("administrador") || i_Tipo.equals("consumidor"))
                    {dbProcess.GuardarUsuario(new Usuario(i_nombre,i_correo,i_contrasena,i_Tipo));
                     Toast.makeText(this.getApplicationContext(),"Usuario agregado",Toast.LENGTH_LONG).show();}

            }
            else
                Toast.makeText(this.getApplicationContext(),"Ya existe una cuenta con este correo",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this.getApplicationContext(),"Debe llenar todos los campos",Toast.LENGTH_LONG).show();
    }


}