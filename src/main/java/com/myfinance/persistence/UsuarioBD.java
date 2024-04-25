package com.myfinance.persistence;
import com.myfinance.entities.Cuenta;
import com.myfinance.entities.Usuario;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioBD {
    private final Connection conn;
    public UsuarioBD(Connection conn) {
        this.conn = conn;
    }

    public boolean crearUsuario(Usuario user) throws SQLException {
        try  {
            Statement stmt = this.conn.createStatement();
            int code = stmt.executeUpdate("INSERT INTO Usuarios (nombreUsuario, password) VALUES ('" + user.getNombre() + "', '" + user.getPassword() + "')");
            stmt.close();
            if (code == 1) {
                System.out.println("Se creo el usuario correctamente");
                return true;
            }
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean buscarUsuario(Usuario user) throws SQLException {
        boolean encontrado = false;
        try  {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Usuarios WHERE nombreUsuario = '" + user.getNombre() + "' AND password = '" + user.getPassword() + "'");
            if (rs.next()){
                System.out.println("Usuario encontrado y valido");
                encontrado = true;
            }
            stmt.close();
            return encontrado;
        }catch (SQLException e){
            e.printStackTrace();
            return encontrado;
        }

    }

    public List<Cuenta> getCuentas(Usuario user){
        List<Cuenta> cuentas = new ArrayList<Cuenta>();
        try  {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Cuentas WHERE nombreUsuario = '" + user.getNombre()+ "'");
            while (rs.next()) {
                int cuentaID = rs.getInt("cuentaID");
                String nombreUsuario = rs.getString("nombreUsuario");
                String nombreCuenta = rs.getString("nombreCuenta");
                double saldo = rs.getDouble("saldo");
                Cuenta cuenta = new Cuenta(cuentaID,nombreUsuario, nombreCuenta, saldo);
                cuentas.add(cuenta);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return cuentas;
    }
}
