/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Enumeracion Especialization del Commercial, indicando su especialziacion profesional
 * @author Enaitz Izagirre
 */
@XmlRootElement
public enum Especialization implements Serializable{

    /**
     * Opcion 0 de la enumeracion, el Commercial es un técnico de Sonido
     */
    SONIDO,

    /**
     * Enumeration option 1, el Commercial es un técnico de Iluminacion
     */
    ILUMINACION,
    
    /**
     * Enumeration option 2, el Commercial es un técnico de Pirotecnia
     */
    PIROTECNIA,
    
    /**
     * Enumeration option 3, el Commercial es un técnico de Logistica
     */
    LOGISTICA;
}