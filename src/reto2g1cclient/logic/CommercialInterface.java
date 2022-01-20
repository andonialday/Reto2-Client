/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.logic;

import entities.Commercial;

/**
 *
 * @author Enaitz Izagirre
 */
public interface CommercialInterface {
    
    /**
     * Este metodo actualiza los datos del propio commercial 
     * @param commercial es una entidad que hereda de user
     */
    public void updateCommercial(Commercial commercial);
    
}
