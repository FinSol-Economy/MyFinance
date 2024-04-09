package com.myfinance;

import com.myfinance.controllers.IniciarSesionViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            System.out.println("GG");
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Views/View_InicioSesion/IniciarSesionView.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setTitle("MyFinance");
            stage.setScene(scene);
            IniciarSesionViewController controladorIniciarSesion= loader.getController();
            controladorIniciarSesion.setStage(stage);
            stage.show();
        }
        catch (Exception e){
            System.out.println("Error");
            e.printStackTrace();
        }
    }
}