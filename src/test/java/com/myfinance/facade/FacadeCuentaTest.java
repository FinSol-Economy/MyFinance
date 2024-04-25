package com.myfinance.facade;

import com.myfinance.entities.Cuenta;
import com.myfinance.persistence.BDTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FacadeCuentaTest {

    private FacadeCuenta facadecuenta;
    private static Connection conn;

    @BeforeAll
    static void connect(){
        BDTest database = new BDTest();
        database.Conectar();
        conn = database.getConn();
    }

    @BeforeEach
    void init(){
        this.facadecuenta = new FacadeCuenta(conn);
    }

    @Test
    void crearCuentaVacia(){
        //Datos vacíos
        Cuenta result = facadecuenta.crearCuenta("", "", 0);
        assertNull(result);
    }
}
