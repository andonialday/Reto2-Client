/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import reto2g1cclient.logic.ClientInterface;

/**
 *
 * @author Jaime San Sebastian
 */
public class VClientTableController {

    private ClientInterface clientInterface;
    private Stage stage;
    
    public void setClientInterface(ClientInterface clientInterface) {
        this.clientInterface = clientInterface;
    }
    
    /**
     * Sets the stage
     *
     * @param stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Gets the stage
     *
     * @return stage to get
     */
    public Stage getStage() {
        return stage;
    }

    @FXML
    private TableView<?> tbClient;
    @FXML
    private TableColumn<?, ?> colLogin;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colEmail;
    @FXML
    private TableColumn<?, ?> colType;
    @FXML
    private Button btnDeleteClient;
    @FXML
    private Button btnNewClient;
    @FXML
    private TextField txtFilter;
    @FXML
    private Button btnSaveClient;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnSearch;
    @FXML
    private Label lblErrorEmail;
    @FXML
    private Button btnPrint;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfLogin;
    @FXML
    private PasswordField tfConfirmPassword;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private ComboBox<?> cbType;
    @FXML
    private ComboBox<?> cbSearchBy;
    @FXML
    private Label lblErrorName;
    @FXML
    private Label lblErrorPassword;
    @FXML
    private Label lblErrorConfirmPassword;
    @FXML
    private Label lblErrorLogin;
    @FXML
    private Button btnViewEvents;

    /**
     * Initialize and show window
     *
     * @param root
     * @throws IOException
     */
    public void initStage(Parent root) throws IOException {

        LOGGER.info("Initializing VClientTable stage.");

        //Create a new scene
        Scene scene = new Scene(root);

        //CSS (route & scene)
        String css = this.getClass().getResource("/reto2client/view/javaFXUIStyles.css").toExternalForm();
        scene.getStylesheets().add(css);

        //Associate the scene to the stage
        stage.setScene(scene);

        //Set the scene properties
        stage.setTitle("Client Table");
        stage.setResizable(false);

        //Set Windows event handlers 
        stage.setOnShowing(this::handleWindowShowing);
        stage.setOnCloseRequest(this::closeVClientTable);

        //Show main window
        stage.show();
    }
    
    /**
     * Shows the buttons that are enabled or disabled for the client table when we enter
     * the VClientTable window
     *
     * @param event
     */
    private void handleWindowShowing(WindowEvent event) {
        LOGGER.info("Beginning VClientTable::handleWindowShowing");

        //ViewEvents button is disabled
        btnViewEvents.setDisable(true);
        //NewClient button is disabled
        btnNewClient.setDisable(true);
        //SaveClient button is disabled
        btnSaveClient.setDisable(true);
        //DeleteClient button is disabled
        btnDeleteClient.setDisable(true);
        
        //The SaveClient button does not allow spaces to be entered
        btnSaveClient.disableProperty().bind(tfEmail.textProperty().isEmpty()
                .or(tfLogin.textProperty().isEmpty())
                .or(tfPassword.textProperty().isEmpty())
                .or(tfConfirmPassword.textProperty().isEmpty()));

        //Print button is enabled
        btnPrint.setDisable(false);
        //Back button is enabled
        btnBack.setDisable(false);
    }

    @FXML
    private void handleDeleteClient(ActionEvent event) {
        Alert alert1 = new Alert(AlertType.CONFIRMATION);
        alert1.setTitle("Confirmation");
        alert1.setHeaderText(null);
        alert1.setContentText("¿Seguro que quieres borrar este cliente?");
        alert1.showAndWait();
        Optional<ButtonType> result = alert1.showAndWait();
        if (result.get() == ButtonType.OK) {
            try{
                //ELIMINAR EL CLIENTE SELECCIONADO
                //REFRESCAR LA TABLA (.refresh)
            } catch (Exception e){
                Alert alert2 = new Alert(AlertType.ERROR);
                alert2.setTitle("Ayuda");
                alert2.setHeaderText("Error");
                alert2.setContentText(e.getMessage());
                alert2.showAndWait();
            }
        } else {
            Alert alert3 = new Alert(AlertType.INFORMATION);
            alert3.setTitle("Cliente no eliminado");
            alert3.setHeaderText(null);
            alert3.setContentText(null);
            alert3.showAndWait();
        }
    }

    @FXML
    private void handleAddClient(ActionEvent event) {
        Alert alert1 = new Alert(AlertType.CONFIRMATION);
        alert1.setTitle("Confirmation");
        alert1.setHeaderText(null);
        alert1.setContentText("¿Seguro que quieres añadir este cliente?");
        alert1.showAndWait();
        Optional<ButtonType> result = alert1.showAndWait();
        if (result.get() == ButtonType.OK) {
            try{
                //AÑADIR EL CLIENTE SELECCIONADO
                //REFRESCAR LA TABLA (.refresh)
            } catch (Exception e){
                Alert alert2 = new Alert(AlertType.ERROR);
                alert2.setTitle("Ayuda");
                alert2.setHeaderText("Error");
                alert2.setContentText(e.getMessage());
                alert2.showAndWait();
            }
        } else {
            Alert alert3 = new Alert(AlertType.INFORMATION);
            alert3.setTitle("Cliente no añadido");
            alert3.setHeaderText(null);
            alert3.setContentText(null);
            alert3.showAndWait();
        }
    }

    @FXML
    private void handleSaveClient(ActionEvent event) {
        Alert alert1 = new Alert(AlertType.CONFIRMATION);
        alert1.setTitle("Confirmation");
        alert1.setHeaderText(null);
        alert1.setContentText("¿Seguro que quieres guardar los cambios del cliente?");
        alert1.showAndWait();
        Optional<ButtonType> result = alert1.showAndWait();
        if (result.get() == ButtonType.OK) {
            try{
                //GUARDAR LAS MODIFICACIONES DEL CLIENTE
                //REFRESCAR LA TABLA (.refresh)
            } catch (Exception e){
                Alert alert2 = new Alert(AlertType.ERROR);
                alert2.setTitle("Ayuda");
                alert2.setHeaderText("Error");
                alert2.setContentText(e.getMessage());
                alert2.showAndWait();
            }
        } else {
            Alert alert3 = new Alert(AlertType.INFORMATION);
            alert3.setTitle("Cliente modificado");
            alert3.setHeaderText(null);
            alert3.setContentText(null);
            alert3.showAndWait();
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        //VOLVER A LA VENTANA VAdmin
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        
        try {

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("AYUDA");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void handlePrintClient(ActionEvent event) {
        Alert alert1 = new Alert(AlertType.CONFIRMATION);
        alert1.setTitle("Confirmation");
        alert1.setHeaderText(null);
        alert1.setContentText("¿Quieres imprimir la información?");
        alert1.showAndWait();
        Optional<ButtonType> result = alert1.showAndWait();
        if (result.get() == ButtonType.OK) {
            try{
                //IMPRIMIR LA TABLA CON LOS CLIENTES SELECCIONADOS
            } catch (Exception e){
                Alert alert2 = new Alert(AlertType.ERROR);
                alert2.setTitle("Ayuda");
                alert2.setHeaderText("Error");
                alert2.setContentText(e.getMessage());
                alert2.showAndWait();
            }
        }
    }

    @FXML
    private void handleChangeType(ActionEvent event) {
        
        try {

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("AYUDA");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleSearchBy(ActionEvent event) {
        
        try {

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("AYUDA");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleOpenEventTable(ActionEvent event) {
        
        try {

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("AYUDA");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    
    /**
     * Method to advise the user when uses the UI's innate close button (button
     * X) that the application will close
     *
     * @param event the event linked to clicking on the button;
     */
    public void closeVClientTable(WindowEvent event) {
        LOGGER.info("Requesting confirmation for application closing...");
        Alert alert = new Alert(AlertType.CONFIRMATION);
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

}