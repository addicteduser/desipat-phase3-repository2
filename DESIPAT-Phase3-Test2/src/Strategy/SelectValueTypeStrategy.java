/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategy;

import queryBuilder.SelectQueryBuilder;
import queryBuilder.SelectQueryDirector;
import queryBuilder.SelectQueryString;
import queryBuilder.SelectValueTypeQueryBuilder;

/**
 *
 * @author sgtan_
 */
public class SelectValueTypeStrategy implements ConnectionStrategy{

     @Override
    public String buildString() {
        SelectQueryDirector myDirector = new SelectQueryDirector();
        SelectQueryBuilder myBuilder = new SelectValueTypeQueryBuilder();
        myDirector.setNewBuilder(myBuilder);
        myDirector.construct();
        SelectQueryString myQuery = myDirector.getQuery();
       
        return myQuery.toString();
    }
    
}
