/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.logic;

import reto2g1cclient.exception.*;
import reto2g1cclient.model.*;

/**
 * Signable Interface
 *
 * @author Jaime San Sebastian,Enaitz Izagirre
 */
public interface Signable {

    /**
     * Sign In Method Interface
     *
     * @param usr Takes a user from the client side
     * @return Returns a user with all the data
     * @throws ClientServerConnectionException
     * @throws DBConnectionException
     * @throws CredentialErrorException
     */
    public User signIn(User usr) throws ClientServerConnectionException, DBConnectionException, CredentialErrorException;

    /**
     * Sign Up Method Interface
     *
     * @param usr Takes a user from the client side
     * @return Returns a user with all the data
     * @throws ClientServerConnectionException
     * @throws DBConnectionException
     * @throws LoginOnUseException
     */
    public void signUp(User usr) throws ClientServerConnectionException, DBConnectionException, LoginOnUseException;

    public void resetPassword(String log) throws ClientServerConnectionException, DBConnectionException, LoginOnUseException;

    public void changePassword(User usr) throws ClientServerConnectionException, DBConnectionException, LoginOnUseException;

}
