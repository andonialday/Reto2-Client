/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.logic;

import reto2g1cclient.implementation.EquipmentImplementation;

/**
 *
 * @author Aitor Perez
 */
public class EquipmentFactory {
    
    public static EquipmentInterface getImplementation(){
        EquipmentInterface ei = new EquipmentImplementation();
        return ei;
    }
    
}
