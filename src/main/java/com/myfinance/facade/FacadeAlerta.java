package com.myfinance.facade;

import com.myfinance.persistence.AlertaBD;

import java.sql.Connection;
import java.time.LocalDate;

public class FacadeAlerta {
    private Connection conn;
    private AlertaBD alertaBD;

    public FacadeAlerta(Connection conn) {
        this.conn = conn;
        this.alertaBD = new AlertaBD(conn);
    }

    public boolean asignarAlerta(int movimientoID, LocalDate fechaAlerta, String tipoNotificacion) {
        return alertaBD.asignarAlerta(movimientoID, fechaAlerta, tipoNotificacion);
    }
}

