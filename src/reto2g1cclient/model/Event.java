/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad Event para la gestion de eventos
 *
 * @author Andoni Alday
 */
@XmlRootElement
public class Event implements Serializable {

    private Integer id;

    private Date dateStart;

    private Date dateEnd;

    private String description;

    private Client client;
    
    private Set<EventEquipment> equipments;

    /**
     * Método Getter para obtener la ID del Event
     * @return ID del Event
     */
    public Integer getId() {
        return id;
    }

    /**
     * Método Setter para definir la ID del Event
     * @param id a asignar al Event
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Método Getter para obtener la DateStart <i>(Fecha de Inicio)</i> del Event
     * @return DateStart <i>(Fecha de Inicio)</i> del Event
     */
    public Date getDateStart() {
        return dateStart;
    }

    /**
     * Método Setter para definir la DateStart <i>(Fecha de Inicio)</i> del Event
     * @param dateStart <i>(Fecha de Inicio)</i> a asignar al Event
     */
    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    /**
     * Método Getter para obtener la DateEnd <i>(Fecha de Fin)</i> del Event
     * @return DateEnd <i>(Fecha de Fin)</i> del Event
     */
    public Date getDateEnd() {
        return dateEnd;
    }

    /**
     * Método Setter para asignar la DateEnd <i>(Fecha de Fin)</i> al Event
     * @param dateEnd <i>(Fecha de Fin)</i> a asignar al Event
     */
    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    /**
     * Método Getter para obtener la Description <i>(Descripcion)</i> del Event
     * @return Description <i>(Descripcion)</i> del Event
     */
    public String getDescription() {
        return description;
    }

    /**
     * Método Setter para asignar la Description <i>(Descripcion)</i> al Event
     * @param description <i>(Descripcion)</i> a asignar al Event
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Método Getter para obtener el Client <i>(Client)</i> "propietario" del Event
     * @return Client<i>(Cliente)</i> "propietario" del Event
     */
    public Client getClient() {
        return client;
    }

    /**
     * Método Setter para asignar el Client <i>(Client)</i> "propietario" al Event
     * @param client Client <i>(Cliente)</i> "propietario" a asignar al Event
     */
    public void setClient(Client client) {
        this.client = client;
    }
     
    /**
     * Método Getter para obtener los Equipments <i>(Equipamientos empleados)</i> del Event
     * @return Equipments <i>(Equipamientos empleados)</i> del Event
     */
    public Set<EventEquipment> getEquipments() {
        return equipments;
    }

    /**
     * Método Setter para asignar los Equipments <i>(Equipamientos empleados)</i> al Event
     * @param equipments Equipments <i>(Equipamientos empleados)</i> a asignar al Event
     */
    public void setEquipments(Set<EventEquipment> equipments) {
        this.equipments = equipments;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd + ", description=" + description + ", client=" + client + ", equipments=" + equipments + '}';
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.dateStart);
        hash = 13 * hash + Objects.hashCode(this.dateEnd);
        hash = 13 * hash + Objects.hashCode(this.description);
        hash = 13 * hash + Objects.hashCode(this.client);
        hash = 13 * hash + Objects.hashCode(this.equipments);
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
        final Event other = (Event) obj;
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.dateStart, other.dateStart)) {
            return false;
        }
        if (!Objects.equals(this.dateEnd, other.dateEnd)) {
            return false;
        }
        if (!Objects.equals(this.client, other.client)) {
            return false;
        }
        if (!Objects.equals(this.equipments, other.equipments)) {
            return false;
        }
        return true;
    }

}
