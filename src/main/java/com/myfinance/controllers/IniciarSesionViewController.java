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


public class IniciarSesionViewController {
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
    public Button inicioSesionBtn;
    @FXML
    public Button registroBtn;



    @FXML
    protected void clicInicioSesion() throws SQLException {
        String userName = userNameField.getText();
        String password = passwordField.getText();
        Usuario usuario = new Usuario(userName, password);
        UsuarioBD userbd = new UsuarioBD();
        boolean encontrado = userbd.buscarUsuario(usuario);
        if (encontrado){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Usuario correcto");
            alert.setContentText(userName+" "+password);
            alert.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Usuario Incorrecto");
            alert.setContentText(userName+" "+password+" no existe");
            alert.show();
        }
        userNameField.clear();
        passwordField.clear();
    }

    @FXML
    protected void clicRegistro() {
        try{
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(IniciarSesionViewController.class.getResource("/com/myfinance/Views/View_InicioSesion/RegistroView.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setTitle("MyFinance");
            stage.setScene(scene);
            RegistroViewController controladorRegistro= loader.getController();
            controladorRegistro.setStage(stage);
            stage.show();
            this.stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
