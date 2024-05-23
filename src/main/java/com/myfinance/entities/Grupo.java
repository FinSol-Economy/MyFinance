package com.myfinance.entities;

import java.util.ArrayList;
import java.util.List;

public class Grupo {
    private int grupoId;
    private String nombreGrupo;
    private double balance;

    public Grupo(int grupoId, String nombreGrupo, double balance) {
        this.grupoId = grupoId;
        this.nombreGrupo = nombreGrupo;
        this.balance = balance;
    }

    public Grupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
        this.balance = 0;
        this.grupoId = 0;
    }

    public int getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(int grupoId) {
        this.grupoId = grupoId;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "grupoId: " + grupoId +
                "\nnombreGrupo: " + nombreGrupo +
                "\nbalance: " + balance;
    }
}

