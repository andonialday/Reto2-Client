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

   
    public User signIn(User usr) throws DBServerException, CredentialErrorException, ClientServerConnectionException;

    public void signUp(User usr) throws DBServerException, LoginOnUseException, ClientServerConnectionException;

    public void resetPassword(String log) throws ClientServerConnectionException, DBServerException;

    public void changePassword(User usr) throws ClientServerConnectionException, DBServerException;

}
