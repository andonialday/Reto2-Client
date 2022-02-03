/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;

import java.time.format.DateTimeFormatter;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
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
import reto2g1cclient.model.Commercial;

/**
 *
 * @author Enaitz Izagirre
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VCommercialTest extends ApplicationTest {

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
    private TextField txtLoginC;
    private TextField txtEmail;
    private ComboBox cbEspecialization;
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
    private TableView tbCommercial;
    private TableColumn colName;
    private TableColumn colLogin;
    private TableColumn colEmail;
    private TableColumn colEspecialization;

    private static final String user = "admin";
    private static final String password = "Abcd*1234";

    public VCommercialTest() {
    }

    /**
     * Starts application to be tested.
     *
     * @param stage Primary Stage object
     * @throws Exception If there is any error
     */
    @Test
    public void start(Stage stage) throws Exception {

        new ClientApplication().start(stage);
        txtName = lookup("#tfName").query();
        txtLoginC = lookup("#tfLogin").query();
        txtEmail = lookup("#tfEmail").query();
        cbEspecialization = lookup("#cbEspecialization").queryComboBox();
        cbSearch = lookup("#cbSearchBy").queryComboBox();
        txtSearch = lookup("#txtSearch").query();
        btnSearch = lookup("#btnSearch").query();
        btnPrint = lookup("#btnPrint").query();
        btnNew = lookup("#btnNew").query();
        btnSave = lookup("#btnSave").query();
        btnDelete = lookup("#btnDelete").query();
        btnBack = lookup("#btnBack").query();

    }

    public void limpiarCampos() {
        //limpia campos
        txtName.clear();
        txtLoginC.clear();
        txtEmail.clear();
        //campo default
        clickOn("#cbEspecialization");
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.ENTER).release(KeyCode.ENTER);

    }

    @Test
    public void testA_NavigateToCommercialTable() {
        clickOn("#txtLogin");
        write(user);
        clickOn("#txtPassword");
        write(password);
        clickOn("#btnSignIn");
        sleep(2000);
        //verifyThat("#pAdmin", isVisible());
        clickOn("#mData");
        clickOn("#miCommercial");
        verifyThat("#pCommercialTable", isVisible());
    }

    //works
    @Ignore
    @Test
    public void testB_VCommercialTableInitialState() {
        //Verificacion de elementos superiores
        //  Labels indicativos
        verifyThat("#lbName", isVisible());
        verifyThat("#lbLogin", isVisible());
        verifyThat("#lbEmail", isVisible());
        verifyThat("#lbPassword", isVisible());
        verifyThat("#lbConfirmPassword", isVisible());
        verifyThat("#lbEspecialization", isVisible());
        //  Labels de error
        //  Campos "rellenablesw"
        verifyThat("#tfName", hasText(""));
        verifyThat("#tfLogin", hasText(""));
        verifyThat("#tfEmail", hasText(""));
        verifyThat("#tfPassword", hasText(""));
        verifyThat("#tfConfirmPassword", hasText(""));
        //Verificacion de Botones
        verifyThat("#btnNew", isDisabled());
        verifyThat("#btnSave", isDisabled());
        verifyThat("#btnDelete", isDisabled());
        verifyThat("#btnBack", isEnabled());
        verifyThat("#btnPrint", isEnabled());
        //Verificacion seccion busqueda
        verifyThat("#txtFilter", hasText(""));
        verifyThat("#btnSearch", isEnabled());
        verifyThat("#cbSearchBy", isEnabled());
        //Verificacion Tabla
        verifyThat("#tbCommercial", isVisible());
        verifyThat("#colName", isVisible());
        verifyThat("#colLogin", isVisible());
        verifyThat("#colEmail", isVisible());
        verifyThat("#colEspecialization", isVisible());
    }

    /**
     * Test that the client confirm password entered is not correct. An error
     * label will appear indicating it.
     *
     * @throws java.lang.InterruptedException
     */
    @Ignore
    @Test
    public void testC_NewCommercialAdded() throws InterruptedException {

        clickOn("#tfName");
        write("username");

        clickOn("#tfEmail");
        write("email@gmail.com");

        clickOn("#tfLogin");
        write("login");

        clickOn("#tfPassword");
        write("abcd12345");

        clickOn("#tfConfirmPassword");
        write("abcd*12345");

        clickOn("#cbEspecialization");
        clickOn("SONIDO"); //Escoger una opción del Combo Box

        verifyThat("#btnNew", isEnabled());
        clickOn("#btnNew");

        press(KeyCode.ENTER).release(KeyCode.ENTER);
        //Comprobar que el cliente se ha añadido correctamente a la tabla
    }

    /**
     * Test that the client email entered is not correct. An error label will
     * appear indicating it.
     *
     * @throws java.lang.InterruptedException
     */
    @Ignore
    @Test
    public void testD_IncorrectAdd() throws InterruptedException {

        clickOn("#tfName");
        write("username");

        clickOn("#tfEmail");
        write("el email introducido debe ser un email válido");

        clickOn("#tfLogin");
        write("login");
        clickOn("#tfPassword");
        write("Abcd*12345");
        clickOn("#tfConfirmPassword");
        write("Abcd*12345");
        clickOn("#cbEspecialization");
        clickOn("SONIDO"); //Escoger una opción del Combo Box

        verifyThat("#btnNew", isEnabled());
        clickOn("#btnNew");

        verifyThat("#lblErrorEmail", isEnabled());
    }

    //works
    @Ignore
    @Test
    public void testE_tableSelect_Deselect() {
        limpiarCampos();
        //Seleccionando un elemento de la tabla
        int rowCount = tbCommercial.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        Commercial commerical = (Commercial) tbCommercial.getSelectionModel().getSelectedItem();
        //Carga de datos de evento seleccionado en campos superiores
        verifyThat("#tfName", hasText(commerical.getFullName()));
        verifyThat("#tfLogin", hasText(commerical.getLogin()));
        verifyThat("#tfEmail", hasText(commerical.getEmail()));
        //Deseleccionarelemento de la tabla
        press(KeyCode.CONTROL);
        clickOn(row);
        release(KeyCode.CONTROL);
        //Vaciado de campos superiores
        verifyThat("#tfName", hasText(""));
        verifyThat("#tfLogin", hasText(""));
        verifyThat("#tfEmail", hasText(""));

        limpiarCampos();
    }

    @Ignore
    @Test
    public void testF_Delete() {
        int rowCount = tbCommercial.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);

        verifyThat("#btnDelete", isEnabled());
        clickOn("#btnDelete");
        press(KeyCode.ENTER).release(KeyCode.ENTER);
    }

    @Ignore
    @Test
    public void testG_DeleteCancel() {
        limpiarCampos();
        int rowCount = tbCommercial.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        clickOn("#btnDelete");
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Cancelar");
        
    }

    @Ignore
    @Test
    public void testH_Modify() {
        int rowCount = tbCommercial.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        
        verifyThat("#btnSave", isEnabled());
        
        clickOn("#tfName");
        write("modify");
        
        clickOn("#btnSave");
        press(KeyCode.ENTER).release(KeyCode.ENTER);
	verifyThat("#btnSave", isDisabled());
    }

    @Ignore
    @Test
    public void testH_ModifyCancel() {
        int rowCount = tbCommercial.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        
        verifyThat("#btnSave", isEnabled());
        
        clickOn("#tfName");
        write("modify");
        
        clickOn("#btnSave");
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Cancelar");
	verifyThat("#btnSave", isDisabled());
    }

    @Ignore
    @Test
    public void testI_Print() {
        clickOn("#btnPrint");
        press(KeyCode.ENTER).release(KeyCode.ENTER);
    }

    @Ignore
    @Test
    public void testJ_Back() throws InterruptedException {

        clickOn("#btnBack");
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        verifyThat("#VAdmin", isVisible());
    }

    @Ignore
    @Test
    public void testK_BackCancel() throws InterruptedException {

        clickOn("#btnBack");
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Cancelar");
    }

}
