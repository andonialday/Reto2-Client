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
    
    /**
     *
     * @param event
     */
    public void createEvent(Evento event);
    
    /**
     *
     * @param event
     */
    public void edit(Evento event);
    
    /**
     *
     * @param event
     */
    public void remove(Evento event);
    
    /**
     *
     * @return
     */
    public String count();
    
    /**
     *
     * @param dateMin
     * @param dateMax
     * @return
     */
    public List<Evento> findStartRange(Date dateMin, Date dateMax);
    
    /**
     *
     * @param client
     * @return
     */
    public List<Evento> findEventByClient(Client client);
    
    /**
     *
     * @param dateMin
     * @param dateMax
     * @return
     */
    public List<Evento> findRange(Date dateMin, Date dateMax);
    
    /**
     *
     * @param dateMin
     * @param dateMax
     * @return
     */
    public List<Evento> findEndRange(Date dateMin, Date dateMax);
    
    /**
     *
     * @param dateMin
     * @param dateMax
     * @param client
     * @return
     */
    public List<Evento> findEndRangeClient(Date dateMin, Date dateMax, Client client);
    
    /**
     *
     * @param dateMin
     * @param dateMax
     * @return
     */
    public List<Evento> findDateRange(Date dateMin, Date dateMax);
    
    /**
     *
     * @param id
     * @return
     */
    public Evento find(Integer id);
    
    /**
     *
     * @return
     */
    public List<Evento> findAll();
    
    /**
     *
     * @param year
     * @return
     */
    public List<Evento> deleteOldestEvents(Integer year);
    
}
