/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import reto2g1cclient.exception.ClientServerConnectionException;
import reto2g1cclient.exception.DBServerException;
import reto2g1cclient.logic.EventFactory;
import reto2g1cclient.logic.EventInterface;
import reto2g1cclient.model.Client;
import reto2g1cclient.model.Evento;

/**
 * Controlador para la ventana VEventTable destinada a Mostrar los eventos
 * almacenados en la BBDD y permitir Crear, Modificar o Eliminar eventos en la
 * misma
 *
 * @author Andoni Alday
 */
public class VEventTableController {

    private static final Logger LOGGER = Logger.getLogger("package.class");
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter database = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private Stage stage;
    private Client client;
    private List<Evento> events = null;
    //private Evento event;
    private Boolean editable;
    private EventInterface ei;
    private ObservableList<Evento> eventData;
    // Booleanos para control de contenido introducido valido
    private Boolean dateEnd = false;
    private Boolean dateStart = false;
    private Boolean desc = false;
    private Boolean name = false;
    // Booleano para control de seleccion de elemento en tabla para habilitar el Botón Nuevo
    private Boolean tableSelec = false;
    // Integer para control de filtro seleccionado
    private Integer filter;

    /**
     * Getter de la Stage sobre la que se va a mostrar la ventana
     *
     * @return la ventana
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Setter de la Stage sobre la que se va a mostrar la ventana
     *
     * @param stage sobre la que se va a mostrar la ventana
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Getter del cliente del que se quieren gestionar los eventos
     *
     * @return el cliente del que se van a gestionar los eventos
     */
    public Client getClient() {
        return client;
    }

    /**
     * Setter del cliente cuyos eventos se desean gestionar
     *
     * @param client del que se quiere gestionar los eventos
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Getter de la coleccion local de eventos
     *
     * @return la coleccion local de eventos
     */
    public List<Evento> getEvents() {
        return events;
    }

    /**
     * Setter para la coleccion de eventos en la memoria local
     *
     * @param events coleccion de eventos local
     */
    public void setEvents(List<Evento> events) {
        this.events = events;
    }

    /**
     * Getter del parametro que determina si la ventana es editable o no
     *
     * @return booleano indicativo de si la ventana es editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * Setter para determinar si una ventana es editable (ha accedido un cliente
     * o un admin) o no
     *
     * @param editable parametro para determinar editabilidad
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * Getter para la interfaz de control del controlador con el nucleo de
     * control de la aplicacion
     *
     * @return interfaz de control
     */
    public EventInterface getEi() {
        return ei;
    }

    /**
     * Setter para la interfaz de control del controlador con el nucleo de
     * control de la aplicacion
     *
     * @param ei interfaz de control
     */
    public void setEi(EventInterface ei) {
        this.ei = ei;
    }

    /**
     * Getter para la coleccion observable de la tabla
     *
     * @return coleccion de eventos
     */
    public ObservableList<Evento> getEventData() {
        return eventData;
    }

    /**
     * Setter para la coleccion observable de la tabla
     *
     * @param eventData coleccion de eventos
     */
    public void setEventData(ObservableList<Evento> eventData) {
        this.eventData = eventData;
    }

    // Elementos FXML de la ventana
    //Controlador del Menu
    @FXML
    private MenuAdminController hMenuBar;
    //Botones
    /**
     * Boton Atras en la ventana
     */
    @FXML
    private Button btnBack;
    /**
     * Boton Eliminar Elemento
     */
    @FXML
    private Button btnDelete;
    /**
     * Boton Nuevo Elemento
     */
    @FXML
    private Button btnNew;
    /**
     * Boton Guardar Cambios
     */
    @FXML
    private Button btnSave;
    /**
     * Boton Buscar
     */
    @FXML
    private Button btnSearch;
    /**
     * Boton Imprimir Informe
     */
    @FXML
    private Button btnPrint;
    //Tabla
    /**
     * Tabla de Eventos
     */
    @FXML
    private TableView<Evento> tbEvent;
    /**
     * Columna Nombre de Evento
     */
    @FXML
    public TableColumn<Evento, String> clName;
    /**
     * Columna Fecha de Inicio de Evento
     */
    @FXML
    public TableColumn<Evento, String> clDateStart;
    /**
     * Columna Fecha de Finalizacion de Evento
     */
    @FXML
    public TableColumn<Evento, String> clDateEnd;
    /**
     * Columna Descripcion de Evento
     */
    @FXML
    public TableColumn<Evento, String> clDescription;
    //Elementos Filtrado Búsqueda   
    /**
     * TextField de Busqueda
     */
    @FXML
    private TextField txtSearch;
    /**
     * ComboBox de Filtros de Busqueda
     */
    @FXML
    private ComboBox<String> cbSearch;
    //Elementos Zona Edicion/Creacion
    /**
     * TextField de Nombre
     */
    @FXML
    private TextField txtName;
    /**
     * TextArea de Descripcion
     */
    @FXML
    private TextArea taDescription;
    /**
     * DatePicker de Fecha de Inicio
     */
    @FXML
    private DatePicker dpDateStart;
    /**
     * DatePicker de Fecha de Finalizacion
     */
    @FXML
    private DatePicker dpDateEnd;
    /**
     * Label de Nombre
     */
    @FXML
    private Label lblName;
    /**
     * Label de Fecha de Inicio
     */
    @FXML
    private Label lblDateStart;
    /**
     * Label de Fecha de Finalizacion
     */
    @FXML
    private Label lblDateEnd;
    /**
     * Label de Error en Fecha de Inicio
     */
    @FXML
    private Label lblDateStartEr;
    /**
     * Label de Error en Fecha de Finalizacion
     */
    @FXML
    private Label lblDateEndEr;
    /**
     * Label de Descripcion
     */
    @FXML
    private Label lblDescription;

    /**
     * Metodo de inicio de la ventana, ejecutando los metodos necesarios para
     * que la ventana se ejecute correctamente
     *
     * @param root contexto de ventana heredado de la ventana anterior o del
     * inicio de la aplicacion
     * @throws IOException si la ventana no puede iniciarse corrctamente
     */
    public void initStage(Parent root) throws IOException {

        LOGGER.info("Initializing VEventTable stage.");

        //Create a new scene
        Scene scene = new Scene(root);

        //CSS (route & scene)
        //String css = this.getClass().getResource("/reto2g1cclient/view/javaFXUIStyles.css").toExternalForm();
        //scene.getStylesheets().add(css);
        //Associate the scene to the stage
        stage.setScene(scene);

        //Set the scene properties
        stage.setTitle("Ventana de Gestión de Eventos");
        stage.setResizable(false);

        //Set Windows event handlers 
        stage.setOnShowing(this::handleWindowShowing);
        stage.setOnCloseRequest(this::closeVEventTable);
        //Set Window initial State

        //Leer Eventos Disponibles
        ei = EventFactory.getImplementation();
        loadData();
        // Iniciar Tabla        
        tbEvent.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tbEvent.getSelectionModel().selectedItemProperty().addListener(this::eventTableSelection);
        // Cargar Datos Iniciales en tabla
        loadTable();
        // Seccion Busqueda
        ObservableList<String> filtros = FXCollections.observableArrayList("Nombre del Evento", "Fecha de Inicio", "Fecha de Finalización", "Descripción");
        cbSearch.getItems().addAll(filtros);
        cbSearch.getSelectionModel().selectedItemProperty().addListener(this::selectedFilter);
        filter = 1;
        btnSearch.setOnAction(this::filterEvents);
        // Botones y Fields
        btnPrint.setOnAction(this::printData);
        btnBack.setOnAction(this::back);
        btnDelete.setOnAction(this::deleteEvent);
        btnNew.setOnAction(this::newEvent);
        btnSave.setOnAction(this::saveChanges);
        txtName.textProperty().addListener(this::txtNameVal);
        dpDateStart.valueProperty().addListener(this::dateStartVal);
        dpDateEnd.valueProperty().addListener(this::dateEndVal);
        taDescription.textProperty().addListener(this::taDescVal);
        //Show main window
        stage.show();
        LOGGER.info("VEventTable stage initiated");

    }

    /**
     * Metodo que determina el valor inicial de los parametros de control
     * (Booleans e Integer) e inicia los elementos al estado adecuado
     * ([des]habilitado / [in]visible)
     *
     * @param event
     */
    public void handleWindowShowing(WindowEvent event) {
        LOGGER.info("Beginning VEventTableController::handleWindowShowing");
        // Visibilidad de labels
        lblDateEnd.setVisible(true);
        lblDateStart.setVisible(true);
        lblDescription.setVisible(true);
        lblName.setVisible(true);
        lblDateEndEr.setVisible(false);
        lblDateStartEr.setVisible(false);
        // Estado inicial de eventos
        btnBack.setDisable(false);
        btnDelete.setDisable(true);
        btnNew.setDisable(true);
        btnSearch.setDisable(false);
        btnPrint.setDisable(false);
        btnSave.setDisable(true);
        // Inicio de booleanos de control a false
        name = false;
        dateStart = false;
        dateEnd = false;
        desc = false;
        tableSelec = false;
        //Control de edición en función de usuario que accede
        editableSetter();

    }

    /**
     * Metodo para control de retorno a la ventana anterior
     *
     * Se solicitara confirmacion al usuario de si esta seguro de volver atras,
     * si confirma, se procedera a cambiar de ventana, sino, se ignora la
     * peticion
     *
     * @param event evento de cierre de ventana
     */
    @FXML
    public void back(ActionEvent event) {
        LOGGER.info("Requesting confirmation for application closing...");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Volviendo a Ventana Principal");
        alert.setHeaderText("¿Seguro que desea volver a la ventana principal? Se perdera el progreso no guardado");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                LOGGER.info("Cambiando a ventana de Admin");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VAdmin.fxml"));
                Parent root = (Parent) loader.load();
                VAdminController controller = ((VAdminController) loader.getController());
                controller.setStage(this.stage);
                controller.initStage(root);
            } catch (Exception e) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Error al cambiar de ventana");
                alert2.setHeaderText("Error al volver a la ventana anterior");
                alert2.setContentText("Ha sucedido un error al intentar volver a la ventana anterior, intentelo de nuevo. "
                        + "\nSi el error persiste, puede deberse a que la ventana a la que se intenta ir no este implementada todavia");
                alert2.showAndWait();
            }
        } else {
            event.consume();
            LOGGER.info("Closing aborted");
        }
    }

    /**
     * Metodo para eliminar evento tras pulsar en el boton correspondiente tras
     * confirmacion e usuario
     *
     * @param event evento de solicitud de eliminacion de evento tras pulsar en
     * boton
     */
    @FXML
    public void deleteEvent(ActionEvent event) {
        // Se solicita confirmacion al usuario sobre eliminacion de un evento
        try {
            LOGGER.info("Requesting confirmation for Event Deletion...");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Esta Eliminando un Evento");
            alert.setHeaderText("¿Seguro que desea Eliminar el Evento Seleccionado?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // Si el usuario confirma, se elimina el evento seleccionado en la tabla del sistema
                ei.remove(tbEvent.getSelectionModel().getSelectedItem());
                loadData();
                loadTable();
                tbEvent.refresh();
            } else {
                event.consume();
                LOGGER.info("Closing aborted");
            }
        } catch (DBServerException ex) {
            Logger.getLogger(VEventTableController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Error al Borrar Evento");
            alert2.setHeaderText("Error al borrar el evento de la base de datos");
            alert2.setContentText("Ha sucedido un error al intentar eliminar el "
                    + "evento de la base de datos, por favor, intentelo mas tarde."
                    + "\nSi el error persiste, comuníquese con el sevicio tecnico");
            alert2.showAndWait();
        } catch (ClientServerConnectionException ex) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Error de Conexión");
            alert2.setHeaderText("Error al conectar con el servidor que alberga la base de datos");
            alert2.setContentText("Ha sucedido un error al intentar eliminar el "
                    + "evento de la base de datos, por favor, intentelo mas tarde."
                    + "\nSi el error persiste, comuníquese con el sevicio tecnico");
            alert2.showAndWait();
        }
    }

    /**
     * Metodo para crear nuevo evento tras pulsar en el boton correspondiente
     * empleando los datos introducidos en los campos superiores
     *
     * @param event evento de solicitud de nuevo evento tras pulsar en boton
     */
    @FXML
    public void newEvent(ActionEvent event) {
        // Comprobacion de que la fecha de finalizacion no es anterior a la de inicio
        if (dpDateEnd.getValue().isAfter(dpDateStart.getValue()) || dpDateEnd.getValue().isEqual(dpDateStart.getValue())) {    
            // Si el label estaba visible por valor de fecha invalida anteriormente, se vuelve a esconder
            lblDateEndEr.setVisible(false);
            if (txtName.getText().trim().length() < 400 && taDescription.getText().trim().length() < 400) {
                try {
                    // Creacion de nuevo evento con carga de datos desde los campos superiores
                    Evento ev = new Evento();
                    ev.setName(txtName.getText().trim());
                    ev.setDescription(taDescription.getText().trim());
                    ev.setDateStart(dpDateStart.getValue().format(formatter));
                    ev.setDateEnd(dpDateEnd.getValue().format(formatter));
                    // Cambio del formato de las fechas al solicitado pr la BBDD
                    ev = devolverFormatoFechas(ev);
                    // Solicitud de creacion del evento a la BBDD
                    ei.createEvent(ev);
                    // Recarga de datos de la BBD y refrescar la tabla
                    loadData();
                    loadTable();
                    tbEvent.refresh();
                    // "Reset" de los campos superior a contenido vacio
                    txtName.setText("");
                    taDescription.setText("");
                    dpDateStart.setValue(null);
                    dpDateEnd.setValue(null);
                } catch (DBServerException ex) {
                    Logger.getLogger(VEventTableController.class.getName()).log(Level.SEVERE, null, ex);
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error al Crear Evento");
                    alert.setHeaderText("Error al crear un nuevo evento en la base de datos");
                    alert.setContentText("Ha sucedido un error al intentar añadir el evento "
                            + "\nen el servidor de datos, por favor, intentelo mas tarde."
                            + "\nSi el error persiste, comuníquese con el sevicio tecnico");
                    alert.showAndWait();
                } catch (ClientServerConnectionException ex) {
                    Alert alert2 = new Alert(Alert.AlertType.WARNING);
                    alert2.setTitle("Error de Conexión");
                    alert2.setHeaderText("Error al conectar con el servidor que alberga la base de datos");
                    alert2.setContentText("Ha sucedido un error al intentar crear el nuevo "
                            + "evento en la base de datos, por favor, intentelo mas tarde."
                            + "\nSi el error persiste, comuníquese con el sevicio tecnico");
                    alert2.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error de Contenido");
                alert.setHeaderText("El contenido introducido no es válido");
                alert.setContentText("El sistema permite una longitud máxima de "
                        + "50 caracteres para el nombre y 400 para la descripcion,"
                        + "uno de estos límites se ha excedido. Si quiere añadir un "
                        + "evento, limite la longitud del nombre y la descripcion "
                        + "dentro de estos limites.\nDisculpe las molestias. Para "
                        + "mas informacion, comuniquese con soporte tecnico.");
                alert.showAndWait();
            }
        } else {
            lblDateEndEr.setVisible(true);
            dpDateEnd.setValue(dpDateStart.getValue());
        }

    }

    /**
     * Metodo de actualizacion de datos en la base de datos tras pulsar en boton
     * Guardar Cambios.
     *
     * Se ha extraido la funcionalidad a otro metodo (saveData) al poder
     * ejecutarse de mas formas que solo pulsar un boton (como al solicitar
     * impresion de informe)
     *
     * @param event evento de solicitud de Guardar Cambios al pulsar en el boton
     */
    @FXML
    public void saveChanges(ActionEvent event) {
        LOGGER.info("Update of data on database requested");
        saveData();
    }

    /**
     * Metodo para control del cierre de la ventana mediante el acceso "X" de la
     * misma
     *
     * Se solicitara confirmacion al usuario de si esta seguro de cerrar el
     * programa, si confirma, se procedera a cerrar el programa, sino, se ignora
     * la peticion
     *
     * @param event evento de cierre de ventana
     */
    public void closeVEventTable(WindowEvent event) {
        LOGGER.info("Requesting confirmation for application closing...");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Esta Cerrando la ventana");
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
     * Metodo para cargar los eventos de la base de datos, fiultrandolos por
     * cliente en caso de que de acceda a la ventana desde un cliente
     */
    public void loadData() {
        LOGGER.info("Loading available Events");
        try {
            // Si se accede desde un cliente (ventana de clientes o sesion de usuario 
            // de un cliente) se emplearan solo los eventos de ese cliente
            if (client != null) {
                events = (List<Evento>) ei.findEventByClient(client);
                // Si se accede de forma "general" (desde la ventana del Administrador", 
                // se cargaran todos los eventos del sistema
            } else {
                events = (List<Evento>) ei.findAll();
            }
        } catch (DBServerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error al actualizar datos en el servidor");
            alert.setHeaderText("Ha sucedido un error al intentar guardar los cambios "
                    + "\nen el servidor de datos, por favor, intentelo mas tarde."
                    + "\nSi el error persiste, comuníquese con el sevicio tecnico");
            alert.showAndWait();
            events = null;
        } catch (ClientServerConnectionException ex) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Error de Conexión");
            alert2.setHeaderText("Error al conectar con el servidor que alberga la base de datos");
            alert2.setContentText("Ha sucedido un error al intentar cargar los "
                    + "eventos de la base de datos, por favor, intentelo mas tarde."
                    + "\nSi el error persiste, comuníquese con el sevicio tecnico");
            alert2.showAndWait();
        }
        cambiarFormatoFechas();
    }

    /**
     * Metodo para cargar los datos obtenidos en la tabla
     */
    public void loadTable() {
        LOGGER.info("Loading Data on Table");
        eventData = FXCollections.observableArrayList(events);
        tbEvent.setItems(eventData);
        LOGGER.info("Data Loaded on Table");
    }

    /**
     * Metodo para controlar si se ha seleccionado un elemento en la Tabla de
     * Eventos
     *
     * · Si se ha seleccionado un elemento, se cargan sus datos en los campos
     * superiores y se habilitan los botones de guardar cambios y eliminar y se
     * deshabiita el boton de nuevo, imposibilitando su habilitacion mientras
     * haya un elemento seleccionado
     *
     * · Si se des-selecciona un elemento, los campos superiores se vacian, se
     * deshabilitan los botones de guardar y eliminar, y se vuelve a poder
     * habilityar el boton de nuevo
     *
     * @param observable Tabla de Eventos que se observa
     * @param oldValue anterior seleccion
     * @param newValue nueva seleccion
     */
    public void eventTableSelection(ObservableValue observable, Evento oldValue, Evento newValue) {
        // Carga de datos en sección de edición
        if (newValue != null) {
            LOGGER.info("New item selected");
            txtName.setText(newValue.getName());
            dpDateStart.setValue(LocalDate.parse(newValue.getDateStart(), formatter));
            dpDateEnd.setValue(LocalDate.parse(newValue.getDateEnd(), formatter));
            taDescription.setText(newValue.getDescription());
            btnNew.setDisable(true);
            btnDelete.setDisable(false);
            btnSave.setDisable(false);
            name = false;
            desc = false;
            dateStart = false;
            dateEnd = false;
            tableSelec = true;
        } else {
            LOGGER.info("Item Deselected");
            txtName.setText("");
            dpDateStart.setValue(null);
            dpDateEnd.setValue(null);
            taDescription.setText("");
            btnNew.setDisable(true);
            btnDelete.setDisable(true);
            btnSave.setDisable(true);
            name = false;
            desc = false;
            dateStart = false;
            dateEnd = false;
            tableSelec = false;
        }
    }

    /**
     * Metodo que controla el valor seleccionado en el ComboBox de Busqueda para
     * el filtrado
     *
     * @param observable ComboBox de busqueda
     * @param oldValue anterior seleccion del ComboBox
     * @param newValue nueva seleccion del ComboBox
     */
    public void selectedFilter(ObservableValue observable, Object oldValue, Object newValue) {
        // Para simplificar el código del metodo de busqueda, se emplea un valor 
        //numerico para almacenar el filtro seleccionado
        LOGGER.info("Filtering parameter selection changed");
        if (newValue != null) {
            switch (cbSearch.getSelectionModel().getSelectedIndex()) {
                // Filtro Nombre
                case 0:
                    filter = 1;
                    break;
                // Filtro Fecha Inicio
                case 1:
                    filter = 2;
                    break;
                // Filtro Fecha Fin
                case 2:
                    filter = 3;
                    break;
                // Filtro Descripcion
                case 3:
                    filter = 4;
                    break;
                // Ningun filtro seleccionado (no deberia darse el caso, pero por si acaso)    
                default:
                    filter = 0;
            }
        }
        LOGGER.info("Filtering parameter selected: " + newValue);
    }

    /**
     * Metodo para ejecutar filtrados en la tabla en función de la seleccion en
     * el ComboBox de busqueda empleando el contenido del cuadro de texto de
     * busqueda
     *
     * 1.- Nombre del Evento - Se mostraran eventos que contengan el texto en su
     * nombre<br/>2.- Fecha de Inicio - Se mostraran eventos cuya fecha de
     * inicio coincida con la introducida<br/>3.- Fecha de Finalizacion - Se
     * mostraran eventos cuya fecha de finalizacion coincida con la
     * introducida<br/>
     * 4.- Descripcion del Evento - Se mostraran eventos que contengan el texto
     * en su descripcion
     *
     * Si no se introduce ningun texto, sea cual sea el filtro seleccionado, se
     * mostraran todos los eventos disponibles
     *
     * @param event evento ejecutado al pulsar el botón buscar
     */
    @FXML
    public void filterEvents(ActionEvent event) {
        LOGGER.info("Filtering available Events");
        try {
            Collection temp = null;
            switch (filter) {
                // Filtro Nombre
                case 1:
                    //Carga los datos de la base de datos
                    loadData();
                    //Filtrar los elementos cuyo nombre no contenga el texto de filtrado
                    temp = events.stream().filter(ev -> !ev.getName().toLowerCase().contains(txtSearch.getText().trim().toLowerCase())).collect(Collectors.toList());
                    //Eliminar de la coleccion local los elementos filtrados
                    events.removeAll(temp);
                    break;
                // Filtro Fecha Inicio
                case 2:
                    //Carga los datos de la base de datos
                    loadData();
                    //Filtrar los elementos cuya fecha de inicio no coincida con la introducida
                        // Control si el texto de busqueda es nulo (si lo es, no realiza filtrado)
                    if (!txtSearch.getText().trim().equals("")) {
                        temp = events.stream().filter(ev -> (LocalDate.parse(ev.getDateStart(), formatter)).compareTo(LocalDate.parse(txtSearch.getText().trim(), formatter)) < 0).collect(Collectors.toList());
                        //Eliminar de la coleccion local los elementos filtrados
                        events.removeAll(temp);
                    }
                    break;
                    // Filtro Fecha Fin
                case 3:
                    //Carga los datos de la base de datos
                    loadData();
                    //Filtrar los elementos cuya fecha de finalizacion no coincida con la introducida
                        // Control si el texto de busqueda es nulo (si lo es, no realiza filtrado)
                    if (!txtSearch.getText().trim().equals("")) {
                        temp = events.stream().filter(ev -> (LocalDate.parse(ev.getDateEnd(), formatter)).compareTo(LocalDate.parse(txtSearch.getText().trim(), formatter)) < 0).collect(Collectors.toList());
                        //Eliminar de la coleccion local los elementos filtrados
                        events.removeAll(temp);
                    }
                    break;
                // Filtro Descripcion
                case 4:
                    //Carga los datos de la base de datos
                    loadData();
                    //Filtrar los elementos cuya descripcion no contenga el texto de filtrado
                    temp = events.stream().filter(ev -> !ev.getDescription().toLowerCase().contains(txtSearch.getText().trim().toLowerCase())).collect(Collectors.toList());
                    //Eliminar de la coleccion local los elementos filtrados
                    events.removeAll(temp);
                    break;
                default:
                    //Carga los datos de la base de datos
                    //  En este caso, que no deberia darse, no se ejecutaria ningun tipo de filtrado
                    loadData();
            }
            //Carga los datos filtrados en la tabla
            loadTable();
            //Refresca la tabla
            tbEvent.refresh();
        } catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Fecha introducida o valida");
            alert.setHeaderText("Ha realizado una busqueda por fecha con texto no valido, "
                    + "si desea buscar por fechas,\nintroduzcala en formato DD/MM/AAAA "
                    + "(por ejemplo, 14/02/2008)");
            alert.showAndWait();
        }
    }

    /**
     * Metodo para comprobar que los campos para crear nuevos eventos estan
     * informados y que no hay ningún elemento de la tabla seleccionado para
     * habilitar el botón de nuevo evento
     */
    public void validateData() {
        if (name && dateStart && dateEnd && desc && !tableSelec) {
            btnNew.setDisable(false);
        } else {
            btnNew.setDisable(true);
        }
    }

    /**
     *
     * Metodo para controlar que se escribe en el TextField del nombre,
     * condicion para habilitar el boton de Nuevo Evento. Si el contenido nuevo
     * es distinto de nulo y de longitud valida, da el OK del nombre a la
     * habilitacion el boton de Nuevo Evento
     *
     * @param observable TextField Nombre a analizar
     * @param oldValue contenido del TextField "antiguo"
     * @param newValue contenido del TextField "nuevo"
     */
    public void txtNameVal(ObservableValue observable, String oldValue, String newValue) {
        name = false;
        if (!newValue.trim().equals("")) {
            name = true;
        }
        if (newValue.trim().length() > 50) {
            newValue = oldValue;
        }
        validateData();
    }

    /**
     *
     * Metodo para controlar que se selecciona o introduce una fecha en el
     * DatePicker de la Fecha de Inicio, condicion para habilitar el boton de
     * Nuevo Evento. Si el contenido nuevo es distinto de nulo, da el OK de la
     * fecha de inicio a la habilitacion el boton de Nuevo Evento
     *
     * @param observable DatePicker Fecha de Inicio a analizar
     * @param oldValue contenido del DatePicker "antiguo"
     * @param newValue contenido del DatePicker "nuevo"
     */
    public void dateStartVal(ObservableValue observable, LocalDate oldValue, LocalDate newValue) {
        dateStart = false;
        if (newValue != null) {
            dateStart = true;
        }
        validateData();
    }

    /**
     *
     * Metodo para controlar que se selecciona o introduce una fecha en el
     * DatePicker de la Fecha de Finalizacion, condicion para habilitar el boton
     * de Nuevo Evento. Si el contenido nuevo es distinto de nulo, da el OK de
     * la fecha de finalizacion a la habilitacion el boton de Nuevo Evento
     *
     * @param observable DatePicker Fecha de Finalizacion a analizar
     * @param oldValue contenido del DatePicker "antiguo"
     * @param newValue contenido del DatePicker "nuevo"
     */
    public void dateEndVal(ObservableValue observable, LocalDate oldValue, LocalDate newValue) {
        dateEnd = false;
        if (newValue != null) {
            dateEnd = true;
        }
        validateData();
    }

    /**
     * Metodo para controlar que se escribe en el TextArea de la descripcion,
     * condicion para habilitar el boton de Nuevo Evento. Si el contenido nuevo
     * es distinto de nulo y de longitud valida, da el OK de la descripcion a la
     * habilitacion el boton de Nuevo Evento
     *
     * @param observable TextArea Descripcion a analizar
     * @param oldValue contenido del TextArea "antiguo"
     * @param newValue contenido del TextArea "nuevo"
     */
    public void taDescVal(ObservableValue observable, String oldValue, String newValue) {
        desc = false;
        if (!newValue.trim().equals("")) {
            desc = true;
        }
        if (newValue.trim().length() > 400) {
            newValue = oldValue;
        }
        validateData();
    }

    /**
     * Metodo para iniciar las celdas en la tabla en modo NO editable para el
     * uso de la ventasna por parte de un comercial
     */
    public void initiateNonEditableTableColumns() {
        LOGGER.info("Generating Table Properties");
        //Columna
        // Factoria de Celda para Valor de Propiedades
        // -> Factoria de Celdas para Edicion
        clName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clName.setCellFactory(TextFieldTableCell.<Evento>forTableColumn());
        clDateStart.setCellValueFactory(new PropertyValueFactory<>("dateStart"));
        clDateStart.setCellFactory(TextFieldTableCell.<Evento>forTableColumn());
        clDateEnd.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));
        clDateEnd.setCellFactory(TextFieldTableCell.<Evento>forTableColumn());
        clDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        clDescription.setCellFactory(TextFieldTableCell.<Evento>forTableColumn());
        LOGGER.info("Table Columns initiated");
    }

    /**
     * Metodo para iniciar las celdas en la tabla en modo editable para el uso
     * de la ventasna por parte de un cliente o el administrador
     */
    public void initiateEditableTableColumns() {
        LOGGER.info("Generating Table Properties");
        //Columna Nombre
        // Factoria de Celda para Valor de Propiedades
        // -> Factoria de Celdas para Edicion
        clName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clName.setCellFactory(TextFieldTableCell.<Evento>forTableColumn());
        clName.setOnEditCommit(this::handleEditCommitName);
        clDateStart.setCellValueFactory(new PropertyValueFactory<>("dateStart"));
        clDateStart.setCellFactory(TextFieldTableCell.<Evento>forTableColumn());
        clDateStart.setOnEditCommit(this::handleEditCommitDateStart);
        clDateEnd.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));
        clDateEnd.setCellFactory(TextFieldTableCell.<Evento>forTableColumn());
        clDateEnd.setOnEditCommit(this::handleEditCommitDateEnd);
        clDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        clDescription.setCellFactory(TextFieldTableCell.<Evento>forTableColumn());
        clDescription.setOnEditCommit(this::handleEditCommitDescription);
    }

    /**
     * Metodo para controlar la edición de los nombres en la tabla
     *
     * @param t celda de la tabla siendo editada
     */
    private void handleEditCommitName(CellEditEvent<Evento, String> t) {
        // Control e edicion del nombre
        // Si el nuevo valor del nombre es no nulo y de longitud aceptable (max 50) se actualiza
        if (!t.getNewValue().equals("") && t.getNewValue().length() <= 50) {
            ((Evento) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setName(t.getNewValue());
            txtName.setText(t.getNewValue());
            editando(tbEvent.getSelectionModel().getSelectedItem());;
            // Si el valor del nombre no es valido, se devuelve al valor original y se avisa al usuario
        } else {
            ((Evento) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setName(t.getOldValue());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error en el valor del Nombre");
            alert.setHeaderText("El Nombre no puede ser un campo vacio ni superar "
                    + "la longitud maxima de 50 caracteres");
            alert.showAndWait();
        }
        tbEvent.refresh();
    }

    /**
     * Metodo para controlar la edición de las fechas de inicio en la tabla
     *
     * @param t celda de la tabla siendo editada
     */
    private void handleEditCommitDateStart(CellEditEvent<Evento, String> t) {
        // Control del cambio e fecha
        try {
            // Intento de conversion de fecha. Si no esta en el formato adecuado, 
            // provocara un fallo que avisara al usuario del mismo
            LocalDate.parse(t.getNewValue(), formatter);
            ((Evento) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setDateStart(t.getNewValue());
            // Comprobacion de validez de fecha: la nueva fecha de inicio no 
            // puede ser posterior a la fecha de finalizacion
            //  Si cumple la comprobacion, se actualiza el valor de lafecha
            if (correctDateStart(tbEvent.getSelectionModel().getSelectedItem(), t.getNewValue())) {
                dpDateStart.setValue(LocalDate.parse(t.getNewValue(), formatter));
                editando(tbEvent.getSelectionModel().getSelectedItem());
                // Si no se cumple la comprobacion, se avisa al usuario y se devuelve el valor anterior
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error en el valor de la fecha");
                alert.setHeaderText("La Fecha de Inicio no puede ser posterior a "
                        + "la Fecha de Finalizacion");
                alert.showAndWait();
                ((Evento) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setDateStart(t.getOldValue());
            }
        } catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error en el formato de la Decha");
            alert.setHeaderText("Ha intentado cambiar la Decha por una no valida."
                    + "\nAsegurese de que la fecha exista y este introducida en "
                    + "formato DD/MM/AAAA");
            alert.showAndWait();
            ((Evento) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setDateStart(t.getOldValue());
        }
        tbEvent.refresh();
    }

    /**
     * Metodo para controlar la edición de las fechas de finalizacion en la
     * tabla
     *
     * @param t celda de la tabla siendo editada
     */
    private void handleEditCommitDateEnd(CellEditEvent<Evento, String> t) {
        // Control del cambio e fecha
        try {
            // Intento de conversion de fecha. Si no esta en el formato adecuado, 
            // provocara un fallo que avisara al usuario del mismo
            LocalDate.parse(t.getNewValue(), formatter);
            ((Evento) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setDateEnd(t.getNewValue());
            // Comprobacion de validez de fecha: la nueva fecha de finalizacion no 
            // puede ser anterior a la fecha de inicio
            //  Si cumple la comprobacion, se actualiza el valor de lafecha
            if (correctDateEnd(tbEvent.getSelectionModel().getSelectedItem(), t.getNewValue())) {
                dpDateEnd.setValue(LocalDate.parse(t.getNewValue(), formatter));
                editando(tbEvent.getSelectionModel().getSelectedItem());
                // Si no se cumple la comprobacion, se avisa al usuario y se devuelve el valor anterior
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error en el valor de la fecha");
                alert.setHeaderText("La Fecha de Finalizacion no puede ser anterior "
                        + "a la Fecha de Inicio");
                alert.showAndWait();
                ((Evento) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setDateEnd(t.getOldValue());
            }
        } catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error en el formato de Fecha");
            alert.setHeaderText("Ha intentado cambiar la Fecha por una no valida."
                    + "\nAsegurese de que la fecha exista y este introducida en "
                    + "formato DD/MM/AAAA");
            alert.showAndWait();
            ((Evento) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setDateEnd(t.getOldValue());
        }
        tbEvent.refresh();
    }

    /**
     * Metodo para controlar la edición de las descripciones en la tabla
     *
     * @param t celda de la tabla siendo editada
     */
    private void handleEditCommitDescription(CellEditEvent<Evento, String> t) {
        // Control e edicion e la descripcion
        // Si el nuevo valor de la descripcion es no nulo y de longitud aceptable (max 400) se actualiza
        if (!t.getNewValue().equals("") && t.getNewValue().length() <= 400) {
            ((Evento) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setDescription(t.getNewValue());
            taDescription.setText(t.getNewValue());
            editando(tbEvent.getSelectionModel().getSelectedItem());
            // Si el valor de la descripcion no es valido, se devuelve al valor original y se avisa al usuario
        } else {
            ((Evento) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setDescription(t.getOldValue());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error en el valor de la Descripcion");
            alert.setHeaderText("La Descripcion no puede ser un campo vacio ni superar "
                    + "la longitud maxima de 400 caracteres");
            alert.showAndWait();
        }
        tbEvent.refresh();
    }

    /**
     * Metodo ejecutado por el botón imprimir
     *
     * @param event evento ejecutado por el botón imprimir
     */
    public void printData(ActionEvent event) {
        // Confirmacion de creacion de informe
        // Si acepta, se actualizan datos y se imprime informe
        // Si cancela, no se imprime informe
        LOGGER.info("Preparing to print");
        if (tableSelec) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Proceso de impresión de informes");
            alert.setHeaderText("Ha solicitado imprimir un informe, para ello, se "
                    + "\nvan a actualizar los datos de la tabla en la base de datos. "
                    + "\n¿Esta seguro de continuar?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                saveData();
                print();
            } else {
                event.consume();
            }
        } else {
            print();
        }
    }

    /**
     * Metodo para guarar los cambios realizados en la base de datos
     */
    public void saveData() {
        LOGGER.info("Updating changes on DataBase");
        // Comprobación de que los campos a actualizar cumplen los parametros:
        // Nombre "no nulo" y longitud maxima 50
        // Descripcion "no nula" y longitud maxima 400
        // Fechas de Inicio y Finalizacion "no nulas"
        // Fecha de Finalizacion posterior o igual a Fecha de Inicio
        if (txtName.getText().trim().length() != 0
                && txtName.getText().trim().length() <= 50
                && taDescription.getText().trim().length() != 0
                && taDescription.getText().trim().length() <= 400
                && dpDateEnd.getValue() != null && dpDateStart.getValue() != null
                && dpDateEnd.getValue().compareTo(dpDateStart.getValue()) >= 0) {
            // Para evitar duplicados, se quita el evento a actualizar de la 
            // coleccion local que e ha cargado en la tabla
            events.remove(tbEvent.getSelectionModel().getSelectedItem());
            // Se guardan los datos del evento seleccionado en uno nuevo, conservando 
            // asi su id y otros datos que no se muestrean en la tabla
            Evento e = tbEvent.getSelectionModel().getSelectedItem();
            // Se actualizan los datos del evento
            e.setName(txtName.getText());
            e.setDescription(taDescription.getText());
            e.setDateEnd(dpDateEnd.getValue().format(formatter));
            e.setDateStart(dpDateStart.getValue().format(formatter));
            // Se devuelve el evento actualizado a la coleccion local
            events.add(e);
            // Se intentan actualizar todos los elementos de la coleccion de la tabla,
            // intentando solventar posibles fallos de actualizacion de datos en intentos anteriores
            for (Evento ev : events) {
                editando(ev);
            }
            // Recarga de datos tras actualización (sea fallida o exitosa, mostrando los datos en la BBDD)
            loadData();
            loadTable();
            tbEvent.refresh();
            LOGGER.info("Updating changes on DataBase");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nuevos valores no validos");
            alert.setHeaderText("Debe introducir valores validos:"
                    + "\nEl Nombre y la Descripción no pueden ser nulos"
                    + "\nni superar las longitudes maximas de 50 y 400 caracteres respectivamente"
                    + "\nLas Fechas deben estar en formato valido"
                    + "\nLa Fecha de Finalización no puede ser anterior a la de Inicio");
            alert.showAndWait();
        }
    }

    /**
     * Metodo para imprimir un informe con los datos de la tabla
     */
    public void print() {
        try {
            // Carga del informe para Equipment table
            JasperReport report = JasperCompileManager.compileReport(getClass()
                    .getResourceAsStream("/reto2g1cclient/reports/EventReport.jrxml"));
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Evento>) this.tbEvent.getItems());
            // Carga de propiedades
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            // Inicialización de visor de informe
            JasperViewer jasperviewer = new JasperViewer(jasperPrint, false);
            jasperviewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(VEventTableController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo para activar las opciones de edicion en los elementos de la
     * ventana si el usuario que la usa tiene lor permisos adecuados
     */
    private void editableSetter() {
        //Si accede a la vista de eventos un cliente o  un administrador, editable sera true y se podran 
        //editar la tabla y lños campos superiores, si accede un comercial, editable sera false y no seran editables
        txtName.setEditable(editable);
        taDescription.setEditable(editable);
        dpDateEnd.setEditable(editable);
        dpDateStart.setEditable(editable);
        tbEvent.setEditable(editable);
        if (editable) {
            // Si inician Admin o Cliente, editable = true -> Tabla Editable
            initiateEditableTableColumns();
        } else {
            // Si inicia comercial, editable = false -> Tabla NO Editable, TextFields, DatePicker y TextArea deshabilitados
            initiateNonEditableTableColumns();
            ((DatePickerSkin) dpDateEnd.getSkin()).getPopupContent().setVisible(editable);
            ((DatePickerSkin) dpDateStart.getSkin()).getPopupContent().setVisible(editable);
        }
    }

    /**
     * Metodo para cambiar el formato de las fechas de los eventos del de la
     * base de datos () al que se desea mostrar (AAAA-DD-MMTHH:mm:ss+TMZ)
     */
    public void cambiarFormatoFechas() {
        //Convertir fechas de la BBDD en fechas mostradas
        for (Evento ev : events) {
            //  Fecha de la BBDD en String a LocalDate
            LocalDate fecha = LocalDate.parse(ev.getDateStart(), database);
            //  LocalDate a String mostrado
            String dt = fecha.format(formatter);
            //  Actualizacion de fecha del evento
            ev.setDateStart(dt);
            //      Repetir para DateEnd
            fecha = LocalDate.parse(ev.getDateEnd(), database);
            dt = fecha.format(formatter);
            ev.setDateEnd(dt);
        }
    }

    /**
     * Metodo para devolver al evento las fechas del formato que se desea
     * mostrar (DD/MM/AAAA) al formato que emplea la BBDD
     * (AAAA-DD-MMTHH:mm:ss+TMZ)
     *
     * @param event evento al que se va a actualizar las fechas
     * @return evento con las fechas actualizadas
     */
    public Evento devolverFormatoFechas(Evento event) {
        try {
            //Convertir fechas mostradas en fecha compatible con la BBDD
            //  Fecha mostrada en String a LocalDate
            LocalDate date = LocalDate.parse(event.getDateStart(), formatter);
            //  LocalDate a String compatible en BBDD
            String fecha = date.atStartOfDay().atZone(ZoneId.systemDefault()).format(database);
            //  Actualizacion de fecha del evento
            event.setDateStart(fecha);
            //      Repetir para DateEnd
            date = LocalDate.parse(event.getDateEnd(), formatter);
            fecha = date.atStartOfDay().atZone(ZoneId.systemDefault()).format(database);
            event.setDateEnd(fecha);
        } catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error en el formato de Fecha");
            alert.setHeaderText("Ha intentado cambiar la Fecha por una no valida."
                    + "\nAsegurese de que la fecha exista y este introducida en "
                    + "formato DD/MM/AAAA");
            alert.showAndWait();
        }
        return event;
    }

    /**
     * Metodo para actualizar la Base de Datos convirtiendo la fecha mostrada
     * (DD/MM/AAAA) en el formato de la BBDD (AAAA-DD-MMTHH:mm:ss+TMZ) y tras
     * actualizarlo volviendo a convertirla en el formato que se desea mostrar
     *
     * @param event el evento del que se esta modificando la fecha
     */
    public void editando(Evento event) {
        try {
            //Convertir fechas mostradas en fecha compatible con la BBDD empleando 
            //metodo devolverFormatoFechas
            event = devolverFormatoFechas(event);
            ei.edit(event);
            //Convertir fecha de BBDD en fecha mostrada
            //  Fecha de BBDD en String a LocalDate
            LocalDate date = LocalDate.parse(event.getDateStart(), database);
            //  LocalDate a fecha mostrada
            String fecha = date.format(formatter);
            //  Actualizacion de fecha del evento
            event.setDateStart(fecha);
            //      Repetir para DateEnd
            date = LocalDate.parse(event.getDateEnd(), database);
            fecha = date.format(formatter);
            event.setDateEnd(fecha);
        } catch (DBServerException ex) {
            Logger.getLogger(VEventTableController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error al Actualizar");
            alert.setHeaderText("Error al Actualizar valores en la Base de Datos");
            alert.setContentText("Debe introducir valores validos:"
                    + "\nEl Nombre y la Descripción no pueden ser nulos"
                    + "\nLas Fechas deben estar en formato valido"
                    + "\nLa Fecha de Finalización no puede ser anterior a la de Inicio");
            alert.showAndWait();
        } catch (ClientServerConnectionException ex) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Error de Conexión");
            alert2.setHeaderText("Error al conectar con el servidor que alberga la base de datos");
            alert2.setContentText("Ha sucedido un error al intentar actualizar el "
                    + "evento de la base de datos, por favor, intentelo mas tarde."
                    + "\nSi el error persiste, comuníquese con el sevicio tecnico");
            alert2.showAndWait();
        }
    }

    /**
     * Metodo para controlar que la nueva fecha de finalización del evento es
     * posterior o igual a la de finalización
     *
     * @param ev evento que se esta actualizando
     * @param dateStart nueva fecha de finalización introducida
     * @return boolean indicando si la fecha es valida
     */
    private boolean correctDateEnd(Evento ev, String dateEnd) {
        boolean correct = false;
        if (LocalDate.parse(ev.getDateStart(), formatter).compareTo(LocalDate.parse(dateEnd, formatter)) <= 0) {
            correct = true;
        }
        return correct;
    }

    /**
     * Metodo para controlar que la nueva fecha de inicio del evento es anterior
     * o igual a la de finalización
     *
     * @param ev evento que se esta actualizando
     * @param dateStart nueva fecha de inicio introducida
     * @return boolean indicando si la fecha es valida
     */
    private boolean correctDateStart(Evento ev, String dateStart) {
        boolean correct = false;
        if (LocalDate.parse(ev.getDateEnd(), formatter).compareTo(LocalDate.parse(dateStart, formatter)) >= 0) {
            correct = true;
        }
        return correct;
    }
}
