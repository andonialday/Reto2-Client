/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.concurrent.TimeoutException;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import reto2g1cclient.application.ClientApplication;

/**
 * Testing class for Client Table view and controller. 
 * Tests Client Table view behavior using TestFX framework.
 * 
 * @author Jaime San Sebastian
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientTest extends ApplicationTest {
    
    /**
     * Starts application to be tested.
     * @param stage Primary Stage object
     * @throws Exception If there is any error
     */
    @Override public void start(Stage stage) throws Exception {
       new ClientApplication().start(stage);
    }
    
    /**
     * Stops application to be tested: it does nothing.
     */
    @Override public void stop() {}
    
    /**
     * Client Java FX fixture for tests. This is a general approach for using a 
     * unique instance of the application in the test.
     * @throws java.util.concurrent.TimeoutException
     */
    @BeforeClass
    public static void clientClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(ClientApplication.class);
   }
    
    /**
     * Test of initial state of login view.
     */
    @Test
    public void test1_InitialState() {
        
        verifyThat("#tfName", hasText(""));
        verifyThat("#tfEmail",hasText(""));
        verifyThat("#tfLogin", hasText(""));
        verifyThat("#tfPassword",hasText(""));
        verifyThat("#tfConfirmPassword", hasText(""));
        
        verifyThat("#btnViewEvents", isDisabled());
        verifyThat("#btnDeleteClient", isDisabled());
        verifyThat("#btnNewClient", isDisabled());
        verifyThat("#btnSaveClient", isDisabled());
        verifyThat("#btnSearch", isDisabled());
        verifyThat("#txtFilter", isDisabled());
        
        
        verifyThat("#btnPrint", isEnabled());
        verifyThat("#btnSearch", isEnabled());
        verifyThat("#btnBack", isEnabled());
        verifyThat("#cbType", isEnabled());
        verifyThat("#cbSearchBy", isEnabled());
        
    }
    
    /**
     * Test that button Nuevo Cliente is disabled if any fields are empty.
     * 
     * @throws java.lang.InterruptedException
    */ 
    @Test
    public void test2a_ButtonNewClientIsDisabled() throws InterruptedException{
        
        clickOn("#tfName");
        write("username");
        verifyThat("#btnNewClient", isDisabled());
        eraseText(8);
        
        clickOn("#tfEmail");
        write("email@gmail.com");
        verifyThat("#btnNewClient", isDisabled());
        eraseText(5);
        
        clickOn("#tfLogin");
        write("login");
        verifyThat("#btnNewClient", isDisabled());
        eraseText(5);
        
        clickOn("#tfPassword");
        write("password");
        verifyThat("#btnNewClient", isDisabled());
        eraseText(8);
        
        clickOn("#tfConfirmPassword");
        write("password");
        verifyThat("#btnNewClient", isDisabled());
        eraseText(8);
        
        clickOn("#cbType");
        clickOn("PARTICULAR"); //Escoger una opción del Combo Box
        verifyThat("#btnNewClient", isDisabled());
        
        verifyThat("#btnNewClient", isDisabled());
    }
    
    /**
     * Test that button Nuevo Cliente is enabled when all the fields are full.
     * 
     * @throws java.lang.InterruptedException
    */ 
    @Test
    public void test2b_ButtonNewClientIsEnabled() throws InterruptedException{
        
        clickOn("#tfName");
        write("username");
        
        clickOn("#tfEmail");
        write("email@gmail.com");
        
        clickOn("#tfLogin");
        write("login");
        
        clickOn("#tfPassword");
        write("abcd*12345");
        
        clickOn("#tfConfirmPassword");
        write("abcd*12345");
        
        clickOn("#cbType");
        clickOn("PARTICULAR"); //Escoger una opción del Combo Box
        
        verifyThat("#btnNewClient", isEnabled());
    }
    
    /**
     * Test that the client name entered is not correct.
     * An error label will appear indicating it.
     * 
     * @throws java.lang.InterruptedException
    */ 
    @Test
    public void test2c_IncorrectClientName() throws InterruptedException{
        
        clickOn("#tfName");
        write("el nombre del cliente introducido es demasiado largo");
        
        clickOn("#tfEmail");
        write("email@gmail.com");
        clickOn("#tfLogin");
        write("login");
        clickOn("#tfPassword");
        write("abcd*12345");
        clickOn("#tfConfirmPassword");
        write("abcd*12345");
        clickOn("#cbType");
        clickOn("PARTICULAR"); //Escoger una opción del Combo Box
        
        verifyThat("#btnNewClient", isEnabled());
        clickOn("#btnNewClient");
        
        verifyThat("#lblErrorName", isEnabled());
    }
    
    /**
     * Test that the client email entered is not correct.
     * An error label will appear indicating it.
     * 
     * @throws java.lang.InterruptedException
    */ 
    @Test
    public void test2d_IncorrectClientEmail() throws InterruptedException{
        
        clickOn("#tfName");
        write("username");
        
        clickOn("#tfEmail");
        write("el email introducido debe ser un email válido");
        
        clickOn("#tfLogin");
        write("login");
        clickOn("#tfPassword");
        write("abcd*12345");
        clickOn("#tfConfirmPassword");
        write("abcd*12345");
        clickOn("#cbType");
        clickOn("PARTICULAR"); //Escoger una opción del Combo Box
        
        verifyThat("#btnNewClient", isEnabled());
        clickOn("#btnNewClient");
        
        verifyThat("#lblErrorEmail", isEnabled());
    }
    
    /**
     * Test that the client login entered is not correct.
     * An error label will appear indicating it.
     * 
     * @throws java.lang.InterruptedException
    */ 
    @Test
    public void test2e_IncorrectClientLogin() throws InterruptedException{
        
        clickOn("#tfName");
        write("username");
        clickOn("#tfEmail");
        write("email@gmail.com");
        
        clickOn("#tfLogin");
        write("la longitud y los espacios del login introducidos no son válidos");
        
        clickOn("#tfPassword");
        write("abcd*12345");
        clickOn("#tfConfirmPassword");
        write("abcd*12345");
        clickOn("#cbType");
        clickOn("PARTICULAR"); //Escoger una opción del Combo Box
        
        verifyThat("#btnNewClient", isEnabled());
        clickOn("#btnNewClient");
        
        verifyThat("#lblErrorLogin", isEnabled());
    }
    
    /**
     * Test that the client password entered is not correct.
     * An error label will appear indicating it.
     * 
     * @throws java.lang.InterruptedException
    */ 
    @Test
    public void test2f_IncorrectClientPassword() throws InterruptedException{
        
        clickOn("#tfName");
        write("username");
        clickOn("#tfEmail");
        write("email@gmail.com");
        clickOn("#tfLogin");
        write("login");
        
        clickOn("#tfPassword");
        write("la contraseña introducida debe ser una contraseña válida");
        
        clickOn("#tfConfirmPassword");
        write("abcd*12345");
        clickOn("#cbType");
        clickOn("PARTICULAR"); //Escoger una opción del Combo Box
        
        verifyThat("#btnNewClient", isEnabled());
        clickOn("#btnNewClient");
        
        verifyThat("#lblErrorPassword", isEnabled());
    }
    
    /**
     * Test that the client confirm password entered is not correct.
     * An error label will appear indicating it.
     * 
     * @throws java.lang.InterruptedException
    */ 
    @Test
    public void test2g_IncorrectClientConfirmPassword() throws InterruptedException{
        
        clickOn("#tfName");
        write("username");
        clickOn("#tfEmail");
        write("email@gmail.com");
        clickOn("#tfLogin");
        write("login");
        clickOn("#tfPassword");
        write("abcd*12345");
        
        clickOn("#tfConfirmPassword");
        write("la contraseña de confirmación debe ser igual a la contraseña");
        
        clickOn("#cbType");
        clickOn("PARTICULAR"); //Escoger una opción del Combo Box
        
        verifyThat("#btnNewClient", isEnabled());
        clickOn("#btnNewClient");
        
        verifyThat("#lblErrorConfirmPassword", isEnabled());
    }
    
    /**
     * Test that the client confirm password entered is not correct.
     * An error label will appear indicating it.
     * 
     * @throws java.lang.InterruptedException
    */ 
    @Test
    public void test2h_NewClientAdded() throws InterruptedException{
        
        clickOn("#tfName");
        write("username");
        
        clickOn("#tfEmail");
        write("email@gmail.com");
        
        clickOn("#tfLogin");
        write("login");
        
        clickOn("#tfPassword");
        write("abcd*12345");
        
        clickOn("#tfConfirmPassword");
        write("abcd*12345");
        
        clickOn("#cbType");
        clickOn("PARTICULAR"); //Escoger una opción del Combo Box
        
        verifyThat("#btnNewClient", isEnabled());
        clickOn("#btnNewClient");
        
        //Pulsar OK en la alerta de confirmación
        //Comprobar que el cliente se ha añadido correctamente a la tabla
    }
    
    /**
     * Test that button Ver Eventos Del Cliente is enabled 
     * when a client is selected in the table.
     * Then, test that event's view is opened when button 
     * Ver Eventos Del Cliente is clicked.
     * Finally, if we press the button Atrás in the events window 
     * we return to the clients window.
     * 
     * @throws java.lang.InterruptedException
    */ 
    @Test
    public void test3_EventsViewOpenedOnViewEventsClick() throws InterruptedException{
        
        //Seleccionar un cliente en la tabla
        verifyThat("#btnViewEvents", isEnabled());
        
        clickOn("#btnViewEvents");
        verifyThat("#VEventTable", isVisible());
        
        clickOn("#btnBack");
        verifyThat("#VClientTable", isVisible());
    }
    
    /**
     * Test that button Eliminar Cliente is enabled 
     * when a client is selected in the table.
     * Then, check that the table has been updated 
     * when you have deleted the client.
     * 
     * @throws java.lang.InterruptedException
    */ 
    @Test
    public void test4_ButtonDeleteClientIsEnabled() throws InterruptedException{
        
        //Seleccionar un cliente en la tabla
        verifyThat("#btnDeleteClient", isEnabled());
        
        clickOn("#btnDeleteClient");
        //Pulsar OK en la alerta de confirmación
        //Comprobar que el cliente se ha eliminado correctamente de la tabla
    }
    
    /**
     * Test that button Guardar Cambios is disabled 
     * when a client is selected in the table.
     * Then, test that the client data appears in the corresponding fields.
     * Password fields will not be visible.
     * If you do not make any changes in the client data 
     * the button will not be disabled
     * 
     * @throws java.lang.InterruptedException
    */ 
    @Test
    public void test5a_ButtonSaveClientIsDisabled() throws InterruptedException{
        
        //Seleccionar un cliente en la tabla
        
         //Comprobar que los datos del cliente aparecen en los campos 
        //menos la contraseña y confirmar contraseña
        verifyThat("#tfName", isVisible());
        verifyThat("#tfEmail", isVisible());
        verifyThat("#tfLogin", isVisible());
        verifyThat("#tfPassword", isDisabled());
        verifyThat("#tfConfirmPassword", isDisabled());
        verifyThat("#cbType", isVisible());
        
        verifyThat("#btnSaveClient", isDisabled());
    }
    
    /**
     * Test that button Guardar Cambios is disabled 
     * when a client is selected in the table.
     * Then, check that the client data appears in the corresponding fields.
     * Password fields will not be visible.
     * Enter the new client name.
     * Finally, check that the table has been updated 
     * when you have saved the client.
     * 
     * @throws java.lang.InterruptedException
    */ 
    @Test
    public void test5b_SaveClientName() throws InterruptedException{
        
        //Seleccionar un cliente en la tabla
        
        //Comprobar que los datos del cliente aparecen en los campos 
        //menos la contraseña y confirmar contraseña
        verifyThat("#tfName", isVisible());
        verifyThat("#tfEmail", isVisible());
        verifyThat("#tfLogin", isVisible());
        verifyThat("#tfPassword", isDisabled());
        verifyThat("#tfConfirmPassword", isDisabled());
        verifyThat("#cbType", isVisible());
        
        verifyThat("#btnSaveClient", isDisabled());
        
        clickOn("#tfName");
        write("newUsername");
        verifyThat("#btnNewClient", isEnabled());
        
        clickOn("#btnSaveClient");
        //Pulsar OK en la alerta de confirmación
        //Comprobar que el cliente se ha cambiado correctamente de la tabla
    }
    
    /**
     * Test that button Guardar Cambios is disabled 
     * when a client is selected in the table.
     * Then, check that the client data appears in the corresponding fields.
     * Password fields will not be visible.
     * Enter the new client email.
     * Finally, check that the table has been updated 
     * when you have saved the client.
     * 
     * @throws java.lang.InterruptedException
    */ 
    @Test
    public void test5c_SaveClientEmail() throws InterruptedException{
        
        //Seleccionar un cliente en la tabla
        
        //Comprobar que los datos del cliente aparecen en los campos 
        //menos la contraseña y confirmar contraseña
        verifyThat("#tfName", isVisible());
        verifyThat("#tfEmail", isVisible());
        verifyThat("#tfLogin", isVisible());
        verifyThat("#tfPassword", isDisabled());
        verifyThat("#tfConfirmPassword", isDisabled());
        verifyThat("#cbType", isVisible());
        
        verifyThat("#btnSaveClient", isDisabled());
        
        clickOn("#tfEmail");
        write("newEmail@gmail.com");
        verifyThat("#btnNewClient", isEnabled());
        
        clickOn("#btnSaveClient");
        //Pulsar OK en la alerta de confirmación
        //Comprobar que el cliente se ha cambiado correctamente de la tabla
    }
    
    /**
     * Test that button Guardar Cambios is disabled 
     * when a client is selected in the table.
     * Then, check that the client data appears in the corresponding fields.
     * Password fields will not be visible.
     * Enter the new client login.
     * Finally, check that the table has been updated 
     * when you have saved the client.
     * 
     * @throws java.lang.InterruptedException
    */ 
    @Test
    public void test5d_SaveClientLogin() throws InterruptedException{
        
        //Seleccionar un cliente en la tabla
        
        //Comprobar que los datos del cliente aparecen en los campos 
        //menos la contraseña y confirmar contraseña
        verifyThat("#tfName", isVisible());
        verifyThat("#tfEmail", isVisible());
        verifyThat("#tfLogin", isVisible());
        verifyThat("#tfPassword", isDisabled());
        verifyThat("#tfConfirmPassword", isDisabled());
        verifyThat("#cbType", isVisible());
        
        verifyThat("#btnSaveClient", isDisabled());
        
        clickOn("#tfLogin");
        write("newLogin");
        verifyThat("#btnNewClient", isEnabled());
        
        clickOn("#btnSaveClient");
        //Pulsar OK en la alerta de confirmación
        //Comprobar que el cliente se ha cambiado correctamente de la tabla
    }
    
    /**
     * Test that button Guardar Cambios is disabled 
     * when a client is selected in the table.
     * Then, check that the client data appears in the corresponding fields.
     * Password fields will not be visible.
     * Enter the new client type.
     * Finally, check that the table has been updated 
     * when you have saved the client.
     * 
     * @throws java.lang.InterruptedException
    */ 
    @Test
    public void test5e_SaveClientType() throws InterruptedException{
        
        //Seleccionar un cliente en la tabla
        
        //Comprobar que los datos del cliente aparecen en los campos 
        //menos la contraseña y confirmar contraseña
        verifyThat("#tfName", isVisible());
        verifyThat("#tfEmail", isVisible());
        verifyThat("#tfLogin", isVisible());
        verifyThat("#tfPassword", isDisabled());
        verifyThat("#tfConfirmPassword", isDisabled());
        verifyThat("#cbType", isVisible());
        
        
        verifyThat("#btnSaveClient", isDisabled());
        
        clickOn("#cbType");
        clickOn("PARTICULAR"); //Escoger una opción del Combo Box
        verifyThat("#btnNewClient", isEnabled());
        
        clickOn("#btnSaveClient");
        //Pulsar OK en la alerta de confirmación
        //Comprobar que el cliente se ha cambiado correctamente de la tabla
    }
    
    /**
     * Test that if we press the back button 
     * it takes us to the Administrator window.
     * 
     * @throws java.lang.InterruptedException
    */ 
    @Test
    public void test6_BackButton() throws InterruptedException{
        
        clickOn("#btnBack");
        //Pulsar OK en la alerta de confirmación
        verifyThat("#VAdmin", isVisible());
    }
    
    /**
     * Test if the search field has something written 
     * when the button Buscar is pressed.
     * 
     * @throws java.lang.InterruptedException
    */ 
    @Test
    public void test7a_SearchFieldEmpty() throws InterruptedException{
        
        clickOn("#cbSearchBy");
        clickOn("PARTICULAR"); //Escoger una opción del Combo Box
        verifyThat("#txtFilter", isEnabled());
        
        clickOn("#btnSearch");
        //Aviso de que el campo de busqueda esta vacío
    }
    
    /**
     * Test if the name entered in the search field is correct
     * 
     * @throws java.lang.InterruptedException
    */ 
    @Test
    public void test7b_SearchFieldNameCorrect() throws InterruptedException{
        
        clickOn("#cbSearchBy");
        clickOn("Client Name"); //Escoger opción Client Name del Combo Box
        verifyThat("#txtFilter", isEnabled());
        
        clickOn("#txtFilter");
        //Escribir un nombre que exista en la base de datos
        
        clickOn("#btnSearch");
        //Que aparezca en la tabla el cliente solicitado
    }
    
    /**
     * Test if the name entered in the search field is incorrect
     * 
     * @throws java.lang.InterruptedException
    */ 
    @Test
    public void test7c_SearchFieldNameIncorrect() throws InterruptedException{
        
        clickOn("#cbSearchBy");
        clickOn("Client Name"); //Escoger opción Client Name del Combo Box
        verifyThat("#txtFilter", isEnabled());
        
        clickOn("#txtFilter");
        //Escribir un nombre que no exista en la base de datos
        
        clickOn("#btnSearch");
        //Mensaje de error de cliente no encontrado
    }
    
    /**
     * Test if the login entered in the search field is correct
     * 
     * @throws java.lang.InterruptedException
    */ 
    @Test
    public void test7d_SearchFieldLoginCorrect() throws InterruptedException{
        
        clickOn("#cbSearchBy");
        clickOn("Client Login"); //Escoger opción Client Login del Combo Box
        verifyThat("#txtFilter", isEnabled());
        
        clickOn("#txtFilter");
        //Escribir un login que exista en la base de datos
        
        clickOn("#btnSearch");
        //Que aparezca en la tabla el cliente solicitado
    }
    
    /**
     * Test if the login entered in the search field is incorrect
     * 
     * @throws java.lang.InterruptedException
    */ 
    @Test
    public void test7e_SearchFieldLoginIncorrect() throws InterruptedException{
        
        clickOn("#cbSearchBy");
        clickOn("Client Login"); //Escoger opción Client Login del Combo Box
        verifyThat("#txtFilter", isEnabled());
        
        clickOn("#txtFilter");
        //Escribir un login que no exista en la base de datos
        
        clickOn("#btnSearch");
        //Mensaje de error de cliente no encontrado
    }
    
    /**
     * Test if the email entered in the search field is correct
     * 
     * @throws java.lang.InterruptedException
    */ 
    @Test
    public void test7f_SearchFieldEmailCorrect() throws InterruptedException{
        
        clickOn("#cbSearchBy");
        clickOn("Client Email"); //Escoger opción Client Email del Combo Box
        verifyThat("#txtFilter", isEnabled());
        
        clickOn("#txtFilter");
        //Escribir un email que exista en la base de datos
        
        clickOn("#btnSearch");
        //Que aparezca en la tabla el cliente solicitado
    }
    
    /**
     * Test if the email entered in the search field is incorrect
     * 
     * @throws java.lang.InterruptedException
    */ 
    @Test
    public void test7g_SearchFieldEmailIncorrect() throws InterruptedException{
        
        clickOn("#cbSearchBy");
        clickOn("Client Email"); //Escoger opción Client Email del Combo Box
        verifyThat("#txtFilter", isEnabled());
        
        clickOn("#txtFilter");
        //Escribir un email que no exista en la base de datos
        
        clickOn("#btnSearch");
        //Mensaje de error de cliente no encontrado
    }
    
    /**
     * Test if the type entered in the search field is correct
     * 
     * @throws java.lang.InterruptedException
    */ 
    @Test
    public void test7h_SearchFieldTypeCorrect() throws InterruptedException{
        
        clickOn("#cbSearchBy");
        clickOn("Client Type"); //Escoger opción Client Type del Combo Box
        verifyThat("#txtFilter", isEnabled());
        
        clickOn("#txtFilter");
        //Escribir un tipo que exista en la base de datos
        
        clickOn("#btnSearch");
        //Que aparezcan en la tabla todos los clientes de ese tipo
    }
    
    /**
     * Test if the type entered in the search field is incorrect
     * 
     * @throws java.lang.InterruptedException
    */ 
    @Test
    public void test7i_SearchFieldTypeIncorrect() throws InterruptedException{
        
        clickOn("#cbSearchBy");
        clickOn("Client Type"); //Escoger opción Client Type del Combo Box
        verifyThat("#txtFilter", isEnabled());
        
        clickOn("#txtFilter");
        //Escribir un tipo que no exista en la base de datos
        
        clickOn("#btnSearch");
        verifyThat("There are no clients with this type", isVisible()); //Mensaje de error de clientes no encontrados
    }
    
}