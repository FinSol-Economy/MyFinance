package com.myfinance.controllersView;

import com.myfinance.facade.facadeCuenta;
import com.myfinance.facade.facadeUsuario;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class generalControllerView {

    private static generalControllerView instance;
    private Stage stage;
    private static Image icono;
    private static Connection conn;

    private static Map<Class<?>, Object> facades;

    private generalControllerView() {}

    public static generalControllerView getInstance() {
        return instance;
    }

    public static generalControllerView getInstance(Connection connec) {
        if (instance == null) {
            instance = new generalControllerView();
            //Icono para las ventanas
            icono = new Image(generalControllerView.class.getResourceAsStream("/com/myfinance/Images/FinSol.png"));
            conn = connec;
            facades = new HashMap<>();
            crearFacades();
        }

        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void showScreen(String screenName, Object... params) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/myfinance/Views/"+screenName));
        Parent root = loader.load();


        Object controllerView = loader.getController();
        Object facade = getFacade(controllerView);
        interfaceControllerView IcontrollerView = (interfaceControllerView) controllerView;
        IcontrollerView.setControlador(facade);
        IcontrollerView.inicializar(params);

        stage.setScene(new Scene(root));
        stage.setTitle("MyFinance");
        stage.getIcons().add(icono);
        stage.show();
    }

    //Para ventana emergente
    public void showSecundaryScreen(String screenName, String tittle, Object... params) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/myfinance/Views/"+screenName));
        Parent root = loader.load();


        Object controllerView = loader.getController();
        Object facade = getFacade(controllerView);
        interfaceControllerView IcontrollerView = (interfaceControllerView) controllerView;
        IcontrollerView.setControlador(facade);
        IcontrollerView.inicializar(params);

        Stage stage2 = (new Stage());
        stage2.setTitle(tittle);
        stage2.getIcons().add(icono);
        stage2.initModality(Modality.APPLICATION_MODAL); // Bloquear la ventana anterior
        stage2.setScene(new Scene(root));
        stage2.showAndWait();
    }

    private static void crearFacades(){
        facadeUsuario fcdusuario = new facadeUsuario(conn);
        facadeCuenta fcdcuenta = new facadeCuenta(conn);

        facades.put(IniciarSesionViewController.class, fcdusuario);
        facades.put(RegistroViewController.class, fcdusuario);
        facades.put(CuentaViewController.class, fcdusuario);
        facades.put(CrearCuentaViewController.class, fcdcuenta);
        facades.put(InicioViewController.class, fcdcuenta);

    }

    private Object getFacade(Object controllerView){
        return facades.get(controllerView.getClass());
    }
}
