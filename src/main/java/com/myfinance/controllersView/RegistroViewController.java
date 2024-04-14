package com.myfinance.controllersView;

import com.myfinance.entities.Usuario;
import com.myfinance.facade.FacadeUsuario;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;

public class RegistroViewController implements interfaceControllerView  {
    private Stage stage;
    private Connection conn;
    private FacadeUsuario facade;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
    @FXML
    public TextField userNameField;
    @FXML
    public TextField passwordField;
    @FXML
    public Button registroBtn;
    @FXML
    public Button iniciarSesionBtn;

    @FXML
    protected void clicFinalizarRegistro() throws SQLException {
        String userName = userNameField.getText();
        String password = passwordField.getText();
        Usuario usuario = this.facade.crearUsuario(userName, password);
        Alert alert;
        if (usuario!=null){
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Usuario creado");
            alert.setContentText(userName+" "+password);
        }else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error al crear");
            alert.setContentText("Ya se encuentra registrado o se ingresaron datos incorrectos");
        }
        alert.show();
        userNameField.clear();
        passwordField.clear();
    }
    @FXML
    protected void clicIniciarSesion() {
        try{
            generalControllerView.getInstance().showScreen("View_InicioSesion/IniciarSesionView.fxml", this.conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setFacade(Object facade) {
        this.facade = (FacadeUsuario) facade;
    }

    @Override
    public void inicializar(Object... params) {
        if (params[0] instanceof Connection){
            this.conn = (Connection)params[0];
        }
    }
}
