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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;


import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
/**
 * Entidad Equipment para gestion y control de Equipamiento
 * @author Aitor Perez
 */

@XmlRootElement
public class Equipment implements Serializable {
    
    

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty description;
    private final SimpleStringProperty name;
    private final SimpleStringProperty dateAdd;
    private final SimpleStringProperty cost;
    private final SimpleObjectProperty<Set<EventEquipment>> events;
    
    public Equipment(Integer id, String name, String description, String cost, String dateAdd, Set<EventEquipment> events){
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.dateAdd = new SimpleStringProperty(dateAdd);
        this.cost = new SimpleStringProperty(cost);
        this.description = new SimpleStringProperty(description);
        this.events = new SimpleObjectProperty<>(events);
    }
    
    
    /**
     * Metodo Getter para obtener el Name del Equipamiento
     * @return el Nombre del equipamiento
     */
    public String getName() {
        return this.name.get();
    }
    
     /**
     * Metodo Setter para obtener el Name del Equipamiento
     * @param name el Nombre del equipamiento
     */
    public void setName(String name) {
        this.name.set(name);
    }
    
    /**
     * Metodo Getter para obtener la ID del Equipamiento
     * @return ID del equipamiento
     */
    public Integer getId() {
        return this.id.get();
    }

    /**
     * Metodo Setter para definir el ID del Equipamiento
     * @param id a asignar al Equipamiento
     */
    public void setId(Integer id) {
        this.id.set(id);
    }

    /**
     * Metodo Getter para obtener la Descripcion del Equipamiento
     * @return Descripcion del Equipamiento
     */
    public String getDescription() {
        return this.description.get();
    }

    /**
     * Metodo Setter para definir la Descripcion del Equipamiento
     * @param description a asignar al Equipamiento
     */
    public void setDescription(String description) {
        this.description.set(description);
    }

    /**
     * Metodo Getter para obtener la DateAdd <i>(Fecha de alta)</i> del Equipamiento
     * @return DateAdd <i>(Fecha de alta)</i> del Equipamiento
     */
    public String getDateAdd() {
        return this.dateAdd.get();
    }

    /**
     * Metodo Setter para definir el DateAdd <i>(Fecha de alta)</i> del Equipamiento
     * @param dateAdd <i>(Fecha de alta)</i> a asignar al Equipamiento
     */
    public void setDateAdd(String dateAdd) {
        this.dateAdd.set(dateAdd);
    }

    /**
     * Metodo Getter para obtener el Cost <i>(Coste)</i> del Equipamiento
     * @return Cost<i>(Coste)</i> del Equipamiento
     */
    public String getCost() {
        return this.cost.get();
    }

    /**
     * Metodo Setter para definir el Cost <i>(Coste)</i> del Equipamiento
     * @param cost <i>(Coste)</i> a asignar al Equipamiento
     */
    public void setCost(String cost) {
        this.cost.set(cost);
    }

    /**
     * Metodo Getter para obtener los Event <i>(Eventos)</i> asignados al Equipamiento
     * @return Events <i>(Eventos)</i> del Equipamiento
     */
    @XmlTransient
    public Set<EventEquipment> getEvents() {
        return this.events.get();
    }

    /**
     * Metodo Setter para definir los Event <i>(Eventos)</i> vinculados al Equipamiento
     * @param events <i>(Eventos)</i> vinculados al Equipamiento
     */
    public void setEvents(Set<EventEquipment> events) {
        this.events.set(events);
    }

    @Override
    public String toString() {
        return "Equipment{" + "id=" + id + ", description=" + description + ", dateAdd=" + dateAdd + ", cost=" + cost +
               ", events=" + events + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.description);
        hash = 67 * hash + Objects.hashCode(this.dateAdd);
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.cost);
        hash = 67 * hash + Objects.hashCode(this.events);
        return hash;
    }

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
        final Equipment other = (Equipment) obj;
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.dateAdd, other.dateAdd)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.cost, other.cost)) {
            return false;
        }
        if (!Objects.equals(this.events, other.events)) {
            return false;
        }
        return true;
    }
   
    
    
}
