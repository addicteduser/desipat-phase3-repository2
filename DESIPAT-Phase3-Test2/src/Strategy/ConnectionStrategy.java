/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategy;

import java.sql.Connection;

import databaseConnection.DBFactory;

/**
 *
 * @author sgtan_
 */
public interface ConnectionStrategy{
    
    DBFactory dbfactory = DBFactory.getInstance();
    Connection connection = dbfactory.getConnection();
    
    public String buildString();
}
