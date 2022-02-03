/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.logic;

import java.util.Collection;
import reto2g1cclient.exception.ClientServerConnectionException;
import reto2g1cclient.exception.DBServerException;
import reto2g1cclient.exception.LoginOnUseException;
import reto2g1cclient.model.Client;

/**
 *
 * @author Jaime San Sebastián
 */
public interface ClientInterface {
    
    /**
     *
     * @param client
     * @throws ClientServerConnectionException
     */
    public void createClient(Client client) 
            throws ClientServerConnectionException;
    
    /**
     *
     * @param client
     * @throws ClientServerConnectionException
     */
    public void editClient(Client client) 
            throws ClientServerConnectionException;
    
    /**
     *
     * @param clientId
     * @throws ClientServerConnectionException
     */
    public void removeClient(String clientId) 
            throws ClientServerConnectionException;
    
    /**
     *
     * @param client
     * @throws ClientServerConnectionException
     */
    public void searchClient(Client client) 
            throws ClientServerConnectionException;
    
    /**
     *
     * @param client
     * @throws ClientServerConnectionException
     */
    public void findClientCommercial(Client client) 
            throws ClientServerConnectionException;
    
    /**
     *
     * @param client
     * @throws ClientServerConnectionException
     */
    public void deleteAllClientDisabled(Client client) 
            throws ClientServerConnectionException;
    
    /**
     *
     * @return
     * @throws ClientServerConnectionException
     */
    public Collection<Client> getAllClient() 
            throws ClientServerConnectionException;
    
    /**
     *
     * @param usr
     * @throws ClientServerConnectionException
     * @throws LoginOnUseException
     * @throws DBServerException
     */
    public void signUp(Client usr) 
            throws ClientServerConnectionException, LoginOnUseException, DBServerException;
}