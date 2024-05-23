package com.myfinance.controllersView;

import com.myfinance.entities.Usuario;
import com.myfinance.facade.FacadeGrupo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class CrearGrupoViewController implements InterfaceControllerView{
    private FacadeGrupo facade;
    private Usuario usuario;
    private Connection conn;
    @FXML
    public TextField nombreGrupo;
    @FXML
    public Button btnvolver;
    @FXML
    public Button btnCrearGrupo;
    @Override
    public void setFacade(Object facade) {
        this.facade = (FacadeGrupo) facade;
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


    public void volverMenuGrupo(ActionEvent actionEvent) throws IOException {
        GeneralControllerView.getInstance().showScreen("View_Grupos/menuGrupos.fxml", this.conn, this.usuario);
    }

    public void guardarGrupo(ActionEvent actionEvent) throws IOException {
        String name = this.nombreGrupo.getText();
        boolean flag = this.facade.crearGrupo(name,this.usuario);
        nombreGrupo.clear();
        if (flag){
            Stage stage = (Stage) this.btnCrearGrupo.getScene().getWindow();
            stage.close();
            GeneralControllerView.getInstance().showScreen("View_Grupos/menuGrupos.fxml", this.conn, usuario);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error al crear un grupo");
            alert.setContentText(name+" ya existe o ingresó un nombre incorrecto");
            alert.show();
        }
    }
}
