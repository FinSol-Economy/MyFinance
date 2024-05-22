package com.myfinance.controllersView;

import com.myfinance.entities.Cuenta;
import com.myfinance.entities.Usuario;
import com.myfinance.facade.FacadeUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class CuentaViewController implements InterfaceControllerView {

    public Button btnGrupos;
    private Connection conn;
    private Usuario usuario;
    private List<Cuenta> cuentas;
    private FacadeUsuario facade;
    @FXML
    public Label txtuserName;
    @FXML
    public Button btnContinuar;
    @FXML
    public Button btncrearCuenta;

    @FXML
    public Button btnIniciarSesion;
    @FXML
    public TableView<Cuenta> tableCuentas;
    @FXML
    public TableColumn<Cuenta, String> columnNombreCuenta;
    @FXML
    public TableColumn<Cuenta, Double> columnSaldo;


    @Override
    public void setFacade(Object facade) {
        this.facade = (FacadeUsuario) facade;
    }

    @Override
    public void inicializar(Object... params) {
        if (params[0] instanceof Connection){
            this.conn = (Connection)params[0];
        }
        if (params[1] instanceof Usuario){
            this.usuario = (Usuario)params[1];
        }
        this.cuentas =  this.facade.getCuentas(this.usuario);
        txtuserName.setText(this.usuario.getNombre());
        if (!this.cuentas.isEmpty()){
            columnNombreCuenta.setCellValueFactory(cellData -> cellData.getValue().getPropertyNombreCuenta());
            columnSaldo.setCellValueFactory(cellData -> cellData.getValue().getPropertySaldo().asObject());
            tableCuentas.getItems().addAll(this.cuentas);
        }
    }

    @FXML
    protected void onActionContinuar() throws IOException {
        Cuenta seleccionada = tableCuentas.selectionModelProperty().get().getSelectedItem();
        if (seleccionada!=null){
            GeneralControllerView.getInstance().showScreen("View_Inicio/InicioView.fxml", this.conn, this.usuario, seleccionada);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Cuenta no seleccionada");
            alert.setContentText("Elija una cuenta para continuar o cree una");
            alert.show();
        }

    }

    @FXML
    protected void onActionCrearCuenta() throws IOException {
        GeneralControllerView.getInstance().showSecundaryScreen("View_Cuentas/CrearCuentaView.fxml", "Crear Cuenta", this.conn, this.usuario);
    }

    @FXML
    protected void onActionIniciarSesion() {
        try{
            GeneralControllerView.getInstance().showScreen("View_InicioSesion/IniciarSesionView.fxml", this.conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void seccionGrupos(ActionEvent actionEvent) throws IOException {
        GeneralControllerView.getInstance().showScreen("View_Grupos/menuGrupos.fxml", this.conn, this.usuario);
    }
}
