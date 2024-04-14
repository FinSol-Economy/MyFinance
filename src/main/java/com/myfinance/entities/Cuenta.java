package com.myfinance.entities;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Cuenta {

    private int cuentaID;
    private String nombreUsuario;
    private String nombreCuenta;
    private double saldo;

    public Cuenta(int cuentaID, String nombreUsuario, String nombreCuenta, double saldo) {
        this.cuentaID = cuentaID;
        this.nombreUsuario = nombreUsuario;
        this.nombreCuenta = nombreCuenta;
        this.saldo = saldo;
    }

    public Cuenta(String nombreUsuario, String nombreCuenta, double saldo) {
        this.nombreUsuario = nombreUsuario;
        this.nombreCuenta = nombreCuenta;
        this.saldo = saldo;
    }

    public int getCuentaID() {
        return cuentaID;
    }

    public void setCuentaID(int cuentaID) {
        this.cuentaID = cuentaID;
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

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public SimpleStringProperty getPropertyNombreCuenta(){
        return new SimpleStringProperty(this.nombreCuenta);
    }

    public SimpleDoubleProperty getPropertySaldo(){
        return new SimpleDoubleProperty(this.saldo);
    }
}
