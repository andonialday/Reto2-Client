/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.implementation;

import java.util.Collection;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import reto2g1cclient.cypher.EncryptAsim;
import reto2g1cclient.exception.ClientServerConnectionException;
import reto2g1cclient.exception.DBServerException;
import reto2g1cclient.exception.LoginOnUseException;
import reto2g1cclient.logic.ClientInterface;
import reto2g1cclient.model.Client;

/**
 *
 * @author Jaime San Sebastian
 */
public class ClientImplementation implements ClientInterface{

    private final ClientJersey clientJersey;
    
    public ClientImplementation() {
            clientJersey = new ClientJersey();
    }
    
    @Override
    public void createClient (Client client)
            throws ClientServerConnectionException {
        try{
            clientJersey.create(client);
        }catch (ClientErrorException e){
            throw new ClientServerConnectionException(e.getMessage());
        }
    }

    @Override
    public void editClient(Client client) 
            throws ClientServerConnectionException {
        try{
            clientJersey.edit(client, String.valueOf(client.getId()));
        }catch (ClientErrorException e){
            throw new ClientServerConnectionException(e.getMessage());
        }
    }

    @Override
    public void removeClient(String clientId) 
            throws ClientServerConnectionException {
        try{
            clientJersey.remove(clientId);
        }catch (ClientErrorException e){
            throw new ClientServerConnectionException(e.getMessage());
        }
    }

    @Override
    public void searchClient(Client client) 
            throws ClientServerConnectionException {
        
    }

    @Override
    public void findClientCommercial(Client client) 
            throws ClientServerConnectionException {
        
    }

    @Override
    public void deleteAllClientDisabled(Client client) 
            throws ClientServerConnectionException {
        
    }

    @Override
    public Collection<Client> getAllClient() 
            throws ClientServerConnectionException {
        Collection <Client> clients = null;
        try{
            clients = clientJersey
                    .findAll(new GenericType <Collection <Client>>(){});
        }catch (ClientErrorException e){
            throw new ClientServerConnectionException(e.getMessage());
        }
        return clients;
    }
    
    @Override
    public void signUp(Client usr) throws ClientServerConnectionException,  LoginOnUseException,  DBServerException{
        try {
            usr.setPassword(EncryptAsim.encryption(usr.getPassword()));
            Client user = clientJersey.signUp(Client.class, usr);
            if (!usr.getFullName().equals(user.getFullName()) || !usr.getEmail().equals(user.getEmail())) {
                throw new LoginOnUseException("El login está en uso");
            }

        } catch (ClientErrorException e) {
            throw new DBServerException(e.getMessage());
        } catch (Exception es) {
            throw new ClientServerConnectionException(es.getMessage());
        }
    }

}