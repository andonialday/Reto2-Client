/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import java.io.IOException;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Aitor Perez
 */
public class EquipmentController {
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

    
      public void initStage(Parent root) throws IOException {

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
       /* stage.setOnCloseRequest(this::closeVEquipmentTable);
        // AÑADIR LOS NUEVOS LABEL Y HYPERLINK

        tbEvent.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        loadData();
        btnBack.setOnAction(this::back);
        btnDeleteEvent.setOnAction(this::deleteEvent);
        btnSave.setOnAction(this::saveChanges);*/

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
       /* btnSignIn.setDisable(true);
        //The SignIn button does not allow spaces to be entered
        btnSignIn.disableProperty().bind(txtLogin.textProperty().isEmpty().or(txtPassword.textProperty().isEmpty()));

        //Exit button is enabled
        btnExit.setDisable(false);

        //SignUp hyperlink is enabled
        hyperSignUp.setDisable(false);*/
    }
}
