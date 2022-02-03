/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;

import static groovy.xml.dom.DOMCategory.name;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
 * Clase para verificar 
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
    
    private static final String DESCRIPCION = "Altavoz de ceramica hecho en china a 1000º para exteriores TESES";
    
    
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
   /**
    * metodo para "cargar" los componentes de la ventana VEquipmentTable y el 
    * robot los pueda usar y reconocer
    */
    public void analizaComponentesEquipamiento()  {
       
        
        tfName = lookup("#tfName").query();
        tfCost = lookup("#tfCost").query();
        tfFinding = lookup("#tfFinding").query();
        taDescription = lookup("#taDescription").query();
        tbEquipment = lookup("#tbEquipment").query();
        dpDate = lookup("#dpDate").query();
        btnBack = lookup("#btnBack").query();
        btnSaveEquip = lookup("#btnSaveEquip").query();
        btnCrearEquip = lookup("#btnCrearEquip").query();
        btnDeleteEquip = lookup("#btnDeleteEquip").query();
        btnFind = lookup("#btnFind").query();
        btnPrint = lookup("#btnPrint").query();
        
        cbSearch = lookup("#cbSearch").query();
        

    }

   

  
    
    
    /**
     * Metodo para limpiar la seleccion de la tabla.
     */
    public void limpiarSeleccionDeFila() {
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
     *  Metodo pra recorrer cuantos elementos hay en la tabla para verificar que 
     * se han añadido o no una fila mas
     * @param rowCount contador de filas
     * @return la cantidad de filas
     */
   public int comprobarCreaciones(int rowCount){
       List<Equipment> equipments = tbEquipment.getItems();
        int count = 0;
        for (Equipment equipment : equipments) {
          if(equipment.getDescription().equals(DESCRIPCION + rowCount) ){
              count ++;
          }  
            
        }
        return count;
   }

    /**
     * Metodo para registrarse y acceder a la ventana de equipamiento
     */
    @Test
    public void testA_NavigateToVEquipmentTable() {
        analizaComponentesEquipamiento();
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
    /**
     * Test para comprobar el estado inicial de la ventana de equipamiento
     */
     @Test
    public void testB_VEventTableInitialState() {
        analizaComponentesEquipamiento();
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
    
    

    

    /**
     * Metodo para habilitar boton de crear equipamiento y verificar si se habilita
     * y si se crea un equipamiento y se añade a la tabla
     */
    @Test
    public void testC_HabilitarBotonNuevoEquipamientoyCreacion() {
        analizaComponentesEquipamiento();
        vaciarCampos();
        int rowCount = tbEquipment.getItems().size();
        clickOn("#tfName");
        write("Altavoz philips TESTEADO");

        clickOn("#tfCost");
        write("100");
        clickOn("#dpDate");
        dpDate.setValue(LocalDate.parse(("06/06/2021"), formatter));

        press(KeyCode.ENTER);
        release(KeyCode.ENTER);
        clickOn("#taDescription");
        write(DESCRIPCION + rowCount);

        verifyThat("#btnCrearEquip", isEnabled());
         clickOn("#btnCrearEquip");     
         int count = comprobarCreaciones(rowCount);
        assertEquals("Error al crear Equipamiento",  rowCount + 1, tbEquipment.getItems().size());
        
        assertEquals("Error al crear Equipamiento", 1,count);
    }
  
    /**
     * Metodo para verificar que introduciendo datos no validos no se crea un 
     * equipamiento nuevo
     */
    @Test
    public void testD_VerificacionCrearEquipamientoFail() {
        
      analizaComponentesEquipamiento();
      vaciarCampos();
       int rowCount = tbEquipment.getItems().size();
        clickOn("#tfName");
        write("Altavoz");

        clickOn("#tfCost");
        write("100000000");
        clickOn("#dpDate");
        dpDate.setValue(LocalDate.parse(("01/02/2020"), formatter));

        press(KeyCode.ENTER);
        release(KeyCode.ENTER);
        clickOn("#taDescription");
        write(DESCRIPCION);

        verifyThat("#btnCrearEquip", isEnabled());
         clickOn("#btnCrearEquip");
         verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Aceptar");
        
         assertEquals("Se a creado equipamiento",  rowCount , tbEquipment.getItems().size());
        int count = comprobarCreaciones(rowCount);
        assertEquals("Se a creado equipamiento", 0,count);
        limpiarSeleccionDeFila();
        vaciarCampos();
        
    }
     /**
      * Test para visualizar los campos de la tabla en la parte superior de la ventana 
      * cuando seleccionamos una fila
      */
   
    @Test
    public void testE_tableSelect_Deselect() {
        vaciarCampos();
         analizaComponentesEquipamiento();
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
        sleep(1000);
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
    
    /**
     * Metodo para modificar el coste de un equipamiento
     * seleccionado en la tabla de manera erronea verificando la aparicion
     * de una alerta
     */
    @Test
    public void testF_ModifyEquipmentFormFail() {
        vaciarCampos();
         analizaComponentesEquipamiento();
        int rowCount = tbEquipment.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        Equipment equipment = (Equipment) tbEquipment.getSelectionModel().getSelectedItem();
               String click = equipment.getCost();
        Set<Node> list = lookup(click).queryAll();
        Node cell = null;
        for (Iterator<Node> it = list.iterator(); it.hasNext();) {
            cell = it.next();
        }
        doubleClickOn(cell);
        
        
        write("03/02/2066");
       clickOn("#btnSaveEquip");
        
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Aceptar");
        assertEquals("Equipamiento modificado", equipment, tbEquipment.getSelectionModel().getSelectedItem());
        limpiarSeleccionDeFila();
    }
     
    /**
      * Metodo para modificar la fecha de compra de un equipamiento
     * seleccionado en la tabla de manera valida verificando que la fecha
     * introducida es igual a la fecha que se ha guardado
     */
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
        assertEquals("No se ha modificado el Evento", equipment.getDateAdd(), "02/09/1999");
        vaciarCampos();
        limpiarSeleccionDeFila();
        vaciarCampos();
    }
       
    
    /**
     * Metodo para verificar que se puede cancelar el borrado de un equipamiento
     */
    @Test
    public void testH_DeleteEventCancel() {
        vaciarCampos();
        int rowCount = tbEquipment.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        clickOn("#btnDeleteEquip");
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Cancelar");
        assertEquals("NO Se ha borrado el Equipamiento", rowCount, tbEquipment.getItems().size());
        analizaComponentesEquipamiento();
           vaciarCampos();
    }

    
    /**
     * Metodo para verificar que se puede eliminar un equipamiento seleccionado
     * en la tabla
     */
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
        analizaComponentesEquipamiento();
           
    }
    
    /**
     * Metodo para verificar que se puede editar con dobleclick la fecha de compra
     * en la tabla editable y añadiendo datos erroneos lanza una alerta y no se
     * varia el dato.
     */
    @Test
    public void testJ_ModificarEnTablaEditableSuccessfull(){
          vaciarCampos();
       
        int rowCount = tbEquipment.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        tbEquipment.getSelectionModel().select(rowCount - 1, clDate);
        Equipment equipment = (Equipment) tbEquipment.getSelectionModel().getSelectedItem();
        String click = equipment.getDateAdd();
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
        write("drgredgd");
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Aceptar");
      
        assertNotEquals("Se se ha modificado el Equipamiento", equipment.getDateAdd(),"drgredgd");
        
        vaciarCampos();
        limpiarSeleccionDeFila();
    }

    /**
     * Metodo para verificar que se puede editar con dobleclick la fecha de compra
     * en la tabla editable y añadiendo datos correctos se verifica que la fecha se ha cambiado
     */
    @Test
     public void testK_ModifyEventTableSuccessfull() {
        vaciarCampos();
       
        int rowCount = tbEquipment.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        tbEquipment.getSelectionModel().select(rowCount - 1, clDate);
        Equipment equipment = (Equipment) tbEquipment.getSelectionModel().getSelectedItem();
        String click = equipment.getDateAdd();
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
        write("02/01/2021");
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);
        equipment = (Equipment) tbEquipment.getSelectionModel().getSelectedItem();
        assertEquals("No se ha modificado el E", equipment.getDateAdd(), "02/01/2021");
        vaciarCampos();
        limpiarSeleccionDeFila();
    }
     
      /**
     * Metodo que comprueba que a la hora de filtrar en el caso de no escribir 
     * nada mostrara topdos los equipamientos
     */
    @Test
     public void testL_filtrarVacio(){
          analizaComponentesEquipamiento();
        vaciarCampos();
        List<Equipment> equipments = tbEquipment.getItems();
        clickOn("#btnFind");
        sleep(250);
        List<Equipment> equipments2 = tbEquipment.getItems();
        
         
        assertEquals("Se han filtrado todos los equipamientos", equipments.size(),equipments2.size());
        vaciarCampos();
     }

    /**
     * Metodo que comprueba que a la hora de filtrar en el caso de escribir un nombre,
     * ya que, de manera preseleccionada el primer filtro es el nombre, verifica
     * que solo aparecen los equipamientos con ese nombre
     * 
     */ 
    @Test
     public void testM_filtrarNombre(){
          analizaComponentesEquipamiento();
        vaciarCampos();
        testL_filtrarVacio();
        List<Equipment> equipments = tbEquipment.getItems();
        clickOn("#tfFinding");
        write("altavoz");
        sleep(250);
       
         clickOn("#btnFind");
          List<Equipment> equipments2 = tbEquipment.getItems();
         
        assertNotEquals("No se ha filtrado el equipamiento", equipments.size(),equipments2.size());
        vaciarCampos();
     }
      
     
    
    /**
     * Metodo para comprobar que el boton de imprimir funciona
     */
    @Test
    public void testN_printReport() {
        clickOn("#btnPrint");
        //verifyThat();
    }

    /**
     * Metodo para volver a la ventana de admin
     * @throws IOException en el caso de haber un error
     */
    @Test
     public void testO_VolverVentanaAdmin() throws IOException {
        // Comprobacion de Atras -> Cancelar -> Mantener en VEventTable
        clickOn("#btnBack");
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Cancelar");
        verifyThat("#pEquipment", isVisible());
        // Comprobacion de Atras -> Aceptar -> Retorno a VAdmin
        clickOn("#btnBack");
        verifyThat(".alert", NodeMatchers.isVisible());
        clickOn("Aceptar");
        verifyThat("#pAdmin", isVisible());
        // Retorno a VEventTable mediante menu de navegacion
        clickOn("#mData");
        clickOn("#miEquipment");
        verifyThat("#pEquipment", isVisible());
    }

    /**
     * Metodo para volver a la ventana de equipamiento
     * @throws IOException en el caso de haber un error
     */
    @Test
     public void testP_NavigateToEquipmentTable() throws IOException {
       
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
     * Metodo para vaciar campos
     */
    public void vaciarCampos() {
        analizaComponentesEquipamiento();
        tfName.clear();
          tfCost.clear();
        dpDate.setValue(null);
        taDescription.clear();
        tfFinding.clear();

    }
    

}
