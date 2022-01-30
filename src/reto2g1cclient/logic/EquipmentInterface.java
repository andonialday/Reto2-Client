/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.logic;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ws.rs.PathParam;
import reto2g1cclient.exception.DBServerException;
import reto2g1cclient.model.Equipment;

/**
 *
 * @author Aitor Perez
 */
public interface EquipmentInterface {
    
    
    public Collection<Equipment> findCostRange(Double min, Double max)throws DBServerException;
    
    public void create(Equipment equipment)throws DBServerException;
    
    public void edit(Equipment equipment)throws DBServerException;
    
    public void remove(Equipment equipment)throws DBServerException;
    
    public Equipment find(Integer id)throws DBServerException;
    
     public Collection<Equipment> findAll() throws DBServerException;
     
     public Collection<Equipment> findRange(Integer from, Integer to)throws DBServerException;
     
     public String countREST()throws DBServerException;
     
    public Collection<Equipment> findOrderPreviousDate(Date datePrev)throws DBServerException;
    
    public Collection<Equipment> findOrderAfterDate(Date dateAfter)throws DBServerException;
    
     public Collection<Equipment> deleteOldEquip(Date year)throws DBServerException;
     
      public Collection<Equipment> updateCost(Double ratio)throws DBServerException;
}
