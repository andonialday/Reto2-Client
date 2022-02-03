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
import reto2g1cclient.model.Equipment;

/**
 *
 * @author Aitor Perez
 */
public interface EquipmentInterface {
    
    /**
     *
     * @param min
     * @param max
     * @return
     * @throws DBServerException
     * @throws ClientServerConnectionException
     */
    public Collection<Equipment> findCostRange(Double min, Double max)throws DBServerException, ClientServerConnectionException;
    
    /**
     *
     * @param equipment
     * @throws DBServerException
     * @throws ClientServerConnectionException
     */
    public void create(Equipment equipment)throws DBServerException, ClientServerConnectionException;
    
    /**
     *
     * @param equipment
     * @throws DBServerException
     * @throws ClientServerConnectionException
     */
    public void edit(Equipment equipment)throws DBServerException, ClientServerConnectionException;
    
    /**
     *
     * @param equipment
     * @throws DBServerException
     * @throws ClientServerConnectionException
     */
    public void remove(Equipment equipment)throws DBServerException, ClientServerConnectionException;
    
    /**
     *
     * @param id
     * @return
     * @throws DBServerException
     * @throws ClientServerConnectionException
     */
    public Equipment find(Integer id)throws DBServerException, ClientServerConnectionException ;
    
    /**
     *
     * @return
     * @throws DBServerException
     * @throws ClientServerConnectionException
     */
    public Collection<Equipment> findAll() throws DBServerException, ClientServerConnectionException;
     
    /**
     *
     * @param from
     * @param to
     * @return
     * @throws DBServerException
     * @throws ClientServerConnectionException
     */
    public Collection<Equipment> findRange(Integer from, Integer to)throws DBServerException, ClientServerConnectionException;
     
    /**
     *
     * @return
     * @throws DBServerException
     * @throws ClientServerConnectionException
     */
    public String countREST()throws DBServerException, ClientServerConnectionException;
     
    /**
     *
     * @param datePrev
     * @return
     * @throws DBServerException
     * @throws ClientServerConnectionException
     */
    public Collection<Equipment> findOrderPreviousDate(Date datePrev)throws DBServerException, ClientServerConnectionException;
    
    /**
     *
     * @param dateAfter
     * @return
     * @throws DBServerException
     * @throws ClientServerConnectionException
     */
    public Collection<Equipment> findOrderAfterDate(Date dateAfter)throws DBServerException, ClientServerConnectionException;
    
    /**
     *
     * @param year
     * @return
     * @throws DBServerException
     * @throws ClientServerConnectionException
     */
    public Collection<Equipment> deleteOldEquip(Date year)throws DBServerException, ClientServerConnectionException;
     
    /**
     *
     * @param ratio
     * @return
     * @throws DBServerException
     * @throws ClientServerConnectionException
     */
    public Collection<Equipment> updateCost(Double ratio)throws DBServerException, ClientServerConnectionException;
}
