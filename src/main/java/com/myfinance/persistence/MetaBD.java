package com.myfinance.persistence;

import com.myfinance.entities.Meta;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MetaBD {
    private final Connection conn;

    public MetaBD(Connection conn) {
        this.conn = conn;
    }

    public boolean crearMeta(Meta meta) {
        boolean creado = false;
        try {
            String insercion = "INSERT INTO Metas (cuentaID, nombre, montoObjetivo, fechaLimite) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = this.conn.prepareStatement(insercion, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, meta.getCuentaID());
            pstmt.setString(2, meta.getNombre());
            pstmt.setDouble(3, meta.getMontoObjetivo());
            pstmt.setDate(4, Date.valueOf(meta.getFechaLimite()));

            int code = pstmt.executeUpdate();
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                meta.setMetaID(generatedKeys.getInt(1));
            }
            pstmt.close();
            if (code == 1) {
                System.out.println("Se registró la meta correctamente");
                creado = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return creado;
    }

    public boolean modificarMeta(Meta meta) {
        boolean modificado = false;
        try {
            String update = "UPDATE Metas SET nombre = ?, montoObjetivo = ?, fechaLimite = ? WHERE metaID = ?";
            PreparedStatement pstmt = this.conn.prepareStatement(update);

            pstmt.setString(1, meta.getNombre());
            pstmt.setDouble(2, meta.getMontoObjetivo());
            pstmt.setDate(3, Date.valueOf(meta.getFechaLimite()));
            pstmt.setInt(4, meta.getMetaID());

            int code = pstmt.executeUpdate();
            pstmt.close();
            if (code == 1) {
                System.out.println("Se actualizó la meta correctamente");
                modificado = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modificado;
    }

    public List<Meta> getMetas(int cuentaID) {
        List<Meta> metas = new ArrayList<>();
        try {
            PreparedStatement pstmt = this.conn.prepareStatement("SELECT * FROM Metas WHERE cuentaID = ?");
            pstmt.setInt(1, cuentaID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Meta meta = new Meta(
                        rs.getInt("metaID"),
                        rs.getInt("cuentaID"),
                        rs.getString("nombre"),
                        rs.getDouble("montoObjetivo"),
                        rs.getDate("fechaLimite").toLocalDate()
                );
                metas.add(meta);
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return metas;
    }
}

