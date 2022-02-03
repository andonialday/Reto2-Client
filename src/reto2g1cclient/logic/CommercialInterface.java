/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.logic;

import java.util.Collection;
import java.util.List;
import reto2g1cclient.exception.ClientServerConnectionException;
import reto2g1cclient.exception.LoginOnUseException;
import reto2g1cclient.exception.DBServerException;
import reto2g1cclient.model.Commercial;



/**
 *
 * @author Enaitz Izagirre
 */
public interface CommercialInterface {
        /**
     *
     * @param commercial
     */
    public void create(Commercial commercial);
    
    /**
     *
     * @param commercial
     */
    public void edit(Commercial commercial);
    
    /**
     *
     * @param id
     */
    public void remove(String id);
    
    
    /**
     *
     * @param id
     * @return
     */
    public Commercial find(Integer id);
    
    /**
     *
     * @return
     */
    public Collection<Commercial> findAll();
    
    /**
     * 
     * @param usr
     * @throws reto2g1cclient.exception.LoginOnUseException
     * @throws reto2g1cclient.exception.ClientServerConnectionException
     * @throws reto2g1cclient.exception.DBServerException
     */
    public void signUp(Commercial usr) throws LoginOnUseException ,ClientServerConnectionException,DBServerException  ;
    
    
}