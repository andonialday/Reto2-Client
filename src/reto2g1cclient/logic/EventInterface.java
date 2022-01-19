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
 * @author Andoni Alday
 */
public interface EventInterface {
    
    public void createEvent(Evento event);
    
    public void edit(Evento event);
    
    public void remove(Evento event);
    
    public String count();
    
    public List<Evento> findStartRange(Date dateMin, Date dateMax);
    
    public List<Evento> findEventByClient(Client client);
    
    public List<Evento> findRange(Date dateMin, Date dateMax);
    
    public List<Evento> findEndRange(Date dateMin, Date dateMax);
    
    public List<Evento> findEndRangeClient(Date dateMin, Date dateMax, Client client);
    
    public List<Evento> findDateRange(Date dateMin, Date dateMax);
    
    public Evento find(Integer id);
    
    public List<Evento> findAll();
    
    public List<Evento> deleteOldestEvents(Integer year);
    
}
