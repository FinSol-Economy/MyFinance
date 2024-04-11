package com.myfinance.controllersView;

import com.myfinance.entities.Cuenta;
import com.myfinance.entities.Usuario;
import com.myfinance.facade.facadeCuenta;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.Connection;

public class InicioViewController implements interfaceControllerView{

    private Connection conn;
    private Usuario usuario;
    private Cuenta cuenta;
    private facadeCuenta facade;
    @FXML
    public Label txtuserName;
    @FXML
    public Label txtNombreCuenta;
    @FXML
    public Button btnCerrarSesion;
    @FXML
    public Button btnOtraCuenta;


    public void onActionCerrarSesion() throws IOException {
        generalControllerView.getInstance().showScreen("View_InicioSesion/IniciarSesionView.fxml", this.conn);
    }

    @Override
    public void setControlador(Object controlador) {
        this.facade = (facadeCuenta) controlador;
    }

    @Override
    public void inicializar(Object... params) {
        if (params[0] instanceof Connection){
            this.conn = (Connection)params[0];
        }
        if (params[1] instanceof Usuario){
            this.usuario = (Usuario)params[1];
        }
        if (params[2] instanceof Cuenta){
            this.cuenta = (Cuenta)params[2];
        }
        txtuserName.setText(this.usuario.getNombre());
        txtNombreCuenta.setText(this.cuenta.getNombreCuenta());
    }

    public void onActionElegirOtraCuenta() throws IOException {
        generalControllerView.getInstance().showScreen("View_Cuentas/CuentaView.fxml", this.conn, this.usuario);
    }
}
