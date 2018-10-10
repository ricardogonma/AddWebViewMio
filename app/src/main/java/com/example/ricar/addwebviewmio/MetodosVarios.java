package com.example.ricar.addwebviewmio;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class MetodosVarios {

    static String leerPreferencias(SharedPreferences sharedPref,String clave){
        return sharedPref.getString(clave, "null");
    }

    static void escribirPreferencias(SharedPreferences sharedPref, String valor, String clave){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(clave, valor);
        editor.apply();
    }

    static void mostrarToast(Context context, String texto){
        Toast toast = Toast.makeText(context, texto, Toast.LENGTH_SHORT);
        toast.show();
    }

}
