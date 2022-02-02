/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.implementation;

import java.util.List;
import javax.ws.rs.core.GenericType;
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
    private static final ResourceBundle configFile = ResourceBundle.getBundle("reto2g1cclient.properties.config");
    //declare logger
    private static final Logger LOGGER = Logger.getLogger("package.class");
    private UserJerseyClient ujc;

    public ViewSignableImplementation() {
        ujc = new UserJerseyClient();
    }
    
    @Override
    public User signIn(User usr) throws DBServerException, CredentialErrorException, ClientServerConnectionException {
        List<User> user;
        try {
            user = ujc.signIn(new GenericType<List<User>>() {
            }, usr.getLogin(), EncryptAsim.encryption(usr.getPassword()));
            usr = user.get(0);
            if (usr == null) {
                throw new CredentialErrorException("No existe ningun usuario con esas credenciales");
            }
        } catch (ClientErrorException e) {
            throw new DBServerException(e.getMessage());
        } catch (javax.ws.rs.InternalServerErrorException es) {
            throw new CredentialErrorException(es.getMessage());
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
