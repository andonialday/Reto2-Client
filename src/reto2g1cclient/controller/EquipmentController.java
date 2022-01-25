/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import reto2g1cclient.logic.EquipmentInterface;
import reto2g1cclient.model.Equipment;
import javafx.collections.ObservableList;
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
    private EquipmentInterface eqif;
    private ObservableList<Equipment> equipmentData;
    private Boolean bolName;
    private Boolean bolCost;
    private Boolean bolDescription;
    private Boolean bolDateBuy;
    private Boolean bolTableEquipSelec;
    
    
    

    public ObservableList<Equipment> getEquipmentData() {
        return equipmentData;
    }

    public void setEquipmentData(ObservableList<Equipment> equipmentData) {
        this.equipmentData = equipmentData;
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
    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public EquipmentInterface getEqif() {
        return eqif;
    }

    public void setEqif(EquipmentInterface eqif) {
        this.eqif = eqif;
    }
    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }
    public List<Equipment> getEquipments() {
        return equipments;
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
       private Button btnBack;
       @FXML
       private Button btnDeleteEquip;
       @FXML
       private Button btnSaveEquip;
       @FXML
       private Button btnFind;
       @FXML
       private ComboBox cbSearch;
       @FXML
       private TextField tfFinding;
       @FXML
       private TextField tfName;
       @FXML
       private TextField tfCost;
       @FXML
       private DatePicker dpDate;
       @FXML
       private TextArea taDescription;
       @FXML
       private Button btnCrearEquip;
       @FXML
       private Button btnPrint;
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
       private Label lblWarningDate;




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
        
        //Set Windows event handlers 
        stage.setOnShowing(this::handleWindowShowing);
        btnBack.setOnAction(this::back);
        btnCrearEquip.setOnAction(this::newEquipment);
        btnDeleteEquip.setOnAction(this::deleteEquipment);
        
        tfName.textProperty().addListener(this::tfNameValue);
        tfCost.textProperty().addListener(this::tfCostValue);
        taDescription.textProperty().addListener(this::taDescriptionValue);
        dpDate.valueProperty().addListener(this::dpDateAddValue);
       /* stage.setOnCloseRequest(this::closeVEquipmentTable);
        // AADIR LOS NUEVOS LABEL Y HYPERLINK
        tbEvent.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        loadData();
        
        btnDeleteEvent.setOnAction(this::deleteEvent);
        btnSave.setOnAction(this::saveChanges);*/
        //Show main window
        loaddata();
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
     //El boton BUSCAR esta habilitado
        btnFind.setDisable(false);
        //El boton IMPRIMIR esta habilitado
        btnPrint.setDisable(false);
        //El boton de crear Equipamiento esta desabilitado
        btnCrearEquip.setDisable(true);
         //El boton de guardar cambios del Equipamiento esta desabilitado
        btnSaveEquip.setDisable(true);
         //El boton de borrar Equipamiento esta desabilitado
        btnDeleteEquip.setDisable(true);
         //El boton de atras esta habilitado
        btnBack.setDisable(false);
        //El TextField de Nombre esta habilitado
        tfName.setDisable(false);
        //El TextField de Coste esta habilitado
        tfCost.setDisable(false);
        //El textField de Descripcion esta habilitado
        taDescription.setDisable(false);
        //El DatePicker esta habilitado
        dpDate.setDisable(false);
        //El lavel WarningNumValue invisible 
        lblWarninNumValue.setVisible(false);
        //El lavel lblWarningData invisible 
        lblWarningDate.setVisible(false);
        //El TextField de Buscar esta habilitado
        tfFinding.setDisable(false);
        //El ComboBox esta Habilitado
        cbSearch.setDisable(false);
        //Todos los booleanos declarados a false
        bolCost = false;
        bolDateBuy = false;
        bolDescription = false;
        bolName = false;
    }
    private void loaddata() {
        try {
            equipments =  eqif.findAll();
        } catch (Exception e) {
             LOGGER.info(e.getMessage()+"Load data fallo");
        }
        
    }
    private void loadTblEquipment(){
        equipmentData = FXCollections.observableArrayList(equipments);
        tbEquipment.setItems(equipmentData);
    }
    
        
    /**
     *
     * @param event
     */
    @FXML
    public void back(ActionEvent event) {
        LOGGER.info("Requesting confirmation for application closing...");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
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
    
    @FXML
    public void newEquipment(ActionEvent event){
        double coste = Double.parseDouble(tfCost.getText());
       if(coste > 0  ){
            Equipment eq = null;
       eq.setName(tfName.getText());
       eq.setCost(Double.parseDouble(tfCost.getText()));
       eq.setDescription(taDescription.getText());
       eq.setDateAdd(Date.from(dpDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
       eqif.create(eq);
       equipments.add(eq);
       loadTblEquipment();
      tbEquipment.refresh();
       }else{
           lblWarninNumValue.setVisible(true);
       }
       
       
    }
    
    @FXML
    public void deleteEquipment(ActionEvent event){
        
        LOGGER.info("Deleting Equipment");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Está Eliminando un Equipamiento");
        alert.setHeaderText("¿Seguro que desea Eliminar el Equipamiento Seleccionado?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            eqif.remove(this.equipment);
            equipments.remove(event);
            loadTblEquipment();
            tbEquipment.refresh();
        } else {
            event.consume();
            LOGGER.info("Closing aborted");
        }
    }
    
    @FXML 
    public void editEquipment(ActionEvent event){
        
    }
    
    
    /********* METODOS PARA HABILITAR LOS BOTONES *****************/
    
        /**
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    public void tfNameValue(ObservableValue observable, String oldValue, String newValue) {
        
        if (newValue.trim() != null) {
            bolName = true;
              
        }else{
            bolName = false;
            LOGGER.info("name is empty");
        }
        
        validateEquipData();
    }
    public void tfCostValue(ObservableValue observable, String oldValue, String newValue) {
        bolCost = false;
        if (newValue.trim() != null) {
            bolCost = true;
           
        }else{
            bolCost = false; 
            LOGGER.info("cost is empty");
        }
        
        
        validateEquipData();
    }
    public void taDescriptionValue(ObservableValue observable, String oldValue, String newValue) {
        bolDescription = false;
        if (newValue.trim() != null) {
            bolDescription = true;
           
        }else{
           bolDescription = false; 
           LOGGER.info("description is empty");
        }
        
         
        validateEquipData();
    }
     public void dpDateAddValue(ObservableValue observable, Object oldValue, Object newValue) {
        bolDateBuy = false;
        if (dpDate.getValue() != null) {
            bolDateBuy = true;
            
            
        }else{
             bolDateBuy = false;
        LOGGER.info("date buy is empty");
        }
       
        validateEquipData();
    }
     public void validateEquipData(){
         if(bolName && bolDescription && bolCost && bolDateBuy ){
             LOGGER.info("Validate All data is empty");
             btnCrearEquip.setDisable(false);
         }else{
             btnCrearEquip.setDisable(true);
         }
     }
}