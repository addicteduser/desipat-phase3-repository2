/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategy;

import queryBuilder.SelectClassificationQueryBuilder;
import queryBuilder.SelectQueryBuilder;
import queryBuilder.SelectQueryDirector;
import queryBuilder.SelectQueryString;

/**
 *
 * @author sgtan_
 */
public class SelectClassificationStrategy implements ConnectionStrategy{

     @Override
    public String buildString() {
        SelectQueryDirector myDirector = new SelectQueryDirector();
        SelectQueryBuilder myBuilder = new SelectClassificationQueryBuilder();
        myDirector.setNewBuilder(myBuilder);
        myDirector.construct();
        SelectQueryString myQuery = myDirector.getQuery();
       
        return myQuery.toString();
    }
    
    
}
