package com.myfinance.controllers;

import com.myfinance.entities.Usuario;
import com.myfinance.persistence.UsuarioBD;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class RegistroViewController {
    private Stage stage;

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
        Usuario usuario = new Usuario(userName, password);
        UsuarioBD userbd = new UsuarioBD();
        boolean creado = userbd.crearUsuario(usuario);
        if (creado){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Usuario creado");
            alert.setContentText(userName+" "+password);
            alert.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error al crear");
            alert.setContentText("Ya se encuentra registrado o se ingresaron datos incorrectos");
            alert.show();
        }
        userNameField.clear();
        passwordField.clear();
    }

    @FXML
    protected void clicIniciarSesion() {
        try{
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/myfinance/Views/View_InicioSesion/IniciarSesionView.fxml"));
            Scene scene = new Scene(loader.load());
            IniciarSesionViewController controladorIniciarSesion= loader.getController();
            controladorIniciarSesion.setStage(stage);
            stage.setTitle("MyFinance");
            stage.setScene(scene);
            stage.show();
            this.stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
