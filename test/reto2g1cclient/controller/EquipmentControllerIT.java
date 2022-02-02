/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.TemporalQueries.zone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import reto2g1cclient.application.TestAplicationEquipment;
import reto2g1cclient.model.Equipment;

/**
 *
 * @author Aitor Perez
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EquipmentControllerIT extends ApplicationTest{
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
     * Starts application to be tested.
     * @param stage Primary Stage object
     * @throws Exception If there is any error
     */
    @Override
    public void start(Stage stage) throws Exception {
         //txtLogin = lookup("#txtLogin").query();
         new TestAplicationEquipment().start(stage);
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
        // clCost = lookup("#clCost").queryAll(Equipment, String);
        // clDate = lookup("#clDate").query();
         
         
       
    }
    public EquipmentControllerIT() {
        
    
    }
    /*
    -Test para comprobar si el servidor esta apagado notifica al usuario
    
    -Test comprobar si el boton de nuevo equipamiento se habilita y crea equipamiento
    -Test comprobar si el boton de nuevo equipamiento se  NOO se habilita y NOO crea equipamiento y lanza excepcion
    
    
    
    
    
    */
    @Ignore
    @Test
    public void testA_NavigateToVEquipmentTable() {
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
    /*Test para verificar activacion de boton y que se crean */
    @Test
    public void testB_HabilitarBotonNuevoEquipamiento(){
        clickOn("#tfName");
        write("Altavoz");
        
        clickOn("#tfCost");
        write("100");
        clickOn("#dpDate");
        dpDate.setValue(LocalDate.parse(("01/02/2020"),formatter));
        
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
    public void vaciarCampos(){
        tfName.clear();
      /*  tfCost.clear();
        dpDate.setValue(null);
        taDescription.clear();*/
        
    }
    
}
