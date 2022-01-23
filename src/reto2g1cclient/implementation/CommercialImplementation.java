/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.implementation;

import java.util.List;
import reto2g1cclient.logic.CommercialInterface;
import reto2g1cclient.model.Client;
import reto2g1cclient.model.Commercial;


/**
 *
 * @author Enaitz Izagirre
 */
public class CommercialImplementation implements CommercialInterface{

    private final CommercialJersey coJ ;
    
    public CommercialImplementation(){
        coJ = new CommercialJersey();
    }
    
    /**
     * 
     * @param commercial 
     */
    @Override
    public void create(Commercial commercial) {
        coJ.create(commercial);
    }

    /**
     * 
     * @param commercial 
     */
    @Override
    public void edit(Commercial commercial) {
        coJ.edit(commercial, commercial.getId().toString());
       
    }

    /**
     * 
     * @param commercial 
     */
    @Override
    public void remove(Commercial commercial) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * 
     * @param id
     * @return 
     */
    @Override
    public Commercial find(Integer id) {
        return coJ.find(Commercial.class, id.toString());
    }

    /**
     * 
     * @return 
     */
    @Override
    public List<Commercial> findAll() {
          return coJ.findAll(List.class);
    }


   
    
}
