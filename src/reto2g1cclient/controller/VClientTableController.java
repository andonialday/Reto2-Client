/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;

import reto2g1cclient.exception.FieldsEmptyException;
import reto2g1cclient.exception.PasswordNotValidException;
import reto2g1cclient.exception.ConfirmPasswordNotValidException;
import reto2g1cclient.exception.LoginNotValidException;
import reto2g1cclient.exception.EmailNotValidException;
import reto2g1cclient.exception.MaxCharacterException;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import reto2g1cclient.exception.ClientServerConnectionException;
import reto2g1cclient.logic.ClientInterface;
import reto2g1cclient.model.Client;
import reto2g1cclient.model.Type;
import reto2g1cclient.model.UserStatus;

/**
 *
 * @author Jaime San Sebastian
 */
public class VClientTableController {

    private static final Logger LOGGER = Logger.getLogger("package.class");

    //Initialization of the email validation pattern
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    //Initialization of the password validation pattern
    public static final Pattern VALID_PASSWORD_REGEX
            = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$", Pattern.CASE_INSENSITIVE);

    //Initialize the values ​​of the combobox of type
    private final String SELECT_PARTICULAR = "PARTICULAR";
    private final String SELECT_ASOCIATION = "ASOCIATION";
    private final String SELECT_ENTERPRISE = "ENTERPRISE";
    private final String SELECT_PUBLIC_ENTITY = "PUBLIC ENTITY";

    //Initialize the values ​​of the combobox of search
    private final String SELECT_NAME = "Client Name";
    private final String SELECT_LOGIN = "Client Login";
    private final String SELECT_EMAIL = "Client Email";
    private final String SELECT_TYPE = "Client Type";

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
    private ComboBox<String> cbType;
    @FXML
    private ComboBox<String> cbSearchBy;
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

    private ClientInterface clientInterface;
    private Stage stage;

    /**
     * Sets the client interface
     *
     * @param clientInterface to set
     */
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
     * Shows the buttons that are enabled or disabled for the client table when
     * we enter the VClientTable window
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

        //Search button is disabled
        btnSearch.setDisable(true);

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

        //Add the values ​​of the customer type combobox
        ObservableList<String> optionsForComboType;
        optionsForComboType = FXCollections.observableArrayList(SELECT_PARTICULAR, SELECT_ASOCIATION, SELECT_ENTERPRISE, SELECT_PUBLIC_ENTITY);
        cbType.setItems(optionsForComboType);
        //Select the first comboBox item by default
        cbType.getSelectionModel().selectFirst();

        //Add the values ​​of the customer type combobox
        ObservableList<String> optionsForComboSearch;
        optionsForComboSearch = FXCollections.observableArrayList(SELECT_NAME, SELECT_LOGIN, SELECT_EMAIL, SELECT_TYPE);
        cbSearchBy.setItems(optionsForComboSearch);
        //Select the first comboBox item by default
        cbSearchBy.getSelectionModel().selectFirst();

        //Insert the table of clients with clients
        colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        //colType.setCellValueFactory(new PropertyValueFactory<>("type"));

        Collection<Client> clients;
        try {
            clients = clientInterface.getAllClient();
            ObservableList<Client> clientsForTable = FXCollections.observableArrayList(clients);
            tbClient.setItems(clientsForTable);
        } catch (ClientServerConnectionException ex) {
            Logger.getLogger(VClientTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method to control if there's a client selected in the table
     *
     * @param observableValue
     * @param oldValue
     * @param newValue
     */
    private void handleSelection(ObservableValue observableValue, Object oldValue, Object newValue) {
        //When a table client is selected
        if (newValue != null) {
            btnViewEvents.setDisable(false);
            btnSaveClient.setDisable(false);
            btnDeleteClient.setDisable(false);
            //When a table client is deselected
        } else {
            btnViewEvents.setDisable(true);
            btnSaveClient.setDisable(true);
            btnDeleteClient.setDisable(true);
        }
    }

    /**
     * Method to check the validity of fields
     *
     * @return
     */
    private boolean checkFields() throws FieldsEmptyException, MaxCharacterException, EmailNotValidException, LoginNotValidException, PasswordNotValidException, ConfirmPasswordNotValidException {

        //Make all error tags invisible
        lblErrorName.setVisible(false);
        lblErrorEmail.setVisible(false);
        lblErrorLogin.setVisible(false);
        lblErrorPassword.setVisible(false);
        lblErrorConfirmPassword.setVisible(false);

        //Checks if all the fields are written
        if (tfName.getText().trim().isEmpty() || tfEmail.getText().trim().isEmpty()
                || tfLogin.getText().trim().isEmpty() || tfPassword.getText().trim().isEmpty()
                || tfConfirmPassword.getText().trim().isEmpty()) {
            LOGGER.info("Some fields are empty");
            throw new FieldsEmptyException("Error, some fields are empty");
        }
        //Checks if the name field has more than 25 characters
        if (tfName.getText().trim().length() > 25) {
            throw new MaxCharacterException("Error, maximum size");
        }
        //Check if the email field is written correctly
        if (!validateEmail(tfEmail.getText().trim())) {
            throw new EmailNotValidException("Error, invalid email");
        }
        //Check if the login field has no more than 25 characters and no less than 5
        if (tfLogin.getText().trim().length() > 25 || tfLogin.getText().trim().length() < 5) {
            throw new LoginNotValidException("Error, invalid login");
        }
        //Check if the password field is written correctly
        if (!validatePassword(tfPassword.getText().trim())) {
            throw new PasswordNotValidException("Error, invalid password");
        }
        //Check if the confirm password field is written correctly
        if (!validatePassword(tfConfirmPassword.getText().trim())) {
            throw new PasswordNotValidException("Error, invalid password");
        }
        //Check that the confirm password field contains exactly the same characters typed
        if (!tfConfirmPassword.getText().trim().equals(tfPassword.getText().trim())) {
            throw new ConfirmPasswordNotValidException("Error, different confirmation password");
        }
        return true;
    }

    /**
     * Method that validates the email
     *
     * @param emailStr
     * @return
     */
    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    /**
     * Method that validates the password
     *
     * @param emailStr
     * @return
     */
    public static boolean validatePassword(String emailStr) {
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(emailStr);
        return matcher.find();
    }

    /**
     * Method that controls the action button of removing a client
     *
     * @param event the event linked to clicking on the button;
     */
    @FXML
    private void handleDeleteClient(ActionEvent event) {
        LOGGER.info("Option to delete the selected client");

        Alert alert1 = new Alert(AlertType.CONFIRMATION);
        alert1.setTitle("Delete Client");
        alert1.setHeaderText(null);
        alert1.setContentText("Are you sure you want to delete this client?");
        alert1.showAndWait();
        Optional<ButtonType> result = alert1.showAndWait();
        if (result.get() == ButtonType.OK) {

            try {
                LOGGER.info("Delete selected client");
                //Delete the selected client by calling the logic part
                Client clientToRemove = tbClient.getSelectionModel().getSelectedItem();
                clientInterface.removeClient(String.valueOf(clientToRemove.getId()));
                //Refresh the table
                tbClient.refresh();

            } catch (ClientServerConnectionException e) {
                Alert alert2 = new Alert(AlertType.ERROR);
                alert2.setTitle("Help");
                alert2.setHeaderText("Error");
                alert2.setContentText(e.getMessage());
                alert2.showAndWait();
            }
            //Information alert when you dismiss the confirmation alert
        } else {
            Alert alert3 = new Alert(AlertType.INFORMATION);
            alert3.setTitle("Cliente not removed");
            alert3.setHeaderText(null);
            alert3.setContentText(null);
            alert3.showAndWait();
        }
    }

    /**
     * Method that controls the action button of creating a new client
     *
     * @param event the event linked to clicking on the button;
     */
    @FXML
    private void handleAddClient(ActionEvent event) {
        LOGGER.info("Option to create a new client");

        try {

            //Check the information of the data entered in the fields
            if (checkFields()) {
                LOGGER.info("Correct text fields");

                Alert alert1 = new Alert(AlertType.CONFIRMATION);
                alert1.setTitle("New Client");
                alert1.setHeaderText(null);
                alert1.setContentText("Are you sure you want to add this client?");
                alert1.showAndWait();
                Optional<ButtonType> result = alert1.showAndWait();
                if (result.get() == ButtonType.OK) {

                    try {
                        LOGGER.info("Adding a new client");
                        Client clientToCreate = new Client();
                        clientToCreate.setFullName(tfName.getText());
                        clientToCreate.setEmail(tfEmail.getText());
                        clientToCreate.setLogin(tfLogin.getText());
                        clientToCreate.setPassword(tfPassword.getText());
                        clientToCreate.setStatus(UserStatus.ENABLED);
                        //Type of object selected in the combobox
                        switch (cbType.getSelectionModel().getSelectedItem()) {
                            case SELECT_PARTICULAR:
                                clientToCreate.setType(Type.PARTICULAR);
                                break;
                            case SELECT_ASOCIATION:
                                clientToCreate.setType(Type.ASOCIATION);
                                break;
                            case SELECT_ENTERPRISE:
                                clientToCreate.setType(Type.ENTERPRISE);
                                break;
                            case SELECT_PUBLIC_ENTITY:
                                clientToCreate.setType(Type.PUBLIC_ENTITY);
                                break;
                        }
                        //Add the client introduced by calling the logical part
                        clientInterface.createClient(clientToCreate);
                        //Refresh the table
                        tbClient.refresh();

                    } catch (ClientServerConnectionException e) {
                        Alert alert2 = new Alert(AlertType.ERROR);
                        alert2.setTitle("Ayuda");
                        alert2.setHeaderText("Error");
                        alert2.setContentText(e.getMessage());
                        alert2.showAndWait();
                    }
                    //Information alert when you dismiss the confirmation alert
                } else {
                    Alert alert3 = new Alert(AlertType.INFORMATION);
                    alert3.setTitle("Cliente not added");
                    alert3.setHeaderText(null);
                    alert3.setContentText(null);
                    alert3.showAndWait();
                }
            }

        } catch (FieldsEmptyException ex) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Look, a Warning Dialog");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        } catch (MaxCharacterException ex) {
            lblErrorName.setVisible(true);
        } catch (EmailNotValidException ex) {
            lblErrorEmail.setVisible(true);
        } catch (LoginNotValidException ex) {
            lblErrorLogin.setVisible(true);
        } catch (PasswordNotValidException ex) {
            lblErrorPassword.setVisible(true);
        } catch (ConfirmPasswordNotValidException ex) {
            lblErrorConfirmPassword.setVisible(true);
        }
    }

    /**
     * Method that controls the action button of editing a new client
     *
     * @param event the event linked to clicking on the button;
     */
    @FXML
    private void handleSaveClient(ActionEvent event) {
        LOGGER.info("Option to edit a selected client");
        
        LOGGER.info("Editing a client");
        Client clientToEdit = tbClient.getSelectionModel().getSelectedItem();
        clientToEdit.setFullName(tfName.getText());
        clientToEdit.setEmail(tfEmail.getText());
        clientToEdit.setLogin(tfLogin.getText());
        clientToEdit.setPassword(tfPassword.getText());
        //Type of object selected in the combobox
        switch (cbType.getSelectionModel().getSelectedItem()) {
            case SELECT_PARTICULAR:
                clientToEdit.setType(Type.PARTICULAR);
                break;
            case SELECT_ASOCIATION:
                clientToEdit.setType(Type.ASOCIATION);
                break;
            case SELECT_ENTERPRISE:
                clientToEdit.setType(Type.ENTERPRISE);
                break;
            case SELECT_PUBLIC_ENTITY:
                clientToEdit.setType(Type.PUBLIC_ENTITY);
                break;
        }
        
        try {

            //Check the information of the data entered in the fields
            if (checkFields()) {
                LOGGER.info("Correct text fields");

                Alert alert1 = new Alert(AlertType.CONFIRMATION);
                alert1.setTitle("Save Client");
                alert1.setHeaderText(null);
                alert1.setContentText("Are you sure you want to edit this client?");
                alert1.showAndWait();
                Optional<ButtonType> result = alert1.showAndWait();
                if (result.get() == ButtonType.OK) {

                    try {
                        LOGGER.info("Edit the client");
                        //Edit the client by calling the logical part
                        clientInterface.editClient(clientToEdit);
                        //Refresh the table
                        tbClient.refresh();

                    } catch (ClientServerConnectionException e) {
                        Alert alert2 = new Alert(AlertType.ERROR);
                        alert2.setTitle("Ayuda");
                        alert2.setHeaderText("Error");
                        alert2.setContentText(e.getMessage());
                        alert2.showAndWait();
                    }
                    //Information alert when you dismiss the confirmation alert
                } else {
                    Alert alert3 = new Alert(AlertType.INFORMATION);
                    alert3.setTitle("Unedited client");
                    alert3.setHeaderText(null);
                    alert3.setContentText(null);
                    alert3.showAndWait();
                }
            }

        } catch (FieldsEmptyException ex) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Look, a Warning Dialog");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        } catch (MaxCharacterException ex) {
            lblErrorName.setVisible(true);
        } catch (EmailNotValidException ex) {
            lblErrorEmail.setVisible(true);
        } catch (LoginNotValidException ex) {
            lblErrorLogin.setVisible(true);
        } catch (PasswordNotValidException ex) {
            lblErrorPassword.setVisible(true);
        } catch (ConfirmPasswordNotValidException ex) {
            lblErrorConfirmPassword.setVisible(true);
        }
    }

    /**
     * Method that controls the action button of printing the information of the
     * clients
     *
     * @param event the event linked to clicking on the button;
     */
    @FXML
    private void handlePrintClient(ActionEvent event) {
        LOGGER.info("Option to print clients information");

        Alert alert1 = new Alert(AlertType.CONFIRMATION);
        alert1.setTitle("Print Information");
        alert1.setHeaderText(null);
        alert1.setContentText("Do you want to print the information?");
        alert1.showAndWait();
        Optional<ButtonType> result = alert1.showAndWait();
        if (result.get() == ButtonType.OK) {

            try {
                LOGGER.info("Print selectes information");
                //IMPRIMIR LA TABLA CON LOS CLIENTES SELECCIONADOS EMPLEANDO EL GESTOR DE INFORMES

            } catch (Exception e) {
                Alert alert2 = new Alert(AlertType.ERROR);
                alert2.setTitle("Ayuda");
                alert2.setHeaderText("Error");
                alert2.setContentText(e.getMessage());
                alert2.showAndWait();
            }
            //Information alert when you dismiss the confirmation alert
        } else {
            Alert alert3 = new Alert(AlertType.INFORMATION);
            alert3.setTitle("Non printed information");
            alert3.setHeaderText(null);
            alert3.setContentText(null);
            alert3.showAndWait();
        }
    }
    
    /**
     * Method that controls a combobox with four different types of clients
     *
     * @param event the event linked to clicking on the button;
     */
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

    /**
     * Method that controls a combobox with four different search filter types
     *
     * @param event the event linked to clicking on the button;
     */
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

    /**
     * Method that controls the search action button by a specific filter
     *
     * @param event the event linked to clicking on the button;
     */
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

    /**
     * Method that controls the action button that allows to see the different
     * events of a specific client
     *
     * @param event the event linked to clicking on the button;
     */
    @FXML
    private void handleOpenEventTable(ActionEvent event) {
        LOGGER.info("Open the window with the client events table");

        try {
            LOGGER.info("Open VEventTable");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto2client/view/VEventTable.fxml"));
            Parent root = (Parent) loader.load();
            VEventTable controller = loader.getController();
            controller.setStage(this.stage);
            controller.initStage(root);

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("AYUDA");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Method that controls the action button to return to the previous window
     *
     * @param event the event linked to clicking on the button;
     */
    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        LOGGER.info("Returning to VAdmin window");

        Alert alert1 = new Alert(AlertType.CONFIRMATION);
        alert1.setTitle("Back to VAdmin");
        alert1.setHeaderText(null);
        alert1.setContentText("Are you sure you want to return to the previous window?");
        alert1.showAndWait();
        Optional<ButtonType> result = alert1.showAndWait();

        if (result.get() == ButtonType.OK) {
            LOGGER.info("Closing VClientTable window and returning to VAdmin");
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto2client/view/VAdmin.fxml"));

            try {
                Parent root = (Parent) loader.load();
                VAdminController controller = loader.getController();
                controller.setStage(this.stage);
                controller.initStage(root);

            } catch (IOException ex) {
                LOGGER.info("Error closing client table window");
            }
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
        alert.setTitle("Program is closing");
        alert.setHeaderText("Are you sure you want to close the program?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            LOGGER.info("Closing the application");
            Platform.exit();
        } else {
            event.consume();
            LOGGER.info("Application closing cancelled");
        }
    }

    /**
     * Method to launch a AlertType.WARNING
     */
    private void warning() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Look, a Warning Dialog");
        alert.setContentText("The following fields are wrong");
        alert.showAndWait();
    }

}
