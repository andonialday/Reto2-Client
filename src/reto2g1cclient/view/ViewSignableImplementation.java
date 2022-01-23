/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import reto2g1cclient.exception.ClientServerConnectionException;
import reto2g1cclient.exception.CredentialErrorException;
import reto2g1cclient.exception.DBConnectionException;
import reto2g1cclient.exception.LoginOnUseException;
import reto2g1cclient.logic.Signable;
import reto2g1cclient.model.User;

/**
 * Signable Implementation Class for the View Layer
 * @author Enaitz Izagirre
 */
public class ViewSignableImplementation implements Signable {

    //declare Object I/O to take User
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    //establish config file route 
    private static final ResourceBundle configFile = ResourceBundle.getBundle("reto1client.controller.config");
    private static final String SERVER = configFile.getString("IP");;
    private static final Integer PORT = Integer.valueOf(configFile.getString("PORT"));
    //declare logger
    private static final Logger LOGGER = Logger.getLogger("package.class");

    /**
     * Method to SignIn the Client in to the Data Base
     * @param usr Asks a user to encapsulate in order to send it to the server
     * @return Returns a null or a complete user depending if it fails
     * @throws CredentialErrorException If the user is not correct
     * @throws DBConnectionException Error processing feedback from the Database
     * @throws ClientServerConnectionException If the Client cant Connect With
     * the server cause of the Server error
     */
    @Override
    public User signIn(User usr) throws CredentialErrorException, DBConnectionException, ClientServerConnectionException {

        //create the encapsulation
        Encapsulation enc = new Encapsulation();
        //intro data on the encapsulation
        enc.setUser(usr);
        enc.setStatus(Status.PENDING);
        enc.setMethod(Method.SIGNIN);
        
        LOGGER.info("Sign In procedure initiated");
        //create client socket
        Socket client;

        try {
            //establish socket parameter
            client = new Socket(SERVER, PORT);
            //write oos with encapsulation data
            oos = new ObjectOutputStream(client.getOutputStream());
            oos.writeObject(enc);
            ois = new ObjectInputStream(client.getInputStream());
            enc = (Encapsulation) ois.readObject();
            usr = enc.getUser();
            //if + switch to check encapsulation status
            if (null != enc.getStatus()) //check encapsulation status
            switch (enc.getStatus()) {
                case FAIL:
                    throw new CredentialErrorException("Login already on usen on the Database");
                case ERROR:
                    throw new DBConnectionException("Failed to connect to the Database");
                case PENDING:
                    throw new ClientServerConnectionException("The server is busy");
                default:
                    break;
            }
            //catch oos/ois error
        } catch (IOException ex) {
            throw new ClientServerConnectionException("Failed to connect to server");
            //catch class client error
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ViewSignableImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return User object
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
    public User signUp(User usr) throws LoginOnUseException, ClientServerConnectionException, DBConnectionException {
        //create the encapsulation
        Encapsulation enc = new Encapsulation();
        //intro data on the encapsulation
        enc.setUser(usr);
        enc.setStatus(Status.PENDING);
        enc.setMethod(Method.SIGNUP);

        LOGGER.info("Sign Up procedure initiated");
        //create client socket
        Socket client;

        try {
            //establish socket parameter
            client = new Socket(SERVER, PORT);
            //write oos with encapsulation data
            oos = new ObjectOutputStream(client.getOutputStream());
            oos.writeObject(enc);
            //take ois object and load in to encapsulation
            ois = new ObjectInputStream(client.getInputStream());
            enc = (Encapsulation) ois.readObject();
            //take the user from encapsulation
            usr = enc.getUser();
            if (null != enc.getStatus()) //check encapsulation status
            switch (enc.getStatus()) {
                case FAIL:
                    throw new LoginOnUseException("Login already on usen on the Database");
                case ERROR:
                    throw new DBConnectionException("Failed to connect to the Database");
                case PENDING:
                    throw new ClientServerConnectionException("The server is busy");
                default:
                    break;
            }
        } catch (IOException ex) {
            throw new ClientServerConnectionException("Failed to connect to server");
            //catch class encapsulation error
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ViewSignableImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return User object
        return usr;
    }

  
}