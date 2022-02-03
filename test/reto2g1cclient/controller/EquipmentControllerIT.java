/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;

import static groovy.xml.dom.DOMCategory.name;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import static org.hibernate.criterion.Projections.rowCount;
import org.junit.BeforeClass;
import org.testfx.api.FxToolkit;
import reto2g1cclient.model.Equipment;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import static org.junit.Assert.assertEquals;
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
 * @author Aitor Perez
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EquipmentControllerIT extends ApplicationTest {

    /*   private TextField txtLogin;
    private PasswordField txtPassword;
    private Button btnSignIn;*/

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private TableView<Equipment> tbEquipment;

    private TableColumn<Equipment, String> clName;

    private TableColumn<Equipment, String> clCost;

    private TableColumn<Equipment, String> clDescription;

    private TableColumn<Equipment, String> clDate;

    private Button btnBack;

    private Button btnDeleteEquip;

    private Button btnSaveEquip;

    private Button btnFind;

    private ComboBox cbSearch;

    private TextField tfFinding;

    private TextField tfName; //= lookup("#tfName").query();

    private TextField tfCost;

    private DatePicker dpDate;

    private TextArea taDescription;

    private Button btnCrearEquip;

    private Button btnPrint;

    private Label lblNameEquipment;

    private Label lblName;

    private Label lblCost;

    private Label lblDescription;

    private Label lblBuyData;

    private Label lblWarninNumValue;

    private Label lblWarningDate;
    
    
    
    /**
     * Inicia el main del cliente para poder ejecutar los teses.
     *
     * @throws TimeoutException Excepcion en caso de que java no responda en un
     * tiempo determinado
     */
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(ClientApplication.class);
    }
    
    /**
     * Starts application to be tested.
     *
     * @param stage Primary Stage object
     * @throws Exception If there is any error
     */
   
    public void lookEquipment()  {
        //txtLogin = lookup("#txtLogin").query();
        
        tfName = lookup("#tfName").query();
        tfCost = lookup("#tfCost").query();
        tfFinding = lookup("#tfFinding").query();
        taDescription = lookup("#taDescription").query();
        dpDate = lookup("#dpDate").query();
        btnBack = lookup("#btnBack").query();
        btnCrearEquip = lookup("#btnCrearEquip").query();
        btnCrearEquip = lookup("#btnCrearEquip").query();
        btnDeleteEquip = lookup("#btnDeleteEquip").query();
        btnFind = lookup("#btnFind").query();
        btnPrint = lookup("#btnPrint").query();
        btnSaveEquip = lookup("#btnSaveEquip").query();
        cbSearch = lookup("#cbSearch").query();
        

    }

    public EquipmentControllerIT() {

    }

    /*
    -Test para comprobar si el servidor esta apagado notifica al usuario
    
    -Test comprobar si el boton de nuevo equipamiento se habilita y crea equipamiento
    -Test comprobar si el boton de nuevo equipamiento se  NOO se habilita y NOO crea equipamiento y lanza excepcion
    
    
    
   
    
     */
   
    @Test
    public void testA_NavigateToVEquipmentTable() {
        lookEquipment();
        clickOn("#txtLogin");
        write("admin");
        clickOn("#txtPassword");
        write("Abcd*1234");
        clickOn("#btnSignIn");
        sleep(2000);
       // verifyThat("#pAdmin", isVisible());
        clickOn("#mData");
        clickOn("#miEquipment");
        verifyThat("#pEquipment", isVisible());
    }
    
     @Test
    public void testB_VEventTableInitialState() {
        lookEquipment();
        //Verificacion de elementos superiores
        //  Labels indicativos
        verifyThat("#lblName", isVisible());
        verifyThat("#lblCost", isVisible());
        verifyThat("#lblDescription", isVisible());
        verifyThat("#lblBuyData", isVisible());
         //  Labels de error
        //  Campos "rellenables"
        verifyThat("#tfName", hasText(""));
        verifyThat("#tfCost", hasText(""));
        verifyThat("#taDescription", hasText(""));
        verifyThat("#dpDate", isEnabled());
        //Verificacion de Botones
        verifyThat("#btnCrearEquip", isDisabled());
        verifyThat("#btnSaveEquip", isDisabled());
        verifyThat("#btnDeleteEquip", isDisabled());
        verifyThat("#btnBack", isEnabled());
        verifyThat("#btnPrint", isEnabled());
      
          //Verificacion seccion busqueda
        verifyThat("#tfFinding", hasText(""));
        verifyThat("#btnFind", isEnabled());
        verifyThat("#cbSearch", isEnabled());
        //Verificacion Tabla
        verifyThat("#clName", isVisible());
        verifyThat("#clCost", isVisible());
        verifyThat("#clDescription", isVisible());
        verifyThat("#clDate", isVisible());
       
    }
    
    

    /*Test para verificar activacion de boton y que se crean */
    @Test
    public void testC_HabilitarBotonNuevoEquipamiento() {
        lookEquipment();
        //int rowCount = tbEquipment.getItems().size();
        clickOn("#tfName");
        write("Altavoz");

        clickOn("#tfCost");
        write("100");
        clickOn("#dpDate");
        dpDate.setValue(LocalDate.parse(("01/02/2020"), formatter));

        press(KeyCode.ENTER);
        release(KeyCode.ENTER);
        clickOn("#taDescription");
        write("el perro de sanroque no tiene rabo");

        verifyThat("#btnCrearEquip", isEnabled());
         clickOn("#btnCrearEquip");
         //Error
/*          assertEquals("Error al crear evento",  tbEquipment.getItems().size()  , tbEquipment.getItems().size());
        List<Equipment> users = tbEquipment.getItems();
        assertEquals("Error al crear evento",
                users.stream().filter(u -> u.getName().equals(tfName)).count(), 1);*/
    }
    
    @Test
    public void testD_VerificacionCrearEquipamientoFail() {
        vaciarCampos();
      lookEquipment();
       // int rowCount = tbEquipment.getItems().size();
        clickOn("#tfName");
        write("Altavoz");

        clickOn("#tfCost");
        write("100000");
        clickOn("#dpDate");
        dpDate.setValue(LocalDate.parse(("01/02/2020"), formatter));

        press(KeyCode.ENTER);
        release(KeyCode.ENTER);
        clickOn("#taDescription");
        write("el perro de sanroque no tiene rabo");

        verifyThat("#btnCrearEquip", isEnabled());
         clickOn("#btnCrearEquip");
         verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Aceptar");
         //Error
/*          assertEquals("Error al crear evento", rowCount  , tbEquipment.getItems().size());
        List<Equipment> users = tbEquipment.getItems();
        assertEquals("Error al crear evento",
                users.stream().filter(u -> u.getName().equals(tfName)).count(), 1);*/
        
      
        
    }
     
    @Test
    public void testE_tableSelect_Deselect() {
        vaciarCampos();
        //Seleccionando un elemento de la tabla
        int rowCount = tbEquipment.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        Equipment equipment = (Equipment) tbEquipment.getSelectionModel().getSelectedItem();
        //Carga de datos de equipmaiento seleccionado en campos superiores
        verifyThat("#tfName", hasText(equipment.getName()));
        verifyThat("#tfCost", hasText(equipment.getCost()));
        verifyThat("#taDescription", hasText(equipment.getDescription()));
        assertNotNull("No ha cargado la fecha de compra", dpDate.getValue());
        
        //Deseleccionarelemento de la tabla
        press(KeyCode.CONTROL);
        clickOn(row);
        release(KeyCode.CONTROL);
        //Vaciado de campos superiores
        verifyThat("#tfName", hasText(""));
        verifyThat("#tfCost", hasText(""));
        
        verifyThat("#taDescription", hasText(""));
        assertEquals("No se ha deseleccionado la fecha", dpDate.getValue(), null);
        
        vaciarCampos();
    }
    //works
    @Ignore
    @Test
    public void testF_ModifyEquipmentFormFail() {
        vaciarCampos();
        int rowCount = tbEquipment.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        Equipment equipment = (Equipment) tbEquipment.getSelectionModel().getSelectedItem();
        clickOn("#dateAdd");
        dpDate.setValue(null);
        write("03/02/2022");
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);
        clickOn("#btnSaveEquip");
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Aceptar");
        assertEquals("Equipamiento modificado", equipment, tbEquipment.getSelectionModel().getSelectedItem());
    }
     //works
    @Ignore
    @Test
    public void testG_ModifyEquipmentFormSuccessfull() {
        vaciarCampos();
        int rowCount = tbEquipment.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
      Equipment equipment = (Equipment) tbEquipment.getSelectionModel().getSelectedItem();
        clickOn(dpDate);
        dpDate.setValue(null);
        write("02/09/1999");
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);
        clickOn("#btnSaveEquip");
        assertEquals("Se ha modificado el Evento", equipment.getDateAdd(), "02/09/1999");
        vaciarCampos();
    }
        //works
    @Ignore
    @Test
    public void testH_DeleteEventCancel() {
        vaciarCampos();
        int rowCount = tbEquipment.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        clickOn("#btnDelete");
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Cancelar");
        assertEquals("NO Se ha borrado el Equipamiento", rowCount, tbEquipment.getItems().size());
    }

    //works
    @Ignore
    @Test
    public void testI_DeleteEventSuccessfull() {
        vaciarCampos();
        int rowCount = tbEquipment.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        clickOn("#btnDeleteEquip");
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Aceptar");
        assertEquals("Se ha borrado el equipamiento", rowCount - 1, tbEquipment.getItems().size());
    }
     @Ignore
    @Test
    public void testM_printReport() {
        clickOn("#btnPrint");
        //verifyThat();
    }
    public void vaciarCampos() {
        lookEquipment();
        tfName.clear();
          tfCost.clear();
        dpDate.setValue(null);
        taDescription.clear();

    }

}
