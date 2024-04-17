package com.myfinance.controllersView;

import com.myfinance.entities.Movimiento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class VerMovimientosViewController implements InterfaceControllerView {
    private List<Movimiento> movimientos;
    @FXML
    public Button btnVolver;
    @FXML
    public Button btnVerMovimiento;
    @FXML
    public TableView<Movimiento> tableMovimientos;
    @FXML
    public TableColumn<Movimiento, String> columnNombreMovimiento;
    @FXML
    public TableColumn<Movimiento, LocalDate> columnFechaMovimiento;
    @FXML
    public TableColumn<Movimiento, Double> columnMovimientoMonto;


    public void onActionVerMovimiento() {
        Movimiento seleccionado = tableMovimientos.selectionModelProperty().get().getSelectedItem();
        if (seleccionado!=null){
            mostrarMovimiento(seleccionado);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Movimiento no seleccionada");
            alert.setContentText("Elija un movimiento para ver sus detalles");
            alert.show();
        }
    }

    private void mostrarMovimiento(Movimiento movimiento){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Movimiento seleccionado");
        alert.setContentText("Nombre: "+movimiento.getMovimientoNombre()+"\n"
                            +"Monto: "+movimiento.getMonto()+"\n"
                            +"Fecha: "+movimiento.getMovimientoFecha()+"\n"
                            +"Tipo: "+movimiento.getTipo()+"\n"
                            +"Fecha del Registro: "+movimiento.getMovimientoRegistro()+"\n"
                            +"Descripción: "+movimiento.getMovimientoDesc());
        alert.show();
    }

    public void onActionVolver() {
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        stage.close();
    }

    @Override
    public void setFacade(Object facade) {

    }

    @Override
    public void inicializar(Object... params) {
        if (params[0] instanceof List<?>){
            this.movimientos = (List<Movimiento>)params[0];
        }
        inicializarLista();
    }

    private void inicializarLista(){
        columnNombreMovimiento.setCellValueFactory(new PropertyValueFactory<>("movimientoNombre"));
        columnFechaMovimiento.setCellValueFactory(new PropertyValueFactory<>("movimientoFecha"));
        columnMovimientoMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));

        ObservableList<Movimiento> movimientosTable = FXCollections.observableArrayList(this.movimientos);

        tableMovimientos.setItems(movimientosTable);
    }
}
