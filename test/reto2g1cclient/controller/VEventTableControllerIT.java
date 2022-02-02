/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;

import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import reto2g1cclient.application.ClientApplication;
import reto2g1cclient.model.Evento;

/**
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

    private static final String user = "admin";
    private static final String password = "Abcd*1234";
    private static final String fechaStart = "10/01/2000";
    private static final String fechaEndOK = "10/01/2020";
    private static final String fechaEndFail = "10/01/1000";
    private static final String name = "Evento de test";
    private static final String desc = "Descripcion del Evento de Prueba";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public VEventTableControllerIT() {
    }

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
        //txtLogin = lookup("#txtLogin").query();
        //txtPassword = lookup("#txtPassword").query();
        //btnSignIn = lookup("#btnSignIn").query();
        //"lookup" de los nodos de la ventana VEventTable 
        new ClientApplication().start(stage);
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

    /*
    protected static TableCell<?, ?> localizarCelda(String tableSelector, int row, int column) {
      List<Node> current = row(tableSelector, row).getChildrenUnmodifiable();
      while (current.size() == 1 && !(current.get(0) instanceof TableCell)) {
          current = ((Parent) current.get(0)).getChildrenUnmodifiable();
      }

      Node node = current.get(column);
      if (node instanceof TableCell) {
          return (TableCell<?, ?>) node;
      }
      else {
          throw new RuntimeException("Expected TableRowSkin with only TableCells as children");
      }*/
    /**
     * This method allows to see users' table view by interacting with login
     * view.
     */
    @Test
    public void testA_NavigateToVEventTable() {
        clickOn("#txtLogin");
        write(user);
        clickOn("#txtPassword");
        write(password);
        clickOn("#btnSignIn");
        sleep(2000);
        //verifyThat("#pAdmin", isVisible());
        clickOn("#mData");
        clickOn("#miEvent");
        verifyThat("#pEventTable", isVisible());
    }

    //works
    @Ignore
    @Test
    public void testB_VEventTableInitialState() {
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

    //works
    @Ignore
    @Test
    public void testC_CreateButtonEnable() {
        limpiarCampos();
        clickOn("#txtName");
        write(name);
        verifyThat("#btnNew", isDisabled());
        clickOn("#dpDateStart");
        write(fechaStart);
        verifyThat("#btnNew", isDisabled());
        clickOn("#dpDateEnd");
        write("10/01/2000");
        verifyThat("#btnNew", isDisabled());
        clickOn("#taDescription");
        write(desc);
        verifyThat(btnNew, isEnabled());
        limpiarCampos();
    }

    //works
    @Ignore
    @Test
    public void testD_CreateWrongDates() {
        limpiarCampos();
        int rowCount = tbEvent.getItems().size();
        clickOn("#txtName");
        write(name);
        clickOn("#dpDateStart");
        write("10/01/2020");
        clickOn("#dpDateEnd");
        write("10/01/2000");
        clickOn("#taDescription");
        write(desc);
        clickOn("#btnNew");
        verifyThat(".alert", isVisible());
        assertEquals("Ha creado el evento", rowCount, tbEvent.getItems().size());
        List<Evento> users = tbEvent.getItems();
        assertEquals("Ha creado el evento",
                users.stream().filter(u -> u.getName().equals(name)).count(), 0);
        limpiarCampos();
    }

    //works
    @Ignore
    @Test
    public void testE_CreateEventSuccessfull() {
        limpiarCampos();
        int rowCount = tbEvent.getItems().size();
        clickOn("#txtName");
        write(name);
        clickOn("#taDescription");
        write(desc);
        clickOn("#dpDateStart");
        write("10/01/2000");
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);
        clickOn("#dpDateEnd");
        write("10/01/2020");
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);
        clickOn("#btnNew");
        assertEquals("Error al crear evento", rowCount + 1, tbEvent.getItems().size());
        List<Evento> users = tbEvent.getItems();
        assertEquals("Error al crear evento",
                users.stream().filter(u -> u.getName().equals(name)).count(), 1);
        limpiarCampos();
    }

    //works
    @Ignore
    @Test
    public void testF_tableSelect_Deselect() {
        limpiarCampos();
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
        //Deseleccionarelemento de la tabla
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

    //works
    @Ignore
    @Test
    public void testG_ModifyEventFormFailure() {
        limpiarCampos();
        int rowCount = tbEvent.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        Evento event = (Evento) tbEvent.getSelectionModel().getSelectedItem();
        clickOn("#dpDateEnd");
        dpDateEnd.setValue(null);
        write("01/01/2000");
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);
        clickOn(btnSave);
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Aceptar");
        assertEquals("Se ha modificado el Evento", event, tbEvent.getSelectionModel().getSelectedItem());
    }

    //works
    @Ignore
    @Test
    public void testH_ModifyEventFormSuccessfull() {
        limpiarCampos();
        int rowCount = tbEvent.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        Evento event = (Evento) tbEvent.getSelectionModel().getSelectedItem();
        clickOn(dpDateEnd);
        dpDateEnd.setValue(null);
        write("10/01/2000");
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);
        clickOn(btnSave);
        assertEquals("Se ha modificado el Evento", event.getDateEnd(), "10/01/2000");
        limpiarCampos();
    }

    //works
    @Ignore
    @Test
    public void testI_ModifyEventTableFailure() {
        limpiarCampos();
        int rowCount = tbEvent.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
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
        write("01/01/1000");
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Aceptar");
        assertEquals("Se ha modificado el Evento", event, tbEvent.getSelectionModel().getSelectedItem());
        limpiarCampos();
    }

    //works
    @Ignore
    @Test
    public void testJ_ModifyEventTableSuccessfull() {
        limpiarCampos();
        int rowCount = tbEvent.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        Evento event = (Evento) tbEvent.getSelectionModel().getSelectedItem();
        tbEvent.getSelectionModel().select(rowCount - 1, clDateEnd);
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
        write("10/01/2000");
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);
        clickOn("#btnSave");
        assertNotEquals("No se ha modificado el Evento", event, tbEvent.getSelectionModel().getSelectedItem());
        limpiarCampos();
    }

    //works
    @Ignore
    @Test
    public void testK_DeleteEventCancel() {
        limpiarCampos();
        int rowCount = tbEvent.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        clickOn("#btnDelete");
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Cancelar");
        assertEquals("Se ha borrado el Evento", rowCount, tbEvent.getItems().size());
    }

    //works
    @Ignore
    @Test
    public void testL_DeleteEventSuccessfull() {
        limpiarCampos();
        int rowCount = tbEvent.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        clickOn("#btnDelete");
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Aceptar");
        assertEquals("Se ha borrado el Evento", rowCount - 1, tbEvent.getItems().size());
    }

    @Ignore
    @Test
    public void testM_printReport() {
        clickOn("#btnPrint");
        //verifyThat();
    }

}
