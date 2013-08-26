/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategy;

import queryBuilder.SelectAssetTypeQueryBuilder;
import queryBuilder.SelectQueryBuilder;
import queryBuilder.SelectQueryDirector;
import queryBuilder.SelectQueryString;

/**
 *
 * @author sgtan_
 */
public class SelectAssetTypeStrategy implements ConnectionStrategy{

    @Override
    public String buildString() {
        SelectQueryDirector myDirector = new SelectQueryDirector();
        SelectQueryBuilder myBuilder = new SelectAssetTypeQueryBuilder();
        myDirector.setNewBuilder(myBuilder);
        myDirector.construct();
        SelectQueryString myQuery = myDirector.getQuery();
       
        return myQuery.toString();
    }
    
}
