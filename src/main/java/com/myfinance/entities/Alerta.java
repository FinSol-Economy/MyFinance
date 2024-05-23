package com.myfinance.entities;

import java.time.LocalDate;

public class Alerta {
    private int id;
    private int movimientoID;
    private LocalDate fechaAlerta;
    private String tipoNotificacion;

    public Alerta(int id, int movimientoID, LocalDate fechaAlerta, String tipoNotificacion) {
        this.id = id;
        this.movimientoID = movimientoID;
        this.fechaAlerta = fechaAlerta;
        this.tipoNotificacion = tipoNotificacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovimientoID() {
        return movimientoID;
    }

    public void setMovimientoID(int movimientoID) {
        this.movimientoID = movimientoID;
    }

    public LocalDate getFechaAlerta() {
        return fechaAlerta;
    }

    public void setFechaAlerta(LocalDate fechaAlerta) {
        this.fechaAlerta = fechaAlerta;
    }

    public String getTipoNotificacion() {
        return tipoNotificacion;
    }

    public void setTipoNotificacion(String tipoNotificacion) {
        this.tipoNotificacion = tipoNotificacion;
    }
}

