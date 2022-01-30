/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import reto2g1cclient.exception.DBServerException;
import reto2g1cclient.logic.EquipmentFactory;

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
    private List<Equipment> equipments = null;
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
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter database = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

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
        eqif = EquipmentFactory.getImplementation();
        loaddata();
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
            equipments = (List<Equipment>) eqif.findAll();
        } catch (DBServerException e) {
            LOGGER.info(e.getMessage() + " Load data fallo");
        }
        cambiarFormatoFecha();
    }

    private void loadTblEquipment() {
        LOGGER.info("Cargando datos en tabla");
        equipmentData = FXCollections.observableArrayList(equipments);
        System.out.println(equipmentData);
        tbEquipment.setItems(equipmentData);

    }

    /**
     *
     * @param event
     */
    @FXML
    public void back(ActionEvent event) {
        LOGGER.info("Se va a cerrar la aplicacion");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Está Cerrando el Programa");
        alert.setHeaderText("¿Seguro que desea cerrar el programa?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try{
                LOGGER.info("Cambiando a ventana de Admin");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VAdmin.fxml"));
                Parent root = (Parent) loader.load();
                //VAdminController controller = ((VAdminController) loader.getController());
               // controller.setStage(this.stage);
               // controller.initStage(root);  
            }catch (Exception e) {
                Alert alertVolver = new Alert(Alert.AlertType.WARNING);
                alertVolver.setTitle("Error al cambiar de ventana");
                alertVolver.setHeaderText("Error al volver a la ventana anterior");
                alertVolver.setContentText("Ha sucedido un error al volver a la ventana anterior"
                        + "en el caso de ser persistente intentelo denuevo o mas tarde");
                alertVolver.showAndWait();
            }
            Platform.exit();
            LOGGER.info("Closing the application");
        } else {
            event.consume();
            LOGGER.info("Closing aborted");
        }
    }
        public void closeVEquipmentTable(WindowEvent event) {
        LOGGER.info("Preguntando si desea cerrar la ventana");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Esta Cerrando la ventana");
        alert.setHeaderText("¿Seguro que desea cerrar la ventana?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
            LOGGER.info("Cerrando la aplicacion");
        } else {
            event.consume();
            LOGGER.info("Operacion de cerrado cancelada");
        }
    }

    @FXML
    public void newEquipment(ActionEvent event) throws DBServerException {
        try {
            double coste = Double.parseDouble(tfCost.getText());

            if (coste > 0) {
                lblWarninNumValue.setVisible(false);
                Equipment eq = new Equipment();
                eq.setName(tfName.getText().trim());
                eq.setCost(tfCost.getText());
                eq.setDescription(taDescription.getText().trim());
                eq.setDateAdd(dpDate.getValue().format(formatter));
                eq = devolverFormatoFecha(eq);
                eqif.create(eq);
                equipments.add(eq);
                loaddata();
                loadTblEquipment();
                tbEquipment.refresh();
                tfName.setText("");
                tfCost.setText("");
                taDescription.setText("");
                dpDate.setValue(null);
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
    public void deleteEquipment(ActionEvent event) throws DBServerException {
        try {
            LOGGER.info("Deleting Equipment");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Está Eliminando un Equipamiento");
            alert.setHeaderText("¿Seguro que desea Eliminar el Equipamiento Seleccionado?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                eqif.remove(tbEquipment.getSelectionModel().getSelectedItem());
                equipments.remove(equipment);
                loaddata();
                loadTblEquipment();
                tbEquipment.refresh();
            } else {
                event.consume();
                LOGGER.info("Closing aborted");
            }
        } catch (DBServerException e) {
            LOGGER.severe("Error en el guardado del equipamiento" + e);
            Alert altWarningLog = new Alert(AlertType.WARNING);
            altWarningLog.setTitle("Error al guardar en la base de datos");
            altWarningLog.setHeaderText("La base de datos puede no estar disponible en este momento ");
            altWarningLog.setContentText("Porfavor intentelo mas tarde");
            altWarningLog.showAndWait();
        }

    }

    @FXML
    public void saveEquipment(ActionEvent event) throws DBServerException {
        try {
            LOGGER.info("Actualizando cambios en la base de datos");
            if (bolTableEquipSelec) {
                if (comprobarValoresGuardar()) {

                    Equipment e = tbEquipment.getSelectionModel().getSelectedItem();
                    e.setName(tfName.getText());
                    e.setCost(tfCost.getText());
                    e.setDateAdd(dpDate.getValue().format(formatter));
                    e.setDescription(taDescription.getText());
                    equipments.add(e);
                    for (Equipment eq : equipments) {
                        editandoFormatosCondicionales(eq);
                    }

                    loaddata();
                    loadTblEquipment();
                    tbEquipment.refresh();
                } else {
                    tfName.setText(tbEquipment.getSelectionModel().getSelectedItem().getName());
                    tfCost.setText(tbEquipment.getSelectionModel().getSelectedItem().getCost());
                    taDescription.setText(tbEquipment.getSelectionModel().getSelectedItem().getDescription());
                    dpDate.setValue(LocalDate.parse(tbEquipment.getSelectionModel().getSelectedItem().getDateAdd(), formatter));
                    LOGGER.severe("Aviso alguno de los valores introducidos no cumple con los parametros"
                            + "o esta vacio");
                    Alert altWarningLog = new Alert(AlertType.INFORMATION);
                    altWarningLog.setTitle("Error al modificar los datos");
                    altWarningLog.setHeaderText("Aviso alguno de los valores introducidos no cumple con los parametros"
                            + " o esta vacio");
                    altWarningLog.setContentText("En el caso de ser un coste "
                            + "debe cumplir con el siguiente formato <b>xxx.yy</b> \n"
                            + "a su vez si lo que ha modificado es una fecha debera cumplir "
                            + "el formato <b>DD/MM/AAAA</b>");
                    altWarningLog.showAndWait();
                }

            }
        } catch (NumberFormatException e) {
            tfName.setText(tbEquipment.getSelectionModel().getSelectedItem().getName());
            tfCost.setText(tbEquipment.getSelectionModel().getSelectedItem().getCost());
            taDescription.setText(tbEquipment.getSelectionModel().getSelectedItem().getDescription());
            dpDate.setValue(LocalDate.parse(tbEquipment.getSelectionModel().getSelectedItem().getDateAdd(), formatter));
            LOGGER.severe("Error en el guardado del equipamiento" + e);
            Alert altWarningLog = new Alert(AlertType.INFORMATION);
            altWarningLog.setTitle("Error  al guardar en la base de datos");
            altWarningLog.setHeaderText("El coste introducido no es numerico");
            altWarningLog.setContentText("El coste que se ha introducido debe ser numerico"
                    + ",mayor que 0 y ademas con formato <b>xxx.yy</b>."
                    + "Dado este error se establecera al valor previo a la modificacion");
            altWarningLog.showAndWait();

        } catch (DateTimeParseException e) {
            tfName.setText(tbEquipment.getSelectionModel().getSelectedItem().getName());
            tfCost.setText(tbEquipment.getSelectionModel().getSelectedItem().getCost());
            taDescription.setText(tbEquipment.getSelectionModel().getSelectedItem().getDescription());
            dpDate.setValue(LocalDate.parse(tbEquipment.getSelectionModel().getSelectedItem().getDateAdd(), formatter));
            LOGGER.severe("Error en el guardado del equipamiento" + e);
            Alert altWarningLog = new Alert(AlertType.INFORMATION);
            altWarningLog.setTitle("Error al guardar en la base de datos");
            altWarningLog.setHeaderText("La fecha introducida no es valida ");
            altWarningLog.setContentText("La fecha introducida debe de cumplir el formato,"
                    + " \n DD/MM/AAAA");
            altWarningLog.showAndWait();
        } catch (DBServerException e) {

            LOGGER.severe("Error en el guardado del equipamiento" + e);
            Alert altWarningLog = new Alert(AlertType.WARNING);
            altWarningLog.setTitle("Error al guardar en la base de datos");
            altWarningLog.setHeaderText("La base de datos puede no estar disponible en este momento ");
            altWarningLog.setContentText("Porfavor intentelo mas tarde");
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

        validateEquipData();
    }

    public void tfCostValue(ObservableValue observable, String oldValue, String newValue) {
        bolCost = false;
        try {
            if (!newValue.trim().equals("")) {

                Double c = Double.valueOf(newValue);
                lblWarninNumValue.setVisible(false);
                if (c > 0) {
                    bolCost = true;
                }

            } else {
                bolCost = false;
            }

        } catch (NumberFormatException e) {
            LOGGER.severe(e.getMessage() + " EXCEPCIONE EN EL COSTE");
            lblWarninNumValue.setVisible(true);
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

    public void dpDateAddValue(ObservableValue observable, LocalDate oldValue, LocalDate newValue) {
        bolDateBuy = false;
        if (newValue != null) {
            bolDateBuy = true;

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
        tbEquipment.setEditable(true);
        clName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clName.setCellFactory(TextFieldTableCell.<Equipment>forTableColumn());
        clName.setOnEditCommit(this::cellNameEdit);

        clCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        clCost.setCellFactory(TextFieldTableCell.<Equipment>forTableColumn());
        clCost.setStyle("-fx-alignment: CENTER-RIGHT;");
        clCost.setOnEditCommit(this::cellCostEdit);

        clDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        clDescription.setCellFactory(TextFieldTableCell.<Equipment>forTableColumn());
        clDescription.setOnEditCommit(this::cellDescriptionEdit);

        clDate.setCellValueFactory(new PropertyValueFactory<>("dateAdd"));
        clDate.setCellFactory(TextFieldTableCell.<Equipment>forTableColumn());

        clDate.setOnEditCommit(this::cellDateAddEdit);

    }

    public void cellNameEdit(CellEditEvent<Equipment, String> t) {

        if (!t.getNewValue().equals("") && t.getNewValue().length() < 50) {
            ((Equipment) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setName(t.getNewValue());
            tfName.setText(t.getNewValue());
            editandoFormatosCondicionales(tbEquipment.getSelectionModel().getSelectedItem());
        } else {
            ((Equipment) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setName(t.getOldValue());

        }
        tbEquipment.refresh();

    }

    public void cellCostEdit(CellEditEvent<Equipment, String> t) {

        try {
            Double valor = Double.parseDouble(t.getNewValue());
            if (valor > 0) {
                ((Equipment) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setCost(t.getNewValue());
                tfCost.setText(t.getNewValue());
                editandoFormatosCondicionales(tbEquipment.getSelectionModel().getSelectedItem());
            } else {
                ((Equipment) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setCost(t.getOldValue());

                Alert altWarningLog = new Alert(AlertType.INFORMATION);
                altWarningLog.setTitle("Error al guardar en la base de datos");
                altWarningLog.setHeaderText("El coste introducido no es valido");
                altWarningLog.setContentText("El coste que se ha introducido debe ser mayor a 0 "
                        + "en formato xxx.yy "
                        + "el coste se va a restablecer al valor anterior");
                altWarningLog.showAndWait();
            }
            tbEquipment.refresh();
        } catch (NumberFormatException e) {

            LOGGER.severe("El coste introducido no es valido" + e);

            Alert altWarningLog = new Alert(AlertType.WARNING);
            altWarningLog.setTitle("Error ");
            altWarningLog.setHeaderText("El coste introducido no es numerico");
            altWarningLog.setContentText("El coste que se ha introducido debe ser numerico");
            altWarningLog.showAndWait();
            tbEquipment.refresh();
        }

    }

    public void cellDescriptionEdit(CellEditEvent<Equipment, String> t) {

        if (!t.getNewValue().equals("") && t.getNewValue().length() < 400) {
            ((Equipment) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setDescription(t.getNewValue());
            taDescription.setText(t.getNewValue());
            editandoFormatosCondicionales(tbEquipment.getSelectionModel().getSelectedItem());
        } else {
            ((Equipment) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setDescription(t.getOldValue());

        }
        tbEquipment.refresh();

    }

    public void cellDateAddEdit(CellEditEvent<Equipment, String> t) {
        try {
            LocalDate.parse(t.getNewValue(), formatter);
            dpDate.setValue(LocalDate.parse(t.getNewValue(), formatter));
            ((Equipment) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setDateAdd(t.getNewValue());
            editandoFormatosCondicionales(tbEquipment.getSelectionModel().getSelectedItem());
        } catch (DateTimeParseException e) {
            Alert altWarningLog = new Alert(AlertType.WARNING);
            altWarningLog.setTitle("La fecha introducida no es correcta ");
            altWarningLog.setHeaderText("La fecha introducida no cumple los "
                    + "siguientes paramentros");
            altWarningLog.setContentText("El coste que se ha introducido deben"
                    + " cumplir el formato DD/MM/AAAA");
            altWarningLog.showAndWait();
            ((Equipment) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setDateAdd(t.getOldValue());
        }

        tbEquipment.refresh();

    }

    public void setDataOnTblEquip(ObservableValue observable, Equipment oldValue, Equipment newValue) {
        if (newValue != null) {
            LOGGER.info("Equipamiento seleccionado");
            tfName.setText(newValue.getName());
            tfCost.setText(newValue.getCost());
            taDescription.setText(newValue.getDescription());
            dpDate.setValue(LocalDate.parse(newValue.getDateAdd(), formatter));
            btnCrearEquip.setDisable(true);
            btnSaveEquip.setDisable(false);
            btnDeleteEquip.setDisable(false);
            bolCost = false;
            bolDateBuy = false;
            bolDescription = false;
            bolName = false;

            bolTableEquipSelec = true;
        } else {
            LOGGER.info("Equipamiento deseleccionado");
            tfName.setText("");
            tfCost.setText("");
            taDescription.setText("");
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

        try {

            List<Equipment> eqs = new ArrayList<>();
            loaddata();
            switch (cbSearch.getSelectionModel().getSelectedIndex()) {
                case 0:
                    if (!tfFinding.getText().trim().equals("")) {
                        for (Equipment eq : equipments) {
                            if (eq.getName().toUpperCase().contains(tfFinding.getText().toUpperCase().trim())) {
                                System.out.println(eq);
                                eqs.add(eq);
                                System.out.println("elemento eliminado");

                            }
                        }
                        equipments = eqs;
                    } else {
                        loaddata();
                    }

                    break;

                case 1:
                    if (!tfFinding.getText().trim().equals("")) {
                        for (Equipment eq : equipments) {
                            if (Double.valueOf(eq.getCost()) <= Double.valueOf(tfFinding.getText())) {
                                eqs.add(eq);
                            }
                        }
                        equipments = eqs;
                    } else {
                        loaddata();
                    }

                    break;
                case 2:
                    if (!tfFinding.getText().trim().equals("")) {
                        for (Equipment eq : equipments) {
                            if (Double.valueOf(eq.getCost()) >= Double.valueOf(tfFinding.getText())) {
                                eqs.add(eq);
                            }
                        }
                        equipments = eqs;
                    } else {
                        loaddata();
                    }

                    break;
                case 3:
                    if (!tfFinding.getText().trim().equals("")) {
                        LocalDate fechaBusqueda = LocalDate.parse(tfFinding.getText(), formatter);
                        for (Equipment eq : equipments) {
                            if (LocalDate.parse(eq.getDateAdd(), formatter).compareTo(LocalDate.parse(tfFinding.getText(), formatter)) == 0) {
                                eqs.add(eq);
                            }

                        }
                        equipments = eqs;
                    } else {
                        loaddata();
                    }

                    break;
                case 4:
                    if (!tfFinding.getText().trim().equals("")) {
                        for (Equipment eq : equipments) {
                            if (eq.getDescription().toUpperCase().contains(tfFinding.getText().toUpperCase().trim())) {
                                eqs.add(eq);
                            }

                        }
                        equipments = eqs;
                    } else {
                        loaddata();
                    }

                    break;
                default:

                    loaddata();
            }
            loadTblEquipment();
            tbEquipment.refresh();
        } catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Fecha introducida no valida");
            alert.setHeaderText("El valor de fecha introducido no es correcto por favor "
                    + "introduzca una fecha en formato,\n DD/MM/AAAA "
                    + "(por ejemplo, 30/01/2022)");
            alert.showAndWait();
        }
    }

    /* -------------------------- SOLUCION PARA FECHAS ------------------------------*/
    public void cambiarFormatoFecha() {
        for (Equipment eq : equipments) {
            LocalDate fecha = LocalDate.parse(eq.getDateAdd(), database);
            String dt = fecha.format(formatter);
            eq.setDateAdd(dt);

        }

    }

    public Equipment devolverFormatoFecha(Equipment equipment) {
        LocalDate date = LocalDate.parse(equipment.getDateAdd(), formatter);
        String fecha = date.atStartOfDay().atZone(ZoneId.systemDefault()).format(database);
        equipment.setDateAdd(fecha);

        return equipment;
    }

    /**
     * Metodo para transformar la fecha y el coste en los formatos requeridos
     * para las tablas y la base de datos
     *
     * @param equipment que se va a actualizar en la base de datos
     */
    public void editandoFormatosCondicionales(Equipment equipment) {
        LocalDate date = LocalDate.parse(equipment.getDateAdd(), formatter);
        String fecha = date.atStartOfDay().atZone(ZoneId.systemDefault()).format(database);
        equipment.setDateAdd(fecha);

        eqif.edit(equipment);

        date = LocalDate.parse(equipment.getDateAdd(), database);
        fecha = date.format(formatter);
        equipment.setDateAdd(fecha);

        String coste = tfCost.getText();
        if (!tfCost.getText().trim().equals("")) {

            /*Codigo para cambiar todos los decimales anteriores y remplazarlos para
                que solo son dos*/
            DecimalFormatSymbols simbolos = DecimalFormatSymbols.getInstance(Locale.ENGLISH);
            Double remplazo = Double.valueOf(coste);

            coste = new DecimalFormat("#.0#", simbolos).format(remplazo);

            tfCost.setText(coste);
            tbEquipment.getSelectionModel().getSelectedItem().setCost(coste);
        }

    }

    public boolean comprobarValoresGuardar() {
        return tfName.getText().trim().length() != 0
                && tfName.getText().trim().length() <= 50
                && taDescription.getText().trim().length() != 0
                && taDescription.getText().trim().length() <= 400
                && dpDate.getValue() != null
                && Double.parseDouble(tfCost.getText()) > 0;
    }

}