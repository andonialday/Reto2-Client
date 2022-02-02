/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.implementation;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import reto2g1cclient.exception.ClientServerConnectionException;
import reto2g1cclient.exception.DBServerException;
import reto2g1cclient.logic.EquipmentInterface;
import reto2g1cclient.model.Equipment;

/**
 * Clase para la implementacion del Equipamiento
 * @author Aitor Perez
 */
public class EquipmentImplementation implements EquipmentInterface {

    private final EquipmentJerseyClient EquipmentClient;
    private static final Logger LOGGER = Logger.getLogger("package.class");

    public EquipmentImplementation() {
        EquipmentClient = new EquipmentJerseyClient();
    }

    @Override
    public Collection<Equipment> findCostRange(Double min, Double max)throws DBServerException, ClientServerConnectionException{
          List<Equipment> result = null;

        try {
        result =  EquipmentClient.findCostRange(new GenericType<List<Equipment>>() {
        }, min.toString(), max.toString());
         } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "Error al leer los equipamientos de la base de datos");
            throw new DBServerException(e.getMessage());
             
        }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al leer los equipamientos de la base de datos");
            throw new ClientServerConnectionException(e.getMessage());
        }
        return result;
    }

    @Override
    public void create(Equipment equipment) throws DBServerException, ClientServerConnectionException{
       
        try {
         EquipmentClient.create(equipment);
        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "Error al leer los equipamientos de la base de datos");
            throw new DBServerException(e.getMessage());
        }  catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al leer los equipamientos de la base de datos");
            throw new ClientServerConnectionException(e.getMessage());
        }
        
    }

    @Override
    public void edit(Equipment equipment) throws DBServerException, ClientServerConnectionException {
         try {
        EquipmentClient.edit(equipment, equipment.getId());
          } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "Error al leer los equipamientos de la base de datos");
            throw new DBServerException(e.getMessage());
        }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al leer los equipamientos de la base de datos");
            throw new ClientServerConnectionException(e.getMessage());
        }
    }

    @Override
    public void remove(Equipment equipment) throws DBServerException, ClientServerConnectionException {
        try{
        EquipmentClient.remove(equipment.getId());
          } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "Error al leer los equipamientos de la base de datos");
            throw new DBServerException(e.getMessage());
        }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al leer los equipamientos de la base de datos");
            throw new ClientServerConnectionException(e.getMessage());
        }
    }

    @Override
    public Equipment find(Integer id) throws DBServerException, ClientServerConnectionException {
        Equipment result = null;
        try {
            result = EquipmentClient.find(Equipment.class, id.toString());
        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "Error al leer los equipamientos de la base de datos");
            throw new DBServerException(e.getMessage());
        }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al leer los equipamientos de la base de datos");
            throw new ClientServerConnectionException(e.getMessage());
        }
        return result;

    }

    @Override
    public Collection<Equipment> findAll() throws DBServerException, ClientServerConnectionException {
        List<Equipment> result = null;

        try {
            result = EquipmentClient.findAll(new GenericType<List<Equipment>>() {});

        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "Error al leer los equipamientos de la base de datos");
            throw new DBServerException(e.getMessage());
        }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al leer los equipamientos de la base de datos");
            throw new ClientServerConnectionException(e.getMessage());
        }
        return result;
    }

    @Override
    public Collection<Equipment> findRange(Integer from, Integer to) throws DBServerException, ClientServerConnectionException {
        List<Equipment> result = null;
        try {
            result = EquipmentClient.findRange(new GenericType<List<Equipment>>() {
            }, from.toString(), to.toString());
        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "Error al leer los equipamientos de la base de datos");
            throw new DBServerException(e.getMessage());
        }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al leer los equipamientos de la base de datos");
            throw new ClientServerConnectionException(e.getMessage());
        }
        return result;
    }

    @Override
    public String countREST() throws DBServerException, ClientServerConnectionException {
        String result = null;
        try {
            result = EquipmentClient.countREST();
        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "Error al leer los equipamientos de la base de datos");
            throw new DBServerException(e.getMessage());
        }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al leer los equipamientos de la base de datos");
            throw new ClientServerConnectionException(e.getMessage());
        }
        return result;
    }

    @Override
    public Collection<Equipment> findOrderPreviousDate(Date datePrev)throws DBServerException, ClientServerConnectionException{
        List<Equipment> result = null;
        try {
            result = EquipmentClient.findOrderPreviousDate
        (new GenericType<List<Equipment>>() {}, datePrev.toString());

        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "Error al leer los equipamientos de la base de datos");
            throw new DBServerException(e.getMessage());
        }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al leer los equipamientos de la base de datos");
            throw new ClientServerConnectionException(e.getMessage());
        }
        return result;
    }
        @Override
        public Collection<Equipment> findOrderAfterDate(Date dateAfter) throws DBServerException, ClientServerConnectionException{ 
            List<Equipment> result = null;
        
        try {
            
    result = EquipmentClient.findOrderAfterDate
        (new GenericType<List<Equipment>>() {}, dateAfter.toString());
        }  catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "Error al leer los equipamientos de la base de datos");
            throw new DBServerException(e.getMessage());
        }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al leer los equipamientos de la base de datos");
            throw new ClientServerConnectionException(e.getMessage());
        }
        return result;
        }
        
        
        @Override
        public Collection<Equipment> deleteOldEquip(Date year)throws DBServerException, ClientServerConnectionException{
            
              List<Equipment> result = null;
        
        try {
    result = EquipmentClient.deleteOldEquip(new GenericType<List<Equipment>>() {}, year.toString());
    
             }  catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "Error al leer los equipamientos de la base de datos");
            throw new DBServerException(e.getMessage());
        }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al leer los equipamientos de la base de datos");
            throw new ClientServerConnectionException(e.getMessage());
        }
        return result;
        }

        @Override
        public Collection<Equipment> updateCost(Double ratio)throws DBServerException, ClientServerConnectionException{
              List<Equipment> result = null;
        
        try {
    result =  EquipmentClient.updateCost(new GenericType<List<Equipment>>() {}, ratio.toString());
       
        }  catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "Error al leer los equipamientos de la base de datos");
            throw new DBServerException(e.getMessage());
        }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al leer los equipamientos de la base de datos");
            throw new ClientServerConnectionException(e.getMessage());
        }
        return result;
    }
}
