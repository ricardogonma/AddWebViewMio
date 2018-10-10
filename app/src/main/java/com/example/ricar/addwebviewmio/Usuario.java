package com.example.ricar.addwebviewmio;

import java.io.Serializable;

public class Usuario implements Serializable {

    String nombre, clave;
    Boolean iniAuto, datosCorrectos;

    public Usuario(String nombre, String clave, Boolean iniAuto, Boolean datosCorrectos) {
        this.nombre = nombre;
        this.clave = clave;
        this.iniAuto = iniAuto;
        this.datosCorrectos = datosCorrectos;
    }

    public Usuario(String nombre, String clave, Boolean iniAuto) {
        this.nombre = nombre;
        this.clave = clave;
        this.iniAuto = iniAuto;
        this.datosCorrectos = false;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Boolean getIniAuto() {
        return iniAuto;
    }

    public void setIniAuto(Boolean iniAuto) {
        this.iniAuto = iniAuto;
    }

    public Boolean getDatosCorrectos() {
        return datosCorrectos;
    }

    public void setDatosCorrectos(Boolean datosCorrectos) {
        this.datosCorrectos = datosCorrectos;
    }
}
