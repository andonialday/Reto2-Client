/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entidad Evento para la gestion de eventos
 *
 * @author Andoni Alday
 */
@XmlRootElement
public class Evento implements Serializable {

    private final SimpleStringProperty id;

    private final SimpleStringProperty dateStart;

    private final SimpleStringProperty dateEnd;

    private final SimpleStringProperty description;

    private final SimpleStringProperty name;

    private final SimpleObjectProperty<Client> client;

    private final SimpleObjectProperty<Set<EventEquipment>> equipments;

    /**
     * Constructor con atributos para el objeto Evento
     *
     * @param id valor de la propiedad id
     * @param dateStart valor de la propiedad dateStart
     * @param dateEnd valor de la propiedad dateEnd
     * @param description valor de la propiedad description
     * @param name valor de la propiedad name
     * @param client valor de la propiedad client
     * @param equipments valor de la propiedad equipments
     */
    public Evento(String id, String dateStart, String dateEnd, String description, String name, Client client, Set<EventEquipment> equipments) {
        this.id = new SimpleStringProperty(id);
        this.dateStart = new SimpleStringProperty(dateStart);
        this.dateEnd = new SimpleStringProperty(dateEnd);
        this.description = new SimpleStringProperty(description);
        this.name = new SimpleStringProperty(name);
        this.client = new SimpleObjectProperty<>(client);
        this.equipments = new SimpleObjectProperty<>(equipments);
    }

    /**
     * Constructor vacío para el objeto Evento
     */
    public Evento() {
        this.id = new SimpleStringProperty();
        this.dateStart = new SimpleStringProperty();
        this.dateEnd = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.client = new SimpleObjectProperty<>();
        this.equipments = new SimpleObjectProperty<>();
    }
    
    /**
     * Método Getter para obtener la ID del Evento
     *
     * @return ID del Evento
     */
    public String getId() {
        return this.id.get();
    }

    /**
     * Método Setter para definir la ID del Evento
     *
     * @param id a asignar al Evento
     */
    public void setId(String id) {
        this.id.set(id);
    }

    /**
     * Método Getter para obtener la DateStart <i>(Fecha de Inicio)</i> del
     * Evento
     *
     * @return DateStart <i>(Fecha de Inicio)</i> del Evento
     */
    public String getDateStart() {
        return this.dateStart.get();
    }

    /**
     * Método Setter para definir la DateStart <i>(Fecha de Inicio)</i> del
     * Evento
     *
     * @param dateStart <i>(Fecha de Inicio)</i> a asignar al Evento
     */
    public void setDateStart(String dateStart) {
        this.dateStart.set(dateStart);
    }

    /**
     * Método Getter para obtener la DateEnd <i>(Fecha de Fin)</i> del Evento
     *
     * @return DateEnd <i>(Fecha de Fin)</i> del Evento
     */
    public String getDateEnd() {
        return this.dateEnd.get();
    }

    /**
     * Método Setter para asignar la DateEnd <i>(Fecha de Fin)</i> al Evento
     *
     * @param dateEnd <i>(Fecha de Fin)</i> a asignar al Evento
     */
    public void setDateEnd(String dateEnd) {
        this.dateEnd.set(dateEnd);
    }

    /**
     * Método Getter para obtener la Description <i>(Descripcion)</i> del Evento
     *
     * @return Description <i>(Descripcion)</i> del Evento
     */
    public String getDescription() {
        return this.description.get();
    }

    /**
     * Método Setter para asignar la Description <i>(Descripcion)</i> al Evento
     *
     * @param description <i>(Descripcion)</i> a asignar al Evento
     */
    public void setDescription(String description) {
        this.description.set(description);
    }

    /**
     * Método Getter para obtener el Name <i>(Nombre)</i> del Evento
     *
     * @return Name <i>(Nombre)</i> del Evento
     */
    public String getName() {
        return this.name.get();
    }

    /**
     * Método Setter para asignar el Name <i>(Nombre)</i> al Evento
     *
     * @param name <i>(Nombre)</i> a asignar al Evento
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Método Getter para obtener el Client <i>(Client)</i> "propietario" del
     * Evento
     *
     * @return Client<i>(Cliente)</i> "propietario" del Evento
     */
    public Client getClient() {
        return this.client.get();
    }

    /**
     * Método Setter para asignar el Client <i>(Client)</i> "propietario" al
     * Evento
     *
     * @param client Client <i>(Cliente)</i> "propietario" a asignar al Evento
     */
    public void setClient(Client client) {
        this.client.set(client);
    }

    /**
     * Método Getter para obtener los Equipments <i>(Equipamientos
     * empleados)</i> del Evento
     *
     * @return Equipments <i>(Equipamientos empleados)</i> del Evento
     */
    @XmlTransient
    public Set<EventEquipment> getEquipments() {
        return this.equipments.get();
    }

    /**
     * Método Setter para asignar los Equipments <i>(Equipamientos
     * empleados)</i> al Evento
     *
     * @param equipments Equipments <i>(Equipamientos empleados)</i> a asignar
     * al Evento
     */
    public void setEquipments(Set<EventEquipment> equipments) {
        this.equipments.set(equipments);
    }

    /**
     * Método ToString del objeto Evento
     *
     * @return conversión a String del objeto Evento
     */
    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd + ", description=" + description + ", client=" + client + ", equipments=" + equipments + '}';
    }

    /**
     * Método HashCode del objeto Evento
     *
     * @return código hash del objeto Evento
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
     * Método Equals para comparar objetos Evento
     *
     * @param obj Evento con el que se va a comparar
     * @return booleano indicativo de si los objetos son iguales
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
        final Evento other = (Evento) obj;
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
