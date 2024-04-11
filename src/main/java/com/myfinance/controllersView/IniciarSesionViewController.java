package com.myfinance.controllersView;

import com.myfinance.entities.Usuario;
import com.myfinance.facade.facadeUsuario;
import com.myfinance.persistence.UsuarioBD;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


public class IniciarSesionViewController implements interfaceControllerView {
    private Stage stage;
    private Connection conn;
    private facadeUsuario facade;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
    @FXML
    public TextField userNameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Button inicioSesionBtn;
    @FXML
    public Button registroBtn;


    @FXML
    protected void clicInicioSesion() throws SQLException, IOException {
        String userName = userNameField.getText();
        String password = passwordField.getText();
        Usuario usuario = this.facade.buscarUsuario(userName, password);
        if (usuario!=null){
            generalControllerView.getInstance().showScreen("View_Cuentas/CuentaView.fxml", this.conn, usuario);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Usuario Incorrecto");
            alert.setContentText(userName+" no existe o ingresó mal su contraseña");
            alert.show();
        }
        userNameField.clear();
        passwordField.clear();
    }
    @FXML
    protected void clicRegistro() {
        try{
            generalControllerView.getInstance().showScreen("View_InicioSesion/RegistroView.fxml", this.conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setControlador(Object controlador) {
        this.facade = (facadeUsuario) controlador;
    }

    @Override
    public void inicializar(Object... params) {
        if (params[0] instanceof Connection){
            this.conn = (Connection)params[0];
        }
    }

}


