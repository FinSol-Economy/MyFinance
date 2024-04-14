package com.myfinance.facade;

import com.myfinance.entities.Cuenta;
import com.myfinance.entities.Movimiento;
import com.myfinance.persistence.MovimientoBD;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class FacadeMovimiento {
    private Connection conn;
    private MovimientoBD movimientoBD;

    public FacadeMovimiento(Connection conn) {
        this.conn = conn;
        this.movimientoBD = new MovimientoBD(conn);
    }

    public boolean crearMovimiento(int cuentaID, String tipo, String movimientoNombre, String movimientoDesc, double monto, LocalDate movimientoFecha, Date movimientoRegistro, double saldoviejo){
        boolean creado = false;

        Movimiento movimiento = new Movimiento(cuentaID, tipo, movimientoNombre, movimientoDesc, monto, movimientoFecha, movimientoRegistro);
        creado = this.movimientoBD.crearMovimiento(movimiento);
        if (creado){
            this.movimientoBD.actualizarSaldo(cuentaID, monto, saldoviejo);
        }
        return creado;
    }

    public List<Movimiento> getMovimientos(Cuenta cuenta){
        return this.movimientoBD.getMovimientos(cuenta);
    }

}
