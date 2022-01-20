/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.implementation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import reto2g1cclient.exception.*;
import reto2g1cclient.logic.Signable;
import reto2g1cclient.model.User;

/**
 * Signable Implementation Class for the View Layer
 *
 * @author Enaitz Izagirre
 */
public class ViewSignableImplementation implements Signable {

    //establish config file route 
    private static final ResourceBundle configFile = ResourceBundle.getBundle("reto2g1cclient.controller.config");
    //declare logger
    private static final Logger LOGGER = Logger.getLogger("package.class");
    private UserJerseyClient ujc;

    public ViewSignableImplementation() {
        ujc = new UserJerseyClient();
    }

    /**
     * Method to SignIn the Client in to the Data Base
     *
     * @param usr Asks a user to encapsulate in order to send it to the server
     * @return Returns a null or a complete user depending if it fails
     * @throws CredentialErrorException If the user is not correct
     * @throws DBConnectionException Error processing feedback from the Database
     * @throws ClientServerConnectionException If the Client cant Connect With
     * the server cause of the Server error
     */
    @Override
    public User signIn(User usr) throws CredentialErrorException, DBConnectionException, ClientServerConnectionException {

        return usr;
    }

    /**
     * Method to Sign Up the Client in to the Data Base
     *
     * @param usr Asks a user to encapsulate in order to send it to the server
     * @return Returns a null or a complete user depending if it fails
     * @throws LoginOnUseException If the User Parameters are rown
     * @throws ClientServerConnectionException If the Client cant Connect With
     * the server cause of the Server error
     */
    @Override
    public void signUp(User usr) throws LoginOnUseException, ClientServerConnectionException, DBConnectionException {
        ujc.create(usr);
    }
}
