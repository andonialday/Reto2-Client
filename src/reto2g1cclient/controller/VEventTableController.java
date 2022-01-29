/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import reto2g1cclient.exception.DBServerException;
import reto2g1cclient.logic.EventFactory;
import reto2g1cclient.logic.EventInterface;
import reto2g1cclient.model.Client;
import reto2g1cclient.model.Evento;

/**
 *
 * @author Andoni Alday
 */
public class VEventTableController {

    private static final Logger LOGGER = Logger.getLogger("package.class");
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter database = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private Stage stage;
    private Client client;
    private List<Evento> events = null;
    //private Evento event;
    private Boolean editable;
    private EventInterface ei;
    private ObservableList<Evento> eventData;
    // Booleanos para control de contenido introducido valido
    private Boolean dateEnd;
    private Boolean dateStart;
    private Boolean desc;
    private Boolean name;
    // Booleano para control de seleccion de elemento en tabla para habilitar el Botón Nuevo
    private Boolean tableSelec;
    // Integer para control de filtro seleccionado
    private Integer filter;

    /**
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     *
     * @return
     */
    public Stage getStage() {
        return stage;
    }

    /**
     *
     * @return
     */
    public Client getClient() {
        return client;
    }

    /**
     *
     * @param client
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     *
     * @return
     */
    public List<Evento> getEvents() {
        return events;
    }

    /**
     *
     * @param events
     */
    public void setEvents(List<Evento> events) {
        this.events = events;
    }

    /**
     *
     * @return
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     *
     * @param editable
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     *
     * @return
     */
    public EventInterface getEi() {
        return ei;
    }

    /**
     *
     * @param ei
     */
    public void setEi(EventInterface ei) {
        this.ei = ei;
    }

    /**
     *
     * @return
     */
    public ObservableList<Evento> getEventData() {
        return eventData;
    }

    /**
     *
     * @param eventData
     */
    public void setEventData(ObservableList<Evento> eventData) {
        this.eventData = eventData;
    }

    // Elementos FXML de la ventana
    //Controlador del Menu
    @FXML
    private MenuAdminController hMenuBar;
    //Botones
    /**
     *
     */
    @FXML
    private Button btnBack;
    /**
     *
     */
    @FXML
    private Button btnDelete;
    /**
     *
     */
    @FXML
    private Button btnNew;
    /**
     *
     */
    @FXML
    private Button btnSave;
    /**
     *
     */
    @FXML
    private Button btnSearch;
    /**
     *
     */
    @FXML
    private Button btnPrint;
    //Tabla
    /**
     *
     */
    @FXML
    private TableView<Evento> tbEvent;
    /**
     *
     */
    @FXML
    public TableColumn<Evento, String> clName;
    /**
     *
     */
    @FXML
    public TableColumn<Evento, String> clDateStart;
    /**
     *
     */
    @FXML
    public TableColumn<Evento, String> clDateEnd;
    /**
     *
     */
    @FXML
    public TableColumn<Evento, String> clDescription;
    //Elementos Filtrado Búsqueda   
    /**
     *
     */
    @FXML
    private TextField txtSearch;
    /**
     *
     */
    @FXML
    private ComboBox<String> cbSearch;
    //Elementos Zona Edicion/Creacion
    /**
     *
     */
    @FXML
    private TextField txtName;
    /**
     *
     */
    @FXML
    private TextArea taDescription;
    /**
     *
     */
    @FXML
    private DatePicker dpDateStart;
    /**
     *
     */
    @FXML
    private DatePicker dpDateEnd;
    /**
     *
     */
    @FXML
    private Label lblName;
    /**
     *
     */
    @FXML
    private Label lblDateStart;
    /**
     *
     */
    @FXML
    private Label lblDateEnd;
    /**
     *
     */
    @FXML
    private Label lblDateStartEr;
    /**
     *
     */
    @FXML
    private Label lblDateEndEr;
    /**
     *
     */
    @FXML
    private Label lblDescription;

    /**
     *
     * @param root
     * @throws IOException
     */
    public void initStage(Parent root) throws IOException {

        LOGGER.info("Initializing VEventTable stage.");

        //Create a new scene
        Scene scene = new Scene(root);

        //CSS (route & scene)
        //String css = this.getClass().getResource("/reto2g1cclient/view/javaFXUIStyles.css").toExternalForm();
        //scene.getStylesheets().add(css);
        //Associate the scene to the stage
        stage.setScene(scene);

        //Set the scene properties
        stage.setTitle("Ventana de Gestión de Eventos");
        stage.setResizable(false);

        //Set Windows event handlers 
        stage.setOnShowing(this::handleWindowShowing);
        stage.setOnCloseRequest(this::closeVEventTable);
        //Set Window initial State

        //Leer Eventos Disponibles
        ei = EventFactory.getImplementation();
        loadData();
        // Iniciar Tabla        
        tbEvent.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tbEvent.getSelectionModel().selectedItemProperty().addListener(this::eventTableSelection);
        // Cargar Datos Iniciales en tabla
        loadTable();
        // Seccion Busqueda
        ObservableList<String> filtros = FXCollections.observableArrayList("Nombre del Evento", "Fecha de Inicio", "Fecha de Finalización", "Descripción");
        cbSearch.getItems().addAll(filtros);
        cbSearch.getSelectionModel().selectedItemProperty().addListener(this::selectedFilter);
        filter = 1;
        btnSearch.setOnAction(this::filterEvents);
        // Botones y Fields
        btnPrint.setOnAction(this::printData);
        btnBack.setOnAction(this::back);
        btnDelete.setOnAction(this::deleteEvent);
        btnNew.setOnAction(this::newEvent);
        btnSave.setOnAction(this::saveChanges);
        txtName.textProperty().addListener(this::txtNameVal);
        dpDateStart.valueProperty().addListener(this::dateStartVal);
        dpDateEnd.valueProperty().addListener(this::dateEndVal);
        taDescription.textProperty().addListener(this::taDescVal);
        //Show main window
        stage.show();
        LOGGER.info("VEventTable stage initiated");

    }

    /**
     * Método que determina el valor inicial de los parámetros de control
     * (Booleans e Integer) e inicia los elementos al estado adecuado
     * ([des]habilitado / [in]visible)
     *
     * @param event
     */
    public void handleWindowShowing(WindowEvent event) {
        LOGGER.info("Beginning VEventTableController::handleWindowShowing");
        // Visibilidad de labels
        lblDateEnd.setVisible(true);
        lblDateStart.setVisible(true);
        lblDescription.setVisible(true);
        lblName.setVisible(true);
        lblDateEndEr.setVisible(false);
        lblDateStartEr.setVisible(false);
        // Estado inicial de eventos
        btnBack.setDisable(false);
        btnDelete.setDisable(true);
        btnNew.setDisable(true);
        btnSearch.setDisable(false);
        btnPrint.setDisable(false);
        btnSave.setDisable(true);
        // Inicio de booleanos de control a false
        name = false;
        dateStart = false;
        dateEnd = false;
        desc = false;
        tableSelec = false;
        //Control de edición en función de usuario que accede
        editableSetter();

    }

    /**
     *
     * @param event
     */
    @FXML
    public void back(ActionEvent event) {
        LOGGER.info("Requesting confirmation for application closing...");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Está Cerrando el Programa");
        alert.setHeaderText("¿Seguro que desea cerrar el programa?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
            LOGGER.info("Closing the application");
        } else {
            event.consume();
            LOGGER.info("Closing aborted");
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    public void deleteEvent(ActionEvent event) {
        LOGGER.info("Requesting confirmation for Event Deletion...");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Está Eliminando un Evento");
        alert.setHeaderText("¿Seguro que desea Eliminar el Evento Seleccionado?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ei.remove(tbEvent.getSelectionModel().getSelectedItem());
            loadData();
            loadTable();
            tbEvent.refresh();
        } else {
            event.consume();
            LOGGER.info("Closing aborted");
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    public void newEvent(ActionEvent event) {
        if (dpDateEnd.getValue().isAfter(dpDateStart.getValue()) || dpDateEnd.getValue().isEqual(dpDateStart.getValue())) {
            Evento ev = new Evento();
            ev.setName(txtName.getText().trim());
            ev.setDescription(taDescription.getText().trim());
            ev.setDateStart(dpDateStart.getValue().format(formatter));
            ev.setDateEnd(dpDateEnd.getValue().format(formatter));
            ev = devolverFormatoFechas(ev);
            ei.createEvent(ev);
            events.add(ev);
            loadData();
            loadTable();
            tbEvent.refresh();
            txtName.setText("");
            taDescription.setText("");
            dpDateStart.setValue(null);
            dpDateEnd.setValue(null);
        } else {
            lblDateEndEr.setVisible(true);
            dpDateEnd.setValue(dpDateStart.getValue());
        }

    }

    /**
     *
     * @param event
     */
    @FXML
    public void saveChanges(ActionEvent event) {
        LOGGER.info("Update of data on database requested");
        saveData();
    }

    /**
     *
     * @param event
     */
    public void closeVEventTable(WindowEvent event) {
        LOGGER.info("Requesting confirmation for application closing...");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Está Cerrando la ventana");
        alert.setHeaderText("¿Seguro que desea cerrar el programa?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
            LOGGER.info("Closing the application");
        } else {
            event.consume();
            LOGGER.info("Closing aborted");
        }
    }

    /**
     * Método para cargar los eventos de la base de datos, fiultrandolos por
     * cliente en caso de que de acceda a la ventana desde un cliente
     */
    public void loadData() {
        LOGGER.info("Loading available Events");
        try {
            if (client != null) {
                events = (List<Evento>) ei.findEventByClient(client);
                for (Evento event : events) {
                    System.out.println(event.getId());
                }
            } else {
                events = (List<Evento>) ei.findAll();
                for (Evento event : events) {
                    System.out.println(event.getId());
                }
            }
        } catch (DBServerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error al actualizar datos en el servidor");
            alert.setHeaderText("Ha sucedido un error al intentar guardar los cambios "
                    + "\nen el servidor de datos, por favor, inténtelo más tarde."
                    + "\nSi el error persiste, comuníquese con el sevicio técnico");
            alert.showAndWait();
        }
        cambiarFormatoFechas();
    }

    /**
     * Método para cargar los datos obtenidos en la tabla
     */
    public void loadTable() {
        LOGGER.info("Loading Data on Table");
        eventData = FXCollections.observableArrayList(events);
        tbEvent.setItems(eventData);
        LOGGER.info("Data Loaded on Table");
    }

    /**
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    public void eventTableSelection(ObservableValue observable, Evento oldValue, Evento newValue) {
        // Carga de datos en sección de edición
        if (newValue != null) {
            LOGGER.info("New item selected");
            txtName.setText(newValue.getName());
            dpDateStart.setValue(LocalDate.parse(newValue.getDateStart(), formatter));
            dpDateEnd.setValue(LocalDate.parse(newValue.getDateEnd(), formatter));
            taDescription.setText(newValue.getDescription());
            btnNew.setDisable(true);
            btnDelete.setDisable(false);
            btnSave.setDisable(false);
            name = false;
            desc = false;
            dateStart = false;
            dateEnd = false;
            tableSelec = true;
        } else {
            LOGGER.info("Item Deselected");
            txtName.setText("");
            dpDateStart.setValue(null);
            dpDateEnd.setValue(null);
            taDescription.setText("");
            btnNew.setDisable(true);
            btnDelete.setDisable(true);
            btnSave.setDisable(true);
            name = false;
            desc = false;
            dateStart = false;
            dateEnd = false;
            tableSelec = false;
        }
    }

    /**
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    public void selectedFilter(ObservableValue observable, Object oldValue, Object newValue) {
        // Carga de datos en sección de edición
        LOGGER.info("Filtering parameter selection changed");
        if (newValue != null) {
            switch (cbSearch.getSelectionModel().getSelectedIndex()) {
                // Filtro Nombre
                case 0:
                    filter = 1;
                    break;
                // Filtro Fecha Inicio
                case 1:
                    filter = 2;
                    break;
                // Filtro Fecha Fin
                case 2:
                    filter = 3;
                    break;
                // Filtro Descripcion
                case 3:
                    filter = 4;
                    break;
                default:
                    filter = 0;
            }
        }
        LOGGER.info("Filtering parameter selected: " + newValue);
    }

    /**
     *
     * @param event
     */
    @FXML
    public void filterEvents(ActionEvent event) {
        LOGGER.info("Filtering available Events");
        try {
            Collection temp = null;
            switch (filter) {
                case 1:
                    loadData();
                    temp = events.stream().filter(ev -> !ev.getName().toLowerCase().contains(txtSearch.getText().toLowerCase())).collect(Collectors.toList());
                    events.removeAll(temp);
                    break;
                // Filtro Fecha Inicio
                case 2:
                    loadData();
                    temp = events.stream().filter(ev -> (LocalDate.parse(ev.getDateStart(), formatter)).compareTo(LocalDate.parse(txtSearch.getText(), formatter)) != 0).collect(Collectors.toList());
                    events.removeAll(temp);

                    break;
                // Filtro Fecha Fin
                case 3:
                    loadData();
                    temp = events.stream().filter(ev -> (LocalDate.parse(ev.getDateEnd(), formatter)).compareTo(LocalDate.parse(txtSearch.getText(), formatter)) != 0).collect(Collectors.toList());
                    events.removeAll(temp);
                    break;
                // Filtro Descripcion
                case 4:
                    loadData();
                    temp = events.stream().filter(ev -> !ev.getDescription().toLowerCase().contains(txtSearch.getText().toLowerCase())).collect(Collectors.toList());
                    events.removeAll(temp);
                    break;
                default:
                    loadData();
            }
            loadTable();
            tbEvent.refresh();
        } catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Fecha introducida o válida");
            alert.setHeaderText("Ha realizado una búsqueda por fecha con texto no válido, "
                    + "si desea buscar por fechas,\nintroduzcala en formato DD/MM/AAAA "
                    + "(por ejemplo, 14/02/2008)");
            alert.showAndWait();
        }
    }

    /**
     * Método para comprobar que los campos para crear nuevos eventos están
     * informados y que no hay ningún elemento de la tabla seleccionado para
     * habilitar el botón de nuevo evento
     */
    public void validateData() {
        if (name && dateStart && dateEnd && desc && !tableSelec) {
            btnNew.setDisable(false);
        } else {
            btnNew.setDisable(true);
        }
    }

    /**
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    public void txtNameVal(ObservableValue observable, String oldValue, String newValue) {
        name = false;
        if (!newValue.trim().equals("")) {
            name = true;
        }
        if (newValue.trim().length() > 50) {
            newValue = oldValue;
        }
        validateData();
    }

    /**
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    public void dateStartVal(ObservableValue observable, LocalDate oldValue, LocalDate newValue) {
        dateStart = false;
        if (newValue != null) {
            dateStart = true;
        }
        validateData();
    }

    /**
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    public void dateEndVal(ObservableValue observable, LocalDate oldValue, LocalDate newValue) {
        dateEnd = false;
        if (newValue != null) {
            dateEnd = true;
        }
        validateData();
    }

    /**
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    public void taDescVal(ObservableValue observable, String oldValue, String newValue) {
        desc = false;
        if (!newValue.trim().equals("")) {
            desc = true;
        }
        if (newValue.trim().length() > 400) {
            newValue = oldValue;
        }
        validateData();
    }

    /**
     * Método para iniciar las celdas en la tabla en modo NO editable para el
     * uso de la ventasna por parte de un comercial
     */
    public void initiateNonEditableTableColumns() {
        LOGGER.info("Generating Table Properties");
        //Columna
        // Factoria de Celda para Valor de Propiedades
        // -> Factoria de Celdas para Edicion
        clName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clName.setCellFactory(TextFieldTableCell.<Evento>forTableColumn());
        clDateStart.setCellValueFactory(new PropertyValueFactory<>("dateStart"));
        clDateStart.setCellFactory(TextFieldTableCell.<Evento>forTableColumn());
        clDateEnd.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));
        clDateEnd.setCellFactory(TextFieldTableCell.<Evento>forTableColumn());
        clDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        clDescription.setCellFactory(TextFieldTableCell.<Evento>forTableColumn());
        LOGGER.info("Table Columns initiated");
    }

    /**
     * Método para iniciar las celdas en la tabla en modo editable para el uso
     * de la ventasna por parte de un cliente o el administrador
     */
    public void initiateEditableTableColumns() {
        LOGGER.info("Generating Table Properties");
        //Columna Nombre
        // Factoria de Celda para Valor de Propiedades
        // -> Factoria de Celdas para Edicion
        clName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clName.setCellFactory(TextFieldTableCell.<Evento>forTableColumn());
        clName.setOnEditCommit(this::handleEditCommitName);
        clDateStart.setCellValueFactory(new PropertyValueFactory<>("dateStart"));
        clDateStart.setCellFactory(TextFieldTableCell.<Evento>forTableColumn());
        clDateStart.setOnEditCommit(this::handleEditCommitDateStart);
        clDateEnd.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));
        clDateEnd.setCellFactory(TextFieldTableCell.<Evento>forTableColumn());
        clDateEnd.setOnEditCommit(this::handleEditCommitDateEnd);
        clDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        clDescription.setCellFactory(TextFieldTableCell.<Evento>forTableColumn());
        clDescription.setOnEditCommit(this::handleEditCommitDescription);
    }

    /**
     * Método para controlar la edición de los nombres en la tabla
     *
     * @param t celda de la tabla siendo editada
     */
    private void handleEditCommitName(CellEditEvent<Evento, String> t) {
        if (!t.getNewValue().equals("") && t.getNewValue().length() <= 50) {
            ((Evento) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setName(t.getNewValue());
            txtName.setText(t.getNewValue());
            ei.edit(tbEvent.getSelectionModel().getSelectedItem());
        } else {
            ((Evento) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setName(t.getOldValue());
        }
        tbEvent.refresh();
    }

    /**
     * Método para controlar la edición de las fechas de inicio en la tabla
     *
     * @param t celda de la tabla siendo editada
     */
    private void handleEditCommitDateStart(CellEditEvent<Evento, String> t) {
        try {
            LocalDate.parse(t.getNewValue(), formatter);
            ((Evento) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setDateStart(t.getNewValue());
            dpDateStart.setValue(LocalDate.parse(t.getNewValue(), formatter));
            editandoFecha(tbEvent.getSelectionModel().getSelectedItem());
        } catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error en el formato de la fecha");
            alert.setHeaderText("Ha intentado cambiar la fecha por una no válida."
                    + "\nAsegúrese de que la fecha exista y esté introducida en "
                    + "formato DD/MM/AAAA");
            alert.showAndWait();
            ((Evento) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setDateStart(t.getOldValue());
        }
        tbEvent.refresh();
    }

    /**
     * Método para controlar la edición de las fechas de finalizacion en la
     * tabla
     *
     * @param t celda de la tabla siendo editada
     */
    private void handleEditCommitDateEnd(CellEditEvent<Evento, String> t) {
        try {
            LocalDate.parse(t.getNewValue(), formatter);
            ((Evento) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setDateEnd(t.getNewValue());
            if (correctDateEnd(tbEvent.getSelectionModel().getSelectedItem(), t.getNewValue())) {
                dpDateEnd.setValue(LocalDate.parse(t.getNewValue(), formatter));
                editandoFecha(tbEvent.getSelectionModel().getSelectedItem());
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error en el valor de la fecha");
                alert.setHeaderText("La fecha de fnalizacion no puee ser anterior "
                        + "a la fecha de inicio");
                alert.showAndWait();
                ((Evento) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setDateEnd(t.getOldValue());
            }
        } catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error en el formato de la fecha");
            alert.setHeaderText("Ha intentado cambiar la fecha por una no válida."
                    + "\nAsegúrese de que la fecha exista y esté introducida en "
                    + "formato DD/MM/AAAA");
            alert.showAndWait();
            ((Evento) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setDateEnd(t.getOldValue());
        }
        tbEvent.refresh();
    }

    /**
     * Método para controlar la edición de las descripciones en la tabla
     *
     * @param t celda de la tabla siendo editada
     */
    private void handleEditCommitDescription(CellEditEvent<Evento, String> t) {
        if (!t.getNewValue().equals("") && t.getNewValue().length() <= 400) {
            ((Evento) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setDescription(t.getNewValue());
            taDescription.setText(t.getNewValue());
            ei.edit(tbEvent.getSelectionModel().getSelectedItem());
        } else {
            ((Evento) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setDescription(t.getOldValue());
        }
        tbEvent.refresh();
    }

    /**
     * Método ejecutado por el botón imprimir
     *
     * @param event evento ejecutado por el botón imprimir
     */
    public void printData(ActionEvent event) {
        LOGGER.info("Preparing to print");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Proceso de impresión de informes");
        alert.setHeaderText("Ha solicitado imprimir un informe, para ello, se "
                + "\nvan a actualizar los datos de la tabla en la base de datos. "
                + "\n¿Está seguro de continuar?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            saveData();
            print();
        } else {
            event.consume();
        }
    }

    /**
     * Método para guarar los cambios realizados en la base de datos
     */
    public void saveData() {
        //try {
        LOGGER.info("Updating changes on DataBase");
        // Lee todos los eventos de la base de datos
        for (Evento ev : events) {
            ev = devolverFormatoFechas(ev);
            ei.edit(ev);
        }
        loadData();
        loadTable();
        tbEvent.refresh();
        LOGGER.info("Updating changes on DataBase");
        /*} catch (DBServerException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error al actualizar datos en el servidor");
            alert.setHeaderText("Ha sucedido un error al intentar guardar los cambios "
                    + "\nen el servidor de datos, por favor, inténtelo más tarde."
                    + "\nSi el error persiste, comuníquese con el sevicio técnico");
            alert.showAndWait();
        }
        catch (ClientServerConnectionException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error al conectar con en el servidor");
            alert.setHeaderText("Ha sucedido un error al intentar conectar con el "
                    + "\nservidor, por favor, inténtelo más tarde."
                    + "\nSi el error persiste, comuníquese con el servicio técnico");
            alert.showAndWait();
        }*/
    }

    /**
     * Método para imprimir un informe con los datos de la tabla
     */
    public void print() {
        try {
            // Carga del informe para EventTable
            JasperReport report = JasperCompileManager.compileReport("/reto2g1cclient/reports/EventReport.jrxml");
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Evento>) this.tbEvent.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            JasperViewer jasperviewer = new JasperViewer(jasperPrint);
            jasperviewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(VEventTableController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método para activar las opciones de edicion en los elementos de la
     * ventana si el usuario que la usa tiene lor permisos adecuados
     */
    private void editableSetter() {
        //Si accede a la vista de eventos un cliente o  un administrador, editable será true y se podrán 
        //editar la tabla y lños campos superiores, si accede un comercial, editable será false y no serán editables
        txtName.setEditable(editable);
        taDescription.setEditable(editable);
        dpDateEnd.setEditable(editable);
        dpDateStart.setEditable(editable);
        tbEvent.setEditable(editable);
        if (editable) {
            // Si inician Admin o Cliente, editable = true -> Tabla Editable
            initiateEditableTableColumns();
        } else {
            // Si inicia comercial, editable = false -> Tabla NO Editable, TextFields, DatePicker y TextArea deshabilitados
            initiateNonEditableTableColumns();
            ((DatePickerSkin) dpDateEnd.getSkin()).getPopupContent().setVisible(editable);
            ((DatePickerSkin) dpDateStart.getSkin()).getPopupContent().setVisible(editable);
        }
    }

    /**
     * Método para cambiar el formato de las fechas de los eventos del de la
     * base de datos () al que se desea mostrar (AAAA-DD-MMTHH:mm:ss+TMZ)
     */
    public void cambiarFormatoFechas() {
        for (Evento ev : events) {
            LocalDate fecha = LocalDate.parse(ev.getDateStart(), database);
            String dt = fecha.format(formatter);
            ev.setDateStart(dt);
            fecha = LocalDate.parse(ev.getDateEnd(), database);
            dt = fecha.format(formatter);
            ev.setDateEnd(dt);
        }
    }

    /**
     * Método para devolver al evento las fechas del formato que se desea
     * mostrar (DD/MM/AAAA) al formato que emplea la BBDD
     * (AAAA-DD-MMTHH:mm:ss+TMZ)
     *
     * @param event evento al que se va a actualizar las fechas
     * @return evento con las fechas actualizadas
     */
    public Evento devolverFormatoFechas(Evento event) {
        LocalDate date = LocalDate.parse(event.getDateStart(), formatter);
        String fecha = date.atStartOfDay().atZone(ZoneId.systemDefault()).format(database);
        event.setDateStart(fecha);
        date = LocalDate.parse(event.getDateEnd(), formatter);
        fecha = date.atStartOfDay().atZone(ZoneId.systemDefault()).format(database);
        event.setDateEnd(fecha);
        return event;
    }

    /**
     * Método para actualizar la fecha en la Base de Datos convirtiendo la fecha
     * mostrada (DD/MM/AAAA) en el formato de la BBDD (AAAA-DD-MMTHH:mm:ss+TMZ)
     * y tras actualizarlo volviendo a convertirla en el formato que se desea
     * mostrar
     *
     * @param event el evento del que se está modificando la fecha
     */
    public void editandoFecha(Evento event) {
        LocalDate date = LocalDate.parse(event.getDateStart(), formatter);
        String fecha = date.atStartOfDay().atZone(ZoneId.systemDefault()).format(database);
        event.setDateStart(fecha);
        LocalDate date2 = LocalDate.parse(event.getDateEnd(), formatter);
        String fecha2 = date2.atStartOfDay().atZone(ZoneId.systemDefault()).format(database);
        event.setDateEnd(fecha2);
        ei.edit(event);
        date = LocalDate.parse(event.getDateStart(), database);
        fecha = date.format(formatter);
        event.setDateStart(fecha);
        date = LocalDate.parse(event.getDateEnd(), database);
        fecha = date.format(formatter);
        event.setDateEnd(fecha);
    }

    private boolean correctDateEnd(Evento ev, String dateEnd) {
        boolean correct = false;
        System.out.println(ev.getDateStart() + "\t" + dateEnd);
        return correct;
    }
}
