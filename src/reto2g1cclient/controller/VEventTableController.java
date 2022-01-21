/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;

import java.io.IOException;
import java.time.ZoneId;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private Stage stage;
    private Client client;
    private List<Evento> events;
    private Evento event;
    private boolean editable;
    private EventInterface ei;
    private ObservableList<Evento> eventData;
    private boolean dateEnd;
    private boolean dateStart;
    private boolean desc;
    private boolean name;

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

        LOGGER.info("Initializing Login stage.");

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

        tbEvent.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ei = EventFactory.getImplementation();
        loadData();
        tbEvent.getSelectionModel().selectedItemProperty().addListener(this::eventTableSelection);
        cbSearch.getSelectionModel().selectedItemProperty().addListener(this::selectedFilter);
        btnBack.setOnAction(this::back);
        btnDelete.setOnAction(this::deleteEvent);
        btnNew.setOnAction(this::newEvent);
        btnSave.setOnAction(this::saveChanges);
        //Fields
        txtName.textProperty().addListener(this::txtNameVal);
        dpDateStart.valueProperty().addListener(this::dateStartVal);
        dpDateEnd.valueProperty().addListener(this::dateEndVal);
        taDescription.textProperty().addListener(this::taDescVal);
        //Show main window
        stage.show();

    }

    private void handleWindowShowing(WindowEvent event) {
        LOGGER.info("Beginning LoginController::handleWindowShowing");

        btnBack.setDisable(false);
        btnDelete.setDisable(true);
        btnNew.setDisable(true);
        btnSearch.setDisable(false);
        btnPrint.setDisable(false);
        btnSave.setDisable(true);

        //Si accede a la vista de eventos un cliente, editable será true y se podrá 
        //editar la tabla, si accede un comercial, editable será false y no será editable
        tbEvent.setEditable(editable);

    }

    @FXML
    private void back(ActionEvent event) {
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

    @FXML
    private void deleteEvent(ActionEvent event) {
        LOGGER.info("Requesting confirmation for Event Deletion...");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Está Eliminando un Evento");
        alert.setHeaderText("¿Seguro que desea Eliminar el Evento Seleccionado?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ei.remove(this.event);
        } else {
            event.consume();
            LOGGER.info("Closing aborted");
        }
    }

    @FXML
    private void newEvent(ActionEvent event) {
        if (dpDateEnd.getValue().isAfter(dpDateStart.getValue()) || dpDateEnd.getValue().isEqual(dpDateStart.getValue())) {
            Evento ev = new Evento();
        } else {
            lblDateEndEr.setVisible(true);
        }
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

    @FXML
    private void saveChanges(ActionEvent event) {
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

    private void loadData() {
        try {
            if (client != null) {
                events = ei.findEventByClient(client);
            } else {
                events = ei.findAll();
            }
            clName.setCellValueFactory(new PropertyValueFactory<>("name"));
            clDateStart.setCellValueFactory(new PropertyValueFactory<>("Fecha de Inicio"));
            clDateEnd.setCellValueFactory(new PropertyValueFactory<>("Fecha Finalizacion"));
            clDescription.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));
            eventData = FXCollections.observableArrayList(events);
        } catch (Exception e) {

        }
        tbEvent.setItems(eventData);
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
            event = (Evento) newValue;
            txtName.setText(event.getName());
            dpDateStart.setValue(event.getDateStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            dpDateEnd.setValue(event.getDateEnd().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            taDescription.setText(event.getDescription());
            btnNew.setDisable(true);
            btnDelete.setDisable(false);
            btnSave.setDisable(false);
        } else {

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
        if (newValue != null) {

        }
    }

    public void validateData() {
        if (name && dateStart && dateEnd && desc) {
            btnNew.setDisable(false);
        } else {
            btnNew.setDisable(true);
        }
    }

    public void txtNameVal(ObservableValue observable, Object oldValue, Object newValue) {
        name = false;
        if (txtName.getText().trim() != null) {
            name = true;
        }
        validateData();
    }

    public void dateStartVal(ObservableValue observable, Object oldValue, Object newValue) {
        dateStart = false;
        if (dpDateStart.getValue() != null) {
            dateStart = true;
        }
        validateData();
    }

    public void dateEndVal(ObservableValue observable, Object oldValue, Object newValue) {
        dateEnd = false;
        if (dpDateEnd.getValue() != null) {
            dateEnd = true;
        }
        validateData();
    }

    public void taDescVal(ObservableValue observable, Object oldValue, Object newValue) {
        desc = false;
        if (taDescription.getText().trim() != null) {
            desc = true;
        }
        validateData();
    }

}
