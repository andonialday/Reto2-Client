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
     *
     * @param usr
     * @return
     * @throws DBServerException
     * @throws CredentialErrorException
     * @throws ClientServerConnectionException
     */
    public User signIn(User usr) throws DBServerException, CredentialErrorException, ClientServerConnectionException;

    /**
     *
     * @param usr
     * @throws DBServerException
     * @throws LoginOnUseException
     * @throws ClientServerConnectionException
     */
    public void signUp(User usr) throws DBServerException, LoginOnUseException, ClientServerConnectionException;

    /**
     *
     * @param log
     * @throws ClientServerConnectionException
     * @throws DBServerException
     * @throws CredentialErrorException
     */
    public void resetPassword(String log) throws ClientServerConnectionException, DBServerException, CredentialErrorException;

    /**
     *
     * @param usr
     * @throws ClientServerConnectionException
     * @throws DBServerException
     */
    public void changePassword(User usr) throws ClientServerConnectionException, DBServerException;

}