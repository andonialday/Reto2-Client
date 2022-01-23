/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.model;

import java.io.Serializable;

/**
 * Clase para generación de ID Combinada de la relación EventEquipment 
 * @author Andoni Alday , Aitor Perez
 */
public class EventEquipmentId implements Serializable{
    
    private Integer eventId;
    private Integer equipmentId;

    /**
     * Método Getter de la ID del Event de la relación EventEquipment
     * @return eventId ID del Event de la relación EventEquipment
     */
    public Integer getEventId() {
        return eventId;
    }

    /**
     * Método Setter para asignar un Event a la relación EventEquipment
     * @param eventId ID del Event a asignar a la relación EventEquipment
     */
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    /**
     * Método Getter de la ID del Equipment de la relación EventEquipment
     * @return equipmentId ID del Equipment de la relación EventEquipment
     */
    public Integer getEquipmentId() {
        return equipmentId;
    }

    /**
     * Método Setter para asignar un Equipment a la relación EventEquipment
     * @param equipmentId ID del Equipment a asignar a la relación EventEquipment
     */
    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }


}