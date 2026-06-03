package PuntaElPozo.Controller;

import java.io.IOException;

import PuntaElPozo.Model.Buceador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BuceadorController {
    @FXML
    private TableView<Buceador> tablaBuceadores;


    @FXML
    private TableColumn<Buceador, String> colNombre;

    @FXML
    private TableColumn<Buceador, String> colApellidos;

    @FXML
    private TableColumn<Buceador, String> colEmail;

    @FXML
    private TableColumn<Buceador, String> colTelefono;


    @FXML
    private TableColumn<Buceador, String> colFechaAlta;

    @FXML
    private TableColumn<Buceador, String> colFechaCaducidadSeguro;


    @FXML
    private TableColumn<Buceador, String> colTitulacionActual;


    @FXML
    private TableColumn<Buceador, String> colNumeroInmersiones;   

    private static Buceador construirBuceadorFormulario() {
        return null;
    }

    private static void mostrarBuceadorFormulario(Buceador buceador) {

    }

    private static void limpuarFormulario() {

    }

    private static boolean validarFormulario() {
        return true;
    }

    private static void mostrarError(String mensaje, Exception exception) {

    }
    @FXML
    private TextField txtDniNiePasaporte;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefono;
    @FXML
    private DatePicker dpFechaNacimiento;
    @FXML
    private DatePicker dpFechaAlta;
    @FXML
    private DatePicker dpFechaUltimoReconocimientoMedico;
    @FXML
    private DatePicker dpFechaCaducidadSeguro;
    @FXML
    private TextField txtCompaniaSeguro;
    @FXML
    private TextField txtContactoEmergenciaNombre;
    @FXML
    private TextField txtContactoEmergenciaTelefono;
    @FXML
    private TextField txtGrupoSanguineo;
    @FXML
    private TextField txtTitulacionActual;
    @FXML
    private TextField txtOrganizacion;
    @FXML
    private TextField txtNumeroInmersiones;
    @FXML
    private TextArea txtAlergias;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnLimpiar;
    @FXML
    private TextField txtBuscar;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnMostrarTodos;
    @FXML
    private TableColumn<?, ?> colIdBuceador;
    @FXML
    private TableColumn<?, ?> colDniNiePasaporte;
    @FXML
    private Label lblEstado;

    @FXML
    private void onGuardar(ActionEvent event) {
    }

    @FXML
    private void onModificar(ActionEvent event) {
    }

    @FXML
    private void onEliminar(ActionEvent event) {
    }

    @FXML
    private void onLimpiar(ActionEvent event) {
    }

    @FXML
    private void onBuscar(ActionEvent event) {
    }

    @FXML
    private void onMostrarTodos(ActionEvent event) {
    }
}
