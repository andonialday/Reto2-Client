/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
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
    private Stage stage;
    private Client client;
    private List<Evento> events;
    private Evento event;
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
    public Evento getEvent() {
        return event;
    }

    /**
     *
     * @param event
     */
    public void setEvent(Evento event) {
        this.event = event;
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
    //@FXML
    //private MenuAdminController hMenuBar;
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
        String css = this.getClass().getResource("/reto2g1cclient/view/javaFXUIStyles.css").toExternalForm();
        scene.getStylesheets().add(css);

        //Associate the scene to the stage
        stage.setScene(scene);

        //Set the scene properties
        stage.setTitle("SignIn");
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
        btnBack.setDisable(!false);
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
            ei.remove(this.event);
            events.remove(event);
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
            Evento ev = null;
            ev.setName(txtName.getText().trim());
            ev.setDescription(taDescription.getText().trim());
            ev.setDateStart(dpDateStart.getValue().format(formatter));
            ev.setDateEnd(dpDateEnd.getValue().format(formatter));
            ei.createEvent(ev);
            events.add(ev);
            loadTable();
            tbEvent.refresh();
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
     *
     */
    public void loadData() {
        LOGGER.info("Loading available Events");
        try {
            if (client != null) {
                events = ei.findEventByClient(client);
            } else {
                events = ei.findAll();
            }
        } catch (Exception e) {
        }
    }

    /**
     *
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
    public void eventTableSelection(ObservableValue observable, Object oldValue, Object newValue) {
        // Carga de datos en sección de edición
        if (newValue != null) {
            LOGGER.info("New item selected");
            event = (Evento) newValue;
            txtName.setText(event.getName());
            dpDateStart.setValue(LocalDate.parse(event.getDateStart(), formatter));
            dpDateEnd.setValue(LocalDate.parse(event.getDateEnd(), formatter));
            taDescription.setText(event.getDescription());
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
            txtName.setText(null);
            dpDateStart.setValue(null);
            dpDateEnd.setValue(null);
            taDescription.setText(null);
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
        LOGGER.info("Filtering parameter selected: " + filter);
    }

    /**
     *
     * @param event
     */
    @FXML
    public void filterEvents(ActionEvent event) {
        LOGGER.info("Filtering available Events");
        LocalDate date = null;
        if (filter == 2 || filter == 3) {
            date = LocalDate.parse(txtSearch.getText(), formatter);
        }
        switch (filter) {
            case 1:
                loadData();
                for (Evento ev : events) {
                    // Comprobando si el nombre del evento contiene el texto del cuadro de busqueda
                    if (!ev.getName().toLowerCase().contains(txtSearch.getText().toLowerCase().trim())) {
                        events.remove(ev);
                    }
                }
                break;
            // Filtro Fecha Inicio
            case 2:
                loadData();
                for (Evento ev : events) {
                    // Comprobando si coincide la fecha de inicio del evento
                    if ((LocalDate.parse(ev.getDateStart(), formatter)).compareTo(date) != 0) {
                        events.remove(ev);
                    }
                }
                break;
            // Filtro Fecha Fin
            case 3:
                loadData();
                for (Evento ev : events) {
                    // Comprobando si coincide la fecha de inicio del evento
                    if ((LocalDate.parse(ev.getDateEnd(), formatter)).compareTo(date) != 0) {
                        events.remove(ev);
                    }
                }
                break;
            // Filtro Descripcion
            case 4:
                loadData();
                for (Evento ev : events) {
                    // Comprobando si el nombre del evento contiene el texto del cuadro de busqueda
                    if (!ev.getDescription().toLowerCase().contains(txtSearch.getText().toLowerCase().trim())) {
                        events.remove(ev);
                    }
                    break;
                }
            default:
                loadData();
        }
        loadTable();

    }

    /**
     *
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
    public void dateStartVal(ObservableValue observable, Object oldValue, Object newValue) {
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
    public void dateEndVal(ObservableValue observable, Object oldValue, Object newValue) {
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
     *
     */
    public void initiateNonEditableTableColumns() {
        LOGGER.info("Generating Table Properties");
        //Columna Nombre
        // Factoria de Celda para Valor de Propiedades
        // -> Factoria de Celdas para Edicion
        // - - -> lambda para controlar edicion de contenido
        clName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clName.setCellFactory(TextFieldTableCell.<Evento>forTableColumn());
        //Columna Nombre
        // Factoria de Celda para Valor de Propiedades
        // -> Factoria de Celdas para Edicion
        // - - -> Método externo para control de fecha
        clDateStart.setCellValueFactory(new PropertyValueFactory<>("dateStart"));
        clDateStart.setCellFactory(TextFieldTableCell.<Evento>forTableColumn());
        //Columna Nombre
        // Factoria de Celda para Valor de Propiedades
        // -> Factoria de Celdas para Edicion
        // - - -> Método externo para control de fecha
        clDateEnd.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));
        clDateEnd.setCellFactory(TextFieldTableCell.<Evento>forTableColumn());
        //Columna Nombre
        // Factoria de Celda para Valor de Propiedades
        // -> Factoria de Celdas para Edicion
        // - - -> lambda para controlar edicion de contenido
        clDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        clDescription.setCellFactory(TextFieldTableCell.<Evento>forTableColumn());
        LOGGER.info("Table Columns initiated");
    }

    /**
     *
     */
    public void initiateEditableTableColumns() {
        LOGGER.info("Generating Table Properties");
        //Columna Nombre
        // Factoria de Celda para Valor de Propiedades
        // -> Factoria de Celdas para Edicion
        // - - -> lambda para controlar edicion de contenido
        clName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clName.setCellFactory(TextFieldTableCell.<Evento>forTableColumn());
        clName.setOnEditCommit((CellEditEvent<Evento, String> t) -> {
            ((Evento) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setName(t.getNewValue());
        });
        //Columna Nombre
        // Factoria de Celda para Valor de Propiedades
        // -> Factoria de Celdas para Edicion
        // - - -> Método externo para control de fecha
        clDateStart.setCellValueFactory(new PropertyValueFactory<>("dateStart"));
        clDateStart.setCellFactory(TextFieldTableCell.<Evento>forTableColumn());
        clDateStart.setOnEditCommit(this::handleEditCommitDateStart);
        //Columna Nombre
        // Factoria de Celda para Valor de Propiedades
        // -> Factoria de Celdas para Edicion
        // - - -> Método externo para control de fecha
        clDateEnd.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));
        clDateEnd.setCellFactory(TextFieldTableCell.<Evento>forTableColumn());
        clDateEnd.setOnEditCommit(this::handleEditCommitDateEnd);
        //Columna Nombre
        // Factoria de Celda para Valor de Propiedades
        // -> Factoria de Celdas para Edicion
        // - - -> lambda para controlar edicion de contenido
        clDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        clDescription.setCellFactory(TextFieldTableCell.<Evento>forTableColumn());
        clDescription.setOnEditCommit(
                (CellEditEvent<Evento, String> t) -> {
                    ((Evento) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setDescription(t.getNewValue());
                });
        LOGGER.info("Table Columns initiated");
    }

    private void handleEditCommitDateStart(CellEditEvent<Evento, String> t) {
        ((Evento) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setDateStart(t.getNewValue());
    }

    private void handleEditCommitDateEnd(CellEditEvent<Evento, String> t) {
        ((Evento) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setDateStart(t.getNewValue());
    }

    /**
     *
     * @param event
     */
    public void printData(ActionEvent event) {
        LOGGER.info("Preparing to print");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Proceso de impresión de informes");
        alert.setHeaderText("Ha solicitado imprimir un informe, para ello, se "
                + "van a actualizar los datos de la tabla en la base de datos. "
                + "¿Está seguro de continuar?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            saveData();
            print();
        } else {
            event.consume();
        }
    }

    /**
     *
     */
    public void saveData() {
        LOGGER.info("Updating changes on DataBase");
        List<Evento> evs = ei.findAll();
        // Lee todos los eventos de la base de datos
        for (Evento ev : events) {
            Boolean encontrado = false;
            // Busca los eventos actuales en los extraidos de la base de datos
            for (Evento evnt : evs) {
                if (ev.getId() == evnt.getId()) {
                    encontrado = true;
                    // si lo encuentra, analiza si son iguales, si no lo son, lo actualiza
                    if (!ev.equals(evnt)) {
                        ei.edit(ev);
                    }
                }
            }
            // Si no lo ha encontrado, lo añade a la base de datos
            if (!encontrado) {
                ei.createEvent(ev);
            }
        }
        LOGGER.info("Updating changes on DataBase");
    }

    /**
     *
     */
    public void print() {

    }

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
            ((DatePickerSkin)dpDateEnd.getSkin()).getPopupContent().setVisible(editable);
            ((DatePickerSkin)dpDateStart.getSkin()).getPopupContent().setVisible(editable);
        }
    }
}
