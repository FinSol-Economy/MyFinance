package com.myfinance.facade;

import com.myfinance.entities.Cuenta;
import com.myfinance.entities.Usuario;
import com.myfinance.persistence.UsuarioBD;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FacadeUsuario {
    private Connection conn;
    private UsuarioBD userbd;

    public FacadeUsuario(Connection conn) {
        this.conn = conn;
        userbd = new UsuarioBD(conn);
    }

    public Usuario crearUsuario(String nombre, String password) {
        Usuario usuario = new Usuario(nombre, password);
        try{
            if (this.userbd.buscarUsuario(usuario)){
                usuario = null;
            }
            else if (!this.userbd.crearUsuario(usuario)){
                usuario = null;
            }
        }catch (SQLException _){
            usuario = null;
        }

        return usuario;
    }

    public Usuario buscarUsuario(String nombre, String password) throws SQLException {
        Usuario usuario = new Usuario(nombre, password);
        if (!this.userbd.buscarUsuario(usuario)){
            usuario = null;
        }
        return usuario;
    }

    public List<Cuenta> getCuentas(Usuario usuario){
        return this.userbd.getCuentas(usuario);
    }
}

