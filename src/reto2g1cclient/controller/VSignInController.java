/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import reto2g1cclient.exception.*;
import reto2g1cclient.logic.Signable;
import reto2g1cclient.logic.ViewSignableFactory;
import reto2g1cclient.model.*;

/**
 * FXML Controller class
 *
 * @author Jaime San Sebastián
 */
public class VSignInController {

    /**
     * Initializes the controller class. We use logger to record the activity of
     * the application.
     */
    private static final Logger LOGGER = Logger.getLogger("package.class");

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
    private TextField txtLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnSignIn;

    @FXML
    private Button btnExit;

    @FXML
    private Hyperlink hyperSignUp;

    @FXML
    private Hyperlink hyperReset;

    /**
     * Initialize and show window
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
        stage.setOnCloseRequest(this::closeVSignIn);

        btnSignIn.setOnAction(this::signIn);
        btnExit.setOnAction(this::exit);
        hyperSignUp.setOnAction(this::signUp);
        hyperReset.setOnAction(this::reset);

        //Show main window
        stage.show();

    }

    /**
     * Shows the buttons that are enabled or disabled for the user when we enter
     * the Sign In window
     *
     * @param event
     */
    private void handleWindowShowing(WindowEvent event) {
        LOGGER.info("Beginning LoginController::handleWindowShowing");

        //SignIn button is disabled
        btnSignIn.setDisable(true);
        //The SignIn button does not allow spaces to be entered
        btnSignIn.disableProperty().bind(txtLogin.textProperty().isEmpty().or(txtPassword.textProperty().isEmpty()));

        //Exit button is enabled
        btnExit.setDisable(false);

        //SignUp hyperlink is enabled
        hyperSignUp.setDisable(false);
    }

    /**
     * This method is executed when the user presses the SignIn button. If the
     * username and password are correct, the Welcome window will be displayed.
     * If the username and password are incorrect, an information panel will be
     * displayed and the username and password fields will be emptied.
     *
     * @param event
     */
    @FXML
    private void signIn(ActionEvent event) {

        Signable sig = ViewSignableFactory.getView();

        User user = new User();

        user.setLogin(txtLogin.getText());
        user.setPassword(txtPassword.getText());

        LOGGER.info("Clicked on SignIn");
        User usr = null;
        try {
            usr = sig.signIn(user);
            usr.setPassword(user.getPassword());
            if (usr.getPrivilege().equals(Privilege.USER)) {
                // Inicio de ventana de cliente
                //      Por falta de tiempo, se ha optado por sustituir VClient y VCommercial por VFinal
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VFinal.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    VFinalController controller = ((VFinalController) fxmlLoader.getController());
                    Stage primaryStage = this.stage;
                    controller.setUser(usr);
                    controller.setStage(primaryStage);
                    controller.initStage(root);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Seccion no finalizada");
                    alert.setHeaderText("Ha iniciado con Éxito Sesión, " + usr.getFullName()
                            + ",\n pero por descragia no podemos mostrarle el contenido en estos momentos");
                    alert.setContentText("La seccion de la aplicacion a la que intenta "
                            + "acceder no se encuentra disponible en este momento."
                            + "\nLamentamos las molestias.");
                    alert.showAndWait();
                } catch (IOException ex) {
                    LOGGER.info("Error trying to show post SignIn window");
                }
            } else {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VAdmin.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    VAdminController controller = ((VAdminController) fxmlLoader.getController());
                    Stage primaryStage = this.stage;
                    controller.setUser(usr);
                    controller.setStage(primaryStage);
                    controller.initStage(root);
                } catch (IOException ex) {
                    LOGGER.info("Error trying to show post SignIn window");
                }
            }
        } catch (ClientServerConnectionException e) {
            LOGGER.info("Error Connecting to Server");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("System Error");
            alert.setHeaderText("Could not Connect to the Server");
            alert.setContentText("The application could not connect to the server"
                    + "\n The Server may be busy with too many incoming requests, "
                    + "try again later, if this error continues, contact support or check server availability");
            alert.showAndWait();
        } catch (DBServerException e) {
            LOGGER.info("Sign In Data Comprobation Error");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("System Error");
            alert.setHeaderText("Could not Connect to the DataBase");
            alert.setContentText("The server could not check your credentials, try again later. "
                    + "If this error persists, contact support or check the DataBase availability");
            alert.showAndWait();
        } catch (CredentialErrorException ex) {
            LOGGER.info("Sign In Credential Error");
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Username or password is incorrect");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                txtLogin.setText("");
                txtPassword.setText("");
            }
        }
    }

    /**
     * This method is executed when the user clicks the hyperlink SignUp. The
     * Sign Up window will open.
     *
     * @param event
     */
    @FXML
    private void signUp(ActionEvent event) {
        LOGGER.info("Initializing SignUp");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VSignUp.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            VSignUpController controller = ((VSignUpController) fxmlLoader.getController());
            Stage primaryStage = this.stage;
            controller.setStage(primaryStage);
            controller.initStage(root);
        } catch (IOException ex) {
            LOGGER.info("Error initializing VSignUp");
        }
    }

    /**
     * This method is executed when the user clicks the hyperlink SignUp. The
     * Sign Up window will open.
     *
     * @param event
     */
    @FXML
    private void reset(ActionEvent event) {
        LOGGER.info("Initializing Password Reset");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VReset.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            VResetController controller = ((VResetController) fxmlLoader.getController());
            Stage primaryStage = this.stage;
            controller.setStage(primaryStage);
            controller.initStage(root);
        } catch (IOException ex) {
            LOGGER.info("Error initializing Password Reset");
        }
    }

    /**
     * This method is executed when the user presses the Exit button. A
     * confirmation panel will appear, if you press the Accept button the
     * application will close and if you press the Cancel button you will return
     * to the previous window.
     *
     * @param event
     */
    @FXML
    private void exit(ActionEvent event) {
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

    /**
     * Method to advise the user when uses the UI's innate close button (button
     * X) that the application will close
     *
     * @param event the event linked to clicking on the button;
     */
    public void closeVSignIn(WindowEvent event) {
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
