package com.example.ricar.addwebviewmio;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class InterfaceAplicacionWeb {

    private String url;


    public void InterfaceAplicacionWeb(){
        this.url = "";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @JavascriptInterface
    public void sendData(String data){
        Log.v("ENTRODENTRO", data);
        setUrl(data);
    }
}
