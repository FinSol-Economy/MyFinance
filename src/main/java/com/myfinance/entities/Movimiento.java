package com.myfinance.entities;

import java.util.Date;

public class Movimiento {

    private int cuentaID;
    private int movimientoID;
    private String tipo;
    private String movimientoNombre;
    private String movimientoDesc;
    private int monto;
    private Date movimientoFecha;
    private Date movimientoRegistro;
    private Date fechaPlazo;
    private boolean plazo;
    private String destino;
    private String origen;

    public Movimiento(int cuentaID, int movimientoID, String tipo, String movimientoNombre, String movimientoDesc, int monto, Date movimientoFecha, Date movimientoRegistro) {
        this.cuentaID = cuentaID;
        this.movimientoID = movimientoID;
        this.tipo = tipo;
        this.movimientoNombre = movimientoNombre;
        this.movimientoDesc = movimientoDesc;
        this.monto = monto;
        this.movimientoFecha = movimientoFecha;
        this.movimientoRegistro = movimientoRegistro;
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

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public Date getMovimientoFecha() {
        return movimientoFecha;
    }

    public void setMovimientoFecha(Date movimientoFecha) {
        this.movimientoFecha = movimientoFecha;
    }

    public Date getMovimientoRegistro() {
        return movimientoRegistro;
    }

    public void setMovimientoRegistro(Date movimientoRegistro) {
        this.movimientoRegistro = movimientoRegistro;
    }
}
