package com.myfinance;

import com.myfinance.controllersView.GeneralControllerView;
import com.myfinance.persistence.BD;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.Connection;

public class HelloApplication extends Application {
    public static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            //Conexion Base de Datos
            BD bd = new BD();
            bd.Conectar();
            Connection conn = bd.getConn();
            //Manejador de pantallas y controladores
            GeneralControllerView controllerView = GeneralControllerView.getInstance(conn);
            controllerView.setStage(stage);
            controllerView.showScreen("View_InicioSesion/IniciarSesionView.fxml", conn);
        }
        catch (Exception e){
            System.out.println("Error");
            e.printStackTrace();
        }
    }
}