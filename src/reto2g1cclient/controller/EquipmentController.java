/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import reto2g1cclient.model.Equipment;

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
    private List<Equipment> equipments;
    private Stage stage;
    private Equipment equipment;
    

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    public List<Equipment> getEquipments() {
        return equipments;
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
    //Elementos FXML 
    
       @FXML
       private TableView<Equipment> tbEquipment;
       @FXML
       private TableColumn<Equipment, String> clName;
       @FXML
       private TableColumn<Equipment, String> clCost;
       @FXML
       private TableColumn<Equipment, String> clDescription;
       @FXML
       private TableColumn<Equipment, String> clDate;
       @FXML
       private Button btBack;
       @FXML
       private Button btDeleteEquip;
       @FXML
       private Button btSaveEquip;
       @FXML
       private Button btFind;
       @FXML
       private ComboBox cbSearch;
       @FXML
       private TextField tfFinding;
       @FXML
       private TextField tfName;
       @FXML
       private TextField tfCost;
       @FXML
       private TextField dpDate;
       @FXML
       private TextField taDescription;
       @FXML
       private Button btCrearEquip;
       @FXML
       private Label lblNameEquipment;
       @FXML
       private Label lblName;
       @FXML
       private Label lblCost;
       @FXML
       private Label lblDescription;
       @FXML
       private Label lblBuyData;
       @FXML
       private Label lblWarninNumValue;
       @FXML
       private Label lblWarningData;
       
       
       
       
      public void initStage(Parent root) throws IOException {

             //Create a new scene
        Scene scene = new Scene(root);

        //CSS (route & scene)
        String css = this.getClass().getResource("/reto2g1cclient/view/javaFXUIStyles.css").toExternalForm();
        scene.getStylesheets().add(css);

        //Associate the scene to the stage
        stage.setScene(scene);

        //Set the scene properties
        stage.setTitle("Equipamiento");
        stage.setMinWidth(960);
        stage.setMinHeight(720);
        stage.setResizable(false);
        loaddata();
        //Set Windows event handlers 
        stage.setOnShowing(this::handleWindowShowing);
       /* stage.setOnCloseRequest(this::closeVEquipmentTable);
        // AADIR LOS NUEVOS LABEL Y HYPERLINK

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

    private void loaddata() {
    
    
    }
}
