/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.model;

import java.io.Serializable;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad EventEquipment, representativa de la relacion entre os Event y los
 * Equipment, indicando la Quantity <i>(Cantidad de Equipment)</i> de la
 * relación
 *
 * @author Andoni Alday , Aitor Perez
 */
@XmlRootElement
public class EventEquipment implements Serializable {

    private EventEquipmentId eventEquipmentId;

    private Event event;

    private Equipment equipment;

    private Integer quantity;

    /**
     * Método Getter para obtener la ID combinada de Event y Equipment
     *
     * @return eventEquipmentId ID combinada de Event y Equipment
     */
    public EventEquipmentId getEventEquipmentId() {
        return eventEquipmentId;
    }

    /**
     * Método Setter para asignar una ID combinada de Event y Equipment
     *
     * @param eventEquipmentId ID combinada de Event y Equipment
     */
    public void setEventEquipmentId(EventEquipmentId eventEquipmentId) {
        this.eventEquipmentId = eventEquipmentId;
    }

    /**
     * Método Getter para obtener el Event de la relación
     *
     * @return event Event de la relación
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Método Setter para asignar un Event a la relación
     *
     * @param event Event a asignar a la relación
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * Método Getter para obtener el Equipment de la relación
     *
     * @return equipment Equipment de la relación
     */
    public Equipment getEquipment() {
        return equipment;
    }

    /**
     * Método Setter para asignar un Equipment a la relación
     *
     * @param equipment Equipment a asignar a la relación
     */
    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    /**
     * Método Getter para obtener la Quantity <i>(Cantidad de Equipment)</i> de
     * la relación
     *
     * @return quantity Quantity <i>(Cantidad de Equipment)</i> de la relación
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Método Setter para asignar una Quantity <i>(Cantidad de Equipment)</i> a
     * la relación
     *
     * @param quantity Quantity <i>(Cantidad de Equipment)</i> a asignar a la
     * relación
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.event);
        hash = 53 * hash + Objects.hashCode(this.equipment);
        hash = 53 * hash + Objects.hashCode(this.quantity);
        return hash;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EventEquipment other = (EventEquipment) obj;
        if (!Objects.equals(this.event, other.event)) {
            return false;
        }
        if (!Objects.equals(this.equipment, other.equipment)) {
            return false;
        }
        if (!Objects.equals(this.quantity, other.quantity)) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "EventEquipment{" + "event=" + event + ", equipment=" + equipment + ", quantity=" + quantity + '}';
    }

}