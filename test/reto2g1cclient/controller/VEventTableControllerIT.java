/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import reto2g1cclient.application.ClientApplication;
import reto2g1cclient.model.Evento;

/**
 * Clase Integration Test para el Controlador de la Ventana de Evento
 *
 * @author Andoni Alday
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VEventTableControllerIT extends ApplicationTest {

    //Elementos previos a VEventTable
    //  VSignIn
    private TextField txtLogin;
    private PasswordField txtPassword;
    private Button btnSignIn;
    //  Menu Superior
    private MenuBar mbAdmin;
    private Menu mData;
    private MenuItem miEvent;
    //Elementos de VEventTable
    //  Elementos Superiores
    private TextField txtName;
    private DatePicker dpDateStart;
    private DatePicker dpDateEnd;
    private TextArea taDescription;
    //  Elementos de Busqueda
    private ComboBox cbSearch;
    private TextField txtSearch;
    private Button btnSearch;
    //  Botones Principales
    private Button btnPrint;
    private Button btnNew;
    private Button btnSave;
    private Button btnDelete;
    private Button btnBack;
    //  Table    
    private TableView tbEvent;
    private TableColumn clDateEnd;
    //  Label
    private Label lblDateEndEr;

    //Variables escritura test
    private static final String USER = "admin";
    private static final String PASSWORD = "Abcd*1234";
    private static final String FECHASTART = "10/01/2000";
    private static final String FECHAENDOK = "10/01/3000";
    private static final String FECHAENDFAIL = "10/01/1000";
    private static final String NAME = "Evento de test";
    private static final String DESC = "Descripcion del Evento de Prueba";
    //Variables filtrado
    private static final String FILTERNAME = "test";
    private static final String FILTERDESC = "Prueba";

    /**
     * Starts application to be tested.
     *
     * @throws java.util.concurrent.TimeoutException
     */
    @BeforeClass
    public static void setupClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(ClientApplication.class);
    }

    /**
     * Metodo para "mapear" los elementos de la ventana y que el robot pueda
     * reconocerlos. Se ejecuta al comienzo de cada test, al perderse a veces la
     * nocion de los elementos si los test son muy largos.
     */
    private void analizarComponentesVentana() {
        txtName = lookup("#txtName").query();
        dpDateStart = lookup("#dpDateStart").query();
        dpDateEnd = lookup("#dpDateEnd").query();
        taDescription = lookup("#taDescription").query();
        cbSearch = lookup("#cbSearch").queryComboBox();
        txtSearch = lookup("#txtSearch").query();
        btnSearch = lookup("#btnSearch").query();
        btnPrint = lookup("#btnPrint").query();
        btnNew = lookup("#btnNew").query();
        btnSave = lookup("#btnSave").query();
        btnDelete = lookup("#btnDelete").query();
        btnBack = lookup("#btnBack").query();
        tbEvent = lookup("#tbEvent").queryTableView();
        lblDateEndEr = lookup("#lblDateEndEr").query();
    }

    /**
     * Metodo para vaciar los campos superiores (TextField de Nombre, TextArea
     * de Descripcion y DatePickers de Fecha Inicio y Fecha Finalizacion) para
     * aligerar los test que emplean los campos superiores. Se ejecuta al final
     * de cada test para evitar posibles textos residuales.
     */
    public void limpiarCampos() {
        txtName.clear();
        taDescription.clear();
        dpDateStart.setValue(null);
        dpDateEnd.setValue(null);
        txtSearch.clear();
        clickOn(cbSearch);
        sleep(200);
        type(KeyCode.UP);
        sleep(200);
        type(KeyCode.UP);
        sleep(200);
        type(KeyCode.UP);
        sleep(200);
        type(KeyCode.UP);
        sleep(200);
        type(KeyCode.ENTER);
    }

    /**
     * Metodo para limpiar la seleccion de la tabla.
     */
    public void limpiarSeleccion() {
        Node row = lookup(".table-row-cell").nth(0).query();
        press(KeyCode.CONTROL);
        clickOn(row);
        release(KeyCode.CONTROL);
        /**
         * El "robot" es "tan rapido" que confunde los clicks individuales por
         * dobleclick, entrando en el modo de edicio nde la tabla, para evitar
         * esto, se ha añadido un sleep de 1s para separ los clicks y
         * deseleccionar correctamente de la tabla
         */
        sleep(1000);
        press(KeyCode.CONTROL);
        clickOn(row);
        release(KeyCode.CONTROL);
    }

    /**
     * Metodo para viajar desde la ventana de login hasta la ventana que se
     * desea testear
     *
     * @throws java.io.IOException
     */
    @Test
    public void testA1_NavigateToVEventTable() throws IOException {
        clickOn("#txtLogin");
        write(USER);
        clickOn("#txtPassword");
        write(PASSWORD);
        clickOn("#btnSignIn");
        /**
         * Para suplir posibles delays en la respuesta del servidor, se ha
         * aniadido un sleep de 2s para darle algo de tiempo a los test
         */
        sleep(2000);
        verifyThat("#pAdmin", isVisible());
        clickOn("#mData");
        clickOn("#miEvent");
        verifyThat("#pEventTable", isVisible());
    }

    /**
     * Metodo para viajar comprobar la funcionalidad del boton Atras,
     * mostrandole a el usuario un mensaje avisandole de que perdera el progreso
     * no guardado.
     * <br/>Primero se cancela el proceso, confirmando que se mantiene en la
     * ventana VEventTable
     * <br/>Despues de vuelve a solicitar "regresar", volviendo a VAdmin y
     * retorno a VEventTable para el resto de test
     */
    @Ignore
    @Test
    public void testA2_BackToAdminVAndReturnToVEventTable() throws IOException {
        // Comprobacion de Atras -> Cancelar -> Mantener en VEventTable
        clickOn("#btnBack");
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Cancelar");
        verifyThat("#pEventTable", isVisible());
        // Comprobacion de Atras -> Aceptar -> Retorno a VAdmin
        clickOn("#btnBack");
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Aceptar");
        verifyThat("#pAdmin", isVisible());
        // Retorno a VEventTable mediante menu de navegacion
        clickOn("#mData");
        clickOn("#miEvent");
        verifyThat("#pEventTable", isVisible());
    }

    /**
     * Metodo para comprobar el estado inicial de los elementos (si estan
     * (in)visibles, (des)habilitados, etc...
     */
    @Test
    public void testB_VEventTableInitialState() {
        analizarComponentesVentana();
        //Verificacion de elementos superiores
        //  Labels indicativos
        verifyThat("#lblName", isVisible());
        verifyThat("#lblDateStart", isVisible());
        verifyThat("#lblDateEnd", isVisible());
        verifyThat("#lblDescription", isVisible());
        //  Labels de error
        //  Campos "rellenablesw"
        verifyThat("#txtName", hasText(""));
        verifyThat("#taDescription", hasText(""));
        verifyThat("#dpDateStart", isEnabled());
        verifyThat("#dpDateEnd", isEnabled());
        //Verificacion de Botones
        verifyThat("#btnNew", isDisabled());
        verifyThat("#btnSave", isDisabled());
        verifyThat("#btnDelete", isDisabled());
        verifyThat("#btnBack", isEnabled());
        verifyThat("#btnPrint", isEnabled());
        //Verificacion seccion busqueda
        verifyThat("#txtSearch", hasText(""));
        verifyThat("#btnSearch", isEnabled());
        verifyThat("#cbSearch", isEnabled());
        //Verificacion Tabla
        verifyThat("#tbEvent", isVisible());
        verifyThat("#clDateStart", isVisible());
        verifyThat("#clDateEnd", isVisible());
        verifyThat("#clName", isVisible());
        verifyThat("#clDescription", isVisible());
    }

    /**
     * Test de comprobacion de funcionalidad de seleccion-deseleccion de
     * elementos de la tabla. Al seleccionar un elemento se cargan sus datos en
     * los componentes superiores. Al deseleccionar el elemento se "vacian" los
     * componentes superiores
     */
    @Ignore
    @Test
    public void testC_tableSelect_Deselect() {
        analizarComponentesVentana();
        //Seleccionando un elemento de la tabla
        int rowCount = tbEvent.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        Evento event = (Evento) tbEvent.getSelectionModel().getSelectedItem();
        //Carga de datos de evento seleccionado en campos superiores
        verifyThat("#txtName", hasText(event.getName()));
        assertNotNull("No ha cargado la fecha de inicio al seleccionar", dpDateStart.getValue());
        assertNotNull("No ha cargado la fecha de finalizacion al seleccionar", dpDateEnd.getValue());
        verifyThat("#taDescription", hasText(event.getDescription()));
        //Deseleccionar elemento de la tabla
        /**
         * El "robot" es "tan rapido" que confunde los clicks individuales por
         * dobleclick, entrando en el modo de edicio nde la tabla, para evitar
         * esto, se ha añadido un sleep de 1s para separ los clicks y
         * deseleccionar correctamente de la tabla
         */
        sleep(1000);
        press(KeyCode.CONTROL);
        clickOn(row);
        release(KeyCode.CONTROL);
        //Vaciado de campos superiores
        verifyThat("#txtName", hasText(""));
        verifyThat("#taDescription", hasText(""));
        assertEquals("No se ha deseleccionado la fecha", dpDateStart.getValue(), null);
        assertEquals("No se ha deseleccionado la fecha", dpDateEnd.getValue(), null);
        limpiarCampos();
    }

    /**
     * Metodo para comprobar la habilitacion del boton de Crear nuevo Evento
     * tras informar los campos superiores.
     */
    @Ignore
    @Test
    public void testD_CreateButtonEnable() {
        analizarComponentesVentana();
        limpiarCampos();
        clickOn("#txtName");
        write(NAME);
        verifyThat("#btnNew", isDisabled());
        clickOn("#dpDateStart");
        write(FECHASTART);
        verifyThat("#btnNew", isDisabled());
        clickOn("#dpDateEnd");
        write(FECHAENDOK);
        verifyThat("#btnNew", isDisabled());
        clickOn("#taDescription");
        write(DESC);
        verifyThat(btnNew, isEnabled());
        limpiarCampos();
    }

    /**
     * Metodo para confirmar que al relennar la fecha de finalizacion con un
     * valor anterior al de la fecha de inicio para crear nuevo evento al pulsar
     * en el boton no se crear el nuevo evento y se muestra un label al usuario
     * indicandole de su error
     */
    @Ignore
    @Test
    public void testE_CreateWrongDateEnd() {
        analizarComponentesVentana();
        int rowCount = tbEvent.getItems().size();
        clickOn("#txtName");
        write(NAME + " " + rowCount);
        clickOn("#dpDateStart");
        write(FECHASTART);
        clickOn("#dpDateEnd");
        write(FECHAENDFAIL);
        clickOn("#taDescription");
        write(DESC);
        clickOn("#btnNew");
        assertEquals("Ha creado el evento", rowCount, tbEvent.getItems().size());
        verifyThat(lblDateEndEr, isVisible());
        limpiarCampos();
    }

    /**
     * Metodo para confirmar que al rellenar una de las fechas con formato no
     * valido se muestra un label indicativo al usuario.
     * </br><i>Se comprueba solo en uno de los datepicker, al estar los mismos
     * "automatizados" y borrar automaticamente las fechas en formato erroneo al
     * perder el foco</i>
     */
    @Ignore
    @Test
    public void testF_CreateWrongDateFormats() {
        analizarComponentesVentana();
        limpiarCampos();
        clickOn("#dpDateStart");
        write(NAME);
        press(KeyCode.ENTER);
        assertEquals("No se ha deseleccionado la fecha", dpDateStart.getValue(), null);
        clickOn("#dpDateEnd");
        write(NAME);
        press(KeyCode.ENTER);
        assertEquals("No se ha deseleccionado la fecha", dpDateEnd.getValue(), null);
        limpiarCampos();
    }

    /**
     * Metodo para comproabr que se crean eventos nuevos si se rellenan los
     * campos con datos validos
     */
    @Ignore
    @Test
    public void testG_CreateEventSuccessfull() {
        analizarComponentesVentana();
        limpiarCampos();
        int rowCount = tbEvent.getItems().size();
        clickOn("#txtName");
        write(NAME + " " + rowCount);
        clickOn("#taDescription");
        write(DESC);
        clickOn("#dpDateStart");
        write(FECHASTART);
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);
        clickOn("#dpDateEnd");
        write(FECHASTART);
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);
        clickOn("#btnNew");
        assertEquals("Error al crear evento", rowCount + 1, tbEvent.getItems().size());
        List<Evento> users = tbEvent.getItems();
        assertEquals("Error al crear evento", users.stream().filter(u -> u.getName().equals(NAME + " " + rowCount)).count(), 1);
        limpiarCampos();
    }

    /**
     * Metodo para comprobar que si se intentan guardar cambios no validos
     * empleando los campos superiores para modificar, se muestra un alert al
     * usuario y no se realizan los cambios.
     * <br/><i>Para las pruebas se ha empleado el valor de la fecha de
     * finalizacion al ser posiblemente el parametro que mas conflictos puede
     * provocar en un evento.</i>
     */
    @Ignore
    @Test
    public void testH_ModifyEventFormFailure() {
        analizarComponentesVentana();
        int rowCount = tbEvent.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        Evento event = (Evento) tbEvent.getSelectionModel().getSelectedItem();
        clickOn(dpDateEnd);
        dpDateEnd.setValue(null);
        write(FECHAENDFAIL);
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);
        clickOn(btnSave);
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Aceptar");
        assertEquals("Se ha modificado el Evento", event, tbEvent.getSelectionModel().getSelectedItem());
        limpiarCampos();
        limpiarSeleccion();
    }

    /**
     * Metodo para comprobar que si se intenta modificar un evento empleando los
     * campos superiores y se introducen valores validos el evento se modifica
     * correctamente
     */
    @Ignore
    @Test
    public void testI_ModifyEventFormSuccessfull() {
        analizarComponentesVentana();
        int rowCount = tbEvent.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        Evento event = (Evento) tbEvent.getSelectionModel().getSelectedItem();
        clickOn(dpDateEnd);
        dpDateEnd.setValue(null);
        write(FECHAENDOK);
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);
        clickOn(btnSave);
        assertEquals("No se ha modificado el Evento", event.getDateEnd(), FECHAENDOK);
        limpiarCampos();
        limpiarSeleccion();
    }

    /**
     * Metodo para comprobar que si se intentan guardar cambios no validos
     * usando la tabla editable para modificar, se muestra un alert al usuario y
     * no se realizan los cambios, volviendo a poner el valor anterior en la
     * tabla.
     * <br/><i>Para las pruebas se ha empleado el valor de la fecha de
     * finalizacion al ser posiblemente el parametro que mas conflictos puede
     * provocar en un evento.</i>
     */
    @Ignore
    @Test
    public void testJ_ModifyEventTableFailure() {
        analizarComponentesVentana();
        int rowCount = tbEvent.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        assertNotNull("Row is null: table has not that row. ", row);
        Evento event = (Evento) tbEvent.getSelectionModel().getSelectedItem();
        String click = event.getDateEnd();
        Set<Node> list = lookup(click).queryAll();
        Node cell = null;
        for (Iterator<Node> it = list.iterator(); it.hasNext();) {
            cell = it.next();
        }
        doubleClickOn(cell);
        press(KeyCode.CONTROL);
        press(KeyCode.A);
        release(KeyCode.CONTROL);
        release(KeyCode.A);
        write(FECHAENDFAIL);
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Aceptar");
        assertEquals("Se ha modificado el Evento", event, tbEvent.getSelectionModel().getSelectedItem());
        limpiarCampos();
        limpiarSeleccion();
    }

    /**
     * Metodo para comprobar que si se intenta modificar un evento en la tabla
     * editable y se introducen valores validos el evento se modifica
     * correctamente
     */
    @Ignore
    @Test
    public void testK_ModifyEventTableSuccessfull() {
        analizarComponentesVentana();
        int rowCount = tbEvent.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        tbEvent.getSelectionModel().select(rowCount - 1, clDateEnd);
        Evento event = (Evento) tbEvent.getSelectionModel().getSelectedItem();
        String click = event.getDateEnd();
        Set<Node> list = lookup(click).queryAll();
        Node cell = null;
        for (Iterator<Node> it = list.iterator(); it.hasNext();) {
            cell = it.next();
        }
        doubleClickOn(cell);
        press(KeyCode.CONTROL);
        press(KeyCode.A);
        release(KeyCode.CONTROL);
        release(KeyCode.A);
        write(FECHAENDOK);
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);
        event = (Evento) tbEvent.getSelectionModel().getSelectedItem();
        assertEquals("No se ha modificado el Evento", event.getDateEnd(), FECHAENDOK);
        limpiarCampos();
        limpiarSeleccion();
    }

    /**
     * Metodo para comprobar que si se selecciona un evento, se pulsa en
     * cancelar y se deniega la confirmacion, el evento no se elimina
     */
    @Ignore
    @Test
    public void testL_DeleteEventCancel() {
        analizarComponentesVentana();
        int rowCount = tbEvent.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        clickOn("#btnDelete");
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Cancelar");
        assertEquals("Se ha borrado el Evento", rowCount, tbEvent.getItems().size());
        limpiarCampos();
        limpiarSeleccion();
    }

    /**
     * Metodo para comprobar que si se selecciona un evento y se pulsa en
     * aceptar, confirmando la solicitud, el evento se elimina
     */
    @Ignore
    @Test
    public void testM_DeleteEventSuccessfull() {
        analizarComponentesVentana();
        int rowCount = tbEvent.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        clickOn("#btnDelete");
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Aceptar");
        assertEquals("Se ha borrado el Evento", rowCount - 1, tbEvent.getItems().size());
        limpiarCampos();
        limpiarSeleccion();
    }

    /**
     * Test para comprobar que realiza filtrados vacios
     */
    @Ignore
    @Test
    public void testN_filtrarVacio() {
        analizarComponentesVentana();
        limpiarCampos();
        clickOn(btnSearch);
        sleep(250);
        List<Evento> users = tbEvent.getItems();
        assertEquals("Error al filtrar eventos", users.stream().filter(u -> u.getDescription().toLowerCase().contains("")).count(), tbEvent.getItems().size());
        limpiarCampos();
    }

    /**
     * Test para comprobar que realiza filtrado por nombre
     */
    @Ignore
    @Test
    public void testO_filtrarPorNombre() {
        analizarComponentesVentana();
        limpiarCampos();
        testN_filtrarVacio();
        clickOn(txtSearch);
        write(FILTERNAME);
        clickOn(btnSearch);
        sleep(250);
        List<Evento> users = tbEvent.getItems();
        assertEquals("Error al filtrar eventos", users.stream().filter(u -> u.getName().toLowerCase().contains(FILTERNAME.toLowerCase())).count(), tbEvent.getItems().size());
        limpiarCampos();
    }

    

    /**
     * Test para comprobar que realiza filtrado por fecha de inicio
     */
    @Ignore
    @Test
    public void testP_filtrarPorFechaInicio() {
        analizarComponentesVentana();
        limpiarCampos();
        testN_filtrarVacio();
        clickOn(cbSearch);
        sleep(200);
        type(KeyCode.DOWN);
        sleep(200);
        type(KeyCode.DOWN);
        sleep(200);
        type(KeyCode.ENTER);
        clickOn(txtSearch);
        write(FECHASTART);
        clickOn(btnSearch);
        sleep(250);
        List<Evento> users = tbEvent.getItems();
        assertEquals("Error al filtrar eventos", users.stream().filter(u -> u.getDateStart().contains(FECHASTART)).count(), tbEvent.getItems().size());
        limpiarCampos();
    }

    /**
     * Test para comprobar que realiza filtrado por fecha de finalizacion
     */
    @Test
    public void testQ_filtrarPorFechaFin() {
        analizarComponentesVentana();
        limpiarCampos();
        testN_filtrarVacio();
        clickOn(cbSearch);
        sleep(200);
        type(KeyCode.DOWN);
        sleep(200);
        type(KeyCode.DOWN);
        sleep(200);
        type(KeyCode.DOWN);
        sleep(200);
        type(KeyCode.ENTER);
        clickOn(txtSearch);
        write(FECHAENDOK);
        clickOn(btnSearch);
        sleep(250);
        List<Evento> users = tbEvent.getItems();
        assertEquals("Error al filtrar eventos", users.stream().filter(u -> u.getDateEnd().contains(FECHAENDOK)).count(), tbEvent.getItems().size());
        limpiarCampos();
    }

    /**
     * Test para comprobar que realiza filtrado por descripcion
     */
    @Test
    public void testR_filtrarPorDescripcion() {
        analizarComponentesVentana();
        limpiarCampos();
        testN_filtrarVacio();
        clickOn(cbSearch);
        sleep(200);
        type(KeyCode.DOWN);
        sleep(200);
        type(KeyCode.DOWN);
        sleep(200);
        type(KeyCode.DOWN);
        sleep(200);
        type(KeyCode.DOWN);
        sleep(200);
        type(KeyCode.ENTER);
        clickOn(txtSearch);
        write(FILTERDESC);
        clickOn(btnSearch);
        sleep(250);
        List<Evento> users = tbEvent.getItems();
        assertEquals("Error al filtrar eventos", users.stream().filter(u -> u.getDescription().toLowerCase().contains(FILTERDESC.toLowerCase())).count(), tbEvent.getItems().size());
        limpiarCampos();
    }
    
    /**
     * Test para comprobar que realiza falla al filtrar por fecha si se
     * introduce texto sin formato valido
     */
    @Test
    public void testS_filtrarError() {
        analizarComponentesVentana();
        limpiarCampos();
        testN_filtrarVacio();
        clickOn(cbSearch);
        sleep(100);
        type(KeyCode.DOWN);
        sleep(100);
        type(KeyCode.DOWN);
        sleep(100);
        type(KeyCode.ENTER);
        clickOn(txtSearch);
        write(FILTERDESC);
        clickOn(btnSearch);
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Aceptar");
    }

}
