/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import reto2g1cclient.logic.ClientInterface;

/**
 *
 * @author Jaime San Sebastian
 */
public class VClientController {
    
    private ClientInterface clientInterface;
    private Stage stage;
    
    public void setClientInterface(ClientInterface clientInterface) {
        this.clientInterface = clientInterface;
    }
    
    /**
     * Sets the stage
     *
     * @param stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Gets the stage
     *
     * @return stage to get
     */
    public Stage getStage() {
        return stage;
    }
    
    @FXML
    private TextField tfLogin;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfEmail;
    @FXML
    private Button btnAccept;
    @FXML
    private Button btnCancel;
    @FXML
    private ComboBox<?> cbType;

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    @FXML
    private void handleAccept(ActionEvent event) {
    }

    @FXML
    private void handleCancel(ActionEvent event) {
    }

    @FXML
    private void handleChangeType(ActionEvent event) {
    }
    
}
