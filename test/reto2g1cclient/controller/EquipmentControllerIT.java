/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeoutException;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import reto2g1cclient.application.ClientApplication;
import reto2g1cclient.model.Equipment;

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
        btnDeleteEquip = lookup("#btnDeleteEquip").query();
        btnFind = lookup("#btnFind").query();
        btnPrint = lookup("btnPrint").query();
        btnSaveEquip = lookup("btnSaveEquip").query();
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
        verifyThat("#pEquipmentTable", isVisible());
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
        /*  int rowCount=tbEquipment.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                        rowCount,0);*/
    }
    

    public void vaciarCampos() {
        lookEquipment();
        tfName.clear();
          tfCost.clear();
        dpDate.setValue(null);
        taDescription.clear();

    }

}
