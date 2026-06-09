package PuntaElPozo.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

import PuntaElPozo.Dao.BuceadorDAO;
import PuntaElPozo.Model.Buceador;
import PuntaElPozo.Model.GrupoSanguineo;
import PuntaElPozo.Persistence.BuceadorFileDat;
import PuntaElPozo.Persistence.SincronizarBuceadores;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

public class BuceadorController {
    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnLimpiar;

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
    private ComboBox<GrupoSanguineo> cbGrupoSanguineo;

    @FXML
    private TextField txtTitulacionActual;

    @FXML
    private TextField txtOrganizacion;

    @FXML
    private TextField txtNumeroInmersiones;

    @FXML
    private TextArea txtAlergias;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableView<Buceador> tablaBuceadores;

    @FXML
    private Label lblEstado;

    private final BuceadorDAO buceadorDAO = new BuceadorDAO();
    private final ObservableList<Buceador> observableBuceadores = FXCollections.observableArrayList();
    private final SincronizarBuceadores SincronizarBuceadores = new SincronizarBuceadores(buceadorDAO,
            new BuceadorFileDat("buceadores.dat"));

    private Map<Integer, Buceador> mapaBuceadores = new TreeMap<>();

    @FXML
    public void initialize() {
        cbGrupoSanguineo.setItems(FXCollections.observableArrayList(GrupoSanguineo.values()));
        tablaBuceadores.setItems(observableBuceadores);
        tablaBuceadores.getSelectionModel().selectedItemProperty().addListener((observable, anterior, buceador) -> {
            if (buceador != null) {
                mostrarBuceadorEnFormulario(buceador);
            }

            actualizarBotones();
        });

        try {

            mapaBuceadores = SincronizarBuceadores.cargar();
            actualizarTabla();
            lblEstado.setText("Buceadores cargados correctamente");

        } catch (IOException | ClassNotFoundException | RuntimeException e) {

            mapaBuceadores = new TreeMap<>();
            actualizarTabla();
            lblEstado.setText("No hay datos cargados la aplicacion se inicio con una tabla vacia");
            mostrarError("Datos no disponibles", "No se pudieron cargar los datos", e);
        }
    }

    @FXML
    private void onGuardar() {
        try {
            Buceador buceador = crearBuceadorDesdeFormulario();
            boolean guardadoEnMySQL;

            try {
                guardadoEnMySQL = buceadorDAO.insertar(buceador);
            } catch (SQLException e) {
                guardadoEnMySQL = false;
            }

            mapaBuceadores.put(buceador.getId(), buceador);
            SincronizarBuceadores.guardar(mapaBuceadores);
            actualizarTabla();
            limpiarFormulario();
            lblEstado.setText(guardadoEnMySQL
                    ? "Buceador registrado correctamente"
                    : "Buceador registrado en copia local MySQL no esta disponible");

        } catch (IllegalArgumentException e) {
            mostrarAviso("Dato incorrecto", e.getMessage());

        } catch (IOException e) {
            mostrarError("Error al guardar", "No se pudo guardar la copia local", e);
        }
    }

    @FXML
    private void onModificar() {
        Buceador seleccionado = tablaBuceadores.getSelectionModel().getSelectedItem();

        try {
            Buceador buceador = crearBuceadorDesdeFormulario();
            buceador.setId(seleccionado.getId());

            boolean actualizadoEnMySQL;

            try {
                actualizadoEnMySQL = buceadorDAO.actualizar(buceador);
            } catch (SQLException e) {
                actualizadoEnMySQL = false;
            }

            mapaBuceadores.put(buceador.getId(), buceador);
            SincronizarBuceadores.guardar(mapaBuceadores);
            actualizarTabla();
            limpiarFormulario();
            tablaBuceadores.getSelectionModel().clearSelection();
            lblEstado.setText(actualizadoEnMySQL
                    ? "Buceador modificado correctamente"
                    : "Buceador modificado en copia local. MySQL no esta disponible");

        } catch (IllegalArgumentException e) {
            mostrarAviso("Dato incorrecto", e.getMessage());
        } catch (IOException e) {
            mostrarError("Error al guardar", "No se pudo guardar la copia local", e);
        }
    }

    @FXML
    private void onEliminar() {
        Buceador seleccionado = tablaBuceadores.getSelectionModel().getSelectedItem();

        try {
            boolean eliminadoEnMySQL = false;
            if (seleccionado.getId() != null) {
                try {
                    eliminadoEnMySQL = buceadorDAO.eliminar(seleccionado.getId());
                } catch (SQLException e) {
                    eliminadoEnMySQL = false;
                }
            }

            mapaBuceadores.remove(seleccionado.getId());
            SincronizarBuceadores.guardar(mapaBuceadores);
            actualizarTabla();
            limpiarFormulario();
            tablaBuceadores.getSelectionModel().clearSelection();
            lblEstado.setText(eliminadoEnMySQL
                    ? "Buceador eliminado correctamente"
                    : "Buceador eliminado de la copia local MySQL no esta disponible");

        } catch (IOException e) {
            mostrarError("Error al guardar", "No se pudo guardar la copia local", e);
        }
    }

    @FXML
    private void onLimpiar() {
        tablaBuceadores.getSelectionModel().clearSelection();
        limpiarFormulario();
        lblEstado.setText("Formulario limpiado");
    }

    private Buceador crearBuceadorDesdeFormulario() {
        Buceador buceador = new Buceador(obtenerTextoCampo(txtDniNiePasaporte));

        buceador.setNombre(obtenerTextoCampo(txtNombre));
        buceador.setApellidos(obtenerTextoCampo(txtApellidos));
        buceador.setEmail(obtenerTextoCampo(txtEmail));
        buceador.setTelefono(obtenerTextoCampo(txtTelefono));

        if (dpFechaNacimiento.getValue() != null) {
            buceador.setFechaNacimiento(dpFechaNacimiento.getValue());
        }

        buceador.setFechaAlta(dpFechaAlta.getValue());

        if (dpFechaUltimoReconocimientoMedico.getValue() != null) {
            buceador.setFechaUltimoReconocimiento(dpFechaUltimoReconocimientoMedico.getValue());
        }

        buceador.setFechaCaducidadSeguro(dpFechaCaducidadSeguro.getValue());
        buceador.setCompaniaSeguro(obtenerTextoCampo(txtCompaniaSeguro));
        buceador.setContactoEmergNombre(obtenerTextoCampo(txtContactoEmergenciaNombre));
        buceador.setContactoEmergTelefono(obtenerTextoCampo(txtContactoEmergenciaTelefono));
        buceador.setGrupoSanguineo(cbGrupoSanguineo.getValue());
        buceador.setAlergias(obtenerTextoCampo(txtAlergias));
        buceador.setTitulacionActual(obtenerTextoCampo(txtTitulacionActual));
        buceador.setOrganizacion(obtenerTextoCampo(txtOrganizacion));
        buceador.setNumeroInmersiones(obtenerNumeroInmersiones());

        return buceador;
    }

    private void mostrarBuceadorEnFormulario(Buceador buceador) {
        txtDniNiePasaporte.setText(buceador.getDni());
        txtNombre.setText(buceador.getNombre());
        txtApellidos.setText(buceador.getApellidos());
        txtEmail.setText(buceador.getEmail());
        txtTelefono.setText(buceador.getTelefono());
        dpFechaNacimiento.setValue(buceador.getFechaNacimiento());
        dpFechaAlta.setValue(buceador.getFechaAlta());
        dpFechaUltimoReconocimientoMedico.setValue(buceador.getFechaUltimoReconocimiento());
        dpFechaCaducidadSeguro.setValue(buceador.getFechaCaducidadSeguro());
        txtCompaniaSeguro.setText(buceador.getCompaniaSeguro());
        txtContactoEmergenciaNombre.setText(buceador.getContactoEmergNombre());
        txtContactoEmergenciaTelefono.setText(buceador.getContactoEmergTelefono());
        cbGrupoSanguineo.setValue(buceador.getGrupoSanguineo());
        txtTitulacionActual.setText(buceador.getTitulacionActual());
        txtOrganizacion.setText(buceador.getOrganizacion());
        txtNumeroInmersiones.setText(String.valueOf(obtenerNumeroInmersiones()));
        txtAlergias.setText(buceador.getAlergias());
    }

    private void limpiarFormulario() {
        txtDniNiePasaporte.clear();
        txtNombre.clear();
        txtApellidos.clear();
        txtEmail.clear();
        txtTelefono.clear();
        dpFechaNacimiento.setValue(null);
        dpFechaAlta.setValue(LocalDate.now());
        dpFechaUltimoReconocimientoMedico.setValue(null);
        dpFechaCaducidadSeguro.setValue(null);
        txtCompaniaSeguro.clear();
        txtContactoEmergenciaNombre.clear();
        txtContactoEmergenciaTelefono.clear();
        cbGrupoSanguineo.setValue(null);
        txtTitulacionActual.clear();
        txtOrganizacion.clear();
        txtNumeroInmersiones.clear();
        txtAlergias.clear();
    }

    private void actualizarTabla() {
        observableBuceadores.setAll(mapaBuceadores.values());
        tablaBuceadores.setItems(observableBuceadores);
        tablaBuceadores.refresh();
        actualizarBotones();
    }

    private void actualizarBotones() {
        boolean seleccionado = tablaBuceadores.getSelectionModel().getSelectedItem() != null;
        btnModificar.setDisable(!seleccionado);
        btnEliminar.setDisable(!seleccionado);
    }

    private int obtenerNumeroInmersiones() {
        String texto = obtenerTextoCampo(txtNumeroInmersiones);
        return texto.isBlank() ? 0 : Integer.parseInt(texto);
    }

    private String obtenerTextoCampo(TextInputControl campo) {
        return campo.getText() == null ? "" : campo.getText().trim();
    }

    private void mostrarAviso(String titulo, String mensaje) {
        mostrarAlerta(Alert.AlertType.WARNING, titulo, mensaje, null);
    }

    private void mostrarError(String titulo, String mensaje, Exception e) {
        mostrarAlerta(Alert.AlertType.ERROR, titulo, mensaje, e);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje, Exception e) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(titulo);

        if (e == null || e.getMessage() == null || e.getMessage().isBlank()) {
            alerta.setContentText(mensaje);
        } else {
            alerta.setContentText(mensaje + ": " + e.getMessage());
        }

        alerta.showAndWait();
    }
}
