/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.logic;

import java.util.Date;
import java.util.List;
import reto2g1cclient.model.Client;
import reto2g1cclient.model.Evento;

/**
 *
 * @author 2dam
 */
public interface EventInterface {
    
    public void createEvent(Evento event);
    
    public void edit(Evento event);
    
    public void remove(Evento event);
    
    public String count();
    
    public List<Evento> findStartRange(List<Evento> events, Date dateMin, Date dateMax);
    
    public List<Evento> findEventByClient(List<Evento> events, Client client);
    
    public List<Evento> findRange(List<Evento> events, Date dateMin, Date dateMax);
    
    public List<Evento> findEndRange(List<Evento> events, Date dateMin, Date dateMax);
    
    public List<Evento> findEndRangeClient(List<Evento> events, Date dateMin, Date dateMax, Client client);
    
    public List<Evento> findDateRange(List<Evento> events, Date dateMin, Date dateMax);
    
    public Evento find(Evento event, Integer id);
    
    public List<Evento> findAll();
    
    public List<Evento> eleteOldestEvents(List<Evento> events, Integer year);
    
}
