package reto2g1cclient.controller;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import reto2g1cclient.logic.CommercialFactory;
import reto2g1cclient.logic.CommercialInterface;
import reto2g1cclient.model.Commercial;
import reto2g1cclient.model.User;

/**
 * @author Enaitz Izagirre
 */
public class VCommercialTableController {

    private static final Logger LOGGER = Logger.getLogger("package.class");

    private Stage stage;
    private CommercialInterface coI;
    private Commercial commercial;
    private List<Commercial> coL;
    //Array para la tabla
    private ObservableList<Commercial> coO;
    // Booleanos para control de contenido introducido valido
    private Boolean name;
    private Boolean email;
    private Boolean login;
    private Boolean password;
    private Boolean passwordR;
    private Boolean tableSelec;
    // Integer para ComboBox Filtro y Especializacion
    private Integer filter;
    private Integer especializationF;

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

    public CommercialInterface getEi() {
        return coI;
    }

    public void setEi(CommercialInterface coI) {
        this.coI = coI;
    }

    //tabla 
    @FXML
    private TableView<Commercial> tbCommercial;
    @FXML
    private TableColumn clNombre;
    @FXML
    private TableColumn clLogin;
    @FXML
    private TableColumn clEmail;
    @FXML
    private TableColumn clEspecialization;

    //botones
    @FXML
    private Button btnBack;
    @FXML
    private Button btnFind;
    @FXML
    private Button btnNewCommercial;
    @FXML
    private Button btnSaveCommercial;
    @FXML
    private Button btnDeleteCommercial;
    @FXML
    private Button btnPrint;

    //textField
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfLogin;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfFind;

    //PasswordField
    @FXML
    private PasswordField tfPassword;
    @FXML
    private PasswordField tfConfirmPassword;

    //Combo Box 
    @FXML
    private ComboBox cmbEspecialization;
    @FXML
    private ComboBox cmbFind;

    //Label
    @FXML
    private Label lbeLogin;
    @FXML
    private Label lbeName;
    @FXML
    private Label lbeEmail;
    @FXML
    private Label lbeEspecialization;
    @FXML
    private Label lbePassword;
    @FXML
    private Label lbeConfirmPassword;

    public void initStage(Parent root) {

        LOGGER.info("Initializing VCommercialTable");
        // Inicialización de la ventana
        Scene scene = new Scene(root);

        //CSS (route & scene)
        String css = this.getClass().getResource("/reto2g1cclient/view/javaFXUIStyles.css").toExternalForm();
        scene.getStylesheets().add(css);

        //Associate the scene to the stage
        stage.setScene(scene);

        //Set the scene properties
        stage.setTitle("VCommercialTable");
        stage.setResizable(false);

        //Set Windows event handlers 
        stage.setOnShowing(this::handleWindowShowing);
        stage.setOnCloseRequest(this::closeVCommercialTableX);

        //Leer Commerciales Disponibles
        coI = CommercialFactory.getImplementation();
        //loadData();
        // Iniciar Tabla
        initiateTableColumns();
        tbCommercial.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tbCommercial.getSelectionModel().selectedItemProperty().addListener(this::commercialTableSelection);
        // Cargar Datos Iniciales en tabla
        loadTable();
        //Buscar con filtro
        cmbFind.getItems().addAll("Nombre", "Login", "Email", "Especializacion");
        cmbFind.getSelectionModel().selectedItemProperty().addListener(this::selectedFilter);
        btnFind.setOnAction(this::filterEvents);

        //Acciones de Botones 
        btnPrint.setOnAction(this::printForm);
        btnBack.setOnAction(this::closeVCommercialTable);
        btnDeleteCommercial.setOnAction(this::deleteTable);
        btnNewCommercial.setOnAction(this::nuevoCommercial);
        btnSaveCommercial.setOnAction(this::saveCommercial);

        //Acciones TextField para que se habiliten los botones
        tfName.textProperty().addListener(this::txtNameVal);
        tfLogin.textProperty().addListener(this::txtLoginVal);
        tfEmail.textProperty().addListener(this::txtEmailVal);
        tfPassword.textProperty().addListener(this::txtPasswordVal);
        tfConfirmPassword.textProperty().addListener(this::txtConfirmPasswordVal);
        
        //genera la ventana 
        stage.show();
        LOGGER.info("VCommercialTable Window Showing");
    }
    public void validateData() {
        if (name && login && email && password && passwordR && !tableSelec) {
            btnNewCommercial.setDisable(false);
        } else {
            btnNewCommercial.setDisable(true);
        }
    }
    public void txtConfirmPasswordVal(ObservableValue observable, Object oldValue, Object newValue) {
        passwordR = false;
        if (tfConfirmPassword.getText().trim() != null) {
            passwordR = true;
        }
        validateData();
    }
    public void txtPasswordVal(ObservableValue observable, Object oldValue, Object newValue) {
        password = false;
        if (tfPassword.getText().trim() != null) {
            password = true;
        }
        validateData();
    }
    public void txtEmailVal(ObservableValue observable, Object oldValue, Object newValue) {
        email = false;
        if (tfEmail.getText().trim() != null) {
            email = true;
        }
        validateData();
    }
    public void txtLoginVal(ObservableValue observable, Object oldValue, Object newValue) {
        login = false;
        if (tfLogin.getText().trim() != null) {
            login = true;
        }
        validateData();
    }
    public void txtNameVal(ObservableValue observable, Object oldValue, Object newValue) {
        name = false;
        if (tfName.getText().trim() != null) {
            name = true;
        }
        validateData();
    }
    @FXML
    public void filterEvents(ActionEvent event) {
        LOGGER.info("Filtering available Commercials");

        switch (filter) {
            // Filtro Nombre
            case 1:
                loadData();
                for (Commercial c : coL) {
                    // Comprobando si el NOMBRE del comercio coincide con el texto del cuadro de busqueda
                    if (!c.getFullName().toLowerCase().contains(tfFind.getText().toLowerCase())) {
                        coL.remove(c);
                    }
                }
                break;
                // Filtro Login
            case 2:
                loadData();
                for (Commercial c : coL) {
                    // Comprobando si el LOGIN del comercio coincide con el texto del cuadro de busqueda
                    if (!c.getLogin().toLowerCase().contains(tfFind.getText().toLowerCase())) {
                        coL.remove(c);
                    }
                }
                break;
                // Filtro Email
            case 3:
                loadData();
                for (Commercial c : coL) {
                    // Comprobando si el EMAIL del comercio coincide con el texto del cuadro de busqueda
                    if (!c.getEmail().toLowerCase().contains(tfFind.getText().toLowerCase())) {
                        coL.remove(c);
                    }
                }
                break;
                
                // Filtro Especializacion
                //Como se coge una especializacion ?
            case 4:
                loadData();
                for (Commercial c : coL) {
                    // Comprobando si el ESPECIALIZACION del comercio coincide con el texto del cuadro de busqueda
                    //    if (!c.getEspecialization(). / .contains(tfFind.getText().toLowerCase())) {
                    coL.remove(c);
                    // }
                }
                break;
            default:
                loadData();
        }
        loadTable();

    }

    public void selectedFilter(ObservableValue observable, Object oldValue, Object newValue) {
        // Carga de datos en sección de edición
        if (newValue != null) {
            switch (cmbFind.getSelectionModel().getSelectedIndex()) {
                // Filtro Nombre
                case 1:
                    filter = 1;
                    break;
                // Filtro Fecha Inicio
                case 2:
                    filter = 2;
                    break;
                // Filtro Fecha Fin
                case 3:
                    filter = 3;
                    break;
                // Filtro Descripcion
                case 4:
                    filter = 4;
                    break;
                default:
                    filter = 0;
            }
        }
    }

    public void commercialTableSelection(ObservableValue observable, Object oldValue, Object newValue) {

        // Carga de datos en sección de edición
        if (newValue != null) {
            commercial = (Commercial) newValue;
            tfName.setText(commercial.getFullName());
            tfLogin.setText(commercial.getLogin());
            tfEmail.setText(commercial.getEmail());
            //inventada
            cmbEspecialization.setValue(commercial.getEspecialization());
            //Subir el pasword de la tabla a los text field??
            //tfPassword.setText(commercial.getPassword());

            btnNewCommercial.setDisable(true);
            btnDeleteCommercial.setDisable(false);
            btnSaveCommercial.setDisable(false);
            name = false;
            login = false;
            email = false;
            tableSelec = true;
        } else {
            tfName.setText(null);
            tfLogin.setText(null);
            tfEmail.setText(null);
            //INVENTADA
            cmbEspecialization.setValue("Sonido");
            btnNewCommercial.setDisable(true);
            btnDeleteCommercial.setDisable(true);
            btnSaveCommercial.setDisable(true);
            name = false;
            login = false;
            email = false;
            tableSelec = false;
        }
    }

    public void initiateTableColumns() {
        LOGGER.info("Generating Table Properties");
        // Factoria de Celda para Valor de Propiedades
        // -> Factoria de Celdas para Edicion
        // - - -> lambda para controlar edicion de contenido
        clNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clNombre.setCellFactory(TextFieldTableCell.<Commercial>forTableColumn());
        clNombre.setOnEditCommit((CellEditEvent<Commercial, String> t) -> {
            ((Commercial) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setFullName(t.getNewValue());
        });

        clLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        clLogin.setCellFactory(TextFieldTableCell.<Commercial>forTableColumn());
        clLogin.setOnEditCommit((CellEditEvent<Commercial, String> t) -> {
            ((Commercial) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setLogin(t.getNewValue());
        });

        clEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        clEmail.setCellFactory(TextFieldTableCell.<Commercial>forTableColumn());
        clEmail.setOnEditCommit((CellEditEvent<Commercial, String> t) -> {
            ((Commercial) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setEmail(t.getNewValue());
        });

        clEspecialization.setCellValueFactory(new PropertyValueFactory<>("especialization"));
        clEspecialization.setCellFactory(TextFieldTableCell.<Commercial>forTableColumn());
        clEspecialization.setOnEditCommit((CellEditEvent<Commercial, String> t) -> {
            ((Commercial) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setEspecialization(t.getNewValue());
            //Como se selecciona una especializacion concreta para q no se la invente?
            //o se da por hecho q el admin no la va a liar ? 

        });
    }

    public void handleWindowShowing(ActionEvent event) {

        LOGGER.info("Commercial -- handleWindowShowing");

        // Inicialización de variables
        lbeLogin.setVisible(false);
        lbeName.setVisible(false);
        lbeEmail.setVisible(false);
        lbePassword.setVisible(false);
        lbeConfirmPassword.setVisible(false);
        // Estado inicial de botones
        btnDeleteCommercial.setDisable(true);
        btnNewCommercial.setDisable(true);
        btnSaveCommercial.setDisable(true);
        btnFind.setDisable(false);
        btnPrint.setDisable(false);
        btnBack.setDisable(false);
        // Inicio de booleanos de control a false
        name = false;
        login = false;
        email = false;
        password = false;
        passwordR = false;
        tableSelec = false;

        //btnNewCommercial.disableProperty().bind(lbeLogin.textProperty().isEmpty());
        //.or(txtPassword.textProperty().isEmpty())
        //Vincular las columnas de la tabla con los campos del Commercial
        this.clNombre.setCellValueFactory(new PropertyValueFactory("Nombre"));
        this.clLogin.setCellValueFactory(new PropertyValueFactory("Login"));
        this.clEmail.setCellValueFactory(new PropertyValueFactory("Email"));
        this.clEspecialization.setCellValueFactory(new PropertyValueFactory("Especialization"));

        //Corregir
        if (!tfName.getText().equals("")) {
            btnNewCommercial.setDisable(false);
        } else {
            btnNewCommercial.setDisable(true);
        }

    }

    @FXML
    public void saveCommercial(ActionEvent event) {
        
         LOGGER.info("Update of data on database requested");
        saveData();
        
    }
    @FXML
    public void nuevoCommercial(ActionEvent event) {

        if (tfName.getLength() <= 0) {
            btnNewCommercial.setDisable(false);
        }

       
        //antes de eso el asociar en el metodo handle window
        //meter los datos al Commercial 
        User usr = new Commercial();
        usr.setFullName(tfName.getText());
        usr.setLogin(tfLogin.getText());
        usr.setEmail(tfEmail.getText());
        //es un combo box CUIDADO
       // usr.setEspecialization(tfEspecialization.getText();

       //Actualizamos las listas
       coI.create((Commercial) usr);
       coL.add((Commercial) usr);
       loadTable();
       tbCommercial.refresh();
    
       //Comprobacion si existe
//        if (!this.commercials.contains(usr)) {
//            this.commercials.add(usr);
//            this.CommercialTable.setItems(commercials);
//        } else {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText(null);
//            alert.setTitle("Error");
//            alert.setContentText("Persona ya existe");
//            alert.showAndWait();
//        }

    }

    private void selectTable(MouseEvent event) {

        Commercial m = this.tbCommercial.getSelectionModel().getSelectedItem();

        if (m != null) {
            this.lbeName.setText(m.getFullName());
            this.lbeLogin.setText(m.getLogin());
            this.lbeEmail.setText(m.getEmail());
            //lo mismo con el combo BOX de especializacion
            this.btnSaveCommercial.setDisable(false);
        }

    }

    public void modifyTable(ActionEvent event) {

        Commercial m = this.tbCommercial.getSelectionModel().getSelectedItem();

        if (m != null) {

            User aux = new Commercial();
            aux.setFullName(tfName.getText());
            aux.setLogin(tfLogin.getText());
            aux.setEmail(tfEmail.getText());
            //es un combo box CUIDADO
            aux.setEspecialization(tfEspecialization.getText();

            if (!this.commercials.contains(aux)) {

                m.setFullName(aux.getFullName());
                m.setLogin(aux.getLogin());
                m.setEmail(aux.getEmail());
                m.setEspecialization(aux.getEspecialization());

                this.CommercialTable.refresh();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Persona ya existe");
                alert.showAndWait();
            }

        }

    }
    /**
     *
     * @param event
     */
    @FXML
    public void deleteTable(ActionEvent event) {
        LOGGER.info("Requesting confirmation for Commercial Remove...");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Está Eliminando un Commercial");
        alert.setHeaderText("¿Seguro que desea Eliminar el Commercial Seleccionado?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            coI.remove(this.commercial);
            coL.remove(commercial);
            loadTable();
            tbCommercial.refresh();
        } else {
            event.consume();
            LOGGER.info("Closing aborted");
        }

    }

    public void printForm(ActionEvent event) {
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

    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }

    /**
     * Metodo que cierra VCommercialTable preguntando antes si esta seguro volviendo a la ventana VCommercial
     *
     * @param event the event linked to clicking on the button;
     */
    public void closeVCommercialTable(ActionEvent event) {
        LOGGER.info("Requesting confirmation for application closing...");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Está Cerrando el Programa");
        alert.setHeaderText("¿Seguro que desea cerrar el programa?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            LOGGER.info("Closing the application");
            Platform.exit();
        } else {
            LOGGER.info("Application closing cancelled");
        }
    }

    /**
     * Method to create a confirmation popup when user uses the UI's innate
     * close button (button X)
     *
     * @param event the event linked to clicking on the button;
     */
    public void closeVCommercialTableX(WindowEvent event) {
        LOGGER.info("Requesting confirmation for application closing...");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Está Cerrando el Programa");
        alert.setHeaderText("¿Seguro que desea cerrar el programa?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            LOGGER.info("Closing the application");
            Platform.exit();
        } else {
            LOGGER.info("Application closing cancelled");
        }
    }

    /**
     * Method of the LogOut Hyperlink (hlLogOut) that nds thecurrent users
     * session on the application and returns to the SignIn window
     *
     * @param event the event linked to clicking on the button;
     * @throws java.io.IOException
     */
    public void logOut(ActionEvent event) throws IOException {
        LOGGER.info("Requesting confirmation for Signing Out...");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrando Sesión");
        alert.setHeaderText("¿Seguro que desea cerrar sesión?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            LOGGER.info("Signing Out");
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto1client/view/VSignIn.fxml"));
            Parent root = (Parent) loader.load();
            VSignInController controller = loader.getController();
            controller.setStage(this.stage);
            controller.initStage(root);
        } else {
            LOGGER.info("Signing Out cancelled");
        }
    }

    /**
     * Refresca los datos de la tabla
     */
    public void loadTable() {
        LOGGER.info("Loading Data on Table");
        coO = FXCollections.observableArrayList(coL);
        tbCommercial.setItems(coO);
    }
    
      public void loadData() {
        LOGGER.info("Loading available Commercial");
        
          coL = coI.findAll();
//        try {
//            if (commercial != null) {
//                coL = coI.find(commercial.getId());
//            } else {
//              
//            }
//        } catch (Exception e) {
//        }
    }

    private void saveData() {
        List<Commercial> coms = coI.findAll();
        // Lee todos los eventos de la base de datos
        for (Commercial co : coms) {
            Boolean encontrado = false;
            // Busca los eventos actuales en los extraidos de la base de datos
            for (Commercial com : coms) {
                if (co.getId() == com.getId()) {
                    encontrado = true;
                    // si lo encuentra, analiza si son iguales, si no lo son, lo actualiza
                    if (!co.equals(com)) {
                        coI.edit(co);
                    }
                }
            }
            // Si no lo ha encontrado, lo añade a la base de datos
            if (!encontrado) {
                coI.create(co);
            }
        }
    }

    /**
     * Imprime los datos de la tabla como formulario 
     */
    private void print() {
      
        
    }

}
