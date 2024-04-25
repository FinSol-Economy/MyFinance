package com.myfinance.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BDTest {
    private final String user;
    private final String pass;
    private final String URL;
    private Connection conn;

    public BDTest(){
        this.user="sa";
        this.pass="";
        this.URL="jdbc:h2:./src/test/resources/test;INIT=RUNSCRIPT FROM 'src/test/resources/test_db.sql'";
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

    private void CrearTablas(Connection conn){
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS Usuario (ID int primary key auto_increment, nombre VARCHAR(50), password VARCHAR(50))");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void Desconectar() throws SQLException {
        this.conn.close();
        this.conn=null;
    }
}
