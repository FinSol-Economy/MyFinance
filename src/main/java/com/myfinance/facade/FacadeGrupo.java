package com.myfinance.facade;

import com.myfinance.entities.Cuenta;
import com.myfinance.entities.Grupo;
import com.myfinance.entities.Movimiento;
import com.myfinance.entities.Usuario;
import com.myfinance.persistence.GrupoBD;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class FacadeGrupo {
    private Connection conn;
    private GrupoBD grupoBD;

    public FacadeGrupo(Connection conn) {
        this.conn = conn;
        this.grupoBD = new GrupoBD(conn);
    }

    public boolean crearGrupo(String nombreGrupo, Usuario usuario)
    {
        boolean creado = false;
        Grupo grupo = new Grupo(nombreGrupo);
        if(this.grupoBD.crearGrupo(grupo,usuario))
        {

            if(this.grupoBD.buscarGrupo(grupo,usuario))
            {
                if(this.grupoBD.añadirMiembro(grupo,usuario))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean añadirMiembro(Grupo grupo, Usuario usuario)
    {
        if(this.grupoBD.añadirMiembro(grupo, usuario))
        {
            return true;
        }
        return false;
    }

    public List<String> getIntegrantes(Grupo grupo)
    {
        return this.grupoBD.getUsuariosGrupo(grupo.getGrupoId());
    }

    public List<Grupo> getGruposUsuario(Usuario usuario)
    {
        return this.grupoBD.getGruposUsuario(usuario.getNombre());
    }
    public List<Usuario> getUsuarios()
    {
        return this.grupoBD.getUsuarios();
    }
    public List<Cuenta> getCuentas(Usuario usuario){
        return this.grupoBD.getCuentas(usuario);
    }
    /*/
    public boolean crearMovimiento(int cuentaID,String tipo, String movimientoNombre, String movimientoDesc, double monto, LocalDate movimientoFecha, java.util.Date movimientoRegistro, double saldoviejo, int grupoID){
        //CREAR MOVIMIENTO PARA INGRESO
        boolean creado = false;

        creado = this.grupoBD.crearMovimiento(tipo, movimientoNombre, movimientoDesc, monto, movimientoFecha, movimientoRegistro, grupoID);
        if (creado){
            double montonuevo = monto + saldoviejo;
            this.grupoBD.actualizarSaldo(grupoID, montonuevo); //Actualizando saldo para el grupo
        }
        return creado;
    }*/

    public boolean crearMovimiento(String tipo, String movimientoNombre, String movimientoDesc, double monto, LocalDate movimientoFecha, Date movimientoRegistro, double saldoviejo, int grupoID){
        //CREAR MOVIMIENTO PARA EGRESO
        boolean creado = false;
        creado = this.grupoBD.crearMovimiento(tipo, movimientoNombre, movimientoDesc, monto, movimientoFecha, movimientoRegistro, grupoID);
        if (creado){
            double montonuevo = monto + saldoviejo;
            this.grupoBD.actualizarSaldo(grupoID, montonuevo); //Actualizar saldo en el grupo
        }
        return creado;
    }
    public List<Movimiento> getMovimientos(int grupoId){
        return this.grupoBD.getMovimientos(grupoId);
    }
}
