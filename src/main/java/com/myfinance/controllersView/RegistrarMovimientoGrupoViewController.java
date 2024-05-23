package com.myfinance.controllersView;

import com.myfinance.entities.Cuenta;
import com.myfinance.entities.Grupo;
import com.myfinance.entities.Usuario;
import com.myfinance.facade.FacadeGrupo;
import com.myfinance.facade.FacadeMovimiento;
import com.myfinance.facade.FacadeUsuario;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegistrarMovimientoGrupoViewController implements InterfaceControllerView{
    public MenuButton optionTipo;
    public TextField txtFieldNombre;
    public TextField txtFieldMonto;
    public DatePicker datePickerFecha;
    public TextArea txtAreaDesc;
    public Button btnRegistrar;
    public Button btnVolver;
    public ComboBox<Cuenta> CBCuenta;
    private Connection conn;
    private Usuario usuario;
    private Grupo grupo;
    private FacadeGrupo facade;
    private String movimientoTipo;
    private Cuenta cuenta;
    private FacadeMovimiento facadeMovimiento;
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
        if (params[2] instanceof Grupo){
            this.grupo = (Grupo) params[2];
        }
        this.movimientoTipo = null;
        this.facadeMovimiento = new FacadeMovimiento(this.conn);
    }

    public void seleccionarCuenta(ActionEvent actionEvent) {
        this.cuenta = CBCuenta.getSelectionModel().getSelectedItem( );
    }

    public void listarCuentas(Event event) {
        if(Objects.equals(this.movimientoTipo, "Ingreso")){
            List<Cuenta> cuentas = new ArrayList<>();
            System.out.println(this.usuario.getNombre());
            for (Cuenta c : this.facade.getCuentas(this.usuario)) {
                cuentas.add(c);
            }
            this.CBCuenta.getItems().addAll(cuentas);
            this.CBCuenta.setConverter(new StringConverter<Cuenta>() {
                @Override
                public String toString(Cuenta cuenta) {
                    return cuenta.getNombreCuenta();
                }

                @Override
                public Cuenta fromString(String s) {
                    return null;
                }
            });
        }
    }

    public void onActionSelectIngreso() {
        this.movimientoTipo = "Ingreso";
        optionTipo.setText("Ingreso");
    }

    public void onActionSelectEgreso() {
        this.movimientoTipo = "Egreso";
        optionTipo.setText("Egreso");
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
            if(Objects.equals(this.movimientoTipo, "Egreso")){
                monto = monto*-1;
                Double saldoViejo = this.grupo.getBalance();
                if(saldoViejo + monto < 0)
                {
                    return false;
                }
                creado = this.facade.crearMovimiento(this.movimientoTipo, movimientoNombre, movimientoDesc, monto, fecha, registro, saldoViejo, this.grupo.getGrupoId());
                this.grupo.setBalance(saldoViejo+monto);
            }
            else {
                Double saldoViejoCuenta = this.cuenta.getSaldo();
                creado = this.facadeMovimiento.crearMovimiento(this.cuenta.getCuentaID(), this.movimientoTipo, movimientoNombre, movimientoDesc, -monto, fecha, registro, saldoViejoCuenta);
                Double saldoViejo = this.grupo.getBalance();
                creado = this.facade.crearMovimiento(this.movimientoTipo, movimientoNombre, movimientoDesc, monto, fecha, registro, saldoViejo, this.grupo.getGrupoId());
                this.grupo.setBalance(saldoViejo+monto);
            }
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

    public void onActionRegistrar() {
        String errorCuerpo = "Error en los campos para crear movimiento";
        try{
            if(crearMovimiento()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Movimiento Creado");
                alert.show();
                onActionVolver();
                GeneralControllerView.getInstance().showScreen("View_Grupos/menuGruposViewController.fxml", this.conn, this.usuario);
            }
        }catch(Exception e){
            mostrarError(errorCuerpo);
        }
    }

    public void onActionVolver() {
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        stage.close();
    }
}
