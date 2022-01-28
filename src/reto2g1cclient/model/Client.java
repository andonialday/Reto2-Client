/*
 * Para cambiar este encabezado de licencia, elija Encabezados de licencia en Propiedades del proyecto.
 * Para cambiar este archivo de plantilla, elija Herramientas | Plantillas
 * y abra la plantilla en el editor.
 */
package reto2g1cclient.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Clase con los parámetros para la creación y gestión de clientes
 *
 * @author Jaime San Sebastián
 */
@XmlRootElement
public class Client extends User implements Serializable{

    private Type type;

    private Set<Evento> events;

    private Commercial commercial;

    /**
     * Método Getter para obtener el tipo de un cliente
     *
     * @return el tipo de un cliente
     */
    public Type getType() {
        return type;
    }

    /**
     * Método Setter para asignar el tipo a un cliente
     *
     * @param type el tipo de un cliente a establecer
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Método Getter para obtener los eventos del cliente
     *
     * @return los eventos de un cliente
     */
    public Set<Evento> getEvents() {
        return events;
    }

    /**
     * Método Setter para asignar eventos a un cliente
     *
     * @param events los eventos de un cliente a establecer
     */
    public void setEvents(Set<Evento> events) {
        this.events = events;
    }

    /**
     * Método Geter para obtener el Comercial asignado a un cliente
     *
     * @return el comercial de un cliente
     */
    @XmlTransient
    public Commercial getCommercial() {
        return commercial;
    }

    /**
     * Método Setter para asignar un Comercial al cliente
     *
     * @param commercial el comercial de un cliente a establecer
     */
    public void setCommercial(Commercial commercial) {
        this.commercial = commercial;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Client{" + "Type=" + type + ", events=" + events + ", commercial=" + commercial + '}';
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.type);
        hash = 37 * hash + Objects.hashCode(this.events);
        hash = 37 * hash + Objects.hashCode(this.commercial);
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
        final Client other = (Client) obj;
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.events, other.events)) {
            return false;
        }
        if (!Objects.equals(this.commercial, other.commercial)) {
            return false;
        }
        return true;
    }

}
