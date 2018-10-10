package com.example.ricar.addwebviewmio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ActividadWebView extends AppCompatActivity {

    private final String URL = "http://www.juntadeandalucia.es/averroes/centros-tic/18700098/moodle2/login/index.php";
    private WebView webView;
    private Usuario usuario;
    private InterfaceAplicacionWeb iaw;
    private SharedPreferences sharedPref;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_web_view);
        init();
        iniciarWebView();
    }

    private void init(){
        intent = getIntent();
        webView = findViewById(R.id.webView);
        usuario = (Usuario) intent.getSerializableExtra("USUARIO");
        iaw = new InterfaceAplicacionWeb();
        sharedPref = getPreferences(Context.MODE_PRIVATE);
    }

    private void iniciarWebView(){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(URL);
        webView.addJavascriptInterface(iaw,"PUENTE_JAVA");

        webView.setWebViewClient(new WebViewClient(){
            boolean isLoaded = false;
            boolean isLogin = true;
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String strJavaScript = "PUENTE_JAVA.sendData(window.location.href);";
                if(!isLoaded) {
                    strJavaScript =
                        "var username = document.getElementById('username');" +
                        "var password = document.getElementById('password');" +
                        "var iniciar = document.getElementById('loginbtn');"+
                        "var loginerrors = document.getElementsByClassName('loginerrors');"+
                        "username.value = '" + usuario.getNombre() + "';" +
                        "password.value = '" + usuario.getClave() + "';" +
                        "iniciar.click();";
                }

                webView.loadUrl("javascript:" + strJavaScript);

                if(iaw.getUrl() == url){
                    isLogin = false;
                    usuario.setDatosCorrectos(false);
                    MetodosVarios.escribirPreferencias(sharedPref,String.valueOf(usuario.getDatosCorrectos()),getString(R.string.datosCorrectos));

                    MetodosVarios.mostrarToast(ActividadWebView.this,"Error en los datos de inicio");
                }else if(iaw.getUrl() == "salir"){
                    Log.v("ENTRODENTRO","ENTRO");
                    usuario.setIniAuto(false);
                    MetodosVarios.escribirPreferencias(sharedPref,String.valueOf(usuario.getIniAuto()),getString(R.string.iniAuto));
                }else{
                    Log.v("ENTRODENTRO","ENTRO");
                    strJavaScript =
                        "var salir = document.querySelector('.logininfo a')[1];"+
                        "salir.addEventListener('click',function(e){" +
                            "e.preventDefault();"+
                            "PUENTE_JAVA.sendData('salir');" +
                        "});";
                    webView.loadUrl("javascript:" + strJavaScript);
                }
                isLoaded = true;
            }
        });
    }


}
