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
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import reto2g1cclient.exception.ClientServerConnectionException;
import reto2g1cclient.exception.LoginOnUseException;
import reto2g1cclient.exception.DBServerException;
import reto2g1cclient.logic.CommercialFactory;
import reto2g1cclient.logic.CommercialInterface;
import reto2g1cclient.model.Client;
import reto2g1cclient.model.Commercial;
import reto2g1cclient.model.Especialization;
import reto2g1cclient.model.UserStatus;

/**
 * Clase para gestionar la tabla del commercial
 *
 * @author Enaitz Izagirre
 */
public class VCommercialTableController {

    //logger
    private static final Logger LOGGER = Logger.getLogger("package.class");

    //pattern
    //email validation pattern
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                    Pattern.CASE_INSENSITIVE);

    //password validation pattern
    public static final Pattern VALID_PASSWORD_REGEX
            = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$",
                    Pattern.CASE_INSENSITIVE);

    //Valores Combo Box
    //Valores del combo de Especializacion
    private final String SONIDO = "SONIDO";
    private final String ILUMINACION = "ILUMINACION";
    private final String PIROTECNIA = "PIROTECNIA";
    private final String LOGISTICA = "LOGISTICA";

    //Valores combobox search
    private final String NAME = "Name";
    private final String LOGIN = "Login";
    private final String EMAIL = "Email";
    private final String ESPECIALIZATION = "Especialization";

    //Tabla Commercial
    //La tabla 
    @FXML
    private TableView<Commercial> tbCommercial;
    //las columnas de la tabla
    @FXML
    private TableColumn<Commercial, String> colName;
    @FXML
    private TableColumn<Commercial, String> colLogin;
    @FXML
    private TableColumn<Commercial, String> colEmail;
    //Cuidado con especialization 
    //Combo que toma datos del enum Especialization
    @FXML
    private TableColumn<Commercial, Especialization> colEspecialization;

    //Botones
    //Crea Commercial
    @FXML
    private Button btnNew;
    //Borra Commercial
    @FXML
    private Button btnDelete;
    //Guarda los cambios Commercial
    @FXML
    private Button btnSave;
    //Busca Commercial por filtro
    @FXML
    private Button btnSearch;
    //Imprime el formulario con los datos de la tabla
    @FXML
    private Button btnPrint;
    //Vuelve a la ventana anterior
    @FXML
    private Button btnBack;

    //Labels de error para informar al usuario en que campo se confundio
    //Al ser un admin el que introduce los datos este no se equivocara con los parametros establecido en el SignUp
    //Especializazion no tiene error por que los datos son correctos y siempre mantendra una seleccionada
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

    //Text fields de los datos del commercial
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfLogin;
    @FXML
    private TextField tfEmail;
    //Text field para buscar por texto
    @FXML
    private TextField txtFilter;

    //Password fields
    @FXML
    private PasswordField tfPassword;
    @FXML
    private PasswordField tfConfirmPassword;

    //Combo box
    //Para las especializaciones del commercial ya establecidos
    @FXML
    private ComboBox<String> cbEspecialization;
    //Para las busquedas q pueda tener con los campos del commercial
    @FXML
    private ComboBox<String> cbSearchBy;

    //Interfaz del commercial
    private CommercialInterface commercialInterface;
    private Stage stage;
    //Especie de array list para cargar los datos en la tabla
    private ObservableList<Commercial> commercialForTable;
    private List<Commercial> commercialList;

    //Booleanos de control para habilitar botones
    private Boolean name;
    private Boolean email;
    private Boolean login;
    private Boolean password;
    //confirma password
    private Boolean passwordR;
    //para saber si la tabla esta seleccionada (para el modi )
    private Boolean tableSelec;

    /**
     * Inicializa y muestra la ventana . Metodo para mostrar la ventana con los
     * datos cargados
     *
     * @param root Parametro recivido para cargar la ventana
     * @throws IOException
     */
    public void initStage(Parent root) throws IOException {

        LOGGER.info("Initializing VCommercialTable stage.");

        //Crea una nueva escena
        Scene scene = new Scene(root);

        //Introduce los datos de la escena en el escenario
        stage.setScene(scene);

        //Ajustes basicos de la ventana
        stage.setTitle("Commercial Table");
        stage.setResizable(false);

        commercialInterface= CommercialFactory.getImplementation();
        //Metodos de acciones basicas de la ventana 
        //Metodo para cargar los datos basicos de la ventana 
        stage.setOnShowing(this::handleWindowShowing);

        //Metodo para cerrar la ventana al darle a la x de arriba a la derecha 
        stage.setOnCloseRequest(this::close);

        //muestra la ventana
        stage.show();
    }

    /**
     * Prepara los datos basicos de la ventana antes de que esta cargue para asi
     * mostrar unicamente lo que el usuario requiere
     *
     * @param event
     */
    private void handleWindowShowing(WindowEvent event) {
        LOGGER.info("Beginning VCommercialTable::handleWindowShowing");

        //carga los datos seleccionados en la tabla 
        tbCommercial.getSelectionModel().selectedItemProperty().addListener(this::handleSelection);

        //Botones DEShabilitados
        btnDelete.setDisable(true);
        btnNew.setDisable(true);
        btnSave.setDisable(true);

        //Botones HABILitados
        btnPrint.setDisable(false);
        btnSearch.setDisable(false);
        btnBack.setDisable(false);

        //Hacer invisible los label de error
        lblErrorName.setVisible(false);
        lblErrorEmail.setVisible(false);
        lblErrorLogin.setVisible(false);
        lblErrorPassword.setVisible(false);
        lblErrorConfirmPassword.setVisible(false);

        //Text field de la busqueda
        txtFilter.setDisable(false);

        //Acciones TextField para que se habiliten los botones
        tfName.textProperty().addListener(this::txtNameVal);
        tfLogin.textProperty().addListener(this::txtLoginVal);
        tfEmail.textProperty().addListener(this::txtEmailVal);
        tfPassword.textProperty().addListener(this::txtPasswordVal);
        tfConfirmPassword.textProperty().addListener(this::txtConfirmPasswordVal);

        //Metodos al pulsar el boton    
        btnDelete.setOnAction(this::handleDelete);
        btnSave.setOnAction(this::handleSave);
        btnNew.setOnAction(this::handleAdd);
        btnBack.setOnAction(this::handleBack);
        btnPrint.setOnAction(this::handlePrint);
        btnSearch.setOnAction(this::handleSearch);

        //añadir valores a un combo Especialization
        ObservableList<String> optionsForComboEspecialization;
        optionsForComboEspecialization = FXCollections.observableArrayList(SONIDO, ILUMINACION, PIROTECNIA, LOGISTICA);
        cbEspecialization.setItems(optionsForComboEspecialization);
        //selecciona el primer String del combo
        cbEspecialization.getSelectionModel().selectFirst();

        //añadir valores al combo del filter 
        ObservableList<String> optionsForComboSearch;
        optionsForComboSearch = FXCollections.observableArrayList(NAME, LOGIN, EMAIL, ESPECIALIZATION);
        cbSearchBy.setItems(optionsForComboSearch);
        //selecciona el primer String del combo
        cbSearchBy.getSelectionModel().selectFirst();

        //Añade los valores a las celdas de la tabla 
        colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEspecialization.setCellValueFactory(new PropertyValueFactory<>("especialization"));

        try {
            commercialForTable = FXCollections.observableArrayList(commercialInterface.findAll());
            tbCommercial.setItems(commercialForTable);
            //ClientServerConnectionException
        } catch (Exception ex) {
            LOGGER.info("Error, commercial collection not working");
        }
    }

    /**
     * Validacion para saber si los campos estan completos
     */
    public void validateData() {
        if (name && login && email && password && passwordR && !tableSelec) {
            btnNew.setDisable(false);
        } else {
            btnNew.setDisable(true);
        }
    }

    /**
     * observable del text field CONFIRM PASSWORD para saber cuando cambia el
     * campo
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    public void txtConfirmPasswordVal(ObservableValue observable, Object oldValue, Object newValue) {
        passwordR = false;
        if (tfConfirmPassword.getText().trim() != null) {
            passwordR = true;
        }
        validateData();
    }

    /**
     * observable del text field PASSWORD para saber cuando cambia el campo
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    public void txtPasswordVal(ObservableValue observable, Object oldValue, Object newValue) {
        password = false;
        if (tfPassword.getText().trim() != null) {
            password = true;
        }
        validateData();
    }

    /**
     * observable del text field EMAIL para saber cuando cambia el campo
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    public void txtEmailVal(ObservableValue observable, Object oldValue, Object newValue) {
        email = false;
        if (tfEmail.getText().trim() != null) {
            email = true;
        }
        validateData();
    }

    /**
     * observable del text field LOGIN para saber cuando cambia el campo
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    public void txtLoginVal(ObservableValue observable, Object oldValue, Object newValue) {
        login = false;
        if (tfLogin.getText().trim() != null) {
            login = true;
        }
        validateData();
    }

    /**
     * observable del text field NAME para saber cuando cambia el campo
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    public void txtNameVal(ObservableValue observable, Object oldValue, Object newValue) {
        name = false;
        if (tfName.getText().trim() != null) {
            name = true;
        }
        validateData();
    }

    /**
     * Metodo para controlar la seleccion de los campos ,
     *
     * @param observableValue
     * @param oldValue
     * @param newValue
     */
    private void handleSelection(ObservableValue observableValue,
            Object oldValue, Object newValue) {

        //Cuando selecciona algo en la tabla 
        if (newValue != null) {
            //mete los datos en los text field 
            Commercial commercial = tbCommercial.getSelectionModel().getSelectedItem();
            tfName.setText(commercial.getFullName());
            tfLogin.setText(commercial.getLogin());
            tfEmail.setText(commercial.getEmail());
            cbEspecialization.getSelectionModel().select(commercial.getEspecialization().toString());
            //DEShabilita los tf de password para q no los modifique
            tfPassword.setDisable(true);
            tfConfirmPassword.setDisable(true);

            //Buttons enabled
            btnDelete.setDisable(false);
            btnSave.setDisable(false);

            //Actualizar booleanos de control
            name = false;
            login = false;
            email = false;
            tableSelec = true;

            //Commercial DESseleccionado  
        } else {
            //Elimina los datos de los campos
            tfName.setText("");
            tfLogin.setText("");
            tfEmail.setText("");
            cbEspecialization.getSelectionModel().selectFirst();
            //habilita los flied de password
            tfPassword.setDisable(false);
            tfPassword.setText("");
            tfConfirmPassword.setDisable(false);
            tfConfirmPassword.setText("");

            //Buttons disabled
            btnDelete.setDisable(true);
            btnSave.setDisable(true);

            //Actualizar booleanos de control
            name = false;
            login = false;
            email = false;
            tableSelec = false;
        }
    }

    /**
     * Carga los datos en la tabla y la refresca
     */
    private void loadTable() {
        
        
        
        try {

            //limpia los datos de la tabla 
            tbCommercial.getItems().clear();
            //mete los datos del commercial en el list
            commercialList = (List<Commercial>) commercialInterface.findAll();
            //carga los datos del obserbale en la tabla 
            tbCommercial.setItems(commercialForTable);
            //Refresh the table
            tbCommercial.refresh();

            //MODIFICAR : DBServerException
        } catch (Exception e) {
            LOGGER.info(e.getMessage() + "Failed to load data");
        }
        LOGGER.info("Load data table");
        commercialForTable = FXCollections.observableArrayList(commercialList);
        tbCommercial.setItems(commercialForTable);

    }

    /**
     * Comprueba si los datos de la tabla estan correctamente completados
     * (escritos/parametros correctos /etc) Cuenta con un booleano (NUEVO) para
     * reutilizar el metodo dependiendo si la comprobacion es para añadir nuevo
     * o para actualizar
     *
     * @return devuelve un boolean dependiendo si cimple los requisitos sino
     * sale con el throw
     */
    private boolean checkFields(boolean nuevo) throws FieldsEmptyException,
            MaxCharacterException, EmailNotValidException,
            LoginNotValidException, PasswordNotValidException,
            ConfirmPasswordNotValidException {
        //Nuevo
        if (nuevo) {
            //Revisa si TODOS los campos estan escritos
            if (tfName.getText().trim().isEmpty()
                    || tfEmail.getText().trim().isEmpty()
                    || tfLogin.getText().trim().isEmpty()
                    || tfPassword.getText().trim().isEmpty()
                    || tfConfirmPassword.getText().trim().isEmpty()) {
                LOGGER.info("Some fields are empty");
                throw new FieldsEmptyException("Error, some fields are empty");
            }
        } else {
            //Revisa si CASI todos los campos estan escritos a excepcion de los password
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

        if (nuevo) {
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
     * Metodo para validad email
     *
     * @param emailStr String del email
     * @return devuelve si el email es valido
     */
    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    /**
     * Metodo valida password
     *
     * @param password String del password
     * @return verdadero si es valido
     */
    public static boolean validatePassword(String password) {
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.find();
    }

    /**
     * Elimina el Commercial de la tabla
     *
     * @param event the event linked to clicking on the button;
     */
    @FXML
    private void handleDelete(ActionEvent event) {
        LOGGER.info("Option to commercial the selected client");
        //Alert para confirmar el delete
        Alert alert1 = new Alert(AlertType.CONFIRMATION);
        alert1.setTitle("Delete Commercial");
        alert1.setHeaderText(null);
        alert1.setContentText("Are you sure you want to delete this commercial?");
        Optional<ButtonType> result = alert1.showAndWait();
        if (result.get() == ButtonType.OK) {

            try {
                LOGGER.info("Delete selected commercial");
                //Delete the selected client by calling the logic part
                Commercial commercialToRemove = tbCommercial
                        .getSelectionModel()
                        .getSelectedItem();
                commercialInterface
                        .remove(String.valueOf(commercialToRemove.getId()));
                //Update table 
                loadTable();
                //Refresh the table
                tbCommercial.refresh();

            } catch (Exception e) {
                Alert alert2 = new Alert(AlertType.ERROR);
                alert2.setTitle("Help");
                alert2.setHeaderText("Error");
                alert2.setContentText(e.getMessage());
                alert2.showAndWait();
            }
            //Information alert when you dismiss the confirmation alert
        } else {
            Alert alert3 = new Alert(AlertType.INFORMATION);
            alert3.setTitle("Commercial not removed");
            alert3.setHeaderText(null);
            alert3.setContentText(null);
            alert3.showAndWait();
        }
    }

    /**
     * Metodo que gestiona el boton de añadir para asi introducir datos a la tabla 
     * 
     * @param event the event linked to clicking on the button;
     */
    @FXML
    private void handleAdd(ActionEvent event)  {
        LOGGER.info("Option to create a new Commercial");

        try {

            //Revisa si los campos estan completos
            //TRUE para confirmar que es uno nuevo
            if (checkFields(true)) {
                LOGGER.info("Correct text fields");
                //alert
                Alert alert1 = new Alert(AlertType.CONFIRMATION);
                alert1.setTitle("New Commercial");
                alert1.setHeaderText(null);
                alert1.setContentText("Are you sure "
                        + "you want to add this Commercial?");
                Optional<ButtonType> result = alert1.showAndWait();
                
                //confirmacion de ok
                if (result.get() == ButtonType.OK) {
                    try {
                        LOGGER.info("Adding a new Commercial");
                        Commercial commercialToCreate = new Commercial();
                        //llena los datos del commercial
                        commercialToCreate.setId(null);
                        commercialToCreate.setFullName(tfName.getText());
                        commercialToCreate.setEmail(tfEmail.getText());
                        commercialToCreate.setLogin(tfLogin.getText());
                        commercialToCreate.setPassword(tfPassword.getText());
                        commercialToCreate.setStatus(UserStatus.ENABLED);
                        
                        //Para seleccionar la especializacion
                        switch (cbEspecialization.getSelectionModel().getSelectedItem()) {
                            case SONIDO:
                                commercialToCreate.setEspecialization(Especialization.SONIDO);
                                break;
                            case ILUMINACION:
                                commercialToCreate.setEspecialization(Especialization.ILUMINACION);
                                break;
                            case PIROTECNIA:
                                commercialToCreate.setEspecialization(Especialization.PIROTECNIA);
                                break;
                            case LOGISTICA:
                                commercialToCreate.setEspecialization(Especialization.LOGISTICA);
                                break;
                        }
                        //introduce el commercial en la base de datos
                        commercialInterface.signUp(commercialToCreate);
                        
                        //Update table
                        loadTable();

                    } catch (ClientServerConnectionException e) {
                        Alert altErrorSC = new Alert(AlertType.ERROR);
                        altErrorSC.setTitle("System Error");
                        altErrorSC.setHeaderText("Could not Connect to the Server");
                        altErrorSC.setContentText("The application could not connect to the server"
                                + "\n The Server may be busy with too many incoming requests, "
                                + "try again later, if this error continues, contact support or check server availability");
                        altErrorSC.showAndWait();
                    } catch (LoginOnUseException e) {
                        Alert altWarningLog = new Alert(AlertType.WARNING);
                        altWarningLog.setTitle("Login on use");
                        altWarningLog.setHeaderText("Login already on use.");
                        altWarningLog.setContentText("The text value input for the login field is allready on use on the DataBase."
                                + "You may allready be registered on the database. If not another user is using that login, you"
                                + "will have to use another one.");
                        altWarningLog.showAndWait();
                    } catch (DBServerException ex) {
                        Logger.getLogger(VCommercialTableController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //Information alert when you dismiss the confirmation alert
                } else {
                    Alert alert3 = new Alert(AlertType.INFORMATION);
                    alert3.setTitle("Commercial not added");
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
     * Metodo para actualizar el commercial de la tabla 
     *
     * @param event the event linked to clicking on the button;
     */
    @FXML
    private void handleSave(ActionEvent event) {
        LOGGER.info("Option to edit a selected Commercial");
        //alert
        LOGGER.info("Editing a Commercial");
        Commercial commercialToEdit = tbCommercial.getSelectionModel().getSelectedItem();
        commercialToEdit.setFullName(tfName.getText());
        commercialToEdit.setEmail(tfEmail.getText());
        commercialToEdit.setLogin(tfLogin.getText());
        commercialToEdit.setPassword(tfPassword.getText());
        //Combo de especialization
        switch (cbEspecialization.getSelectionModel().getSelectedItem()) {
            case SONIDO:
                commercialToEdit.setEspecialization(Especialization.SONIDO);
                break;
            case ILUMINACION:
                commercialToEdit.setEspecialization(Especialization.ILUMINACION);
                break;
            case PIROTECNIA:
                commercialToEdit.setEspecialization(Especialization.PIROTECNIA);
                break;
            case LOGISTICA:
                commercialToEdit.setEspecialization(Especialization.LOGISTICA);
                break;
        }

        try {

            //Comprueba la informacion 
            if (checkFields(false)) {
                LOGGER.info("Correct text fields");
                //alert
                Alert alert1 = new Alert(AlertType.CONFIRMATION);
                alert1.setTitle("Save Commercial");
                alert1.setHeaderText(null);
                alert1.setContentText("Are you sure "
                        + "you want to edit this Commercial?");
                Optional<ButtonType> result = alert1.showAndWait();
                //comprueba el ok dle alert
                if (result.get() == ButtonType.OK) {
                    //edita el comercial
                    try {
                        LOGGER.info("Edit the Commercial");
                        //Edit the Commercial by calling the logical part
                        commercialInterface.edit(commercialToEdit);
                       
                        //Update Commercial table 
                        loadTable();

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
                    alert3.setTitle("Unedited Commercial");
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
     * Imprime la tabla de los commerciales
     *
     * @param event the event linked to clicking on the button;
     */
    @FXML
    private void handlePrint(ActionEvent event) {
        LOGGER.info("Option to print Commercial information");
        //Alert
        Alert alert1 = new Alert(AlertType.CONFIRMATION);
        alert1.setTitle("Print Information");
        alert1.setHeaderText(null);
        alert1.setContentText("Do you want to print the information?");
        Optional<ButtonType> result = alert1.showAndWait();
        //Confirmacion del alert
        if (result.get() == ButtonType.OK) {

            try {
                LOGGER.info("Beginning printing action...");
                //ruta de donde se crea el print 
                JasperReport report = JasperCompileManager.compileReport(getClass()
                        .getResourceAsStream("/reto2g1cclient/reports/commercialReport.jrxml"));
                //Recoge los datos de la tabla 
                JRBeanCollectionDataSource dataItems
                        = new JRBeanCollectionDataSource((Collection<Commercial>) this.tbCommercial.getItems());
                //Map of parameter to be passed to the report
                Map<String, Object> parameters = new HashMap<>();
                //Fill report with data
                JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
                //Create and show the report window. The second parameter false value makes 
                //report window not to close app.
                JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                jasperViewer.setVisible(true);
                //jasperViewer.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

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
     * Metodo para filtrar los datos de la tabla
     *
     * @param event the event linked to clicking on the button;
     */
    @FXML
    private void handleSearch(ActionEvent event) {

        LOGGER.info("ejecutando filtros ");
        //comprobamos el campo de la combo box
        //recorremos la lista de clientes y si contiene el campo lo añadimos a la nueva lista creada
        List<Commercial> commercials = new ArrayList<>();
        commercialList = (List<Commercial>) commercialInterface.findAll();
        //tb se puede hacer con get value , habria q cambiar la palabra del case
        switch (cbSearchBy.getSelectionModel().getSelectedIndex()) {
            //NAME
            case 0:
                //si ESTAN rellenos
                if (!txtFilter.getText().trim().equals("")) {
                    for (Commercial co : commercialList) {
                        //comprueba NAME
                        if (co.getFullName().toUpperCase().contains(txtFilter.getText().toUpperCase().trim())) {
                            //lo añade a la lista secundaria
                            commercials.add(co);
                        }
                    }
                    //sobre escribe el Observable de la tabla con la lista auxiliar de los datos que buscamos
                    commercialList = commercials;
                } else {
                    //altualiza la tabla con todos los datos 
                    // REVISAR
                    commercialList = (List<Commercial>) commercialInterface.findAll();
                }

                break;
            //LOGIN
            case 2:
                if (!txtFilter.getText().trim().equals("")) {
                    for (Commercial co : commercialList) {
                        //comrueba LOGIN
                        if (co.getLogin().toUpperCase().contains(txtFilter.getText().toUpperCase().trim())) {
                            commercials.add(co);
                        }
                    }
                    commercialList = commercials;
                } else {
                    commercialList = (List<Commercial>) commercialInterface.findAll();
                }

                break;
            //EMAIL
            case 3:
                if (!txtFilter.getText().trim().equals("")) {
                    for (Commercial co : commercialList) {
                        //EMAIL
                        if (co.getEmail().toUpperCase().contains(txtFilter.getText().toUpperCase().trim())) {
                            commercials.add(co);
                        }
                    }
                    commercialList = commercials;
                } else {
                    commercialList = (List<Commercial>) commercialInterface.findAll();
                }

                break;
            //ESPECIALIZATION
            case 4:
                if (!txtFilter.getText().trim().equals("")) {
                    for (Commercial co : commercialList) {
                        //ESPECIALIZATION
                        if (co.getEspecialization().equals(txtFilter.getText().toUpperCase().trim())) {
                            commercials.add(co);
                        }
                    }
                    commercialList = commercials;
                } else {
                    commercialList = (List<Commercial>) commercialInterface.findAll();
                }
                break;
        }
        //metodo para actualizar los datos de la tabla
        loadTable();

    }

    /**
     * Metodo para volver a la ventana anterior , en este caso a la de VAdmin
     * Pregunta mediante un alert si desea volver atras
     *
     * @param event the event linked to clicking on the button
     */
    @FXML
    private void handleBack(ActionEvent event) {
        LOGGER.info("Returning to VAdmin window");

        //Alert
        Alert alert1 = new Alert(AlertType.CONFIRMATION);
        alert1.setTitle("Back to VAdmin");
        alert1.setHeaderText(null);
        alert1.setContentText("Are you sure "
                + "you want to return to the previous window?"
                + " You will lose all your new data");
        Optional<ButtonType> result = alert1.showAndWait();

        //pregunta si desea regresar
        if (result.get() == ButtonType.OK) {
            LOGGER.info("Closing window and returning Back");
            //lector del xml del admin
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto2client/view/VAdmin.fxml"));

            //Carga la ventana anterior 
            try {
                Parent root = (Parent) loader.load();
                VAdminController controller = loader.getController();
                controller.setStage(this.stage);
                controller.initStage(root);

            } catch (IOException ex) {
                LOGGER.info("Error closing client table window");
            }
            //Cierra esta ventana
            this.stage.close();
        }
    }

    /**
     * Metodo para cerrar la ventana desde el boton de x No permite ir a la
     * ventana anterior
     *
     * @param event the event linked to clicking on the button;
     */
    public void close(WindowEvent event) {
        LOGGER.info("Requesting confirmation for application closing...");
        //Alert de confirmacion
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Program is closing");
        alert.setHeaderText("Are you sure you want to close the program?");
        Optional<ButtonType> result = alert.showAndWait();
        //Si le da a ok se cierra la ventana 
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
     * @param commercialInterface
     */
    public void setClientInterface(CommercialInterface commercialInterface) {
        this.commercialInterface = commercialInterface;
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

}
