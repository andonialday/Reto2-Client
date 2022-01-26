/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.application;

import java.io.IOException;
import static java.lang.String.valueOf;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import reto2g1cclient.controller.EquipmentController;
import reto2g1cclient.model.Equipment;

/**
 *
 * @author aitor
 */
public class TestAplicationEquipment extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
          launch(args);
    }
    /**
     * Method to Load and Launch the First Scene
     * @param primaryStage It create a Stage in which all scenes are loaded
     * @throws java.io.IOException I/O error for the Parent loader parameter
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Logger.getLogger("Initializing client...");
        //get the route of the 1. View
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VEquipmentTable.fxml"));
            
        Parent root = (Parent) loader.load();
        //get the controller of the view
        EquipmentController controller = ((EquipmentController) loader.getController());
        
        List<Equipment>equipment = new ArrayList<Equipment>();
        for (int i = 1; i <= 5; i++) {
            Equipment eq = new Equipment(i, "Microfono "+ i ,"HOLA YO SOY LA PRUEBA NUMEROOOO" + i, valueOf(20d*i) , Date.valueOf(LocalDate.now()).toString() , null);
            equipment.add(eq);
            System.out.println(eq.toString());
        }
        controller.setEquipments(equipment);
        controller.setStage(primaryStage);
        //initializate the stage
        controller.initStage(root);

    }
}
