/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.implementation;

import java.util.List;
import reto2g1cclient.logic.EventInterface;
import reto2g1cclient.model.Evento;

/**
 *
 * @author 2dam
 */
public class EventImplementation implements EventInterface {

    @Override
    public void createEvent(Evento event) {
        EventJerseyClient ejc = new EventJerseyClient();
        ejc.create(event);
    }

    public Evento find(Evento event, String id) {
        EventJerseyClient ejc = new EventJerseyClient();
        return ejc.find(Evento.class, id);
    }

    public List<Evento> findAll(Evento event) {
        EventJerseyClient ejc = new EventJerseyClient();
        return ejc.findAll(List.class);
    }
}
