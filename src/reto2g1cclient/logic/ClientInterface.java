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
    
    public void createClient(Client client);
    
    public void updateClient(Client client);
    
    public void deleteClient(Client client);
    
    public void findClient(Client client);
    
    public void findClientCommercial(Client client);
    
    public void deleteAllClientDisabled(Client client);
    
    public Collection<Client> getAllClient();
}
