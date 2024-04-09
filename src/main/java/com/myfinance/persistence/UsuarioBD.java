package com.myfinance.persistence;
import com.myfinance.entities.Usuario;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioBD {
    private final BD bd;
    public UsuarioBD() {
        this.bd = new BD();
    }

    public boolean crearUsuario(Usuario user) throws SQLException {
        try  {
            if (buscarUsuario(user)){
                return false;
            }
            this.bd.Conectar();
            Statement stmt = this.bd.getConn().createStatement();
            int code = stmt.executeUpdate("INSERT INTO Usuario (nombre, password) VALUES ('" + user.getNombre() + "', '" + user.getPassword() + "')");
            stmt.close();
            this.bd.Desconectar();
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
            this.bd.Conectar();
            Statement stmt = this.bd.getConn().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Usuario WHERE nombre = '" + user.getNombre() + "' AND password = '" + user.getPassword() + "'");
            if (rs.next()){
                System.out.println("Usuario encontrado y valido");
                encontrado = true;
            }
            stmt.close();
            this.bd.Desconectar();
            return encontrado;
        }catch (SQLException e){
            e.printStackTrace();
            return encontrado;
        }

    }
}
