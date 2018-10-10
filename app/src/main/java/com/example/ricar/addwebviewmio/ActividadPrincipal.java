package com.example.ricar.addwebviewmio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ActividadPrincipal extends AppCompatActivity {
    private Button btnInicia;
    private EditText etNombre, etClave;
    private CheckBox checkAuto;

    private Usuario usuario;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        init();
        iniciarUsuario();
        iniciarSesion();
    }

    private void init(){
        btnInicia = findViewById(R.id.btnInicia);
        etNombre = findViewById(R.id.etNombre);
        etClave = findViewById(R.id.etClave);
        checkAuto = findViewById(R.id.checkAuto);
        sharedPref = getPreferences(Context.MODE_PRIVATE);

    }

    private void iniciarSesion(){
        btnInicia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = String.valueOf(etNombre.getText());
                String clave = String.valueOf(etClave.getText());

                String error = validarCampos(nombre,clave);
                if(error != null) {
                    MetodosVarios.mostrarToast(ActividadPrincipal.this,error);
                }else{
                    usuario = new Usuario(nombre,clave,checkAuto.isChecked(),true);
                    guardarPreferencias(usuario);
                    iniciarWebView(usuario);
                }

            }
        });
    }

    private String validarCampos(String nombre, String clave){
        String error = null;
        if(nombre.isEmpty() && clave.isEmpty()){
            error = getString(R.string.errorCampos);
        }else if(clave.isEmpty()){
            error = getString(R.string.errorClave);
        }else if(nombre.isEmpty()){
            error = getString(R.string.errorNombre);
        }
        return error;
    }

    private void guardarPreferencias(Usuario usuario){
        MetodosVarios.escribirPreferencias(sharedPref,usuario.getNombre(),getString(R.string.nombre));
        MetodosVarios.escribirPreferencias(sharedPref,usuario.getClave(),getString(R.string.clave));
        MetodosVarios.escribirPreferencias(sharedPref,String.valueOf(usuario.getIniAuto()),getString(R.string.iniAuto));
        MetodosVarios.escribirPreferencias(sharedPref,String.valueOf(usuario.getDatosCorrectos()),getString(R.string.datosCorrectos));
    }

    private void iniciarWebView(Usuario usuario){
        Intent intent = new Intent(this, ActividadWebView.class);
        intent.putExtra("USUARIO", usuario);
        startActivityForResult(intent, -1);
    }

    private void iniciarUsuario(){
        Boolean datosCorrectos = Boolean.parseBoolean(MetodosVarios.leerPreferencias(sharedPref,getString(R.string.datosCorrectos)));
        Boolean iniAuto = Boolean.parseBoolean(MetodosVarios.leerPreferencias(sharedPref,getString(R.string.iniAuto)));
        String nombre = MetodosVarios.leerPreferencias(sharedPref,getString(R.string.nombre));
        String clave = MetodosVarios.leerPreferencias(sharedPref,getString(R.string.clave));
        if(datosCorrectos && iniAuto){
            usuario = new Usuario(nombre,clave,iniAuto,datosCorrectos);
            iniciarWebView(usuario);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.v("ALGO",data.getExtras().getString("ERROR_INICIO"));
    }
}
