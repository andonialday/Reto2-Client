/*
 * Para cambiar este encabezado de licencia, elija Encabezados de licencia en Propiedades del proyecto.
 * Para cambiar este archivo de plantilla, elija Herramientas | Plantillas
 * y abra la plantilla en el editor.
 */
package reto2g1cclient.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase con los parámetros para la creación y gestión de usuarios
 *
 * @author Jaime San Sebastián y Enaitz Izagirre
 */
@XmlRootElement
public class User implements Serializable {

    private Integer id;
    private String login;
    private String email;
    private String fullName;
    private UserStatus status;
    private Privilege privilege;
    private String password;
    private Date lastPasswordChange;

    public User(Integer id, String login, String email, String fullName, 
            UserStatus status, Privilege privilege, String password, 
            Date lastPasswordChange) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.fullName = fullName;
        this.status = status;
        this.privilege = privilege;
        this.password = password;
        this.lastPasswordChange = lastPasswordChange;
    }

    public User() {
        
    }

    
    /**
     * Método Getter para obtener la ID del usuario
     *
     * @return el id de un usuario
     */
    public Integer getId() {
        return id;
    }

    /**
     * Método Setter para asignar una ID al usuario
     *
     * @param id el id de un usuario a establecer
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Método Getter para obtener el login del usuario
     *
     * @return el login de un usuario
     */
    public String getLogin() {
        return login;
    }

    /**
     * Método Setter para asignar un login al usuario
     *
     * @param login el login de un usuario a establecer
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Métogo Getter para obtener el email del usuario
     *
     * @return el email de un usuario
     */
    public String getEmail() {
        return email;
    }

    /**
     * Método Setter para asignar el email al usuario
     *
     * @param email el email de un usuario a establecer
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Método Getter para obtener el Nombre Completo del usuario
     *
     * @return el nombre completo de un usuario
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Método Setter para asignar el Nombre Completo al usuario
     *
     * @param fullName el nombre completo de un usuario a establecer
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Método Getter para obtener el estado de la cuenta del usuario
     *
     * @return el estado de la cuente de un usuario
     */
    public UserStatus getStatus() {
        return status;
    }

    /**
     * Método Setter para determinar el estado de la cuenta del usuario
     *
     * @param status el estado de la cuenta del usuario
     */
    public void setStatus(UserStatus status) {
        this.status = status;
    }

    /**
     * Método Getter para obtener el nivel de privilegio del usuario
     *
     * @return el privilegio de un usuario
     */
    public Privilege getPrivilege() {
        return privilege;
    }

    /**
     * Método Setter para asignar el nivel de privilegio al usuario
     *
     * @param privilege el privilegio de un usuario a establecer
     */
    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    /**
     * Método Getter para obtener la contraseña del usuario
     *
     * @return la contraseña de un usuario
     */
    public String getPassword() {
        return password;
    }

    /**
     * Método Setter para asignar una contraseña al usuario
     *
     * @param password la contraseña de un usuario a establecer
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Método Getter para obtener el momento del último cambio de contraseña del
     * usuario
     *
     * @return el cambio de contraseña de un usuario
     */
    public Date getLastPasswordChange() {
        return lastPasswordChange;
    }

    /**
     * Método Getter para obtener el momento del último cambio de contraseña del
     * usuario
     *
     * @param lastPasswordChange el cambio de contraseña de un usuario a
     * establecer
     */
    public void setLastPasswordChange(Date lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    /**
     * Método toString que convierte a String el objeto usuario
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", login=" + login + ", email=" + email + ", fullName=" + fullName + ", status=" + status + ", privilege=" + privilege + ", password=" + password + ", lastPasswordChange=" + lastPasswordChange + '}';
    }

    /**
     * Método hashCode que complementa al método equals 
     * y sirve para comparar los datos del objeto usuario.
     * Devuelve un número entero.
     * 
     * @return int
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
        hash = 73 * hash + Objects.hashCode(this.login);
        hash = 73 * hash + Objects.hashCode(this.email);
        hash = 73 * hash + Objects.hashCode(this.fullName);
        hash = 73 * hash + Objects.hashCode(this.status);
        hash = 73 * hash + Objects.hashCode(this.privilege);
        hash = 73 * hash + Objects.hashCode(this.password);
        hash = 73 * hash + Objects.hashCode(this.lastPasswordChange);
        return hash;
    }

    /**
     * Método equals que compara los datos del objeto usuario, 
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
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.fullName, other.fullName)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (this.privilege != other.privilege) {
            return false;
        }
        if (!Objects.equals(this.lastPasswordChange, other.lastPasswordChange)) {
            return false;
        }
        return true;
    }

}
