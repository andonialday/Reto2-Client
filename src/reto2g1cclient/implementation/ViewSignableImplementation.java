/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.implementation;

import java.util.ResourceBundle;
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
        usr = ujc.signIn(User.class, usr);
        return usr;
    }

    @Override
    public void signUp(User usr) throws LoginOnUseException, ClientServerConnectionException, DBConnectionException {
        ujc.create(usr);
    }

    @Override
    public void resetPassword(String log) throws LoginOnUseException, ClientServerConnectionException, DBConnectionException {
        User usr = ujc.resetPasswordByLogin(User.class, log);
    }

    @Override
    public void changePassword(User usr) throws ClientServerConnectionException, DBConnectionException, LoginOnUseException {
        ujc.updatePass(usr);
    }
}
