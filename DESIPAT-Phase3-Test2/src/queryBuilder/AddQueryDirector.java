/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package queryBuilder;

/**
 *
 * @author sgtan_
 */
public class AddQueryDirector {
    
    private AddQueryBuilder newBuilder;
    
    public void construct(){
        newBuilder.createNewQuery();
        newBuilder.populateProjections();
        newBuilder.populateTables();
        newBuilder.populateValues();
        
    }
    
    public AddQueryString getQuery(){
        return newBuilder.getQuery();
    }

    public void setNewBuilder(AddQueryBuilder newBuilder) {
        this.newBuilder = newBuilder;
    }
    
}
