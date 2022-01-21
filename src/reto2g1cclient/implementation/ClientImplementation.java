/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.implementation;

import java.util.Collection;
import javax.ws.rs.core.GenericType;
import reto2g1cclient.logic.ClientInterface;
import reto2g1cclient.model.Client;

/**
 *
 * @author Jaime San Sebastian
 */
public class ClientImplementation implements ClientInterface{

    private ClientJersey clientJersey;
    
    public ClientImplementation() {
            clientJersey = new ClientJersey();
    }
    
    @Override
    public void createClient (Client client)throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editClient(Client client) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Client removeClient(Client client) throws Exception {
        client = null;
        try{
            clients = clientJersey.remove(new GenericType Client(){});
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return clients;
    }

    @Override
    public void searchClient(Client client) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void findClientCommercial(Client client) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteAllClientDisabled(Client client) throws Exception {
        
    }

    @Override
    public Collection<Client> getAllClient() throws Exception {
        Collection <Client> clients = null;
        try{
            clients = clientJersey.findAll(new GenericType <Collection <Client>>(){});
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return clients;
    }

}