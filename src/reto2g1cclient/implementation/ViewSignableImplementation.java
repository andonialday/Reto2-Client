/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.implementation;

import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import reto2g1cclient.cypher.EncryptAsim;
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
     * @throws reto2g1cclient.exception.DBServerException
     */
    @Override
    public User signIn(User usr) throws DBServerException, CredentialErrorException, ClientServerConnectionException {
        try {
            usr = ujc.signIn(User.class, usr.getLogin(), EncryptAsim.encryption(usr.getPassword()));
            if (usr == null) {
                throw new CredentialErrorException("No existe ningún usuario con esas credenciales");
            }
        } catch (ClientErrorException e) {
            throw new DBServerException(e.getMessage());
        } catch (Exception es) {
            throw new ClientServerConnectionException(es.getMessage());
        }
        return usr;
    }

    @Override
    public void signUp(User usr) throws DBServerException, LoginOnUseException, ClientServerConnectionException {
        try {
            usr.setPassword(EncryptAsim.encryption(usr.getPassword()));
            User user = ujc.signUp(User.class, usr);
            if (!usr.getFullName().equals(user.getFullName()) || !usr.getEmail().equals(user.getEmail())) {
                throw new LoginOnUseException("El login está en uso");
            }
        } catch (ClientErrorException e) {
            throw new DBServerException(e.getMessage());
        } catch (Exception es) {
            throw new ClientServerConnectionException(es.getMessage());
        }
    }

    @Override
    public void resetPassword(String log) throws DBServerException, ClientServerConnectionException {
        try {
            ujc.resetPasswordByLogin(User.class, log);
        } catch (ClientErrorException e) {
            throw new DBServerException(e.getMessage());
        } catch (Exception es) {
            throw new ClientServerConnectionException(es.getMessage());
        }
    }

    @Override
    public void changePassword(User usr) throws DBServerException, ClientServerConnectionException {
        try {
            usr.setPassword(EncryptAsim.encryption(usr.getPassword()));
            ujc.updatePass(usr);
        } catch (ClientErrorException e) {
            throw new DBServerException(e.getMessage());
        } catch (Exception es) {
            throw new ClientServerConnectionException(es.getMessage());
        }
    }
}
