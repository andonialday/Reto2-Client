/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.logic;

import java.util.Date;
import java.util.List;
import reto2g1cclient.model.Equipment;

/**
 *
 * @author Aitor Perez
 */
public interface EquipmentInterface {
    
    
    public List<Equipment> findCostRange(Double min, Double max);
    
    public void create();
    
    public void edit(Integer id, Equipment equipment);
    
    public void remove( Integer id);
    
    public Equipment find(Integer id);
    
     public List<Equipment> findAll();
     
     public List<Equipment> findRange(Integer from, Integer to);
     
     public String countREST();
     
    public List<Equipment> findOrderPreviousDate(Date datePrev);
    
    public List<Equipment> findOrderAfterDate(Date dateAfter);
    
     public List<Equipment> deleteOldEquip(Date year);
}
