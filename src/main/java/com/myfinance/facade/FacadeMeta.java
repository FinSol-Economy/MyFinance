package com.myfinance.facade;

import com.myfinance.entities.Meta;
import com.myfinance.persistence.MetaBD;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class FacadeMeta {
    private Connection conn;
    private MetaBD metaBD;

    public FacadeMeta(Connection conn) {
        this.conn = conn;
        this.metaBD = new MetaBD(conn);
    }

    public boolean crearMeta(int cuentaID, String nombre, double montoObjetivo, LocalDate fechaLimite) {
        Meta meta = new Meta(0, cuentaID, nombre, montoObjetivo, fechaLimite); // 0 porque el ID se generará automáticamente
        if (validarInformacion(meta)) {
            return metaBD.crearMeta(meta);
        }
        return false;
    }

    public boolean modificarMeta(int metaID, String nombre, double montoObjetivo, LocalDate fechaLimite) {
        Meta meta = new Meta(metaID, 0, nombre, montoObjetivo, fechaLimite); // 0 porque el cuentaID no es necesario para modificar
        if (validarInformacion(meta)) {
            return metaBD.modificarMeta(meta);
        }
        return false;
    }

    private boolean validarInformacion(Meta meta) {
        // Implementa la lógica de validación de la información proporcionada por el usuario
        // Por ejemplo, verificar que el monto objetivo sea positivo y que la fecha límite sea futura, etc.
        if (meta.getMontoObjetivo() <= 0) {
            System.out.println("El monto objetivo debe ser positivo.");
            return false;
        }
        if (meta.getFechaLimite().isBefore(LocalDate.now())) {
            System.out.println("La fecha límite debe ser en el futuro.");
            return false;
        }
        return true;
    }

    public List<Meta> getMetas(int cuentaID) {
        return metaBD.getMetas(cuentaID);
    }
}

