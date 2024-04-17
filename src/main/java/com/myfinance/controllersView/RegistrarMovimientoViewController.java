package com.myfinance.controllersView;


import com.myfinance.entities.Cuenta;
import com.myfinance.entities.Usuario;
import com.myfinance.facade.FacadeMovimiento;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class RegistrarMovimientoViewController implements InterfaceControllerView {

    private FacadeMovimiento facade;
    private Cuenta cuenta;
    private Connection conn;
    private Usuario usuario;
    private String movimientoTipo;
    @FXML
    public MenuButton optionTipo;
    @FXML
    public TextField txtFieldNombre;
    @FXML
    public TextField txtFieldMonto;
    @FXML
    public DatePicker datePickerFecha;
    @FXML
    public TextArea txtAreaDesc;
    @FXML
    public Button btnRegistrar;
    @FXML
    public Button btnVolver;

    public void onActionRegistrar() {
        String errorCuerpo = "Error en los campos para crear movimiento";
        try{
            if(crearMovimiento()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Movimiento Creado");
                alert.show();
                onActionVolver();
                GeneralControllerView.getInstance().showScreen("View_Inicio/InicioView.fxml", this.conn, this.usuario, this.cuenta);
            }
        }catch(Exception e){
            mostrarError(errorCuerpo);
        }
    }

    public void onActionVolver() {
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        stage.close();
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
        this.movimientoTipo = null;
    }

    private boolean crearMovimiento(){
        boolean creado = false;
        LocalDate fecha = datePickerFecha.getValue();
        String movimientoDesc = txtAreaDesc.getText();
        String movimientoNombre = txtFieldNombre.getText();
        Double monto = Double.valueOf(txtFieldMonto.getText());

        if(verificarMovimiento(movimientoNombre, monto)){
            LocalDateTime registrold = LocalDateTime.now();
            Timestamp registro = Timestamp.valueOf(registrold);
            if(Objects.equals(this.movimientoTipo, "Egreso")){monto = monto*-1;}
            Double saldoViejo = this.cuenta.getSaldo();
            creado = this.facade.crearMovimiento(this.cuenta.getCuentaID(), this.movimientoTipo, movimientoNombre, movimientoDesc, monto, fecha, registro, saldoViejo);
            this.cuenta.setSaldo(saldoViejo+monto);
        }

        return creado;
    }

    private boolean verificarMovimiento(String movimientoNombre, Double monto){
        boolean valido = true;
        if (!verificarNombreMovimiento(movimientoNombre)){valido=false;}
        if (!verificarMonto(monto)){valido=false;}
        if (!verificarTipo()){valido=false;}
        return valido;
    }


    private boolean verificarNombreMovimiento(String movimientoNombre){
        boolean valido = true;
        if (movimientoNombre.isEmpty()){
            String error = "Nombre vacio";
            mostrarError(error);
            valido = false;
        }
        return valido;
    }

    private boolean verificarMonto(Double monto){
        boolean valido = true;
        if (monto <= 0){
            String error = "Monto inválido";
            mostrarError(error);
            valido = false;
        }
        return valido;
    }

    private boolean verificarTipo(){
        boolean valido = true;
        if (this.movimientoTipo==null){
            String error = "Tipo de movimiento no seleccionado";
            mostrarError(error);
            valido=false;
        }
        return valido;
    }

    private void mostrarError(String error){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error al crear movimiento");
        alert.setContentText(error);
        alert.show();
    }

    public void onActionSelectIngreso() {
        this.movimientoTipo = "Ingreso";
        optionTipo.setText("Ingreso");
    }

    public void onActionSelectEgreso() {
        this.movimientoTipo = "Egreso";
        optionTipo.setText("Egreso");
    }
}
