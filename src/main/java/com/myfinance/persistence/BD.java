package com.myfinance.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BD {
    private final String user;
    private final String pass;
    private final String URL;
    private Connection conn;

    public BD(){
        this.user="sa";
        this.pass="";
        this.URL="jdbc:h2:./db/test;INIT=RUNSCRIPT FROM 'src/main/resources/bd.sql'";
    }

    public Connection getConn() {
        return conn;
    }

    public void Conectar(){
        try {
            this.conn = DriverManager.getConnection(this.URL, this.user, this.pass);
            System.out.println("Conexión exitosa");
            //CrearTablas(conn);

        } catch (SQLException e) {
            e.printStackTrace();
            this.conn=null;
        }
    }

    public void Desconectar() throws SQLException {
        this.conn.close();
        this.conn=null;
    }

}
