/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.logic;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import reto2g1cclient.exception.DBServerException;
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
    public Collection<Evento> findStartRange(Date dateMin, Date dateMax);
    
    /**
     *
     * @param client
     * @return
     */
    public Collection<Evento> findEventByClient(Client client);
    
    /**
     *
     * @param dateMin
     * @param dateMax
     * @return
     */
    public Collection<Evento> findRange(Date dateMin, Date dateMax);
    
    /**
     *
     * @param dateMin
     * @param dateMax
     * @return
     */
    public Collection<Evento> findEndRange(Date dateMin, Date dateMax);
    
    /**
     *
     * @param dateMin
     * @param dateMax
     * @param client
     * @return
     */
    public Collection<Evento> findEndRangeClient(Date dateMin, Date dateMax, Client client);
    
    /**
     *
     * @param dateMin
     * @param dateMax
     * @return
     */
    public Collection<Evento> findDateRange(Date dateMin, Date dateMax);
    
    /**
     *
     * @param id
     * @return
     */
    public Evento find(Integer id);
    
    /**
     *
     * @return
     * @throws reto2g1cclient.exception.DBServerException
     */
    public Collection<Evento> findAll() throws DBServerException;
    
    /**
     *
     * @param year
     * @return
     */
    public Collection<Evento> deleteOldestEvents(Integer year);
    
}
