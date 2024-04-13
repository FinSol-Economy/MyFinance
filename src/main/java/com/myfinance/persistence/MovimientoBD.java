package com.myfinance.persistence;

import com.myfinance.entities.Cuenta;
import com.myfinance.entities.Movimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovimientoBD {

    private final Connection conn;

    public MovimientoBD(Connection conn) {
        this.conn = conn;
    }

    public boolean crearMovimiento(Movimiento movimiento){
        boolean creado = false;
        try{
            String insercion = "INSERT INTO Movimientos (cuentaID, movimientoNombre, movimientoDesc, movimientoTipo, movimientoMonto, movimientoFecha, movimientoFechaRegistro) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = this.conn.prepareStatement(insercion);

            prepareCrearMovimiento(movimiento, pstmt);

            int code = pstmt.executeUpdate();
            pstmt.close();
            if (code == 1) {
                System.out.println("Se registro el movimiento correctamente");
                creado = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return creado;
    }

    private void prepareCrearMovimiento(Movimiento movimiento, PreparedStatement pstmt) throws SQLException {

        pstmt.setInt(1, movimiento.getCuentaID());//ID cuenta

        pstmt.setString(2, movimiento.getMovimientoNombre());//Movimiento nombre

        pstmt.setString(3, movimiento.getMovimientoDesc());//Movimiento descripcion

        pstmt.setString(4, movimiento.getTipo());//Movimiento tipo

        pstmt.setInt(5, movimiento.getMonto());//Movimiento monto
        java.sql.Timestamp sqlMovimientoFecha = new java.sql.Timestamp(movimiento.getMovimientoFecha().getTime());

        pstmt.setTimestamp(6, sqlMovimientoFecha);//Fecha del movimiento
        java.sql.Timestamp sqlMovimientoRegistro = new java.sql.Timestamp(movimiento.getMovimientoFecha().getTime());

        pstmt.setTimestamp(7, sqlMovimientoRegistro);//Fecha del registro del movimiento

    }

    public List<Movimiento> getMovimientos(Cuenta cuenta){
        List<Movimiento> movimientos = new ArrayList<>();
        try  {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Movimientos WHERE cuentaID = '" + cuenta.getCuentaID()+ "'");
            Movimiento movimiento;
            int ID = cuenta.getCuentaID();
            while (rs.next()) {
                movimiento = getMovimiento(ID, rs);
                movimientos.add(movimiento);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return movimientos;
    }
    private Movimiento getMovimiento(int ID, ResultSet rs) throws SQLException {
        int movimientoID = rs.getInt("movimientoID");
        String tipo = rs.getString("movimientoTipo");
        String movimientoNombre = rs.getString("movimientoNombre");
        String movimientoDesc = rs.getString("movimientoDesc");
        int monto = rs.getInt("movimientoMonto");
        java.util.Date movimientoFecha = rs.getTimestamp("movimientoFecha");
        Date movimientoRegistro = rs.getTimestamp("movimientoFechaRegistro");

        return new Movimiento(ID, movimientoID, tipo, movimientoNombre, movimientoDesc, monto, movimientoFecha, movimientoRegistro);
    }
}


