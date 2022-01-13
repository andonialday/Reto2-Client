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
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
/**
 * Entidad Equipment para gestion y control de Equipamiento
 * @author Aitor Perez
 */
@NamedQueries({
  @NamedQuery(
    name="findCostRange", query="SELECT q FROM Equipment q WHERE q.cost>:cost1 and q.cost<:cost2"),
  @NamedQuery(
    name="findOrderPreviousDate", query="SELECT q FROM Equipment q WHERE q.dateAdd<:date1"),
  @NamedQuery(
    name="findOrderAfterDate", query="SELECT q FROM Equipment q WHERE q.dateAdd>:date1"),
   @NamedQuery(
    name="deleteOldEquip", query="DELETE FROM Equipment q WHERE q.dateAdd<:date1"),
  /* name="deleteOldEquip", query="DELETE FROM Equipment WHERE dateAdd<:current_date'-'365*year"),*/
})
@Entity
@Table(name = "EQUIPMENT", schema="reto2g1c")
@XmlRootElement
public class Equipment implements Serializable {
    
    
    @Id
    @GeneratedValue
    private Integer id;
    private String description;
    @Temporal (TemporalType.DATE)
    private Date dateAdd;
    private Double cost;
    @OneToMany(cascade = ALL , mappedBy = "equipment")
    private Set<EventEquipment> events;
    
    /**
     * Metodo Getter para obtener la ID del Equipamiento
     * @return ID del equipamiento
     */
    public Integer getId() {
        return id;
    }

    /**
     * Metodo Setter para definir el ID del Equipamiento
     * @param id a asignar al Equipamiento
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Metodo Getter para obtener la Descripcion del Equipamiento
     * @return Descripcion del Equipamiento
     */
    public String getDescription() {
        return description;
    }

    /**
     * Metodo Setter para definir la Descripcion del Equipamiento
     * @param description a asignar al Equipamiento
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Metodo Getter para obtener la DateAdd <i>(Fecha de alta)</i> del Equipamiento
     * @return DateAdd <i>(Fecha de alta)</i> del Equipamiento
     */
    public Date getDateAdd() {
        return dateAdd;
    }

    /**
     * Metodo Setter para definir el DateAdd <i>(Fecha de alta)</i> del Equipamiento
     * @param dateAdd <i>(Fecha de alta)</i> a asignar al Equipamiento
     */
    public void setDateAdd(Date dateAdd) {
        this.dateAdd = dateAdd;
    }

    /**
     * Metodo Getter para obtener el Cost <i>(Coste)</i> del Equipamiento
     * @return Cost<i>(Coste)</i> del Equipamiento
     */
    public Double getCost() {
        return cost;
    }

    /**
     * Metodo Setter para definir el Cost <i>(Coste)</i> del Equipamiento
     * @param cost <i>(Coste)</i> a asignar al Equipamiento
     */
    public void setCost(Double cost) {
        this.cost = cost;
    }

    /**
     * Metodo Getter para obtener los Event <i>(Eventos)</i> asignados al Equipamiento
     * @return Events <i>(Eventos)</i> del Equipamiento
     */
    @XmlTransient
    public Set<EventEquipment> getEvents() {
        return events;
    }

    /**
     * Metodo Setter para definir los Event <i>(Eventos)</i> vinculados al Equipamiento
     * @param events <i>(Eventos)</i> vinculados al Equipamiento
     */
    public void setEvents(Set<EventEquipment> events) {
        this.events = events;
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
        if (!Objects.equals(this.cost, other.cost)) {
            return false;
        }
        if (!Objects.equals(this.events, other.events)) {
            return false;
        }
        return true;
    }
   
    
    
}
