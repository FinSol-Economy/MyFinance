package com.myfinance.controllersView;

import com.myfinance.facade.FacadeCuenta;
import com.myfinance.facade.FacadeGrupo;
import com.myfinance.facade.FacadeMovimiento;
import com.myfinance.facade.FacadeUsuario;
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

public class GeneralControllerView {

    private static GeneralControllerView instance;
    private Stage stage;
    private static Image icono;
    private static Connection conn;

    private static Map<Class<?>, Object> facades;

    private GeneralControllerView() {}

    public static GeneralControllerView getInstance() {
        return instance;
    }

    public static GeneralControllerView getInstance(Connection connec) {
        if (instance == null) {
            instance = new GeneralControllerView();
            //Icono para las ventanas
            icono = new Image(GeneralControllerView.class.getResourceAsStream("/com/myfinance/Images/FinSol.png"));
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
        InterfaceControllerView IcontrollerView = (InterfaceControllerView) controllerView;
        IcontrollerView.setFacade(facade);
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
        InterfaceControllerView IcontrollerView = (InterfaceControllerView) controllerView;
        IcontrollerView.setFacade(facade);
        IcontrollerView.inicializar(params);

        Stage stage2 = (new Stage());
        stage2.setTitle(tittle);
        stage2.getIcons().add(icono);
        stage2.initModality(Modality.APPLICATION_MODAL); // Bloquear la ventana anterior
        stage2.setScene(new Scene(root));
        stage2.showAndWait();
    }

    private static void crearFacades(){
        FacadeUsuario fcdusuario = new FacadeUsuario(conn);
        FacadeCuenta fcdcuenta = new FacadeCuenta(conn);
        FacadeMovimiento fcdmovimiento = new FacadeMovimiento(conn);
        FacadeGrupo fcdGrupo = new FacadeGrupo(conn);

        facades.put(IniciarSesionViewController.class, fcdusuario);
        facades.put(RegistroViewController.class, fcdusuario);
        facades.put(CuentaViewController.class, fcdusuario);
        facades.put(CrearCuentaViewController.class, fcdcuenta);
        facades.put(InicioViewController.class, fcdmovimiento);
        facades.put(RegistrarMovimientoViewController.class, fcdmovimiento);
        facades.put(VerMovimientosViewController.class, null);
        facades.put(menuGruposViewController.class, fcdGrupo);
        facades.put(CrearGrupoViewController.class, fcdGrupo);
        facades.put(RegistrarMovimientoGrupoViewController.class, fcdGrupo);
    }

    private Object getFacade(Object controllerView){
        return facades.get(controllerView.getClass());
    }
}
