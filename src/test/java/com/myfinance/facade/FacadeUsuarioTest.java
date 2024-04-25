package com.myfinance.facade;

import com.myfinance.entities.Cuenta;
import com.myfinance.entities.Usuario;
import com.myfinance.persistence.BDTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FacadeUsuarioTest {
    private FacadeUsuario facadeusuario;
    private static Connection conn;

    @BeforeAll
    static void connect(){
        BDTest database = new BDTest();
        database.Conectar();
        conn = database.getConn();
    }

    @BeforeEach
    void init(){
        this.facadeusuario = new FacadeUsuario(conn);
    }

    @Test
    void crearUsuarioRepetido() {
        //Datos vacíos
        Usuario result = facadeusuario.crearUsuario("User1", "456");
        assertNull(result);
    }

    @Test
    void crearUsuario() {
        //Datos vacíos
        Usuario result = facadeusuario.crearUsuario("User2", "456");
        assertEquals(result.getClass(), Usuario.class);
    }

    @AfterEach
    void borrarDatos(){

    }
}
