/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;

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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import reto2g1cclient.exception.*;
import reto2g1cclient.logic.Signable;
import reto2g1cclient.logic.ViewSignableFactory;
import reto2g1cclient.model.*;

/**
 * FXML Controller class Controlador de la ventana VSignUp que ejecuta todos los
 * metodos para una correcta ejecucion de la misma
 *
 *
 * @author Aitor Perez Andoni Alday
 */
public class VSignUpController {

    @FXML
    private TextField txtName;
    @FXML
    private Label lblName;
    @FXML
    private Label lblNameInfo;
    @FXML
    private TextField txtLogin;
    @FXML
    private Label lblLogin;
    @FXML
    private Label lblLoginInfo;
    @FXML
    private TextField txtEmail;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblEmailInfo;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblPassword;
    @FXML
    private Label lblPasswordInfo;
    @FXML
    private PasswordField txtConfirmPassword;
    @FXML
    private Label lblCPassword;
    @FXML
    private Label lblConfirmInfo;
    @FXML
    private Button btSignUp;
    @FXML
    private Button btBack;
    private Boolean bName = false, bLogin = false,
            bEmail = false, bPassword = false,
            bCPassword = false, bWarning = false;

    @FXML
    private Label label;
    //Patron para el email permite Mayusculas,Minusculas,numeros y es obligatorio usar este formato 
    //(prueba@prueba.com)
    private final Pattern pattern = Pattern
            .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    //Patron para el Login que solo permite Mayusculas,Minusculas,numeros y no admite espacios.
    private final Pattern log = Pattern
            .compile("^[A-Za-z0-9-]*$");
    //Patron para la Contrase??a requiere y permite  Numeros,Mayusculas,Minusculas,Simbolos especiales y no admite espacios
    private final Pattern pass = Pattern
            .compile("^.*(?=.{6,25})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+*=]).*$");
    //Patron para el nombre requiere y permite Mayusculas,Minusculas,Numeros y espacios.
    private final Pattern namePat = Pattern
            .compile("^[A-Za-z0-9]*[A-Za-z0-9 ][A-Za-z0-9 ]*$");

    private String name, login, email, password, cpassword, warning;

    private Stage stage;

    private static Logger LOGGER = Logger.getLogger("package.class");
    private Signable  sig;

    /**
     * Initializes the controller class.
     *
     * @param root Elemento recibido desde la ventana VSignIn que permite que la
     * ventana muestre sus elementos hijos (Cuadros de texto,Botones...)
     */
    public void initStage(Parent root) {
        sig = ViewSignableFactory.getView();
        LOGGER.info("Initializing SignUp window... ");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/reto1client/view/javaFXUIStyles.css");
        stage.setTitle("VSignUp");
        stage.setResizable(false);

        stage.setOnCloseRequest(this::closeVSignUpX);
        stage.setScene(scene);
        txtName.requestFocus();

        LOGGER.info("Setting SignUp window elements' parameters... ");
        toolTips();
        lblVisible();
        btSignUp.setOnAction(this::signUp);
        btSignUp.setDisable(true);
        btBack.setOnAction(this::backSingIn);
        // A??ADIR LOS NUEVOS LABEL
        txtName.textProperty().addListener(this::txtNameVal);
        txtLogin.textProperty().addListener(this::txtLoginVal);
        txtEmail.textProperty().addListener(this::txtEmailVal);
        txtPassword.textProperty().addListener(this::txtPasswordVal);
        txtConfirmPassword.textProperty().addListener(this::txtConfirmPasswordVal);

        // M??todo para comprobar si se ejecuta SignUp de Client o Commercial
        // checkType();
        stage.show();
        LOGGER.info("SignUp window started... ");

    }

    /**
     * Recoge todos los tooltips de cada text area
     */
    @FXML
    public void toolTips() {
        txtName.getTooltip();
        txtLogin.getTooltip();
        txtEmail.getTooltip();
        txtPassword.getTooltip();
        txtConfirmPassword.getTooltip();
    }

    /**
     * Oculta todas las lavels de error al iniciar la ventana
     */
    public void lblVisible() {
        lblName.setVisible(false);
        lblLogin.setVisible(false);
        lblEmail.setVisible(false);
        lblPassword.setVisible(false);
        lblCPassword.setVisible(false);
        lblConfirmInfo.setVisible(true);
        lblPasswordInfo.setVisible(true);
        lblEmailInfo.setVisible(true);
        lblLoginInfo.setVisible(true);
        lblNameInfo.setVisible(true);
    }

    /**
     * Metodo para validar que el texto del campo Name una vez eliminados los
     * espacios del principio y del final no es nulo
     *
     * @param observableValue Campo a validar campo texto Name
     * @param oldValue Valor previo del texto
     * @param newValue Valor que se va a validar
     */
    @FXML
    public void txtNameVal(ObservableValue observableValue,
            String oldValue,
            String newValue) {
        if (!newValue.trim().equals("")) {
            bName = true;

        } else {

            bName = false;

        }

        buttonActivation();
    }

    /**
     * Metodo para validar que el texto del campo Login una vez eliminados los
     * espacios del principio y del final no es nulo
     *
     * @param observableValue Campo a validar campo texto Login
     * @param oldValue Valor previo del texto
     * @param newValue Valor que se va a validar
     */
    public void txtLoginVal(ObservableValue observableValue,
            String oldValue,
            String newValue) {
        if (!newValue.trim().equals("")) {
            bLogin = true;

        } else {

            bLogin = false;
        }

        buttonActivation();
    }

    /**
     * Metodo para validar que el texto del campo Email una vez eliminados los
     * espacios del principio y del final no es nulo
     *
     * @param observableValue Campo a validar campo texto Email
     * @param oldValue Valor previo del texto
     * @param newValue Valor que se va a validar
     */
    public void txtEmailVal(ObservableValue observableValue,
            String oldValue,
            String newValue) {
        if (!newValue.trim().equals("")) {
            bEmail = true;

        } else {

            bEmail = false;

        }

        buttonActivation();
    }

    /**
     * Metodo para validar que el texto del campo Name una vez eliminados los
     * espacios del principio y del final no es nulo
     *
     * @param observableValue Campo a validar campo texto Password
     * @param oldValue Valor previo del texto
     * @param newValue Valor que se va a validar
     */
    public void txtPasswordVal(ObservableValue observableValue,
            String oldValue,
            String newValue) {
        if (!newValue.trim().equals("")) {
            bPassword = true;

        } else {

            bPassword = false;

        }

        buttonActivation();
    }

    /**
     * Metodo para validar que el texto del campo Confirm Password una vez
     * eliminados los espacios del principio y del final no es nulo
     *
     * @param observableValue Campo a validar campo texto Confirm Password
     * @param oldValue Valor previo del texto
     * @param newValue Valor que se va a validar
     */
    public void txtConfirmPasswordVal(ObservableValue observableValue,
            String oldValue,
            String newValue) {
        if (!newValue.trim().equals("")) {
            bCPassword = true;

        } else {

            bCPassword = false;

        }

        buttonActivation();
    }

    /**
     * Metodo para que ejecuta la validacion del contenido de los campos
     * ejecutando un metodo de registro si los campos son validos y sino
     * ejecutando mensaje de error y mostrando las indicaciones de error para
     * cada campo(Label y tooltip).
     *
     * @param event Metodo se ejecuta al pulsar el boton SignUp
     */
    public void signUp(ActionEvent event) {
        LOGGER.info("Validating inserted values... ");
        warning = "Los sigientes campos son erroneos: ";
        login = txtLogin.getText().trim();
        name = txtName.getText().trim();
        email = txtEmail.getText().trim();
        password = txtPassword.getText().trim();
        cpassword = txtConfirmPassword.getText().trim();

        //Comprobacion si el nombre cumple los requisitos
        // El Name tendr?? m??ximo 25 car??cteres alfanum??ricos
        //(se admiten espacios, pero si comienza o termina con espacios, estos ser??n ignorados)
        Matcher matchname = namePat.matcher(name);
        if (name.length() <= 25 && matchname.matches()) {

            lblName.setVisible(false);
        } else {

            warning += txtName.getPromptText();
            bWarning = true;

            lblName.setVisible(true);
            bName = false;

            txtName.setText("");

        }
        LOGGER.info("Name validated, Correct: " + bName);
        //Comprobacion si el login cumple los requisitos
        // El Login debe ser m??nimo 5 car??cteres, m??ximo 25, alfanum??ricos (no se admiten espacios)
        Matcher matchlog = log.matcher(login);
        if (login.length() <= 25 && login.length() >= 5 && matchlog.matches()) {
            lblLogin.setVisible(false);

        } else {
            if (!bWarning) {
                warning += txtLogin.getPromptText();
                bWarning = true;
            } else {
                warning += ", " + txtLogin.getPromptText();
            }
            lblLogin.setVisible(true);

            bLogin = false;

            txtLogin.setText("");

        }
        LOGGER.info("Login validated, Correct: " + bLogin);
        //Comprobacion si el email cumple con los parametros
        // El Email deber?? cumplir comprobaci??n de ser email (patr??n) y m??ximo 50 car??cteres (no se admiten espacios)
        Matcher matcher = pattern.matcher(email);
        if (email.length() <= 50 && matcher.matches()) {

            lblEmail.setVisible(false);
        } else {
            if (!bWarning) {
                warning += txtEmail.getPromptText();
                bWarning = true;
            } else {
                warning += ", " + txtEmail.getPromptText();
            }

            lblEmail.setVisible(true);
            bEmail = false;

            txtEmail.setText("");

        }
        LOGGER.info("Email validated, Correct: " + bEmail);
        //Comprobacion si la contrase??a cumple los parametros
        //La contrase??a deber?? ser de m??nimo 6 car??cteres, m??ximo 25, y DEBE
        //contener car??cteres alfanum??ricos (mayus, minus , num??ricos y no se admiten espacios)
        //y m??nimo un car??cter especial (*, %, $, etc...)

        Matcher matchpass = pass.matcher(password);
        if (password.length() >= 6 && password.length() <= 25 && matchpass.matches()) {
            lblPassword.setVisible(false);
        } else {

            if (!bWarning) {
                warning += txtPassword.getPromptText();
                bWarning = true;
            } else {
                warning += ", " + txtPassword.getPromptText();
            }

            lblPassword.setVisible(true);
            bPassword = false;

            txtPassword.setText("");

        }
        //Si contrase??a erronea se fuerza error en confirmar contrase??a
        if (password.equalsIgnoreCase(cpassword) && bPassword) {
            lblCPassword.setVisible(false);
        } else {
            if (!bWarning) {
                warning += txtConfirmPassword.getPromptText();
                bWarning = true;
            } else {
                warning += ", " + txtConfirmPassword.getPromptText();
            }
            lblCPassword.setVisible(true);
            bCPassword = false;

            txtConfirmPassword.setText("");
        }

        LOGGER.info("Password validated, Correct: " + bPassword);
        LOGGER.info("Passwords Match: " + bCPassword);
        //Secuencia para dar foco al primer campo erroneo
        if (!bName) {
            txtName.requestFocus();
        } else if (!bLogin) {
            txtLogin.requestFocus();
        } else if (!bEmail) {
            txtEmail.requestFocus();
        } else if (!bPassword) {
            txtPassword.requestFocus();
        } else if (!bCPassword) {
            txtConfirmPassword.requestFocus();
        } else {
            signUp();
        }
        if (bWarning) {
            warning();
        }
    }

    /**
     * Controla cuando abilitamos el boton SignUp mediante Boolean
     */
    public void buttonActivation() {
        if (bName && bLogin && bEmail && bPassword && bCPassword) {
            btSignUp.setDisable(false);
            bWarning = false;
        } else {
            btSignUp.setDisable(true);
        }

    }

    /**
     * Metodo para registrar al usuario en la base de datos si todos los datos
     * estan correctos.
     */
    // cambiar para que reciba un User user = new Client 
    // o un User user = new Commercial
    // desde el m??todo de CheckType;
    // public void signUp(User user) ??{
    // siendo el user un instanceOf Client o Commercial
    // gestionar que en funcion de Type se ejecute un user.setType ?? un user.setSpecialization
    public void signUp() {
        LOGGER.info("Sign In procedure initiated");
        User user = new Client();
        user.setFullName(name);
        user.setLogin(login);
        user.setEmail(email);
        user.setPassword(password);
        user.setPrivilege(Privilege.USER);
        user.setStatus(UserStatus.ENABLED);
        try {
            sig.signUp(user);
            LOGGER.info("Sign In Correct");
            Alert altInfoSignUp = new Alert(AlertType.INFORMATION);
            altInfoSignUp.setTitle("SignUp Completado");
            altInfoSignUp.setHeaderText(null);
            altInfoSignUp.setContentText("Registro Exitoso, redireccionando a ventana de SignIn");
            altInfoSignUp.showAndWait();
            try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VSignIn.fxml"));
                Parent root = (Parent) loader.load();
                VSignInController controller = ((VSignInController) loader.getController());
                controller.setStage(this.stage);
                controller.initStage(root);
            } catch (IOException ex) {
                Logger.getLogger(VSignUpController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClientServerConnectionException e) {
            Alert altErrorSC = new Alert(AlertType.ERROR);
            altErrorSC.setTitle("System Error");
            altErrorSC.setHeaderText("Could not Connect to the Server");
            altErrorSC.setContentText("The application could not connect to the server"
                    + "\n The Server may be busy with too many incoming requests, "
                    + "try again later, if this error continues, contact support or check server availability");
            altErrorSC.showAndWait();
        } catch (DBServerException e) {
            Alert altErrorDB = new Alert(AlertType.ERROR);
            altErrorDB.setTitle("System Error");
            altErrorDB.setHeaderText("Could not Connect to the DataBase");
            altErrorDB.setContentText("The server could not register your info, try again later. "
                    + "If this error persists, contact support or check the DataBase availability");
            altErrorDB.showAndWait();

        } catch (LoginOnUseException e) {
            Alert altWarningLog = new Alert(AlertType.WARNING);
            altWarningLog.setTitle("Login on use");
            altWarningLog.setHeaderText("Login already on use.");
            altWarningLog.setContentText("The text value input for the login field is allready on use on the DataBase."
                    + "You may allready be registered on the database. If not another user is using that login, you"
                    + "will have to use another one.");
            altWarningLog.showAndWait();
        }
    }

    /**
     * Metodo para asignarle el stage a la ventana
     *
     * @param primaryStage Espacio donde se va a mostrar la ventana
     */
    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }

    /**
     * Metodo para lanzar una alerta de Warning
     */
    private void warning() {
        LOGGER.info("Showing incorrect value fields");
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText("Campos introducidos no validos");
        alert.setContentText(warning);

        alert.showAndWait();
    }

    /**
     * Method to advise the user when uses the UI's innate close button (button
     * X) that the application will close
     *
     * @param event the event linked to clicking on the button;
     */
    public void closeVSignUpX(WindowEvent event) {
        LOGGER.info("Requesting confirmation for application closing...");
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to exit?");
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
     * Method of the LogOut Hyperlink (hlLogOut) that nds thecurrent users
     * session on the application and returns to the SignIn window
     *
     * @param event the event linked to clicking on the button;
     */
    public void backSingIn(ActionEvent event) {
        LOGGER.info("Volviendo a ventana SignIn");
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Volver a SignIn");
        alert.setHeaderText("??Seguro que desea volver a la ventana de SignIn?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            LOGGER.info("Cerrada ventana SignUp y volviendo a SignIn");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VSignIn.fxml"));
            try {
                Parent root = (Parent) loader.load();
                VSignInController controller = loader.getController();
                controller.setStage(this.stage);
                controller.initStage(root);
            } catch (IOException ex) {
                Logger.getLogger(VSignUpController.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

        }
    }

}
