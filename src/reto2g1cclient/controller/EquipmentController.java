/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import reto2g1cclient.exception.ClientServerConnectionException;
import reto2g1cclient.exception.DBServerException;
import reto2g1cclient.logic.EquipmentFactory;

/**
 * Controlador de la ventana de VEquipamientTable para la gestion de 
 * Creacion,Visualizacion, Edicion y Borrado de Equipamientos
 * (Crear,Mostrar,Editar y Borrar)
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
    //Boleanos para el control de todo el contenido de la ventana
    private Boolean bolName;
    private Boolean bolCost;
    private Boolean bolDescription;
    private Boolean bolDateBuy;
    private Boolean bolTableEquipSelec;
    private Boolean bolEquipEncontrado = false;
    
    //Integer para  control de filtros
    private Integer filters;
    
    //Se usa para cambiar el formato de las fechas
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter database = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private static final int MAX_LENGHT_CANTIDAD = 2;
   
    /**
     * Metodo get para obtener la coleccion editable a emplear en la tabla
     * @return la coleccion de la tabla
     */
    public ObservableList<Equipment> getEquipmentData() {
        return equipmentData;
    }

    /** 
     * Metodo set enviar una coleccion editable para emplear en la tabla
     * @param equipmentData coleccion editable
     */
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

    /**
     * Metodo get para obetener los equipamientos
     * @return un equipamiento
     */
    public Equipment getEquipment() {
        return equipment;
    }

    /**
     * Metodo set para enviar un equipamieto
     * @param equipment a enviar
     */
    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    /**
     * Metodo get para la obtencion de la interfaz del equipamiento
     * @return la interfaz del equipamiento
     */
    public EquipmentInterface getEqif() {
        return eqif;
    }

    /**
     * Metodo set para determinar la interfaz a emplear por el controlador
     * @param eqif la interfaz a emplear por el controlador
     */
    public void setEqif(EquipmentInterface eqif) {
        this.eqif = eqif;
    }

    /**
     * Metodo para enviar la coleccion de los equipamientos
     * @param equipments  coleccion a enviar
     */
    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    /**
     * Metodo get para obtener la coleccion de equipamientos    
     * @return coleccion de los equipamientos
     */
    public List<Equipment> getEquipments() {
        return equipments;
    }

    //Elementos FXML declarados 
    
    //Tabla de los Equipamientos
    @FXML
    private TableView<Equipment> tbEquipment;
    
    //Columna de Nombre en la tabla de Equipamientos
    @FXML
    private TableColumn<Equipment, String> clName;
    //Columna de Coste en la tabla de Equipamientos
    @FXML
    private TableColumn<Equipment, String> clCost;
    //Columna de Descripcion en la tabla de Equipamientos
    @FXML
    private TableColumn<Equipment, String> clDescription;
    //Columna de Fecha en la tabla de Equipamientos
    @FXML
    private TableColumn<Equipment, String> clDate;
    //Boton para volver a la ventana anterior 
    @FXML
    private Button btnBack;
    //Boton para borrar el equipamiento
    @FXML
    private Button btnDeleteEquip;
    //Boton para guardar cambios
    @FXML
    private Button btnSaveEquip;
    //Boton para aplicar la busqueda del filtro seleccionado
    @FXML
    private Button btnFind;
    //Combobox para seleccionar los filtros
    @FXML
    private ComboBox cbSearch;
    //TextField para introducir el texto/numeros a filtrar
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

    /**
     * Metodo para lanzar la ventana VEquipmentTable con sus respectivos elementos 
     * @param root recivido desde VAdmin, que permite que la ventana mueste sus
     * elementos hijos (Botones, TextField...)
     *
     */
    public void initStage(Parent root) {

        //Crea una nueva Scene
        Scene scene = new Scene(root);
        //CSS (route & scene)
        String css = this.getClass().getResource("/reto2g1cclient/view/javaFXUIStyles.css").toExternalForm();
        scene.getStylesheets().add(css);
        //Asocia la scena con el Stage
        stage.setScene(scene);
        //Asigna las propiedades de la scene
        stage.setTitle("Equipamiento");
        stage.setMinWidth(960);
        stage.setMinHeight(720);
        stage.setResizable(false);
        //Al seleccionar mandar datos de la tabla a las cajas
        tbEquipment.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tbEquipment.getSelectionModel().selectedItemProperty().addListener(this::setDataOnTblEquip);
        //Set Windows event handlers 
        stage.setOnShowing(this::handleWindowShowing);
         stage.setOnCloseRequest(this::closeVEquipmentTable);
        //Botones
        btnBack.setOnAction(this::back);
        btnCrearEquip.setOnAction(this::newEquipment);
        btnDeleteEquip.setOnAction(this::deleteEquipment);
        btnSaveEquip.setOnAction(this::saveEquipment);
        btnFind.setOnAction(this::filterEquipments);
        btnPrint.setOnAction(this::printData);
        
        //Fields textArea y DatePicker
        tfName.textProperty().addListener(this::tfNameValue);
        tfCost.textProperty().addListener(this::tfCostValue);
        taDescription.textProperty().addListener(this::taDescriptionValue);
        dpDate.valueProperty().addListener(this::dpDateAddValue);
        
        //Filtros
        ObservableList<String> filters = FXCollections.observableArrayList("Nombre del Equipamiento", "Coste maximo", "Coste minimo", "Fecha de compra", "Descripción");
        cbSearch.getItems().addAll(filters);

        //Implementacion del Equipamiento
        eqif = EquipmentFactory.getImplementation();
        //Recuperacion y Cargado de datos en la ventana
        loaddata();
        loadTblEquipment();
        setTableData();
        stage.show();
    }

    /**
    * Metodo que determina el valor inicial de los parametros de control
     * (Boolean , Integer) e inicia los elementos de la ventana al estado
     * predetermninado (Habilitado / Desabilitado
     * 
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
        //El TextField de Nombre esta habilitado y foco en el nombre
        tfName.setDisable(false);
        tfName.requestFocus();
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
    
    /**
     * Metodo para cargar los equipamientos de la base de datos.
     */
    private void loaddata() {
        try {
            equipments = (List<Equipment>) eqif.findAll();
        } catch (DBServerException e) {
            LOGGER.info(e.getMessage() + " Load data fallo");
        }catch (ClientServerConnectionException ex) {
             LOGGER.severe("Error al comunicarse con el servidor" + ex);
            Alert altWarningLog = new Alert(AlertType.WARNING);
            altWarningLog.setTitle("Error al ejecutar la aplicacion");
            altWarningLog.setHeaderText("La base de datos puede no estar disponible en este momento ");
            altWarningLog.setContentText("Porfavor intentelo mas tarde");
            altWarningLog.showAndWait();

        }
        cambiarFormatoFecha();
    }
    
    /**
     * Metodo para cargar datos de la base de datos en la tabla
     */
    private void loadTblEquipment() {
        LOGGER.info("Cargando datos en tabla");
        equipmentData = FXCollections.observableArrayList(equipments);
     
        tbEquipment.setItems(equipmentData);

    }

    /**
     * Metodo para controlar el retorno a la ventana anterior
     * 
     * Se solicita confirmacion para volver a la ventana anterior en el caso 
     * de confirmacion se volvera a la venetana anterior, En el caso de cancelacion
     * nos mantendremos en la ventana.
     *
     * 
     * @param event evento de cierre de ventana
     */
    @FXML
    public void back(ActionEvent event) {
        LOGGER.info("Se va a volver a la ventana anterior");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Se va a volver a la ventana anterior");
        alert.setHeaderText("¿Seguro que desea volver a la ventana anterior?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try{
                LOGGER.info("Cambiando a ventana de Admin");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VAdmin.fxml"));
                Parent root = (Parent) loader.load();
                VAdminController controller = ((VAdminController) loader.getController());
                controller.setStage(this.stage);
                controller.initStage(root);  
            }catch (Exception e) {
                Alert alertVolver = new Alert(Alert.AlertType.WARNING);
                alertVolver.setTitle("Error al cambiar de ventana");
                alertVolver.setHeaderText("Error al volver a la ventana anterior");
                alertVolver.setContentText("Ha sucedido un error al volver a la ventana anterior"
                        + "en el caso de ser persistente intentelo denuevo o mas tarde");
                alertVolver.showAndWait();
            }
         
            LOGGER.info("Redireccionando ventana Admin");
        } else {
            event.consume();
            LOGGER.info("Closing aborted");
        }
    }

    /**
     *  Metodo para mostrar un mensaje de informacion de cierre de ventana
     * al presionar el boton X en el caso de confirmacion la ventana se cerrara y
     * en caso contrario la ventana se mantendra habierta
     * @param event evento de cierre de ventana
     */
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

    /**
     * Metodo para crear un nuevo equipamiento al pulsar el boton se creara un 
     * equipamiento con los campos superiores correspondientes
     * @param event de solicitud de nuevo equipamiento al pulsar el boton
     */
    @FXML
    public void newEquipment(ActionEvent event) {
        try {
            double coste = Double.parseDouble(tfCost.getText());

            if (coste > 0 && coste <= 10000) {
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
                //Comprueba si los textos cumplen los parametros establecidos
            } else if(comprobarValoresTextoGuardar()){
                lblWarninNumValue.setVisible(true);
                LOGGER.info("Error nombre o descripcion han"
                        + " superado el tamaño establecido" );
            lblWarninNumValue.setVisible(true);
            Alert altWarningLog = new Alert(AlertType.INFORMATION);
            altWarningLog.setTitle("Error el contenido no es valido");
            altWarningLog.setHeaderText("Error el contenido no es valido");
            altWarningLog.setContentText("El nombre o la descripcion superan los"
                    + " limites establecidos.\n "
                    + "el nombre no puede superar los 50 caracteres y la descripcion"
                    + " no puede superar los 400 caracteres");
            altWarningLog.showAndWait();
            } else {
                lblWarninNumValue.setVisible(true);
                LOGGER.info("Error on cost value or Cuantity" );
            lblWarninNumValue.setVisible(true);
            Alert altWarningLog = new Alert(AlertType.INFORMATION);
            altWarningLog.setTitle("Error el coste introducido no es valido");
            altWarningLog.setHeaderText("El coste introducido no es numerico o es mayor al coste maximo 10000€");
            altWarningLog.setContentText("Porfavor introduzca un valor numerico dentro del rango establecido");
            altWarningLog.showAndWait();
            }
        } catch (NumberFormatException e) {
            LOGGER.info("Error on cost value" + e);
            lblWarninNumValue.setVisible(true);
            Alert altWarningLog = new Alert(AlertType.WARNING);
            altWarningLog.setTitle("Error el coste introducido no es valido");
            altWarningLog.setHeaderText("El coste introducido no es numerico o es mayor al coste maximo 10000€");
            altWarningLog.setContentText("Porfavor introduzca un valor numerico dentro del rango establecido");
            altWarningLog.showAndWait();
        } catch (DBServerException ex) {
             LOGGER.severe("Error en el guardado del equipamiento" + ex);
            Alert altWarningLog = new Alert(AlertType.WARNING);
            altWarningLog.setTitle("Error al guardar en la base de datos");
            altWarningLog.setHeaderText("La base de datos puede no estar disponible en este momento ");
            altWarningLog.setContentText("Porfavor intentelo mas tarde");
            altWarningLog.showAndWait();

        }catch (ClientServerConnectionException ex) {
             LOGGER.severe("Error en el guardado del equipamiento" + ex);
            Alert altWarningLog = new Alert(AlertType.WARNING);
            altWarningLog.setTitle("Error al guardar en la base de datos");
            altWarningLog.setHeaderText("La base de datos puede no estar disponible en este momento ");
            altWarningLog.setContentText("Porfavor intentelo mas tarde");
            altWarningLog.showAndWait();

        }

    }

    /**
     * Metodo para eliminar un equipamiento seleccionado en la tabla
     * despues de confirmacion en caso contrario no se borrara el equipamiento
     * @param event evento de solicitud de eliminacion de equipamiento despues de 
     * pulsar el boton
     */
    @FXML
    public void deleteEquipment(ActionEvent event) {
        try {
            LOGGER.info("Deleting Equipment");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Está Eliminando un Equipamiento");
            alert.setHeaderText("¿Seguro que desea Eliminar el Equipamiento Seleccionado?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                eqif.remove(tbEquipment.getSelectionModel().getSelectedItem());
            
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
        }catch (ClientServerConnectionException ex) {
             LOGGER.severe("Error en el guardado del equipamiento" + ex);
            Alert altWarningLog = new Alert(AlertType.WARNING);
            altWarningLog.setTitle("Error al guardar en la base de datos");
            altWarningLog.setHeaderText("La base de datos puede no estar disponible en este momento ");
            altWarningLog.setContentText("Porfavor intentelo mas tarde");
            altWarningLog.showAndWait();

        }

    }

    /**
     * Metodo para guardar en la base de datos los cambios despues de pulsar el boton 
     * Guardar Cambios 
     * @param event evento de solicitud de Guardar Cambios al pulsar el boton
     */
    @FXML
    public void saveEquipment(ActionEvent event){
        try {
            LOGGER.info("Actualizando cambios en la base de datos");
            if (bolTableEquipSelec) {
                /*
                    Llama al metodo para comprobar si los parametros establecidos
                    son correctos en el caso de serlo se guardaran 
                    
                */
                if (comprobarValoresGuardar()) {
                    //Creacion de equipamiento actualizado en memoria local 
                    Equipment e = tbEquipment.getSelectionModel().getSelectedItem();
                    e.setName(tfName.getText());
                    e.setCost(tfCost.getText());
                    e.setDateAdd(dpDate.getValue().format(formatter));
                    e.setDescription(taDescription.getText());
                    //Carga de equipamiento actualizado en coleccion local
                    equipments.add(e);
                    //Restauracion de formatos compatibles con base de datos
                    for (Equipment eq : equipments) {
                        editandoFormatosCondicionales(eq);
                    }
                    /*NOTA: Se aniade el equipamiento a la base de datos empleando
                    el ajuste del metodo "edit" del Restfull que crea
                    elementos nuevos si no existen en la base de datos*/
                    loaddata();
                    loadTblEquipment();
                    tbEquipment.refresh();
                } else {
                    //En el caso de haber errores se restauran los datos previos
                    tfName.setText(tbEquipment.getSelectionModel().getSelectedItem().getName());
                    tfCost.setText(tbEquipment.getSelectionModel().getSelectedItem().getCost());
                    taDescription.setText(tbEquipment.getSelectionModel().getSelectedItem().getDescription());
                    dpDate.setValue(LocalDate.parse(tbEquipment.getSelectionModel().getSelectedItem().getDateAdd(), formatter));
                    LOGGER.severe("Aviso: alguno de los valores introducidos no cumple con los parametros"
                            + "o esta vacio");
                    Alert altWarningLog = new Alert(AlertType.INFORMATION);
                    altWarningLog.setTitle("Error al modificar los datos");
                    altWarningLog.setHeaderText("Aviso alguno de los valores introducidos no cumple con los parametros"
                            + " o esta vacio");
                    altWarningLog.setContentText("Parametros validos para los campos:\n"
                            + " El coste debe cumplir con el siguiente formato xxx.yy , el valor mayor que 0 y menor que 10000\n" +
                              "Si lo que desea modificar es una FECHA debera cumplir "
                            + "el formato DD/MM/AAAA.\n"
                            + "El nombre no puede superar la longitd maxima 50 caracteres ni ser nulo.\n"
                            + "La descripcion no puede superar la longitd maxima"
                            + " de 400 caracteres ni ser nulo.");
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
                    + ",mayor que 0 y menor que 10000 ademas con formato xxx.yy."
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
        }
    }

    /**
     * ******* METODOS PARA HABILITAR LOS BOTONES ****************
     */
    /**
     * Metodo de comprobacion a tiempo real de los cambios en el
     * TextField de nombre (tfName)
     * @param observable parametro cuyo cambio se esta observando
     * @param oldValue parametro antiguo del valor
     * @param newValue parametro nuevo del valor
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

    /**
     * Metodo de comprobacion a tiempo real de los cambios en el
     * TextField de nombre (tfCost)
     * @param observable parametro cuyo cambio se esta observando
     * @param oldValue parametro antiguo del valor
     * @param newValue parametro nuevo del valor
     */
    public void tfCostValue(ObservableValue observable, String oldValue, String newValue) {
        bolCost = false;
        try {
            if (!newValue.trim().equals("") ) {
                
                    
                Double c = Double.valueOf(newValue);
                lblWarninNumValue.setVisible(false);
                if (c > 0 ) {
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
      

           

    /**
     * Metodo de comprobacion a tiempo real de los cambios en el
     * TextField de nombre (taDescription)
     * @param observable parametro cuyo cambio se esta observando
     * @param oldValue parametro antiguo del valor
     * @param newValue parametro nuevo del valor
     */
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

    /**
     * Metodo de comprobacion a tiempo real de los cambios en el
     * TextField de nombre (dpDate)
     * @param observable parametro cuyo cambio se esta observando
     * @param oldValue parametro antiguo del valor
     * @param newValue parametro nuevo del valor
     */
    public void dpDateAddValue(ObservableValue observable, LocalDate oldValue, LocalDate newValue) {
        bolDateBuy = false;
        if (newValue != null) {
            bolDateBuy = true;

        }

        validateEquipData();
    }

    /**
     * Metodo para comprobar si todos los campos Texfield, TextArea y datePicker estan
     * informados y ademas no se ha seleccionado ningun elemto en la base de datos
     * 
     */
    public void validateEquipData() {
        if (bolName && bolDescription && bolCost && bolDateBuy && !bolTableEquipSelec) {
           

            btnCrearEquip.setDisable(false);
        } else {

            btnCrearEquip.setDisable(true);
        }

    }

    /**
     * Metodo que configura la tabla de equipamientos para la funcionalidad editable
     */
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

    /**
     * Controlador de edicion de la celda de nombre
     * @param t el indicativo de la celda seleccionada
     */
    public void cellNameEdit(CellEditEvent<Equipment, String> t) {

        if (!t.getNewValue().equals("") && t.getNewValue().length() < 50) {
            ((Equipment) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setName(t.getNewValue());
            tfName.setText(t.getNewValue());
            editandoFormatosCondicionales(tbEquipment.getSelectionModel().getSelectedItem());
        } else {
            ((Equipment) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setName(t.getOldValue());
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error en el valor del Nombre");
            alert.setHeaderText("El Nombre no puede ser un campo vacio ni superar "
                    + "la longitud maxima de 50 caracteres");
            alert.showAndWait();
        }
        tbEquipment.refresh();

    }

    /**
      * Controlador de edicion de la celda de coste
     * @param t el indicativo de la celda seleccionada
     */
    public void cellCostEdit(CellEditEvent<Equipment, String> t) {

        try {
            Double valor = Double.parseDouble(t.getNewValue());
            /*Comprueba que el valor del coste no sea menor que 0
              ni mayor que 10000
            */
            if (valor > 0  && valor <= 10000) {
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
                altWarningLog.setContentText("El coste que se ha introducido debe ser mayor a 0 y menor que 10000 "
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

    /**
     * Controlador de edicion de la celda de descripcion
     * @param t el indicativo de la celda seleccionada
     */
    public void cellDescriptionEdit(CellEditEvent<Equipment, String> t) {

        if (!t.getNewValue().equals("") && t.getNewValue().length() < 400) {
            ((Equipment) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setDescription(t.getNewValue());
            taDescription.setText(t.getNewValue());
            editandoFormatosCondicionales(tbEquipment.getSelectionModel().getSelectedItem());
        } else {
            ((Equipment) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setDescription(t.getOldValue());
                       Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error en el valor de la Descripcion");
            alert.setHeaderText("La Descripcion no puede ser un campo vacio ni superar "
                    + "la longitud maxima de 400 caracteres");
            alert.showAndWait();
        }
        tbEquipment.refresh();

    }

    /**
      * Controlador de edicion de la celda de fecha de compra
     * @param t el indicativo de la celda seleccionada
     */
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

    /**
     * Metodo de insercion de datos en sus correspondientes campos superiores al
     * seleccionar una fila de la tabla en el caso de deseleccionar dicha fila los 
     * campos se vaciaran.
     * @param observable parametro cuyo cambio se esta observando
     * @param oldValue parametro antiguo del valor
     * @param newValue parametro nuevo del valor
     */
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

    /**
      * Metodo para ejecutar filtrados en la tabla en función de la opcion seleccionada
      * 
     * el ComboBox de busqueda empleando el contenido del cuadro de texto de
     * busqueda
     * @param event evento de pulsacion de boton de busqueda
     */
    @FXML
    public void filterEquipments(ActionEvent event) {
        LOGGER.info("ejecutando filtros ");

        try {

            List<Equipment> eqs = new ArrayList<>();
            loaddata();
            switch (cbSearch.getSelectionModel().getSelectedIndex()) {
                case 0:
                    //Filtra por nombre y en el caso de no introducir nada muestra todos
                    if (!tfFinding.getText().trim().equals("")) {
                        for (Equipment eq : equipments) {
                            if (eq.getName().toUpperCase().contains(tfFinding.getText().toUpperCase().trim())) {
                              
                                eqs.add(eq);
                                

                            }
                        }
                        equipments = eqs;
                    } else {
                        loaddata();
                    }

                    break;

                case 1:
                    //Filtra por coste maximo y en el caso de no introducir nada muestra todos
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
                    //Filtra por coste minimo y en el caso de no introducir nada muestra todos
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
                    //Filtra por fecha de compra y en el caso de no introducir nada muestra todos
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
                    //Filtra por descripcion y en el caso de no introducir nada muestra todos
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

    

    /**
     * Metodo para cambiar el formato de las fechas de los equipamientos del de la
     * base de datos () al que se desea mostrar (AAAA-DD-MMTHH:mm:ss+TMZ)
     */

    public void cambiarFormatoFecha() {
        //Convertir las fechas de la base de datos en las fechas mostradas
        for (Equipment eq : equipments) {
            //fechas de la base de datos en string LocalDate
            LocalDate fecha = LocalDate.parse(eq.getDateAdd(), database);
            //LocalDate a String mostrado
            String dt = fecha.format(formatter);
            //Actualizacion a fecha del equipamiento
            eq.setDateAdd(dt);

        }

    }

    /**
     * Metodo para devolver al equipamiento las fechas del formato que se desea
     * mostrar (DD/MM/AAAA) al formato que emplea la base de datos
     * (AAAA-DD-MMTHH:mm:ss+TMZ)
     * @param equipment equipamiento al que se va a actualizar las fechas
     * @return equipamiento con las fechas actualizadas
     */
    public Equipment devolverFormatoFecha(Equipment equipment) {
      try{
          //Convertir fechas mostradas en fecha compatible con la base de datos
        //  Fecha mostrada en String a LocalDate
        LocalDate date = LocalDate.parse(equipment.getDateAdd(), formatter);
         //  LocalDate a String compatible en base de datos
        String fecha = date.atStartOfDay().atZone(ZoneId.systemDefault()).format(database);
        //Actualizacion de la fecha del equipamiento
        equipment.setDateAdd(fecha); 
      }catch(DateTimeParseException e){
          Alert altWarningLog = new Alert(AlertType.WARNING);
            altWarningLog.setTitle("La fecha introducida no es correcta ");
            altWarningLog.setHeaderText("La fecha introducida no cumple los "
                    + "siguientes paramentros");
            altWarningLog.setContentText("El coste que se ha introducido deben"
                    + " cumplir el formato DD/MM/AAAA");
            altWarningLog.showAndWait();
      }
       

        return equipment;
    }

    /**
     * Metodo para transformar la fecha y el coste en los formatos requeridos
     * para las tablas y la base de datos
     *
     * @param equipment que se va a actualizar en la base de datos
     */
    public void editandoFormatosCondicionales(Equipment equipment) {
        try {
            //fechas de la base de datos en string LocalDate
            LocalDate date = LocalDate.parse(equipment.getDateAdd(), formatter);
              //  LocalDate a fecha mostrada
            String fecha = date.atStartOfDay().atZone(ZoneId.systemDefault()).format(database);
            //Actualizacion de la fecha del equipamiento
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
        } catch (DBServerException ex) {
           LOGGER.severe("Error en el guardado del equipamiento" + ex);
            Alert altWarningLog = new Alert(AlertType.WARNING);
            altWarningLog.setTitle("Error al guardar en la base de datos");
            altWarningLog.setHeaderText("La base de datos puede no estar disponible en este momento ");
            altWarningLog.setContentText("Porfavor intentelo mas tarde");
            altWarningLog.showAndWait();

        }catch (ClientServerConnectionException ex) {
             LOGGER.severe("Error en el guardado del equipamiento" + ex);
            Alert altWarningLog = new Alert(AlertType.WARNING);
            altWarningLog.setTitle("Error al guardar en la base de datos");
            altWarningLog.setHeaderText("La base de datos puede no estar disponible en este momento ");
            altWarningLog.setContentText("Porfavor intentelo mas tarde");
            altWarningLog.showAndWait();

        }

    }

    /**
     * Metodo para comprobar que todos los parametros de Nombre,Coste,descripcion
     * y fecha de compra estan informados y dentro de los parametros establecidos
     * @return valor true o false dependiendo de si cumple o no las condiciones
     */
    public boolean comprobarValoresGuardar() {
        return tfName.getText().trim().length() != 0
                && tfName.getText().trim().length() <= 50
                && taDescription.getText().trim().length() != 0 
                && taDescription.getText().trim().length() <= 400
                && dpDate.getValue() != null
                && Double.parseDouble(tfCost.getText()) > 0 
                && Double.parseDouble(tfCost.getText()) <= 10000 ;
    }
    /**
     * Metodo para comprobar si el nombreno es nulo y no tiene mas de
     * 50 caracteres y,
     * La descripcion no es nulo y tiene menos de 400 caracteres
     * 
     * @return valor true o false dependiendo de si cumple o no las condiciones
     */
    public boolean comprobarValoresTextoGuardar() {
       return tfName.getText().trim().length() != 0
                && tfName.getText().trim().length() <= 50
                && taDescription.getText().trim().length() != 0 
                && taDescription.getText().trim().length() <= 400 ;
    }
    
    
    /**
     * Metodo que ejecuta el boton imprimir
     * @param event evento ejecutado por el botón imprimir
     */
    public void printData(ActionEvent event) {
        // Confirmacion de creacion de informe
        // Si acepta, se actualizan datos y se imprime informe
        // Si cancela, no se imprime informe
        LOGGER.info("Preparando impresion de los equipamientos");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Proceso de impresión de informes");
        alert.setHeaderText("Ha solicitado imprimir un informe, para ello, se "
                + "\nvan a actualizar los datos de la tabla en la base de datos. "
                + "\n¿Esta seguro de continuar?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            saveEquipment(event);
            print();
        } else {
            event.consume();
        }
    }

    /**
     *  Metodo para imprimir un informe con los datos de la tabla
     */
    public void print() {
        try {
            // Carga del informe para Equipment table
            JasperReport report = JasperCompileManager.compileReport(getClass()
                    .getResourceAsStream("/reto2g1cclient/reports/EquipmentReport.jrxml"));
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Equipment>) this.tbEquipment.getItems());
            // Carga de propiedades
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            // Inicialización e visor de informe
            JasperViewer jasperviewer = new JasperViewer(jasperPrint,false);
            jasperviewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(EquipmentController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
