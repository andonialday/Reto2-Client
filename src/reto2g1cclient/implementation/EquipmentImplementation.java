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
   
    return EquipmentClient.findCostRange(List.class, min.toString(), max.toString());
    }

    @Override
    public void create(Equipment equipment) {
    EquipmentClient.create(equipment);
    }

    @Override
    public void edit(Integer id, Equipment equipment) {
    
    EquipmentClient.edit(equipment, equipment.getId().toString());
    }

    @Override
    public void remove(Equipment equipment) {
    
    EquipmentClient.remove(equipment.getId().toString());
    }

    @Override
    public Equipment find(Equipment equipment,Integer id) {
   
        return EquipmentClient.find(Equipment.class, id.toString());
    }

    @Override
    public List<Equipment> findAll() {
    return EquipmentClient.findAll(List.class);
    }

    @Override
    public List<Equipment> findRange(Integer from, Integer to) {
    return EquipmentClient.findRange(List.class, from.toString(), to.toString());
    }

    @Override
    public String countREST() {
    return EquipmentClient.countREST();
    }

    @Override
    public List<Equipment> findOrderPreviousDate(Date datePrev) {
    return EquipmentClient.findOrderPreviousDate(List.class, datePrev.toString());
    }

    @Override
    public List<Equipment> findOrderAfterDate(Date dateAfter) {
    return EquipmentClient.findOrderAfterDate(List.class, dateAfter.toString());
    }

    @Override
    public List<Equipment> deleteOldEquip(Date year) {
    return EquipmentClient.deleteOldEquip(List.class, year.toString());
    }

    
    
}
