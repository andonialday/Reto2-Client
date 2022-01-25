/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.implementation;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:ClientFacadeREST
 * [entities.client]<br>
 * USAGE:
 * <pre>
 *        ClientJersey client = new ClientJersey();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Jaime San Sebastian
 */
public class ClientJersey {

    private WebTarget webTarget;
    private Client client;
    
    //Adapt the port of the BASE_URI to the HTTP Port of the Glassfish
    private static final String 
            BASE_URI = "http://localhost:29674/Reto2G1cServer/webresources";

    public ClientJersey() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.client");
    }

    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN)
                .get(String.class);
    }

    public void edit(Object requestEntity, String id) 
            throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, 
                        javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    public <T> T find(GenericType<T> responseType, String id) 
            throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource
                .path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(responseType);
    }

    public <T> T findRange(GenericType<T> responseType, String from, String to) 
            throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource
                .path(java.text.MessageFormat
                        .format("{0}/{1}", new Object[]{from, to}));
        return resource
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(responseType);
    }

    public void create(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .post(javax.ws.rs.client.Entity
                        .entity(requestEntity, 
                                javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    public <T> T findAll(GenericType<T> responseType) 
            throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(responseType);
    }

    public void remove(String id) throws ClientErrorException {
        webTarget
                .path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request()
                .delete();
    }

    public void close() {
        client.close();
    }
    
}