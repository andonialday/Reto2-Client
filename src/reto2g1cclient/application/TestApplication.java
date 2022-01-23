/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.application;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import reto2g1cclient.controller.VEventTableController;
import reto2g1cclient.model.Evento;

/**
 * Client Main class to Launch Client App
 * @author Enaitz Izagirre
 */
public class TestApplication extends Application {

    /**
     * Method to Launch Client 
     * @param args Java Arguments to launch the main
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VEventTable.fxml"));
        List<Evento> events = new ArrayList<>();
        for (int i = 0 ; i < 3 ; i++) {
            Evento ev = new Evento(i+1, Date.from(Instant.now()), Date.from(Instant.now()), "descripcion de prueba " + (i+1), "nombre de prueba " + (i+1), null, null);
            events.add(ev);
        }
        Parent root = (Parent) loader.load();
        //get the controller of the view
        VEventTableController controller = ((VEventTableController) loader.getController());
        controller.setStage(primaryStage);
        //initializate the stage
        controller.setEvents(events);
        controller.setEditable(true);
        controller.initStage(root);

    }
}