/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.logic;

import reto2g1cclient.implementation.UserImplementation;
/**
 *
 * @author Jaime San Sebastian
 */
public class UserFactory {
    //INTERFAZ IGUAL A NEW IMPLEMENTACIÓN
    public static UserImplementation getUser() {
        UserImplementation user = new UserImplementation();
        return user;
    }
}