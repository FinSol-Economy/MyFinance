package com.myfinance.controllersView;

import com.myfinance.entities.Grupo;
import com.myfinance.entities.Movimiento;
import com.myfinance.entities.Usuario;
import com.myfinance.facade.FacadeGrupo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MenuGruposViewController implements InterfaceControllerView{

    public ComboBox<Grupo> CBgrupos;
    public Button btnCrearGrup;
    public Button btnVolver;
    public ListView ListIntegrantes;
    public ComboBox<Usuario> CBnuevoInt;
    public Button btnAñadirInt;
    public Button btnagregarMov;
    public Label balanceCuenta;
    public ListView listaDeMovimientos;
    private Grupo grupo;
    private Usuario usuario;
    private FacadeGrupo facade;
    private Connection conn;
    private Usuario newUser = null;
    private List<Grupo> ngrups;
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
        this.grupo = null;
        this.ngrups = new ArrayList<>();
        this.ngrups.addAll(this.facade.getGruposUsuario(this.usuario));
    }

    public void listarGrupos(Event event) {
        this.CBgrupos.getItems().setAll(this.ngrups);
        this.CBgrupos.setConverter(new StringConverter<Grupo>() {
            @Override
            public String toString(Grupo grupo) {
                return grupo.getNombreGrupo();
            }

            @Override
            public Grupo fromString(String s) {
                return null;
            }
        });

    }

    public void volverMenu(ActionEvent actionEvent) throws IOException {
        GeneralControllerView.getInstance().showScreen("View_Cuentas/CuentaView.fxml", this.conn, this.usuario);
    }

    public void crearGrupo(ActionEvent actionEvent) throws IOException {
        GeneralControllerView.getInstance().showScreen("View_Grupos/crearGrupo.fxml", this.conn, this.usuario);
    }

    public void seleccionarGrupo(ActionEvent actionEvent) {
        this.grupo = CBgrupos.getSelectionModel().getSelectedItem();
        List<String> inte = new ArrayList<>();
        inte = this.facade.getIntegrantes(this.grupo);
        ObservableList<String> observableList = FXCollections.observableArrayList(inte);
        this.ListIntegrantes.setItems(observableList);
        List<String> mov = new ArrayList<>();
        this.balanceCuenta.setText(String.valueOf(this.grupo.getBalance()));
        for (Movimiento m : this.facade.getMovimientos(this.grupo.getGrupoId()))
        {
            mov.add(m.getMovimientoFecha()+ " " + m.getMovimientoNombre() + " " + m.getMonto());
        }
        ObservableList<String> observableList2 = FXCollections.observableArrayList(mov);
        this.listaDeMovimientos.setItems(observableList2);
    }

    public void seleccionarIntegrante(ActionEvent actionEvent) {
        this.newUser =  CBnuevoInt.getSelectionModel().getSelectedItem( );
    }

    public void mostrarUsuarios(Event event) {
        if (this.grupo == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Grupo no seleccionado");
            alert.setContentText("Seleccione un grupo antes de seleccionar usuarios");
            alert.showAndWait();
        }
        else{
            showUsers();
        }
    }

    public void añadirIntegrante(ActionEvent actionEvent) {
        this.facade.añadirMiembro(grupo,newUser);
        List<String>inte = this.facade.getIntegrantes(this.grupo);
        ObservableList<String> observableList = FXCollections.observableArrayList(inte);
        this.ListIntegrantes.setItems(observableList);
    }

    public void agregarMovimiento(ActionEvent actionEvent) throws IOException {
        if (this.grupo == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Grupo no seleccionado");
            alert.setContentText("Seleccione un grupo antes de ingresar un movimiento");
            alert.showAndWait();
        }else{
            GeneralControllerView.getInstance().showSecundaryScreen("View_Grupos/RegistrarMovimientoGrupoView.fxml", "Registrar movimiento grupo",this.conn, this.usuario, this.grupo);
            this.balanceCuenta.setText(String.valueOf(this.grupo.getBalance()));
        }
    }

    private void showUsers(){
        List<Usuario> usuarios = new ArrayList<>();
        List<String> usgrupo = this.facade.getIntegrantes(this.grupo);
        boolean flag = false;
        for (Usuario u : this.facade.getUsuarios()) {
            for (String s : usgrupo)
            {
                if(u.getNombre().equals(s))
                {
                    flag = true;
                }
            }
            if(!flag)
            {
                usuarios.add(u);
            }
            flag = false;
        }
        this.CBnuevoInt.getItems().setAll(usuarios);
        this.CBnuevoInt.setConverter(new StringConverter<Usuario>() {
            @Override
            public String toString(Usuario usuario) {
                return usuario.getNombre();
            }

            @Override
            public Usuario fromString(String s) {
                return null;
            }
        });
    }
}
