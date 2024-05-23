package com.myfinance.entities;

import java.time.LocalDate;

public class Meta {
    private int metaID;
    private int cuentaID;
    private String nombre;
    private double montoObjetivo;
    private LocalDate fechaLimite;

    public Meta(int metaID, int cuentaID, String nombre, double montoObjetivo, LocalDate fechaLimite) {
        this.metaID = metaID;
        this.cuentaID = cuentaID;
        this.nombre = nombre;
        this.montoObjetivo = montoObjetivo;
        this.fechaLimite = fechaLimite;
    }

    public int getMetaID() {
        return metaID;
    }

    public void setMetaID(int metaID) {
        this.metaID = metaID;
    }

    public int getCuentaID() {
        return cuentaID;
    }

    public void setCuentaID(int cuentaID) {
        this.cuentaID = cuentaID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getMontoObjetivo() {
        return montoObjetivo;
    }

    public void setMontoObjetivo(double montoObjetivo) {
        this.montoObjetivo = montoObjetivo;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }
}

