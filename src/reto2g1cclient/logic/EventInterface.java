/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.logic;

import java.util.Collection;
import java.util.Date;
import reto2g1cclient.exception.ClientServerConnectionException;
import reto2g1cclient.exception.DBServerException;
import reto2g1cclient.model.Client;
import reto2g1cclient.model.Evento;

/**
 *
 * @author Andoni Alday
 */
public interface EventInterface {

    /**
     * Método para recibir peticiones de POST - Create para añadir datos
     * pertenecientes a la entidad a la BBDD
     *
     * @param event con los datos de la nueva entrada en la BBDD
     * @throws reto2g1cclient.exception.DBServerException si hay un error en la
     * realizacion del proceso en el servidor
     * @throws reto2g1cclient.exception.ClientServerConnectionException
     */
    public void createEvent(Evento event) throws DBServerException, ClientServerConnectionException;

    /**
     *
     * @param event
     * @throws reto2g1cclient.exception.DBServerException
     * @throws reto2g1cclient.exception.ClientServerConnectionException
     */
    public void edit(Evento event) throws DBServerException, ClientServerConnectionException;

    /**
     *
     * @param event
     * @throws reto2g1cclient.exception.DBServerException
     * @throws reto2g1cclient.exception.ClientServerConnectionException
     */
    public void remove(Evento event) throws DBServerException, ClientServerConnectionException;

    /**
     *
     * @return @throws reto2g1cclient.exception.DBServerException
     */
    public String count() throws DBServerException;

    /**
     *
     * @param dateMin
     * @param dateMax
     * @return
     * @throws reto2g1cclient.exception.DBServerException
     */
    public Collection<Evento> findStartRange(Date dateMin, Date dateMax) throws DBServerException;

    /**
     *
     * @param client
     * @return
     * @throws reto2g1cclient.exception.DBServerException
     */
    public Collection<Evento> findEventByClient(Client client) throws DBServerException;

    /**
     *
     * @param dateMin
     * @param dateMax
     * @return
     * @throws reto2g1cclient.exception.DBServerException
     */
    public Collection<Evento> findRange(Date dateMin, Date dateMax) throws DBServerException;

    /**
     *
     * @param dateMin
     * @param dateMax
     * @return
     * @throws reto2g1cclient.exception.DBServerException
     */
    public Collection<Evento> findEndRange(Date dateMin, Date dateMax) throws DBServerException;

    /**
     *
     * @param dateMin
     * @param dateMax
     * @param client
     * @return
     * @throws reto2g1cclient.exception.DBServerException
     */
    public Collection<Evento> findEndRangeClient(Date dateMin, Date dateMax, Client client) throws DBServerException;

    /**
     *
     * @param dateMin
     * @param dateMax
     * @return
     * @throws reto2g1cclient.exception.DBServerException
     */
    public Collection<Evento> findDateRange(Date dateMin, Date dateMax) throws DBServerException;

    /**
     *
     * @param id
     * @return
     * @throws reto2g1cclient.exception.DBServerException
     */
    public Evento find(Integer id) throws DBServerException;

    /**
     *
     * @return @throws reto2g1cclient.exception.DBServerException
     * @throws reto2g1cclient.exception.ClientServerConnectionException
     */
    public Collection<Evento> findAll() throws DBServerException, ClientServerConnectionException;

    /**
     *
     * @param year
     * @return
     * @throws reto2g1cclient.exception.DBServerException
     */
    public Collection<Evento> deleteOldestEvents(Integer year) throws DBServerException;

}
