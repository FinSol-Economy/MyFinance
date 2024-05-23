package com.myfinance.entities;

import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.SimpleTimeZone;

public class Movimiento {

    private int cuentaID;
    private int movimientoID;
    private String tipo;
    private String movimientoNombre;
    private String movimientoDesc;
    private double monto;
    private LocalDate movimientoFecha;
    private Date movimientoRegistro;
    private Date fechaPlazo;
    private boolean plazo;
    private String destino;
    private String origen;
    private int grupoID;

    public Movimiento(int cuentaID, int movimientoID, String tipo, String movimientoNombre, String movimientoDesc, double monto, LocalDate movimientoFecha, Date movimientoRegistro) {
        this.cuentaID = cuentaID;
        this.movimientoID = movimientoID;
        this.tipo = tipo;
        this.movimientoNombre = movimientoNombre;
        this.movimientoDesc = movimientoDesc;
        this.monto = monto;
        this.movimientoFecha = movimientoFecha;
        this.movimientoRegistro = movimientoRegistro;
    }

    public Movimiento(int cuentaID, String tipo, String movimientoNombre, String movimientoDesc, double monto, LocalDate movimientoFecha, Date movimientoRegistro) {
        this.cuentaID = cuentaID;
        this.tipo = tipo;
        this.movimientoNombre = movimientoNombre;
        this.movimientoDesc = movimientoDesc;
        this.monto = monto;
        this.movimientoFecha = movimientoFecha;
        this.movimientoRegistro = movimientoRegistro;
    }
    public Movimiento(int cuentaID, int movimientoID, String tipo, String movimientoNombre, String movimientoDesc, double monto, LocalDate movimientoFecha, Date movimientoRegistro, int grupoID) {
        this.cuentaID = cuentaID;
        this.movimientoID = movimientoID;
        this.tipo = tipo;
        this.movimientoNombre = movimientoNombre;
        this.movimientoDesc = movimientoDesc;
        this.monto = monto;
        this.movimientoFecha = movimientoFecha;
        this.movimientoRegistro = movimientoRegistro;
        this.grupoID = grupoID;
    }

    public Movimiento(int cuentaID, String tipo, String movimientoNombre, String movimientoDesc, double monto, LocalDate movimientoFecha, Date movimientoRegistro, int grupoID) {
        this.cuentaID = cuentaID;
        this.tipo = tipo;
        this.movimientoNombre = movimientoNombre;
        this.movimientoDesc = movimientoDesc;
        this.monto = monto;
        this.movimientoFecha = movimientoFecha;
        this.movimientoRegistro = movimientoRegistro;
        this.grupoID = grupoID;
    }

    public int getMovimientoID() {
        return movimientoID;
    }

    public void setMovimientoID(int movimientoID) {
        this.movimientoID = movimientoID;
    }

    public String getMovimientoNombre() {
        return movimientoNombre;
    }

    public void setMovimientoNombre(String movimientoNombre) {
        this.movimientoNombre = movimientoNombre;
    }

    public String getMovimientoDesc() {
        return movimientoDesc;
    }

    public void setMovimientoDesc(String movimientoDesc) {
        this.movimientoDesc = movimientoDesc;
    }

    public int getCuentaID() {
        return cuentaID;
    }

    public void setCuentaID(int cuentaID) {
        this.cuentaID = cuentaID;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDate getMovimientoFecha() {
        return movimientoFecha;
    }

    public void setMovimientoFecha(LocalDate movimientoFecha) {
        this.movimientoFecha = movimientoFecha;
    }

    public Date getMovimientoRegistro() {
        return movimientoRegistro;
    }

    public void setMovimientoRegistro(Date movimientoRegistro) {
        this.movimientoRegistro = movimientoRegistro;
    }

}
