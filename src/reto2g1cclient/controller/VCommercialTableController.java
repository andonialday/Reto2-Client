package reto1client.controller;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import reto1libraries.object.User;

/**
 * @author Enaitz Izagirre
 */
public class VCommercialTableController {

    private Stage stage;

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
    private TableView<User> CommercialTable;
    @FXML
    private TableColumn clNombre;
    @FXML
    private TableColumn clLogin;
    @FXML
    private TableColumn clEmail;
    @FXML
    private TableColumn clEspecialization;
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
    @FXML
    private TextField tfName;
    @FXML
    private Label lbeLogin;
    @FXML
    private Label lbeName;
    @FXML
    private Label lbeEmail;
    @FXML
    private Label lbePassword;
    @FXML
    private Label lbeConfirmPassword;

    private ObservableList<User> commercials;

    private User usr;
    private final Logger LOGGER = Logger.getLogger("package.class");

    public void initStage(Parent root) {
        LOGGER.info("Initializing VCommercialTable ...");
        // Inicialización de la ventana
        Scene scene = new Scene(root);
        stage.setTitle("VCommercialTable");
        stage.setResizable(false);

        stage.setScene(scene);
        //stage.setOnShowing(this::handleWindowShowing);
        stage.setOnCloseRequest(this::closeVCommercialTableX);

        btnPrint.setOnAction(this::printForm);

        btnBack.setMnemonicParsing(true);
        btnBack.setOnAction(this::closeVCommercialTable);

        stage.show();
        LOGGER.info("VCommercialTable Window Showing");
    }

    public void handleWindowShowing(ActionEvent event) {

        // Inicialización de variables
        lbeLogin.setVisible(false);
        lbeName.setVisible(false);
        lbeEmail.setVisible(false);
        lbePassword.setVisible(false);
        lbeConfirmPassword.setVisible(false);

        //btnNewCommercial.disableProperty().bind(lbeLogin.textProperty().isEmpty());
        //.or(txtPassword.textProperty().isEmpty())
        //Vincular las columnas de la tabla con los campos del Commercial
        this.clNombre.setCellValueFactory(new PropertyValueFactory("Nombre"));
        this.clLogin.setCellValueFactory(new PropertyValueFactory("Login"));
        this.clEmail.setCellValueFactory(new PropertyValueFactory("Email"));
        this.clEspecialization.setCellValueFactory(new PropertyValueFactory("Especialization"));

        if (!tfName.getText().equals("")) {
            btnNewCommercial.setDisable(false);
        } else {
            btnNewCommercial.setDisable(true);
        }

    }

    public void nuevoCommercial(ActionEvent event) {

        if (tfName.getLength() > 0) {
            btnNewCommercial.setDisable(false);
        }

        //antes de eso el asociar en el metodo handle window
        //meter los datos al Commercial 
        User usr = new Commercial();
        usr.setName(tfName.getText();
        usr.setLogin(tfLogin.getText();
        usr.setEmail(tfEmail.getText();
        //es un combo box CUIDADO
        usr.setEspecialization(tfEspecialization.getText();

        if (!this.commercials.contains(usr)) {
            this.commercials.add(usr);
            this.CommercialTable.setItems(commercials);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Persona ya existe");
            alert.showAndWait();
        }

    }

    private void selectTable(MouseEvent event) {

        Commercial m = this.CommercialTable.getSelectionModel().getSelectedItem();

        if (m != null) {
            this.lbeName.setText(m.getName());
            this.lbeLogin.setText(m.getLogin());
            this.lbeEmail.setText(m.getEmail());
            //lo mismo con el combo BOX de especializacion
            this.btnSaveCommercial.setDisable(false);
        }

    }

    public void modifyTable(ActionEvent event) {

        Commercial m = this.CommercialTable.getSelectionModel().getSelectedItem();

        if (m != null) {

            User aux = new Commercial();
            aux.setName(tfName.getText();
            aux.setLogin(tfLogin.getText();
            aux.setEmail(tfEmail.getText();
            //es un combo box CUIDADO
            aux.setEspecialization(tfEspecialization.getText();

            if (!this.commercials.contains(aux)) {
               
                m.setName(aux.getName());
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
    
     public void deleteTable(ActionEvent event) {
         
          Commercial m = this.CommercialTable.getSelectionModel().getSelectedItem();

        if (m == null) {
            
             Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Persona Sin Seleccionar");
                alert.showAndWait();
            
        }else{
            
            this.commercials.remove(m);
            this.CommercialTable.refresh();
            
        }
         
     }
    

    public void printForm(ActionEvent event) {

        //prueba chorcia
        if (lbeLogin.isVisible() || lbeName.isVisible() || lbeEmail.isVisible() || lbePassword.isVisible() || lbeConfirmPassword.isVisible()) {
            lbeLogin.setVisible(false);
            lbeName.setVisible(false);
            lbeEmail.setVisible(false);
            lbePassword.setVisible(false);
            lbeConfirmPassword.setVisible(false);
        } else {
            lbeLogin.setVisible(true);
            lbeName.setVisible(true);
            lbeEmail.setVisible(true);
            lbePassword.setVisible(true);
            lbeConfirmPassword.setVisible(true);
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
     * Method of the Close Button (btClose) that closes the scene and the
     * program completelly
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

}
