/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.logic;

import reto2g1cclient.implementation.EventImplementation;

/**
 *
 * @author Andoni Alday
 */
public class EventFactory {
    
    public static  EventInterface getImplementation(){
        EventInterface ei = new EventImplementation();
        return ei;
    }
    
}
