/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

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
    private Boolean bolEquipEncontrado = false;
    private Integer filters;

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
        //Al seleccionar mandar datos de la tabla a las cajas
        tbEquipment.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tbEquipment.getSelectionModel().selectedItemProperty().addListener(this::setDataOnTblEquip);
        //Set Windows event handlers 
        stage.setOnShowing(this::handleWindowShowing);
        btnBack.setOnAction(this::back);
        btnCrearEquip.setOnAction(this::newEquipment);
        btnDeleteEquip.setOnAction(this::deleteEquipment);
        btnSaveEquip.setOnAction(this::saveEquipment);
        btnFind.setOnAction(this::filterEquipments);
        tfName.textProperty().addListener(this::tfNameValue);
        tfCost.textProperty().addListener(this::tfCostValue);
        taDescription.textProperty().addListener(this::taDescriptionValue);
        dpDate.valueProperty().addListener(this::dpDateAddValue);
        ObservableList<String> filters = FXCollections.observableArrayList("Nombre del Equipamiento", "Coste maximo", "Coste minimo", "Fecha de compra", "Descripción");
        cbSearch.getItems().addAll(filters);

        //stage.setOnCloseRequest(this::closeVEquipmentTable);
        // AADIR LOS NUEVOS LABEL Y HYPERLINK
        /*  loadData();closeVEquipmentTable
        
        btnDeleteEvent.setOnAction(this::deleteEvent);
        btnSave.setOnAction(this::saveChanges);*/
        //Show main window
        // loaddata();
        loadTblEquipment();
        setTableData();
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
        cbSearch.getSelectionModel().select(0);
        //Todos los booleanos declarados a false
        bolCost = false;
        bolDateBuy = false;
        bolDescription = false;
        bolName = false;
        bolTableEquipSelec = false;
    }

    private void loaddata() {
        try {
            equipments = eqif.findAll();
        } catch (Exception e) {
            LOGGER.info(e.getMessage() + "Load data fallo");
        }

    }

    private void loadTblEquipment() {
        LOGGER.info("Cargando datos en tabla");
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
    public void newEquipment(ActionEvent event) {
        try {
            double coste = Double.parseDouble(tfCost.getText());

            if (coste > 0) {
                lblWarninNumValue.setVisible(false);
                Equipment eq = null;
                eq.setName(tfName.getText());
                eq.setCost(tfCost.getText());
                eq.setDescription(taDescription.getText());
                eq.setDateAdd(Date.from(dpDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()).toString());
               // eqif.create(eq);
                equipments.add(eq);
                loadTblEquipment();
                tbEquipment.refresh();
            } else {
                lblWarninNumValue.setVisible(true);
            }
        } catch (NumberFormatException e) {
            LOGGER.info("Error on cost value" + e);
            lblWarninNumValue.setVisible(true);
            Alert altWarningLog = new Alert(AlertType.WARNING);
            altWarningLog.setTitle("Error ");
            altWarningLog.setHeaderText("El coste introducido no es numerico");
            altWarningLog.setContentText("El coste que se ha introducido debe ser numerico");
            altWarningLog.showAndWait();
        }

    }

    @FXML
    public void deleteEquipment(ActionEvent event) {

        LOGGER.info("Deleting Equipment");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Está Eliminando un Equipamiento");
        alert.setHeaderText("¿Seguro que desea Eliminar el Equipamiento Seleccionado?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            // eqif.remove(this.equipment);
            equipments.remove(equipment);
            loadTblEquipment();
            tbEquipment.refresh();
        } else {
            event.consume();
            LOGGER.info("Closing aborted");
        }
    }

    @FXML
    public void saveEquipment(ActionEvent event) {
        try {
            LOGGER.info("Actualizando cambios en la base de datos");
            List<Equipment> equip = eqif.findAll();

            for (Equipment eq : equipments) {
                bolEquipEncontrado = false;

                for (Equipment equi : equip) {

                    if (eq.getId() == equi.getId()) {
                        bolEquipEncontrado = true;

                        if (!eq.equals(equi)) {
                            eqif.edit(eq.getId(), eq);;
                        }
                    }
                }
                if (!bolEquipEncontrado) {
                    eqif.create(eq);
                }
            }
        } catch (Exception e) {
            LOGGER.severe("Error en el guardado del equipamiento" + e);
            Alert altWarningLog = new Alert(AlertType.WARNING);
            altWarningLog.setTitle("Error al guardar en la base de datos");
            altWarningLog.setHeaderText("El coste introducido no es numerico");
            altWarningLog.setContentText("El coste que se ha introducido debe ser numerico");
            altWarningLog.showAndWait();
        }

    }

    /**
     * ******* METODOS PARA HABILITAR LOS BOTONES ****************
     */
    /**
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    public void tfNameValue(ObservableValue observable, String oldValue, String newValue) {
        bolName = false;
        if (!newValue.trim().equals("")) {
            bolName = true;

        }
        if (newValue.trim().length() > 50) {
            newValue = oldValue;
        }

        LOGGER.info("name is empty");

        validateEquipData();
    }

    public void tfCostValue(ObservableValue observable, String oldValue, String newValue) {
        bolCost = false;
        try {
            if (!newValue.trim().equals("")) {

                Double c = Double.valueOf(newValue);
                if (c > 0) {
                    bolCost = true;
                }

            } else {
                bolCost = false;
            }

        } catch (NumberFormatException e) {
            LOGGER.severe(e.getMessage());

        }

        validateEquipData();
    }

    public void taDescriptionValue(ObservableValue observable, String oldValue, String newValue) {
        bolDescription = false;
        if (!newValue.trim().equals("")) {
            bolDescription = true;

        }
        if (newValue.trim().length() > 400) {
            newValue = oldValue;
        }

        validateEquipData();
    }

    public void dpDateAddValue(ObservableValue observable, Object oldValue, Object newValue) {
        bolDateBuy = false;
        if (dpDate.getValue() != null) {
            bolDateBuy = true;

        } else {
            bolDateBuy = false;
        }

        validateEquipData();
    }

    public void validateEquipData() {
        if (bolName && bolDescription && bolCost && bolDateBuy && !bolTableEquipSelec) {
            LOGGER.info("Validate All data is empty");

            btnCrearEquip.setDisable(false);
        } else {

            btnCrearEquip.setDisable(true);
        }

    }

    public void setTableData() {

        clName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clName.setCellFactory(TextFieldTableCell.<Equipment>forTableColumn());
        clCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        clCost.setCellFactory(TextFieldTableCell.<Equipment>forTableColumn());
        clDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        clDescription.setCellFactory(TextFieldTableCell.<Equipment>forTableColumn());
        clDate.setCellValueFactory(new PropertyValueFactory<>("dateAdd"));
        clDate.setCellFactory(TextFieldTableCell.<Equipment>forTableColumn());
    }

    public void setDataOnTblEquip(ObservableValue observable, Object oldValue, Object newValue) {
        if (newValue != null) {
            equipment = (Equipment) newValue;
            tfName.setText(equipment.getName());
            tfCost.setText(equipment.getCost());
            taDescription.setText(equipment.getDescription());
            dpDate.setValue(LocalDate.parse(equipment.getDateAdd()));
            btnCrearEquip.setDisable(true);
            btnSaveEquip.setDisable(false);
            btnDeleteEquip.setDisable(false);
            bolCost = false;
            bolDateBuy = false;
            bolDescription = false;
            bolName = false;
            bolTableEquipSelec = false;
            bolTableEquipSelec = true;
        } else {
            tfName.setText(null);
            tfCost.setText(null);
            taDescription.setText(null);
            dpDate.setValue(null);
            btnCrearEquip.setDisable(true);
            btnSaveEquip.setDisable(true);
            btnDeleteEquip.setDisable(true);
            bolCost = false;
            bolDateBuy = false;
            bolDescription = false;
            bolName = false;
            bolTableEquipSelec = false;

        }
    }

    @FXML
    public void filterEquipments(ActionEvent event) {
        LOGGER.info("ejecutando filtros ");
        LocalDate fechaBusqueda = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        if (cbSearch.getSelectionModel().getSelectedIndex() == 3) {
            fechaBusqueda = LocalDate.parse(tfFinding.getText(), formatter);
        }
        //loaddata();
        List<Equipment> eqs = new ArrayList<>();
        switch (cbSearch.getSelectionModel().getSelectedIndex()) {
            case 0:
                for (Equipment eq : equipments) {
                    if (eq.getName().toUpperCase().contains(tfFinding.getText().toUpperCase().trim())) {
                        System.out.println(eq);
                        eqs.add(eq);
                        System.out.println("elemento eliminado");
                        
                    }
                }
                break;
            //Equipment -> equipment.getName().toUpperCase().contains(tfFinding.getText().toUpperCase())
            case 1:
                for (Equipment eq : equipments) {
                    if (Double.valueOf(eq.getCost()) <= Double.valueOf(tfFinding.getText())) {
                         eqs.add(eq);
                    }
                }
                break;
            case 2:
                for (Equipment eq : equipments) {
                      if(Double.valueOf(eq.getCost()) >= Double.valueOf(tfFinding.getText())){
                        eqs.add(eq);
                    }
                }
                break;
            case 3:
                for (Equipment eq : equipments) {
                    if (LocalDate.parse(eq.getDateAdd(), formatter).compareTo(LocalDate.parse(tfFinding.getText())) == 0 ){
                         eqs.add(eq);
                    }

                }
                break;
            case 4:
                for (Equipment eq : equipments) {
                    if (eq.getDescription().toUpperCase().contains(tfFinding.getText().toUpperCase().trim())) {
                         eqs.add(eq);
                    }

                }
                break;
            default:
        }
        equipments = eqs;
        loadTblEquipment();
        tbEquipment.refresh();

    }

}
