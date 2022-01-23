/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.logic;

import reto2g1cclient.model.User;

/**
 *
 * @author Jaime San Sebastián y Enaitz Izagirre
 */
public interface UserInterface {
    
    public void createUser(User user) throws Exception;
    
    public void editUser(User user) throws Exception;
    
    public void removeUser(User user) throws Exception;
    
    public void searchUser(User user) throws Exception;
    
    public void signIn(User user) throws Exception;
    
    public void resetPasswordByLogin(String login) throws Exception;
    
    public void updatePass(User user) throws Exception;
}
