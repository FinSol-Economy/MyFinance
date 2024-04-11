package com.myfinance.persistence;

import com.myfinance.entities.Cuenta;

import java.sql.*;

public class CuentaBD {
    private final Connection conn;

    public CuentaBD(Connection conn) {
        this.conn = conn;
    }

    public boolean crearCuenta(Cuenta cuenta){
        try  {
            if (buscarCuenta(cuenta)){
                return false;
            }
            String insercion = "INSERT INTO Cuenta (nombreUsuario, nombreCuenta, saldo) VALUES (?, ?, ?)";
            PreparedStatement pstmt = this.conn.prepareStatement(insercion);

            pstmt.setString(1, cuenta.getNombreUsuario());
            pstmt.setString(2, cuenta.getNombreCuenta());
            pstmt.setInt(3, 0);

            int code = pstmt.executeUpdate();
            pstmt.close();
            if (code == 1) {
                System.out.println("Se creo la cuenta correctamente");
                return true;
            }
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean buscarCuenta(Cuenta cuenta) throws SQLException {
        boolean encontrado = false;
        try  {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Cuenta WHERE nombreCuenta = '" + cuenta.getNombreCuenta() + "' AND nombreUsuario = '" + cuenta.getNombreUsuario() + "'");
            if (rs.next()){
                System.out.println("Cuenta encontrada y valida");
                encontrado = true;
            }
            stmt.close();
            return encontrado;
        }catch (SQLException e){
            e.printStackTrace();
            return encontrado;
        }
    }
}
