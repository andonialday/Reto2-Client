/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;

import reto2g1cclient.exception.EmailNotValidException;
import reto2g1cclient.exception.MaxCharacterException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import reto2g1cclient.exception.ClientServerConnectionException;
import reto2g1cclient.logic.ClientInterface;
import reto2g1cclient.model.Client;

/**
 *
 * @author Jaime San Sebastian
 */
public class VClientTableController {

    private final Logger LOGGER = Logger.getLogger("package.class");
    
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

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
    private TableView<Client> tbClient;
    @FXML
    private TableColumn<Client, String> colLogin;
    @FXML
    private TableColumn<Client, String> colName;
    @FXML
    private TableColumn<Client, String> colEmail;
    @FXML
    private TableColumn<Client, String> colType;
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

        tbClient.getSelectionModel().selectedItemProperty().addListener(this::handleSelection);
        
        //ViewEvents button is disabled
        btnViewEvents.setDisable(true);
        
        //DeleteClient button is disabled
        btnDeleteClient.setDisable(true);
        
        //NewClient button is disabled
        btnNewClient.setDisable(true);
        //The NewClient button does not allow spaces to be entered
        btnNewClient.disableProperty().bind(tfEmail.textProperty().isEmpty()
                .or(tfLogin.textProperty().isEmpty())
                .or(tfPassword.textProperty().isEmpty())
                .or(tfConfirmPassword.textProperty().isEmpty()));
        
        //SaveClient button is disabled
        btnSaveClient.setDisable(true);
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
    
    /**
     * Method to control if there's a item selected in the table
     *
     * @param observableValue
     * @param oldValue
     * @param newValue
     */
    private void handleSelection(ObservableValue observableValue, Object oldValue, Object newValue) {
        if (newValue != null) {
            btnViewEvents.setDisable(false);
            btnSaveClient.setDisable(false);
            btnDeleteClient.setDisable(false);
        } else {
            btnViewEvents.setDisable(true);
            btnSaveClient.setDisable(true);
            btnDeleteClient.setDisable(true);
        }
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
                //ELIMINAR EL CLIENTE SELECCIONADO LLAMANDO A LA PARTE LÓGICA
                Client clientSelect = tbClient.getSelectionModel().getSelectedItem();
                clientInterface.removeClient(String.valueOf(clientSelect.getId()));
                //REFRESCAR LA TABLA (.refresh)
                tbClient.refresh();
            } catch (ClientServerConnectionException e){
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
        try {
            if(checkFields()){
                
            }
        } catch (MaxCharacterException ex) {
            lblErrorName.setVisible(true);
        } catch (EmailNotValidException ex) {
            lblErrorEmail.setVisible(true);
        }
        //NO TIENE QUE HABER NADA SELECCIONADO EN LA TABLA
        //COMPROBAR LA VALIDEZ DE LOS DATOS DE LOS CAMPOS (ESPACIOS)
        //SI NO SON CORRECTOS SE VUELVE VISIBLE EL LABEL DE ERROR Y AlertType.WARNING 
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
        //SE HABILITA CUANDO SE SELECCIONA UN ELEMENTO DE LA TABLA Y SE RELLENAN LOS CAMPOS
        //COMPROBAR LA VALIDEZ DE LOS DATOS DE LOS CAMPOS
        //SI NO SON CORRECTOS SE VUELVE VISIBLE EL LABEL DE ERROR Y AlertType.WARNING 
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
            alert3.setTitle("Cliente sin modificado");
            alert3.setHeaderText(null);
            alert3.setContentText(null);
            alert3.showAndWait();
        }
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        Alert alert1 = new Alert(AlertType.CONFIRMATION);
        alert1.setTitle("Confirmation");
        alert1.setHeaderText(null);
        alert1.setContentText("¿Seguro que quieres volver a la ventana anterior?");
        alert1.showAndWait();
        Optional<ButtonType> result = alert1.showAndWait();
        if (result.get() == ButtonType.OK) {
            try{
                //VOLVER A LA VENTANA VAdmin
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto2client/view/VAdmin.fxml"));
                Parent root = (Parent) loader.load();
                VAdminController controller = loader.getController();
                controller.setStage(this.stage);
                controller.initStage(root);
            } catch (Exception e){
                Alert alert2 = new Alert(AlertType.ERROR);
                alert2.setTitle("Ayuda");
                alert2.setHeaderText("Error");
                alert2.setContentText(e.getMessage());
                alert2.showAndWait();
            }
        } else {
            //QUEDARSE EN LA VENTANA VClientTable
        }
       
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        //EN FUNCION DEL COMBOBOX Y EL TEXTFIELD DE BUSQUEDA EJECUTAR EL FILTRADO EN LA TABLA
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
                //EMPLEANDO EL GESTOR DE INFORMES
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
        //COMBOBOX CON LOS CUATRO POSIBLES TIPOS DE CLIENTE
        //PARTICULAR, ASSOCIATION, ENTERPRISE y PUBLIC ENTITY
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
        //COMBOBOX CON LOS CUATRO POSIBLES TIPOS DE CLIENTE
        //LOGIN, NOMBRE, EMAIL y TIPO
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
        //SE HABILITA CUANDO UN CLIENTE ESTÁ SELECCIONADO EN LA TABLA
        //SE ABRE LA VENTANA VEventTable
        try {
            //IR A LA VENTANA VEventTable
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto2client/view/VEventTable.fxml"));
            Parent root = (Parent) loader.load();
            //VEventTable controller = loader.getController();
            //controller.setStage(this.stage);
            //controller.initStage(root);
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
            LOGGER.info("Closing the application");
            Platform.exit();
        } else {
            event.consume();
            LOGGER.info("Application closing cancelled");
        }
    }

    private boolean checkFields() throws MaxCharacterException, EmailNotValidException {
        //PONER TODAS LAS LABEL DE ERROR INVISIBLES
        lblErrorName.setVisible(false);
        lblErrorEmail.setVisible(false);
        lblErrorLogin.setVisible(false);
        lblErrorPassword.setVisible(false);
        lblErrorConfirmPassword.setVisible(false);
        
        
        if(tfName.getText().trim().length()>25){
            throw new MaxCharacterException("Error, tamaño máximo");
        }
        if(validateEmail (tfEmail.getText().trim())){
           throw new EmailNotValidException("Error, email no válido");
        }
   
        
        
        return true;
    }
    
    //VALIDAR EMAIL
    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
}

}