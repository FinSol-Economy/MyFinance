package com.myfinance.entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Cuenta {
    private String nombreUsuario;
    private String nombreCuenta;
    private int saldo;

    public Cuenta(String nombreUsuario, String nombreCuenta, int saldo) {
        this.nombreUsuario = nombreUsuario;
        this.nombreCuenta = nombreCuenta;
        this.saldo = saldo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public SimpleStringProperty getPropertyNombreCuenta(){
        return new SimpleStringProperty(this.nombreCuenta);
    }

    public SimpleIntegerProperty getPropertySaldo(){
        return new SimpleIntegerProperty(this.saldo);
    }
}
