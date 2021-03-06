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
import java.util.ResourceBundle;

/**
 * Jersey REST client generated for REST resource:EventFacadeREST
 * [entities.event]<br>
 * USAGE:
 * <pre>
 *        EventJerseyClient client = new EventJerseyClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Andoni Alday
 */
public class EventJerseyClient {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ResourceBundle.getBundle("reto2g1cclient.properties.config").getString("RESTFUL");

    /**
     *
     */
    public EventJerseyClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.event");
    }

    /**
     *
     * @param <T>
     * @param responseType
     * @param dateMin
     * @param dateMax
     * @return
     * @throws ClientErrorException
     */
    public <T> T findStartRange(GenericType<T> responseType, String dateMin, String dateMax) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("dateStart/{0}/{1}", new Object[]{dateMin, dateMax}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     *
     * @param <T>
     * @param responseType
     * @param idCli
     * @return
     * @throws ClientErrorException
     */
    public <T> T findEventByClient(GenericType<T> responseType, String idCli) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("byClient/{0}", new Object[]{idCli}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
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
     * @param <T>
     * @param responseType
     * @param dateMin
     * @param dateMax
     * @return
     * @throws ClientErrorException
     */
    public <T> T findEndRange(GenericType<T> responseType, String dateMin, String dateMax) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("dateEnd/{0}/{1}", new Object[]{dateMin, dateMax}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     *
     * @param <T>
     * @param responseType
     * @param dateMin
     * @param dateMax
     * @param idCli
     * @return
     * @throws ClientErrorException
     */
    public <T> T findEndRangeClient(GenericType<T> responseType, String dateMin, String dateMax, String idCli) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("dateEndClient/{0}/{1}/{2}", new Object[]{dateMin, dateMax, idCli}));
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
     * @param id
     * @throws ClientErrorException
     */
    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    /**
     *
     * @return @throws ClientErrorException
     */
    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    /**
     *
     * @param <T>
     * @param responseType
     * @param year
     * @return
     * @throws ClientErrorException
     */
    public <T> T deleteOldestEvents(GenericType<T> responseType, String year) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("oldest/{0}", new Object[]{year}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     *
     * @param <T>
     * @param responseType
     * @param dateMin
     * @param dateMax
     * @return
     * @throws ClientErrorException
     */
    public <T> T findDateRange(GenericType<T> responseType, String dateMin, String dateMax) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("date/{0}/{1}", new Object[]{dateMin, dateMax}));
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
    public <T> T find(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     *
     * @param requestEntity
     */
    public void create(Object requestEntity) throws ClientErrorException {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     *
     * @param <T>
     * @param responseType
     * @param dateMin
     * @param dateMax
     * @param idCli
     * @return
     * @throws ClientErrorException
     */
    public <T> T findDateRangeClient(GenericType<T> responseType, String dateMin, String dateMax, String idCli) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("dateClient/{0}/{1}/{2}", new Object[]{dateMin, dateMax, idCli}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     *
     * @param <T>
     * @param responseType
     * @param dateMin
     * @param dateMax
     * @param idCli
     * @return
     * @throws ClientErrorException
     */
    public <T> T findStartRangeClient(GenericType<T> responseType, String dateMin, String dateMax, String idCli) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("dateStartClient/{0}/{1}/{2}", new Object[]{dateMin, dateMax, idCli}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     *
     */
    public void close() {
        client.close();
    }

}
