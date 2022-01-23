/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.logic;

import java.util.List;
import reto2g1cclient.model.Client;
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
     * @param commercial
     */
    public void remove(Commercial commercial);
    
    
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
    public List<Commercial> findAll();
    
    
    
}
