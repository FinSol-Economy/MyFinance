package com.myfinance.controllersView;

import com.myfinance.entities.Cuenta;
import com.myfinance.entities.Movimiento;
import com.myfinance.entities.Usuario;
import com.myfinance.facade.FacadeMovimiento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class InicioViewController implements InterfaceControllerView {
    private Connection conn;
    private Usuario usuario;
    private Cuenta cuenta;
    private FacadeMovimiento facade;
    private List<Movimiento> movimientos;
    @FXML
    public Label txtuserName;
    @FXML
    public Label txtNombreCuenta;
    @FXML
    public Button btnCerrarSesion;
    @FXML
    public Button btnOtraCuenta;
    @FXML
    public Label txtSaldo;
    @FXML
    public Button btnRegistrarMovimiento;
    @FXML
    public Button btnVerMovimientos;
    @FXML
    public TableView<Movimiento> tableMovimientos;
    @FXML
    public TableColumn<Movimiento, String> columnNombreMovimiento;
    @FXML
    public TableColumn<Movimiento, LocalDate> columnFechaMovimiento;
    @FXML
    public TableColumn<Movimiento, Double> columnMovimientoMonto;



    public void onActionCerrarSesion() throws IOException {
        GeneralControllerView.getInstance().showScreen("View_InicioSesion/IniciarSesionView.fxml", this.conn);
    }

    @Override
    public void setFacade(Object facade) {
        this.facade = (FacadeMovimiento) facade;
    }

    @Override
    public void inicializar(Object... params) {
        if (params[0] instanceof Connection){
            this.conn = (Connection)params[0];
        }
        if (params[1] instanceof Usuario){
            this.usuario = (Usuario)params[1];
        }
        if (params[2] instanceof Cuenta){
            this.cuenta = (Cuenta)params[2];
        }
        txtuserName.setText(this.usuario.getNombre());
        txtNombreCuenta.setText(this.cuenta.getNombreCuenta());
        txtSaldo.setText(Double.toString(this.cuenta.getSaldo()));
        this.movimientos = this.facade.getMovimientos(this.cuenta);
        if(!this.movimientos.isEmpty()){
            inicializarLista(this.movimientos);
        }
        //Inicializar lista
    }

    public void onActionElegirOtraCuenta() throws IOException {
        GeneralControllerView.getInstance().showScreen("View_Cuentas/CuentaView.fxml", this.conn, this.usuario);
    }

    public void onActionRegistrarMovimiento() throws IOException {
        GeneralControllerView.getInstance().showSecundaryScreen("View_Movimiento/RegistrarMovimientoView.fxml", "Registrar Movimiento", this.conn, this.usuario, this.cuenta);
    }

    public void onActionVerMovimientos() throws IOException {
        GeneralControllerView.getInstance().showSecundaryScreen("View_Movimiento/VerMovimientosView.fxml", "Movimientos", this.movimientos);
    }

    private void inicializarLista(List<Movimiento> movimientos){
        int numElementos = Math.min(movimientos.size(), 5);
        columnNombreMovimiento.setCellValueFactory(new PropertyValueFactory<>("movimientoNombre"));
        columnFechaMovimiento.setCellValueFactory(new PropertyValueFactory<>("movimientoFecha"));
        columnMovimientoMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));

        ObservableList<Movimiento> movimientosTable = FXCollections.observableArrayList();

        int contador = 0;
        Movimiento movimiento;
        for (int i = movimientos.size() - 1; i >= 0; i--) {
            if (contador < numElementos) {
                movimiento = movimientos.get(i);
                movimientosTable.add(movimiento);
                contador++;
            } else {
                break;
            }
        }

        tableMovimientos.setItems(movimientosTable);
    }
}