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
    public void createClient (Client client){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editClient(Client client) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeClient(Client client) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void searchClient(Client client) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void findClientCommercial(Client client) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteAllClientDisabled(Client client) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
