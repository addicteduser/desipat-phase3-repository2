/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategy;

import queryBuilder.AddAssetQueryBuilder;
import queryBuilder.AddQueryBuilder;
import queryBuilder.AddQueryDirector;
import queryBuilder.AddQueryString;

/**
 *
 * @author sgtan_
 */
public class AddAssetStrategy implements ConnectionStrategy{
    
    @Override
    public String buildString() {
        AddQueryDirector myDirector = new AddQueryDirector();
        AddQueryBuilder myBuilder = new AddAssetQueryBuilder();
        myDirector.setNewBuilder(myBuilder);
        myDirector.construct();
        AddQueryString myQuery = myDirector.getQuery();
       
        return myQuery.toString();
    }
    
}
