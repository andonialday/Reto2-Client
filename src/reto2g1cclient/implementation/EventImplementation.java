/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.implementation;

import java.util.Date;
import java.util.List;
import reto2g1cclient.logic.EventInterface;
import reto2g1cclient.model.Client;
import reto2g1cclient.model.Evento;

/**
 *
 * @author 2dam
 */
public class EventImplementation implements EventInterface {
    
    private EventJerseyClient ejc;
    
    public EventImplementation() {
        ejc = new EventJerseyClient();
    }

    @Override
    public void createEvent(Evento event) {
        ejc.create(event);
    }

    @Override
    public Evento find(Evento event, Integer id) {
        return ejc.find(Evento.class, id.toString());
    }

    @Override
    public List<Evento> findAll() {
        return ejc.findAll(List.class);
    }

    @Override
    public void edit(Evento event) {
        ejc.edit(event, event.getId().toString());
    }

    @Override
    public void remove(Evento event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String count() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Evento> findStartRange(List<Evento> events, Date dateMin, Date dateMax) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Evento> findEventByClient(List<Evento> events, Client client) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Evento> findRange(List<Evento> events, Date dateMin, Date dateMax) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Evento> findEndRange(List<Evento> events, Date dateMin, Date dateMax) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Evento> findEndRangeClient(List<Evento> events, Date dateMin, Date dateMax, Client client) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Evento> findDateRange(List<Evento> events, Date dateMin, Date dateMax) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Evento> eleteOldestEvents(List<Evento> events, Integer year) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
