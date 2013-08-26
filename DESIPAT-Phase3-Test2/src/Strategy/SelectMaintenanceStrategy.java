/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategy;

import queryBuilder.SelectMaintenanceQueryBuilder;
import queryBuilder.SelectQueryBuilder;
import queryBuilder.SelectQueryDirector;
import queryBuilder.SelectQueryString;

/**
 *
 * @author sgtan_
 */
public class SelectMaintenanceStrategy implements ConnectionStrategy{

     @Override
    public String buildString() {
        SelectQueryDirector myDirector = new SelectQueryDirector();
        SelectQueryBuilder myBuilder = new SelectMaintenanceQueryBuilder();
        myDirector.setNewBuilder(myBuilder);
        myDirector.construct();
        SelectQueryString myQuery = myDirector.getQuery();
       
        return myQuery.toString();
    }
}
