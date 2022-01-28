/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.implementation;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import reto2g1cclient.exception.DBServerException;
import reto2g1cclient.logic.EventInterface;
import reto2g1cclient.model.Client;
import reto2g1cclient.model.Evento;

/**
 *
 * @author Andoni Alday
 */
public class EventImplementation implements EventInterface {

    private static final Logger LOGGER = Logger.getLogger("package.class");
    private EventJerseyClient ejc;

    /**
     *
     */
    public EventImplementation() {
        ejc = new EventJerseyClient();
    }

    /**
     *
     * @param event
     */
    @Override
    public void createEvent(Evento event) {
        ejc.create(event);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Evento find(Integer id) {
        return ejc.find(Evento.class, id.toString());
    }

    /**
     *
     * @return @throws reto2g1cclient.exception.DBServerException
     */
    @Override
    public Collection<Evento> findAll() throws DBServerException {
        List<Evento> ret = null;
        try {
            ret = ejc.findAll(new GenericType<List<Evento>>() {
            });
        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "Error al leer los eventos en la base de datos");
            throw new DBServerException(e.getMessage());
        }
        return ret;
    }

    /**
     *
     * @param event
     */
    @Override
    public void edit(Evento event) {
        ejc.edit(event, String.valueOf(event.getId()));
    }

    /**
     *
     * @param event
     */
    @Override
    public void remove(Evento event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @return
     */
    @Override
    public String count() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param dateMin
     * @param dateMax
     * @return
     */
    @Override
    public Collection<Evento> findStartRange(Date dateMin, Date dateMax) {
        return ejc.findStartRange(new GenericType<List<Evento>>(){},dateMin.toString(),dateMax.toString());
    }

    /**
     *
     * @param client
     * @return
     */
    @Override
    public Collection<Evento> findEventByClient(Client client) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param dateMin
     * @param dateMax
     * @return
     */
    @Override
    public Collection<Evento> findRange(Date dateMin, Date dateMax) {
        return ejc.findRange(new GenericType<List<Evento>>(){}, dateMin.toString(), dateMax.toString());
    }

    /**
     *
     * @param dateMin
     * @param dateMax
     * @return
     */
    @Override
    public Collection<Evento> findEndRange(Date dateMin, Date dateMax) {
        return ejc.findEndRange(new GenericType<List<Evento>>(){}, dateMin.toString(), dateMax.toString());
    }

    /**
     *
     * @param dateMin
     * @param dateMax
     * @param client
     * @return
     */
    @Override
    public Collection<Evento> findEndRangeClient(Date dateMin, Date dateMax, Client client) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param dateMin
     * @param dateMax
     * @return
     */
    @Override
    public Collection<Evento> findDateRange(Date dateMin, Date dateMax) {
        return ejc.findDateRange(new GenericType<List<Evento>>(){}, dateMin.toString(), dateMax.toString());
    }

    /**
     *
     * @param year
     * @return
     */
    @Override
    public Collection<Evento> deleteOldestEvents(Integer year) {
        return ejc.deleteOldestEvents(new GenericType<List<Evento>>(){}, year.toString());
    }
}
