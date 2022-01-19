/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.logic;

import java.util.Collection;
import reto2g1cclient.model.Client;

/**
 *
 * @author 2dam
 */
public interface ClientInterface {
    
    public void createClient(Client client) throws Exception;
    
    public void editClient(Client client) throws Exception;
    
    public void removeClient(Client client) throws Exception;
    
    public void searchClient(Client client) throws Exception;
    
    public void findClientCommercial(Client client) throws Exception;
    
    public void deleteAllClientDisabled(Client client) throws Exception;
    
    public Collection<Client> getAllClient() throws Exception;
}
