package com.myfinance.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class AlertaBD {
    private final Connection conn;

    public AlertaBD(Connection conn) {
        this.conn = conn;
    }

    public boolean asignarAlerta(int movimientoID, LocalDate fechaAlerta, String tipoNotificacion) {
        try {
            String insercion = "INSERT INTO Alertas (movimientoID, fechaAlerta, tipoNotificacion) VALUES (?, ?, ?)";
            PreparedStatement pstmt = this.conn.prepareStatement(insercion);

            pstmt.setInt(1, movimientoID);
            pstmt.setDate(2, java.sql.Date.valueOf(fechaAlerta));
            pstmt.setString(3, tipoNotificacion);

            int code = pstmt.executeUpdate();
            pstmt.close();
            return code == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

