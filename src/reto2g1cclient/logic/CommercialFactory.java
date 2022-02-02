/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.logic;

import reto2g1cclient.implementation.CommercialImplementation;

/**
 *
 * @author Enaitz Izagirre
 */
public class CommercialFactory {
    
    public static CommercialInterface getImplementation(){
        CommercialInterface coI = new CommercialImplementation();
        return coI;
    }
    
}