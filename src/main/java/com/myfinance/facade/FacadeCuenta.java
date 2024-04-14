package com.myfinance.facade;

import com.myfinance.entities.Cuenta;
import com.myfinance.persistence.CuentaBD;

import java.sql.Connection;

public class FacadeCuenta {
    private Connection conn;
    private CuentaBD cuentabd;

    public FacadeCuenta(Connection conn) {
        this.conn = conn;
        cuentabd = new CuentaBD(conn);
    }

    public Cuenta crearCuenta(String nombreUsuario, String nombreCuenta, int saldo){
        Cuenta cuenta = new Cuenta(nombreUsuario, nombreCuenta, 0);
        if(!this.cuentabd.crearCuenta(cuenta)){
            cuenta = null;
        }
        return cuenta;
    }
}
