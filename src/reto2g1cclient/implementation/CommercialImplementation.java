/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.implementation;


import java.util.Collection;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import reto2g1cclient.cypher.EncryptAsim;
import reto2g1cclient.exception.ClientServerConnectionException;
import reto2g1cclient.exception.DBServerException;
import reto2g1cclient.exception.LoginOnUseException;
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
        try {
            coJ.create(commercial);
        } catch (ClientErrorException e) {
            // throw new ClientServerConnectionException(e.getMessage());
        }
    }

    /**
     *
     * @param commercial
     */
    @Override
    public void edit(Commercial commercial) {
        try {
           
            coJ.edit(commercial, String.valueOf(commercial.getId()));
        } catch (ClientErrorException e) {
    //        LOGGER.log(Level.SEVERE, "Error al leer los eventos en la base de datos");
            // throw new DBServerException(e.getMessage());
        }
    }

    @Override
    public void remove(String id) {
        try {
            coJ.remove(id);
        } catch (ClientErrorException e) {
      //      LOGGER.log(Level.SEVERE, "Error al leer los eventos en la base de datos");
            //   throw new DBServerException(e.getMessage());
        }
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Commercial find(Integer id) {
        return coJ.find(new GenericType<Commercial>() {
            },id.toString()
        );
    }

    
  /**
     *
     * @return
     */
    @Override
    public Collection<Commercial> findAll() {
        Collection<Commercial> co = null;
        try {
            co = coJ.findAll(new GenericType<Collection<Commercial>>() {
            });
        } catch (ClientErrorException e) {
    //        LOGGER.log(Level.SEVERE, "Error al leer los eventos en la base de datos");
            //throw new DBServerException(e.getMessage());
        }
        return co;
    }
    
    @Override
    public void signUp(Commercial usr) throws LoginOnUseException ,ClientServerConnectionException,DBServerException {
        try {
            usr.setPassword(EncryptAsim.encryption(usr.getPassword()));
            Commercial user = coJ.signUp(Commercial.class, usr);
            if (!usr.getFullName().equals(user.getFullName()) || !usr.getEmail().equals(user.getEmail())) {
                throw new LoginOnUseException("El login está en uso");
            }
         } catch (ClientErrorException e) {
            throw new DBServerException(e.getMessage());
        } catch (Exception es) {
            throw new ClientServerConnectionException(es.getMessage());
        }
    }

   
    
}
