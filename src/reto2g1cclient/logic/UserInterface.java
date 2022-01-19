/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.logic;

import reto2g1cclient.model.User;

/**
 *
 * @author 2dam
 */
public interface UserInterface {
    
    public void createUser(User user);
    
    public void updateUser(User user);
    
    public void deleteUser(User user);
    
    public void findUser(User user);
}
