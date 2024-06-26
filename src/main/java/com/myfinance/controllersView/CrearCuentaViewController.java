package com.myfinance.controllersView;

import com.myfinance.entities.Cuenta;
import com.myfinance.entities.Usuario;
import com.myfinance.facade.FacadeCuenta;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class CrearCuentaViewController implements InterfaceControllerView {
    private Connection conn;
    private Usuario usuario;
    private FacadeCuenta facade;
    @FXML
    public TextField txtFieldNombreCuenta;
    @FXML
    public Button btnCrearCuenta;
    @FXML
    public Button btnVolver;

    public void onActionCrearCuenta() throws IOException {
        String nombreCuenta = txtFieldNombreCuenta.getText();
        Cuenta cuenta = this.facade.crearCuenta(this.usuario.getNombre(), nombreCuenta, 0);
        txtFieldNombreCuenta.clear();
        if (cuenta!=null){
            Stage stage = (Stage) btnCrearCuenta.getScene().getWindow();
            stage.close();
            GeneralControllerView.getInstance().showScreen("View_Cuentas/CuentaView.fxml", this.conn, usuario);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error al crear cuenta");
            alert.setContentText(nombreCuenta+" ya existe o ingresó un nombre incorrecto");
            alert.show();
        }
    }

    public void onActionVolver() {
        Stage stage = (Stage) btnCrearCuenta.getScene().getWindow();
        stage.close();
    }

    @Override
    public void setFacade(Object facade) {
        this.facade = (FacadeCuenta) facade;
    }

    @Override
    public void inicializar(Object... params) {
        if (params[0] instanceof Connection){
            this.conn = (Connection)params[0];
        }
        if (params[1] instanceof Usuario){
            this.usuario = (Usuario)params[1];
        }
    }
}
