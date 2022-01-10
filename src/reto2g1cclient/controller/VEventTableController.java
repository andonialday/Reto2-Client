/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import reto2g1cclient.model.Client;
import reto2g1cclient.model.Evento;

/**
 *
 * @author Andoni Alday
 */
public class VEventTableController {

    private static final Logger LOGGER = Logger.getLogger("package.class");
    private Stage stage;
    private Client user;
    private Set<Evento> events;
    private Evento event;
    private boolean editable;

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
    public Client getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(Client user) {
        this.user = user;
    }

    /**
     *
     * @return
     */
    public Set<Evento> getEvents() {
        return events;
    }

    /**
     *
     * @param events
     */
    public void setEvents(Set<Evento> events) {
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

    // Elementos FXML de la ventana
    @FXML
    private Button btnBack;

    @FXML
    private Button btnDeleteEvent;

    @FXML
    private Button btnSave;

    @FXML
    private TableView<Evento> tbEvent;

    @FXML
    public TableColumn<Evento, String> clDateStart;

    @FXML
    public TableColumn<Evento, String> clDateEnd;

    @FXML
    public TableColumn<Evento, String> clDescription;

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
        // AÑADIR LOS NUEVOS LABEL Y HYPERLINK

        tbEvent.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        loadData();
        btnBack.setOnAction(this::back);
        btnDeleteEvent.setOnAction(this::deleteEvent);
        btnSave.setOnAction(this::saveChanges);

        //Show main window
        stage.show();

    }

    private void handleWindowShowing(WindowEvent event) {
        LOGGER.info("Beginning LoginController::handleWindowShowing");

        btnBack.setDisable(false);
        btnDeleteEvent.setDisable(true);
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
        //clDateStart.setCellValueFactory(new PropertyValueFactory<>("Fecha de Inicio"));
        //clDateEnd.setCellValueFactory(new PropertyValueFactory<>("Fecha Finalizacion"));
        //clDescription.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));
        for (Evento ev : events) {
            tbEvent.getItems().add(ev);
        }
    }

}
