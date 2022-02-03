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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import reto2g1cclient.exception.ClientServerConnectionException;
import reto2g1cclient.exception.DBServerException;
import reto2g1cclient.exception.LoginOnUseException;
import reto2g1cclient.logic.ClientFactory;
import reto2g1cclient.logic.ClientInterface;
import reto2g1cclient.model.Client;
import reto2g1cclient.model.Type;
import reto2g1cclient.model.UserStatus;

/**
 * Controller for the client table fxml
 *
 * @author Jaime San Sebastian
 */
public class VClientTableController {

    private static final Logger LOGGER = Logger.getLogger("package.class");

    //Initialization of the email validation pattern
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                    Pattern.CASE_INSENSITIVE);

    //Initialization of the password validation pattern
    public static final Pattern VALID_PASSWORD_REGEX
            = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$",
                    Pattern.CASE_INSENSITIVE);

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

    //Control to enable the buttons
    private boolean name;
    private boolean email;
    private boolean login;
    private boolean password;
    private boolean confirmPassword;
    private boolean tableSelect;

    //Table
    @FXML
    private TableView<Client> tbClient;
    @FXML
    private TableColumn<Client, String> colName;
    @FXML
    private TableColumn<Client, String> colLogin;
    @FXML
    private TableColumn<Client, String> colEmail;
    @FXML
    private TableColumn<Client, Type> colType;

    //Buttons
    @FXML
    private Button btnNewClient;
    @FXML
    private Button btnDeleteClient;
    @FXML
    private Button btnSaveClient;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnViewEvents;
    @FXML
    private Button btnPrint;
    @FXML
    private Button btnBack;

    //Labels
    @FXML
    private Label lblErrorName;
    @FXML
    private Label lblErrorLogin;
    @FXML
    private Label lblErrorEmail;
    @FXML
    private Label lblErrorPassword;
    @FXML
    private Label lblErrorConfirmPassword;

    //Text fields
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfLogin;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField txtFilter;

    //Password fields
    @FXML
    private PasswordField tfPassword;
    @FXML
    private PasswordField tfConfirmPassword;

    //Combo box
    @FXML
    private ComboBox<String> cbType;
    @FXML
    private ComboBox<String> cbSearchBy;

    private ClientInterface clientInterface;
    private Stage stage;
    private ObservableList<Client> clientsForTable;
    private List<Client> clientList;

    /**
     * Initialize and show window. Initialize the window, create a scene,
     * associate it with a stage and show the window.
     *
     * @param root
     */
    public void initStage(Parent root) {

        LOGGER.info("Initializing VClientTable stage.");

        //Create a new scene
        Scene scene = new Scene(root);

        //Associate the scene to the stage
        stage.setScene(scene);

        //Set the scene properties
        stage.setTitle("Client Table");
        stage.setResizable(false);

        clientInterface = ClientFactory.getClient();

        //Set Windows event handlers 
        stage.setOnShowing(this::handleWindowShowing); //show window
        stage.setOnCloseRequest(this::closeVClientTable); //close window

        //Show main window
        stage.show();
    }

    /**
     * Shows the buttons that are enabled or disabled for the client table when
     * we enter the VClientTable window. Add all possible options to the
     * Combobox. Disable all comments that show an error if the field is entered
     * incorrectly. Insert the name of each column in the table and enter the
     * customer data.
     *
     * @param event
     */
    private void handleWindowShowing(WindowEvent event) {
        LOGGER.info("Beginning VClientTable::handleWindowShowing");

        tbClient.getSelectionModel().selectedItemProperty()
                .addListener(this::handleSelection); //selected object from table

        //Buttons disabled
        btnViewEvents.setDisable(true);
        btnDeleteClient.setDisable(true);
        btnNewClient.setDisable(true);
        btnSaveClient.setDisable(true);

        //Buttons enabled
        btnPrint.setDisable(false);
        btnSearch.setDisable(false);
        btnBack.setDisable(false);

        //Make all error tags invisible
        lblErrorName.setVisible(false);
        lblErrorEmail.setVisible(false);
        lblErrorLogin.setVisible(false);
        lblErrorPassword.setVisible(false);
        lblErrorConfirmPassword.setVisible(false);

        //Search field disabled
        txtFilter.setDisable(true);

        //TextField actions for buttons to be enabled
        tfName.textProperty().addListener(this::txtNameFull);
        tfLogin.textProperty().addListener(this::txtLoginFull);
        tfEmail.textProperty().addListener(this::txtEmailFull);
        tfPassword.textProperty().addListener(this::txtPasswordFull);
        tfConfirmPassword.textProperty().addListener(this::txtConfirmPasswordFull);

        //Add the values ​​of the client type ComboBox
        ObservableList<String> optionsForComboType;
        optionsForComboType = FXCollections.observableArrayList(SELECT_PARTICULAR,
                SELECT_ASOCIATION, SELECT_ENTERPRISE, SELECT_PUBLIC_ENTITY);
        cbType.setItems(optionsForComboType);

        //Add the values ​​of the client filter ComboBox
        ObservableList<String> optionsForComboSearch;
        optionsForComboSearch = FXCollections.observableArrayList(SELECT_NAME,
                SELECT_LOGIN, SELECT_EMAIL, SELECT_TYPE);
        cbSearchBy.setItems(optionsForComboSearch);

        //Insert the table columns and link them to clients
        colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));

        try {
            clientsForTable = FXCollections.observableArrayList(clientInterface.getAllClient());
            tbClient.setItems(clientsForTable);
        } catch (ClientServerConnectionException ex) {
            LOGGER.info("Error, client collection not working");
        }
    }

    /**
     * Method to control if there's a client selected in the table. If the user
     * selects a client in the table, all client data is displayed in the
     * corresponding fields. If the user selects a client in the table, the View
     * client events and delete clients buttons become visible. If the user
     * deselects a client in the table, the View client events and delete
     * clients buttons become invisible again.
     *
     * @param observableValue
     * @param oldValue
     * @param newValue
     */
    private void handleSelection(ObservableValue observableValue,
            Object oldValue, Object newValue) {
        LOGGER.info("Control the selection of a client");

        //client selected in the table
        if (newValue != null) {
            //Insert client data into fields
            Client client = tbClient.getSelectionModel().getSelectedItem();
            tfName.setText(client.getFullName());
            tfLogin.setText(client.getLogin());
            tfEmail.setText(client.getEmail());
            cbType.getSelectionModel().select(client.getType().toString());
            //Text Fields disabled
            tfPassword.setDisable(true);
            tfConfirmPassword.setDisable(true);

            //Button disabled
            btnNewClient.setDisable(true);

            //Buttons enabled
            btnViewEvents.setDisable(false);
            btnDeleteClient.setDisable(false);
            btnSaveClient.setDisable(false);

            //Field controllers
            name = false;
            email = false;
            login = false;
            tableSelect = true;

            //client deselected in the table (ctrl+click)  
        } else {
            //Delete client data from fields
            tfName.setText("");
            tfLogin.setText("");
            tfEmail.setText("");
            cbType.getSelectionModel().selectFirst();
            //Text Fields enabled
            tfPassword.setDisable(false);
            tfPassword.setText("");
            tfConfirmPassword.setDisable(false);
            tfConfirmPassword.setText("");

            //Buttons disabled
            btnViewEvents.setDisable(true);
            btnDeleteClient.setDisable(true);
            btnSaveClient.setDisable(true);

            //Field controllers
            name = false;
            email = false;
            login = false;
            tableSelect = false;
        }
    }

    /**
     * Method to load the deleted, created or modified client in the table.
     */
    private void loadClientTable() {
        LOGGER.info("Load client table");

        try {

            //Clear the data of the table
            tbClient.getItems().clear();

            //Call to the server side so that it takes all the clients
            clientList = (List<Client>) clientInterface.getAllClient();

            //Set the data in the table
            tbClient.setItems(clientsForTable);

            //Refresh the table
            tbClient.refresh();

        } catch (Exception e) {
            LOGGER.info("Failed to load data");

        }
        LOGGER.info("Load data table");

        clientsForTable = FXCollections.observableArrayList(clientList);
        tbClient.setItems(clientsForTable);
    }

    /**
     * Method to check the validity of fields. Check that all data fields are
     * complete before creating or saving a client. Check that the name and
     * login fields have the correct size. Validate that the email and password
     * fields are written correctly. Check that the password and confirm
     * password fields are the same.
     *
     * @return
     */
    private boolean checkFields(boolean newClientFields) throws FieldsEmptyException,
            MaxCharacterException, EmailNotValidException,
            LoginNotValidException, PasswordNotValidException,
            ConfirmPasswordNotValidException {
        LOGGER.info("Check fields values");

        if (newClientFields) {
            //Check that the fields are complete to add a client
            if (tfName.getText().trim().isEmpty()
                    || tfEmail.getText().trim().isEmpty()
                    || tfLogin.getText().trim().isEmpty()
                    || tfPassword.getText().trim().isEmpty()
                    || tfConfirmPassword.getText().trim().isEmpty()) {
                LOGGER.info("Some fields are empty");
                throw new FieldsEmptyException("Error, some fields are empty");
            }
            //Check that the fields are complete to edit a client
        } else {
            if (tfName.getText().trim().isEmpty()
                    || tfEmail.getText().trim().isEmpty()
                    || tfLogin.getText().trim().isEmpty()) {
                LOGGER.info("Some fields are empty");
                throw new FieldsEmptyException("Error, some fields are empty");
            }
        }

        //Checks if the name field has more than 25 characters
        if (tfName.getText().trim().length() > 25) {
            throw new MaxCharacterException("Error, maximum size");
        }

        //Check if the email field is written correctly
        if (!validateEmail(tfEmail.getText().trim())) {
            throw new EmailNotValidException("Error, invalid email");
        }

        //Check if the login field has no more than 25 characters 
        //and no less than 5
        if (tfLogin.getText().trim().length() > 25
                || tfLogin.getText().trim().length() < 5) {
            throw new LoginNotValidException("Error, invalid login");
        }

        if (newClientFields) {
            //Check if the password field is written correctly
            if (!validatePassword(tfPassword.getText().trim())) {
                throw new PasswordNotValidException("Error, invalid password");
            }

            //Check if the confirm password field is written correctly
            if (!validatePassword(tfConfirmPassword.getText().trim())) {
                throw new PasswordNotValidException("Error, invalid password");
            }

            //Check that the confirm password field 
            //contains exactly the same characters typed
            if (!tfConfirmPassword.getText().trim()
                    .equals(tfPassword.getText().trim())) {
                throw new ConfirmPasswordNotValidException("Error, "
                        + "different confirmation password");
            }
        }

        return true;
    }

    /**
     * Method that validates the email.
     *
     * @param emailStr
     * @return
     */
    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    /**
     * Method that validates the password.
     *
     * @param password
     * @return true if its valid
     */
    public static boolean validatePassword(String password) {
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.find();
    }

    /**
     * Method that controls the action button of removing a client: The delete
     * client button is enabled when a client is selected in the table. Pressing
     * the button will send a confirmation alert. If the remove is confirmed,
     * the implementation method that implements the remove interface will be
     * called. The client ID is sent. The remove implementation will call the
     * client remove restful which will send the information to the server
     * remove restful. The server part removes the client with that ID and the
     * client table is updated.
     *
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
        Optional<ButtonType> result = alert1.showAndWait();
        if (result.get() == ButtonType.OK) {

            try {
                LOGGER.info("Delete selected client");

                //Delete the selected client by calling the logic part
                Client clientToRemove = tbClient
                        .getSelectionModel()
                        .getSelectedItem();
                clientInterface
                        .removeClient(String.valueOf(clientToRemove.getId()));

                //Update client table 
                loadClientTable();

            } catch (ClientServerConnectionException e) {
                LOGGER.info("Error deleting client");
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
     * Check that the name field is filled.
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    public void txtNameFull(ObservableValue observable,
            Object oldValue, Object newValue) {
        LOGGER.info("Check field name filled");

        name = false;
        if (tfName.getText().trim() != null) {
            name = true;
        }
        filledFields();
    }

    /**
     * Check that the email field is filled.
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    public void txtEmailFull(ObservableValue observable,
            Object oldValue, Object newValue) {
        LOGGER.info("Check field email filled");

        email = false;
        if (tfEmail.getText().trim() != null) {
            email = true;
        }
        filledFields();
    }

    /**
     * Check that the login field is filled.
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    public void txtLoginFull(ObservableValue observable,
            Object oldValue, Object newValue) {
        LOGGER.info("Check field login filled");

        login = false;
        if (tfLogin.getText().trim() != null) {
            login = true;
        }
        filledFields();
    }

    /**
     * Check that the password field is filled.
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    public void txtPasswordFull(ObservableValue observable,
            Object oldValue, Object newValue) {
        LOGGER.info("Check field password filled");

        password = false;
        if (tfPassword.getText().trim() != null) {
            password = true;
        }
        filledFields();
    }

    /**
     * Check that the confirm password field is filled.
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    public void txtConfirmPasswordFull(ObservableValue observable,
            Object oldValue, Object newValue) {
        LOGGER.info("Check field password filled");

        confirmPassword = false;
        if (tfConfirmPassword.getText().trim() != null) {
            confirmPassword = true;
        }
        filledFields();
    }

    /**
     * Check that the new client button is enabled when all the fields are
     * complete and there is no client selected in the table.
     */
    public void filledFields() {
        LOGGER.info("Check fields filled");

        if (name && login && email && password && confirmPassword
                && !tableSelect) {
            btnNewClient.setDisable(false);
        } else {
            btnNewClient.setDisable(true);
        }
    }

    /**
     * Method that controls the action button of creating a new client: If there
     * are no clients selected in the table, the New Client button is enabled.
     * All data fields are filled in and it is checked that they are written
     * correctly. Pressing the button will send a confirmation alert. If the
     * creation is confirmed, the implementation method that implements the
     * creation interface will be called. The client object is sent. The create
     * implementation will call the client create restful which will send the
     * information to the server create restful. The server part creates the
     * client and the client table is updated.
     *
     * @param event the event linked to clicking on the button;
     */
    @FXML
    private void handleAddClient(ActionEvent event) {
        LOGGER.info("Option to create a new client");

        try {

            //Check the information entered in the fields 
            //when all the fields are entered
            if (checkFields(true)) {
                LOGGER.info("Correct text fields");

                Alert alert1 = new Alert(AlertType.CONFIRMATION);
                alert1.setTitle("New Client");
                alert1.setHeaderText(null);
                alert1.setContentText("Are you sure "
                        + "you want to add this client?");
                Optional<ButtonType> result = alert1.showAndWait();
                if (result.get() == ButtonType.OK) {

                    try {
                        LOGGER.info("Adding a new client");

                        Client clientToCreate = new Client();
                        clientToCreate.setId(null);
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
                        clientInterface.signUp(clientToCreate);

                        //Update client table 
                        loadClientTable();

                    } catch (ClientServerConnectionException e) {
                        LOGGER.info("Error connecting to the Server");
                        Alert altErrorSC = new Alert(AlertType.ERROR);
                        altErrorSC.setTitle("System Error");
                        altErrorSC.setHeaderText("Could not Connect to the Server");
                        altErrorSC.setContentText("The application could not connect to the server"
                                + "\n The Server may be busy with too many incoming requests, "
                                + "try again later, if this error continues, contact support or check server availability");
                        altErrorSC.showAndWait();

                    } catch (DBServerException e) {
                        LOGGER.info("Error connecting to the DataBase");
                        Alert altErrorDB = new Alert(AlertType.ERROR);
                        altErrorDB.setTitle("System Error");
                        altErrorDB.setHeaderText("Could not Connect to the DataBase");
                        altErrorDB.setContentText("The server could not register your info, try again later. "
                                + "If this error persists, contact support or check the DataBase availability");
                        altErrorDB.showAndWait();

                    } catch (LoginOnUseException e) {
                        LOGGER.info("Error, login on use ");
                        Alert altWarningLog = new Alert(AlertType.WARNING);
                        altWarningLog.setTitle("Login on use");
                        altWarningLog.setHeaderText("Login already on use.");
                        altWarningLog.setContentText("The text value input for the login field is allready on use on the DataBase."
                                + "You may allready be registered on the database. If not another user is using that login, you"
                                + "will have to use another one.");
                        altWarningLog.showAndWait();
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
     * Method that controls the action button of editing a new client: The save
     * change button is enabled when a client is selected in the table. The data
     * of the selected client is displayed in the fields. The necessary changes
     * are made in the fields. It is checked that all the fields are complete
     * and that all the data is correctly entered. Pressing the button will send
     * a confirmation alert. If the edition is confirmed, the implementation
     * method that implements the edit interface will be called. The client
     * object is sent. The edit implementation will call the client edit restful
     * which will send the information to the server edit restful. The server
     * part edits the client and the client table is updated.
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

            //Check the information entered in the fields 
            //without being able to modify the password fields
            if (checkFields(false)) {
                LOGGER.info("Correct text fields");

                Alert alert1 = new Alert(AlertType.CONFIRMATION);
                alert1.setTitle("Save Client");
                alert1.setHeaderText(null);
                alert1.setContentText("Are you sure "
                        + "you want to edit this client?");
                Optional<ButtonType> result = alert1.showAndWait();
                if (result.get() == ButtonType.OK) {

                    try {
                        LOGGER.info("Edit the client");

                        //Edit the client by calling the logical part
                        clientInterface.editClient(clientToEdit);

                        //Update client table 
                        loadClientTable();

                    } catch (ClientServerConnectionException e) {
                        LOGGER.info("Error editing client");
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
                    alert3.setContentText("Unedited client");
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
     * clients.
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
        Optional<ButtonType> result = alert1.showAndWait();
        if (result.get() == ButtonType.OK) {

            try {

                LOGGER.info("Beginning printing action...");

                JasperReport report
                        = JasperCompileManager.compileReport(getClass()
                                .getResourceAsStream("/reto2g1cclient/reports/clientReport.jrxml"));

                //Data for the report: 
                //a collection of UserBean passed as a JRDataSource implementation 
                JRBeanCollectionDataSource dataItems
                        = new JRBeanCollectionDataSource((Collection<Client>) this.tbClient.getItems());

                //Map of parameter to be passed to the report
                Map<String, Object> parameters = new HashMap<>();

                //Fill report with data
                JasperPrint jasperPrint = JasperFillManager
                        .fillReport(report, parameters, dataItems);

                //Create and show the report window. 
                //The second parameter false value makes report window not to close app.
                JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                jasperViewer.setVisible(true);
                // jasperViewer.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

            } catch (JRException ex) {
                LOGGER.info("Error printing information");
                Alert alert2 = new Alert(AlertType.ERROR);
                alert2.setTitle("Ayuda");
                alert2.setHeaderText("Error opening the report");
                alert2.setContentText(ex.getMessage());
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
     * When you select one of the options the search field is enabled.
     *
     * @param event the event linked to clicking on the button;
     */
    @FXML
    private void handleSearchBy(ActionEvent event) {
        LOGGER.info("Filter field disable");

        txtFilter.setDisable(false);
    }

    /**
     * Method that controls the action button that allows to see the different
     * events of a specific client.
     *
     * @param event the event linked to clicking on the button;
     */
    @FXML
    private void handleOpenEventTable(ActionEvent event) {
        LOGGER.info("Open the window with the client events table");

        Client clientEvents = tbClient.getSelectionModel().getSelectedItem();

        try {
            LOGGER.info("Open VEventTable");
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/reto2g1cclient/view/VEventTable.fxml"));
            Parent root = (Parent) loader.load();
            VEventTableController controller = loader.getController();
            controller.setStage(this.stage);
            controller.setClient(clientEvents);
            controller.setEditable(true);
            controller.initStage(root);

        } catch (IOException e) {
            LOGGER.info("Error opening event table window");
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("AYUDA");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Method that controls the action button to return to the previous window.
     * Pressing the button will send a confirmation alert. if the confirmation
     * is accepted, the window will close and load the Admin window.
     *
     * @param event the event linked to clicking on the button;
     */
    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        LOGGER.info("Requesting confirmation for application closing...");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Volviendo a Ventana Principal");
        alert.setHeaderText("¿Seguro que desea volver a la ventana principal? Se perdera el progreso no guardado");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                LOGGER.info("Cambiando a ventana de Admin");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VAdmin.fxml"));
                Parent root = (Parent) loader.load();
                VAdminController controller = ((VAdminController) loader.getController());
                controller.setStage(this.stage);
                controller.initStage(root);
            } catch (Exception e) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Error al cambiar de ventana");
                alert2.setHeaderText("Error al volver a la ventana anterior");
                alert2.setContentText("Ha sucedido un error al intentar volver a la ventana anterior, intentelo de nuevo. "
                        + "\nSi el error persiste, puede deberse a que la ventana a la que se intenta ir no este implementada todavia");
                alert2.showAndWait();
            }
        } else {
            event.consume();
            LOGGER.info("Closing aborted");
        }
}

/**
 * Method to advise the user when uses the UI's innate close button (button X)
 * that the application will close.
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
     * Method that controls the search action button by a specific filter.
     * Depending on the Combo Box filter and the filter introduced in the
     * search, the filtering will be executed in the table.
     *
     * @param event the event linked to clicking on the button;
     */
    @FXML
        private void handleSearch(ActionEvent event) throws ClientServerConnectionException {
        LOGGER.info("Run filters");

        //Create an arraylis that will contain the search data
        List<Client> clients = new ArrayList<>();
        clientList = (List<Client>) clientInterface.getAllClient();

        switch (cbSearchBy.getValue()) {

            //Filter by name
            case SELECT_NAME:
                //Check that the search field contains something typed
                if (!txtFilter.getText().trim().equals("")) {
                    //Go through the clients with the information entered
                    for (Client client : clientList) {
                        if (client.getFullName().toUpperCase()
                                .contains(txtFilter.getText().toUpperCase().trim())) {
                            clients.add(client);
                        }
                    }
                    //Overwrite the observable list with the found data
                    clientList = clients;
                } else {
                    //Load all clients in the table
                    clientList = (List<Client>) clientInterface.getAllClient();
                }
                break;

            //Filter by login
            case SELECT_LOGIN:
                //Check that the search field contains something typed
                if (!txtFilter.getText().trim().equals("")) {
                    //Go through the clients with the information entered
                    for (Client client : clientList) {
                        if (client.getLogin().toUpperCase()
                                .contains(txtFilter.getText().toUpperCase().trim())) {
                            clients.add(client);
                        }
                    }
                    //Overwrite the observable list with the found data
                    clientList = clients;
                } else {
                    //Load all clients in the table
                    clientList = (List<Client>) clientInterface.getAllClient();
                }
                break;

            //Filter by email
            case SELECT_EMAIL:
                //Check that the search field contains something typed
                if (!txtFilter.getText().trim().equals("")) {
                    //Go through the clients with the information entered
                    for (Client client : clientList) {
                        if (client.getEmail().toUpperCase()
                                .contains(txtFilter.getText().toUpperCase().trim())) {
                            clients.add(client);
                        }
                    }
                    //Overwrite the observable list with the found data
                    clientList = clients;
                } else {
                    //Load all clients in the table
                    clientList = (List<Client>) clientInterface.getAllClient();
                }
                break;

            //Filter by type
            case SELECT_TYPE:
                //Check that the search field contains something typed
                if (!txtFilter.getText().trim().equals("")) {
                    //Go through the clients with the information entered
                    for (Client client : clientList) {
                        if (client.getType()
                                .equals(txtFilter.getText().toUpperCase().trim())) {
                            clients.add(client);
                        }
                    }
                    //Overwrite the observable list with the found data
                    clientList = clients;
                } else {
                    //Load all clients in the table
                    clientList = (List<Client>) clientInterface.getAllClient();
                }
                break;
        }

        loadClientTable();
    }

}
