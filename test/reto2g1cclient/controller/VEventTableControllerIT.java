/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import reto2g1cclient.model.Evento;

/**
 *
 * @author Andoni Alday
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VEventTableControllerIT extends ApplicationTest {

    public VEventTableControllerIT() {
    }

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
    
    private String name = "Evento de test";

    /**
     * Starts application to be tested.
     *
     * @param stage Primary Stage object
     * @throws Exception If there is any error
     */
    @Override
    public void start(Stage stage) throws Exception {
        //start JavaFX application to be tested    
        //new ClientApplication().start(stage);
        //"lookup" de los nodos de las ventanas previas a VEventTable
        txtLogin = lookup("#txtLogin").query();
        txtPassword = lookup("#txtPassword").query();
        btnSignIn = lookup("#btnSignIn").query();
        //"lookup" de los nodos de la ventana VEventTable
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
    }

    public void limpiarCampos() {
        txtName.clear();
        taDescription.clear();
        dpDateStart.setValue(null);
        dpDateEnd.setValue(null);
    }

    /**
     * This method allows to see users' table view by interacting with login
     * view.
     */
    @Test
    public void testA_NavigateToVEventTable() {
        clickOn("#txtLogin");
        write("admin");
        clickOn("#txtPassword");
        write("password");
        clickOn("#btnSignIn");
        verifyThat("#pAdmin", isVisible());
        clickOn("n de Datos");
        clickOn("Ver Eventos");
        verifyThat("#pEventTable", isVisible());
    }

    @Test
    public void testB_VEventTableInitialState() {
        //Verificacion de elementos superiores
        //  Labels indicativos
        verifyThat("#lblName", isVisible());
        verifyThat("#lblDateStart", isVisible());
        verifyThat("#lblDateEnd", isVisible());
        verifyThat("#lblDescription", isVisible());
        //  Labels de error
        //verifyThat("#lblDateStartEr", isVisible());
        //verifyThat("#lblDateEndEr", isVisible());
        //  Campos "rellenablesw"
        verifyThat("#txtName", hasText(""));
        verifyThat("#taDescription", hasText(""));
        verifyThat("#dpDateStart", hasText(""));
        verifyThat("#dpDateEnd", hasText(""));
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

    @Test
    public void testC_CreateButtonEnable() {
        doubleClickOn(txtName);
        write(name);
        verifyThat("#btCrear", isDisabled());
        doubleClickOn(taDescription);
        write("anyDescription");
        verifyThat("#btCrear", isDisabled());
        doubleClickOn(dpDateStart);
        write("10/01/2000");
        verifyThat("#btCrear", isDisabled());
        doubleClickOn(dpDateEnd);
        write("10/01/2000");
        verifyThat("#btCrear", isEnabled());
        limpiarCampos();
    }

    @Test
    public void testD_CreateWrongDates() {
        int rowCount = tbEvent.getItems().size();
        doubleClickOn(txtName);
        write(name);
        verifyThat("#btCrear", isDisabled());
        doubleClickOn(taDescription);
        write("anyDescription");
        verifyThat("#btCrear", isDisabled());
        doubleClickOn(dpDateStart);
        write("10/01/2020");
        verifyThat("#btCrear", isDisabled());
        doubleClickOn(dpDateEnd);
        write("10/01/2000");
        verifyThat("#btCrear", isEnabled());
        clickOn(btnNew);
        verifyThat(".alert", isVisible());
        assertEquals("Ha creado el evento", rowCount, tbEvent.getItems().size());
        List<Evento> users = tbEvent.getItems();
        assertEquals("Ha creado el evento",
                users.stream().filter(u -> u.getName().equals(name)).count(), 0);
        limpiarCampos();
    }

    @Test
    public void testE_CreateEventSuccessfull() {
        int rowCount = tbEvent.getItems().size();
        doubleClickOn(txtName);
        name = "Evento de test";
        write(name);
        verifyThat("#btCrear", isDisabled());
        doubleClickOn(taDescription);
        write("Descripcion del Evento de Prueba");
        verifyThat("#btCrear", isDisabled());
        doubleClickOn(dpDateStart);
        write("10/01/2000");
        verifyThat("#btCrear", isDisabled());
        doubleClickOn(dpDateEnd);
        write("10/01/2020");
        verifyThat("#btCrear", isEnabled());
        clickOn(btnNew);
        assertEquals("Error al crear evento", rowCount + 1, tbEvent.getItems().size());
        List<Evento> users = tbEvent.getItems();
        assertEquals("Error al crear evento",
                users.stream().filter(u -> u.getName().equals(name)).count(), 1);
        limpiarCampos();
    }

    @Test
    public void testF_tableSelect_Deselect() {
        //Seleccionando un elemento de la tabla
        int rowCount = tbEvent.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        Evento event = (Evento) tbEvent.getSelectionModel().getSelectedItem();
        //Carga de datos de evento seleccionado en campos superiores
        verifyThat("#clDateStart", hasText(event.getDateStart()));
        verifyThat("#clDateEnd", hasText(event.getDateEnd()));
        verifyThat("#clName", hasText(event.getName()));
        verifyThat("#clDescription", hasText(event.getDescription()));
        //Deseleccionarelemento de la tabla
        press(KeyCode.CONTROL);
        clickOn(row);
        release(KeyCode.CONTROL);
        //Vaciado de campos superiores
        verifyThat("#txtName", hasText(""));
        verifyThat("#taDescription", hasText(""));
        verifyThat("#dpDateStart", hasText(""));
        verifyThat("#dpDateEnd", hasText(""));
    }

    @Test
    public void testG_DeleteEventCancel() {
        int rowCount = tbEvent.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        clickOn(btnDelete);
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Cancelar");
        assertEquals("Se ha borrado el Evento", rowCount, tbEvent.getItems().size());
        limpiarCampos();
    }

    @Test
    public void testH_DeleteEventSuccessfull() {
        int rowCount = tbEvent.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        clickOn(btnDelete);
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Aceptar");
        assertEquals("Se ha borrado el Evento", rowCount - 1, tbEvent.getItems().size());
        limpiarCampos();
    }

    @Test
    public void testI_ModifyEventFormFailure() {
        int rowCount = tbEvent.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        Evento event = (Evento) tbEvent.getSelectionModel().getSelectedItem();
        clickOn(dpDateEnd);
        write("01/01/2000");
        clickOn(btnSave);
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Aceptar");
        assertEquals("Se ha modificado el Evento", event, tbEvent.getSelectionModel().getSelectedItem());
        limpiarCampos();
    }

    @Test
    public void testJ_ModifyEventFormSuccessfull() {
        int rowCount = tbEvent.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        Evento event = (Evento) tbEvent.getSelectionModel().getSelectedItem();
        clickOn(dpDateEnd);
        write("10/01/2000");
        clickOn(btnSave);
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Aceptar");
        assertNotEquals("Se ha modificado el Evento", event, tbEvent.getSelectionModel().getSelectedItem());
        limpiarCampos();
    }

    @Test
    public void testK_ModifyEventTableFailure() {
        int rowCount = tbEvent.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        Evento event = (Evento) tbEvent.getSelectionModel().getSelectedItem();
        tbEvent.getSelectionModel().select(rowCount-1, clDateEnd);
        write("01/01/2000");
        press(KeyCode.ENTER);
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Aceptar");
        assertEquals("Se ha modificado el Evento", event, tbEvent.getSelectionModel().getSelectedItem());
        limpiarCampos();
    }

    @Test
    public void testL_ModifyEventTableSuccessfull() {
        int rowCount = tbEvent.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        Evento event = (Evento) tbEvent.getSelectionModel().getSelectedItem();
        tbEvent.getSelectionModel().select(rowCount-1, clDateEnd);
        write("10/01/2000");
        press(KeyCode.ENTER);
        clickOn(btnSave);
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Aceptar");
        assertNotEquals("Se ha modificado el Evento", event, tbEvent.getSelectionModel().getSelectedItem());
        limpiarCampos();
    }

}
