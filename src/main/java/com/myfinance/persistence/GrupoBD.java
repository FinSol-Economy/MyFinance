package com.myfinance.persistence;

import com.myfinance.entities.Cuenta;
import com.myfinance.entities.Grupo;
import com.myfinance.entities.Movimiento;
import com.myfinance.entities.Usuario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GrupoBD {
    private final Connection conn;

    public GrupoBD(Connection conn) {
        this.conn = conn;
    }

    public boolean buscarGrupo(Grupo grupo, Usuario usuario)
    {
        boolean encontrado = false;
        try
        {
            String sql = "SELECT * FROM Grupos WHERE nombreGrupo = ? AND creador = ?";

            PreparedStatement pstmt = this.conn.prepareStatement(sql);
            pstmt.setString(1, grupo.getNombreGrupo());
            pstmt.setString(2, usuario.getNombre());

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()){
                System.out.println("Grupo encontrado");
                grupo.setGrupoId(resultSet.getInt("idGrupo"));
                encontrado = true;
            }
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return encontrado;
    }
    public boolean crearGrupo(Grupo grupo, Usuario usuario)
    {
        try {
            if(buscarGrupo(grupo,usuario))
            {
                return false;
            }
            grupo.setBalance(0);
            String insercion = "INSERT INTO Grupos (nombreGrupo, balance, creador) VALUES (?, ?, ?)";
            PreparedStatement pstmt = this.conn.prepareStatement(insercion);

            pstmt.setString(1, grupo.getNombreGrupo());
            pstmt.setDouble(2, grupo.getBalance());
            pstmt.setString(3, usuario.getNombre());

            int code = pstmt.executeUpdate();
            pstmt.close();
            if (code == 1) {
                System.out.println("Se creo el grupo correctamente");
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean añadirMiembro(Grupo grupo, Usuario usuario)
    {
        try
        {
            if(usuarioEnGrupo(grupo, usuario))
            {
                return false;
            }
            String insercion = "INSERT INTO UsuariosGrupos (idGrupo, nombreUsuario) values (?,?)";
            PreparedStatement pstmt = this.conn.prepareStatement(insercion);
            System.out.println(grupo.getGrupoId());
            System.out.println(grupo.getNombreGrupo());
            System.out.println(usuario.getNombre());
            pstmt.setInt(1, grupo.getGrupoId());
            pstmt.setString(2, usuario.getNombre());
            int code = pstmt.executeUpdate();
            System.out.println("Bo");
            pstmt.close();
            if (code == 1){
                System.out.println("Se añadio un miembro correctamente");
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean usuarioEnGrupo(Grupo grupo, Usuario usuario)
    {
        try
        {
            String query = "SELECT * from UsuariosGrupos WHERE idGrupo = ? AND nombreUsuario = ?";
            PreparedStatement pstmt = this.conn.prepareStatement(query);
            pstmt.setInt(1, grupo.getGrupoId());
            pstmt.setString(2, usuario.getNombre());

            ResultSet resultSet = pstmt.executeQuery();
            if(resultSet.next())
            {
                System.out.println("El usuario se encontro en el grupo");
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getUsuariosGrupo(int idGrupo)
    {
        try
        {
            System.out.println(idGrupo);
            String query = "SELECT * from UsuariosGrupos WHERE idGrupo = ?";
            PreparedStatement pstmt = this.conn.prepareStatement(query);
            pstmt.setInt(1, idGrupo);

            ResultSet resultSet = pstmt.executeQuery();
            List<String> usuarios = new ArrayList<>();
            while (resultSet.next())
            {
                usuarios.add(resultSet.getString("nombreUsuario"));
            }
            return usuarios;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Grupo> getGruposUsuario(String nombreUsuario)
    {
        try {
            String query = "SELECT g.idGrupo, g.nombreGrupo, g.balance \n" +
                    "FROM UsuariosGrupos ug\n" +
                    "INNER JOIN Grupos g ON ug.idGrupo = g.idGrupo\n" +
                    "WHERE ug.nombreUsuario = ?";
            PreparedStatement pstmt = this.conn.prepareStatement(query);
            pstmt.setString(1, nombreUsuario);
            ResultSet resultSet = pstmt.executeQuery();
            List<Grupo> grupos = new ArrayList<>();
            while (resultSet.next())
            {
                grupos.add(new Grupo(resultSet.getInt("idGrupo"), resultSet.getString("nombreGrupo"), resultSet.getDouble("balance")));
            }
            return grupos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Usuario> getUsuarios()
    {
        try {
            List<Usuario> usuarios = new ArrayList<>();
            String query = "SELECT * FROM Usuarios";
            PreparedStatement pstmt = this.conn.prepareStatement(query);
            ResultSet resultSet = pstmt.executeQuery();
            while(resultSet.next())
            {
                usuarios.add(new Usuario(resultSet.getString("nombreUsuario"),resultSet.getString("password")));
            }
            return usuarios;
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    public boolean crearMovimiento(int cuentaID,String tipo, String movimientoNombre, String movimientoDesc, double monto, LocalDate movimientoFecha, java.util.Date movimientoRegistro, int grupoID){
        //CREAR MOVIMIENTO PARA INGRESO
        boolean creado = false;
        try{
            String insercion = "INSERT INTO Movimientos (cuentaID, movimientoNombre, movimientoDesc, movimientoTipo, movimientoMonto, movimientoFecha, movimientoRegistro, grupoID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = this.conn.prepareStatement(insercion);

            prepareCrearMovimiento(cuentaID,tipo,movimientoNombre,movimientoDesc,monto,movimientoFecha,movimientoRegistro,grupoID, pstmt);

            int code = pstmt.executeUpdate();
            pstmt.close();
            if (code == 1) {
                System.out.println("Se registro el movimiento correctamente");
                creado = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return creado;
    }

    private void prepareCrearMovimiento(int cuentaID,String tipo, String movimientoNombre, String movimientoDesc, double monto, LocalDate movimientoFecha, java.util.Date movimientoRegistro, int grupoID, PreparedStatement pstmt) throws SQLException {
        //PREPARAR MOVIMIENTO INGRESO
        pstmt.setInt(1, cuentaID);//ID cuenta

        pstmt.setString(2, movimientoNombre);//Movimiento nombre

        pstmt.setString(3, movimientoDesc);//Movimiento descripcion

        pstmt.setString(4, tipo);//Movimiento tipo

        pstmt.setDouble(5, monto);//Movimiento monto

        java.sql.Date sqlMovimientoFecha = java.sql.Date.valueOf(movimientoFecha);
        pstmt.setDate(6, sqlMovimientoFecha);//Fecha del movimiento

        java.sql.Timestamp sqlMovimientoRegistro = new java.sql.Timestamp(movimientoRegistro.getTime());
        pstmt.setTimestamp(7, sqlMovimientoRegistro);//Fecha del registro del movimiento
        pstmt.setInt(8, grupoID);
    }

    public boolean crearMovimiento(String tipo, String movimientoNombre, String movimientoDesc, double monto, LocalDate movimientoFecha, java.util.Date movimientoRegistro, int grupoID){
        //CREAR MOVIMIENTO PARA EGRESO
        boolean creado = false;
        try{
            String insercion = "INSERT INTO Movimientos (movimientoNombre, movimientoDesc, movimientoTipo, movimientoMonto, movimientoFecha, movimientoRegistro, grupoID) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = this.conn.prepareStatement(insercion);

            prepareCrearMovimiento(tipo,movimientoNombre,movimientoDesc,monto,movimientoFecha,movimientoRegistro,grupoID, pstmt);

            int code = pstmt.executeUpdate();
            pstmt.close();
            if (code == 1) {
                System.out.println("Se registro el movimiento correctamente");
                creado = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return creado;
    }

    private void prepareCrearMovimiento(String tipo, String movimientoNombre, String movimientoDesc, double monto, LocalDate movimientoFecha, java.util.Date movimientoRegistro, int grupoID, PreparedStatement pstmt) throws SQLException {
        //PREPARAR MOVIMIENTO EGRESO

        pstmt.setString(1, movimientoNombre);//Movimiento nombre

        pstmt.setString(2, movimientoDesc);//Movimiento descripcion

        pstmt.setString(3, tipo);//Movimiento tipo

        pstmt.setDouble(4, monto);//Movimiento monto

        java.sql.Date sqlMovimientoFecha = java.sql.Date.valueOf(movimientoFecha);
        pstmt.setDate(5, sqlMovimientoFecha);//Fecha del movimiento

        java.sql.Timestamp sqlMovimientoRegistro = new java.sql.Timestamp(movimientoRegistro.getTime());
        pstmt.setTimestamp(6, sqlMovimientoRegistro);//Fecha del registro del movimiento
        pstmt.setInt(7, grupoID);
    }

    public void actualizarSaldo(int grupoID, double montoNuevo){
        try{
            String update = "UPDATE Grupos SET balance = ? WHERE idGrupo = ?";
            PreparedStatement pstmt = this.conn.prepareStatement(update);
            pstmt.setDouble(1, montoNuevo);
            pstmt.setInt(2, grupoID);
            pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Movimiento> getMovimientos(int grupoID){
        List<Movimiento> movimientos = new ArrayList<>();
        try  {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Movimientos WHERE grupoID = '" + grupoID + "'");
            Movimiento movimiento;
            while (rs.next()) {
                movimiento = getMovimiento(grupoID, rs);
                movimientos.add(movimiento);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return movimientos;
    }

    private Movimiento getMovimiento(int ID, ResultSet rs) throws SQLException {
        int movimientoID = rs.getInt("movimientoID");
        String tipo = rs.getString("movimientoTipo");
        String movimientoNombre = rs.getString("movimientoNombre");
        String movimientoDesc = rs.getString("movimientoDesc");
        double monto = rs.getDouble("movimientoMonto");
        LocalDate movimientoFecha = rs.getDate("movimientoFecha").toLocalDate();
        java.util.Date movimientoRegistro = rs.getTimestamp("movimientoRegistro");

        return new Movimiento(0, movimientoID, tipo, movimientoNombre, movimientoDesc, monto, movimientoFecha, movimientoRegistro, ID);
    }
}
