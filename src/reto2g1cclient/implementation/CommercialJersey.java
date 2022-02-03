/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.implementation;

import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import reto2g1cclient.model.Commercial;

/**
 * Jersey REST client generated for REST resource:CommercialFacadeREST
 * [entities.commercial]<br>
 * USAGE:
 * <pre>
 *        CommercialJersey client = new CommercialJersey();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Enaitz Izagirre
 */
public class CommercialJersey {

    private WebTarget webTarget;
    private Client client;
    private static String BASE_URI;
    private ResourceBundle resourceBundle = ResourceBundle
            .getBundle("reto2g1cclient.properties.config");

    /**
     *
     */
    public CommercialJersey() {
        BASE_URI = resourceBundle.getString("RESTFUL");
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.commercial");
    }

    /**
     *
     * @param <T>
     * @param responseType
     * @param especialization
     * @return
     * @throws ClientErrorException
     */
    public <T> T getEspecializationByCommercialDisable(Class<T> responseType, String especialization) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("especializationDisable/{0}", new Object[]{especialization}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     *
     * @return
     * @throws ClientErrorException
     */
    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    /**
     *
     * @param requestEntity
     * @param id
     * @throws ClientErrorException
     */
    public void edit(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     *
     * @param <T>
     * @param responseType
     * @param especialization
     * @return
     * @throws ClientErrorException
     */
    public <T> T getEspecializationByCommercialEnable(GenericType<T> responseType, String especialization) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("especializationEnabled/{0}", new Object[]{especialization}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     *
     * @param <T>
     * @param responseType
     * @param id
     * @return
     * @throws ClientErrorException
     */
    public <T> T find(GenericType<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     *
     * @param <T>
     * @param responseType
     * @param from
     * @param to
     * @return
     * @throws ClientErrorException
     */
    public <T> T findRange(GenericType<T> responseType, String from, String to) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     *
     * @param requestEntity
     * @throws ClientErrorException
     */
    public void create(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     *
     * @param <T>
     * @param responseType
     * @param especialization
     * @return
     * @throws ClientErrorException
     */
    public <T> T getEspecializationByCommercialAll(GenericType<T> responseType, String especialization) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("especializationAll/{0}", new Object[]{especialization}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     *
     * @param <T>
     * @param responseType
     * @return
     * @throws ClientErrorException
     */
    public <T> T findAll(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     *
     * @param <T>
     * @param responseType
     * @param user
     * @return
     * @throws ClientErrorException
     */
    public <T> T signUp(Class<T> responseType, Commercial user) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("signUp/{0}/{1}/{2}/{3}/{4}", new Object[]{user.getLogin(), user.getEmail(), user.getPassword(), user.getFullName(), user.getEspecialization()}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     *
     * @param id
     * @throws ClientErrorException
     */
    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    /**
     *
     */
    public void close() {
        client.close();
    }

}
