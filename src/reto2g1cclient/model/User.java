/*
 * Para cambiar este encabezado de licencia, elija Encabezados de licencia en Propiedades del proyecto.
 * Para cambiar este archivo de plantilla, elija Herramientas | Plantillas
 * y abra la plantilla en el editor.
 */
package reto2g1cclient.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
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

    private Set<SignIn> signIns;

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
     * Método Getter para obtener los ultimos inicios de sesion de un usuario
     *
     * @return los ultimos inicios de sesion del usuario
     */
    public Set<SignIn> getSignIns() {
        return signIns;
    }

    /**
     * Método Setter para determinar los ultimos inicios de sesion de un usuario
     *
     * @param signIns los ultimos inicios de sesion del usuario
     */
    public void setSignIns(Set<SignIn> signIns) {
        this.signIns = signIns;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", login=" + login + ", email=" + email + ", fullName=" + fullName + ", status=" + status + ", privilege=" + privilege + ", password=" + password + ", lastPasswordChange=" + lastPasswordChange + ", signIns=" + signIns + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.id);
        hash = 19 * hash + Objects.hashCode(this.login);
        hash = 19 * hash + Objects.hashCode(this.email);
        hash = 19 * hash + Objects.hashCode(this.fullName);
        hash = 19 * hash + Objects.hashCode(this.status);
        hash = 19 * hash + Objects.hashCode(this.privilege);
        hash = 19 * hash + Objects.hashCode(this.password);
        hash = 19 * hash + Objects.hashCode(this.lastPasswordChange);
        hash = 19 * hash + Objects.hashCode(this.signIns);
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
        if (!Objects.equals(this.signIns, other.signIns)) {
            return false;
        }
        return true;
    }

}
