/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.implementation;

import java.util.Date;
import java.util.List;
import reto2g1cclient.logic.EquipmentInterface;
import reto2g1cclient.model.Equipment;

/**
 *
 * @author Aitor Perez
 */
public class EquipmentImplementation implements EquipmentInterface{
    private EquipmentJerseyClient EquipmentClient;
    
    public EquipmentImplementation(){
      EquipmentClient = new  EquipmentJerseyClient(); 
    }
    @Override
    public List<Equipment> findCostRange(Double min, Double max) {
   
    return EquipmentClient.findCostRange(responseType, min, max);
    }

    @Override
    public void create(Equipment equipment) {
    EquipmentClient.create(equipment);
    }

    @Override
    public void edit(Integer id, Equipment equipment) {
    
    EquipmentClient.edit(equipment, id);
    }

    @Override
    public void remove(Integer id) {
    
    EquipmentClient.remove(id);
    }

    @Override
    public Equipment find(Integer id) {
   
    return EquipmentClient.find(responseType, id);
    }

    @Override
    public List<Equipment> findAll() {
    return EquipmentClient.findAll(responseType);
    }

    @Override
    public List<Equipment> findRange(Integer from, Integer to) {
    return EquipmentClient.findRange(responseType, from, to);
    }

    @Override
    public String countREST() {
    return EquipmentClient.countREST();
    }

    @Override
    public List<Equipment> findOrderPreviousDate(Date datePrev) {
    return EquipmentClient.findOrderPreviousDate(responseType, datePrev);
    }

    @Override
    public List<Equipment> findOrderAfterDate(Date dateAfter) {
    return EquipmentClient.findOrderAfterDate(responseType, dateAfter);
    }

    @Override
    public List<Equipment> deleteOldEquip(Date year) {
    return EquipmentClient.deleteOldEquip(responseType, year);
    }

    
    
}
