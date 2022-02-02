/*
 * Para cambiar este encabezado de licencia, elija Encabezados de licencia en Propiedades del proyecto.
 * Para cambiar este archivo de plantilla, elija Herramientas | Plantillas
 * y abra la plantilla en el editor.
 */
package reto2g1cclient.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javafx.beans.property.SimpleObjectProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase con los parámetros para la creación y gestión de clientes
 *
 * @author Jaime San Sebastián
 */
@XmlRootElement
public class Client extends User implements Serializable{
    
    private Type type;
    private Set<Event> events;
    private Commercial commercial;

    public Client() {
        
    }

    
    public Client(Type type, Set<Event> events, Commercial commercial) {
        this.type = type;
        this.events = events;
        this.commercial = commercial;
    }

    
    
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
    public Set<Event> getEvents() {
        return events;
    }

    /**
     * Método Setter para asignar eventos a un cliente
     *
     * @param events los eventos de un cliente a establecer
     */
    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    /**
     * Método Geter para obtener el Comercial asignado a un cliente
     *
     * @return el comercial de un cliente
     */
    public Commercial getCommercial() {
        return commercial;
    }

    /**
     * Método Setter para asignar un Comercial al cliente
     *
     * @param commercial el comercial de un cliente a establecer
     */
    public void setComercial(Commercial commercial) {
        this.commercial = commercial;
    }

    /**
     * Método toString que convierte a String el objeto cliente
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "Client{" + "Type=" + type 
                + ", events=" + events 
                + ", comercial=" + commercial + '}';
    }

    /**
     * Método hashCode que complementa al método equals 
     * y sirve para comparar los datos del objeto cliente.
     * Devuelve un número entero.
     * 
     * @return int
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
     * Método equals que compara los datos del objeto cliente, 
     * para saber si son del mismo tipo y tienen los mismos datos.
     * Nos devuelve el valor true si son iguales
     * y el valor false si no lo son.
     *  
     * @param obj
     * @return boolean
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
