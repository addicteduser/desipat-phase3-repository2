/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Template;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import dataAccessObjects.AssetTypeDAO;
import dataAccessObjects.ClassificationDAO;
import dataAccessObjects.MaintenanceSchedDAO;
import dataAccessObjects.ValueTypeDAO;
import dataModel.*;

/**
 *
 * @author HOLLY
 */
public class AddAssetHistoryTemplate extends QueryTemplate {

    @Override
    public void queryStatement(PreparedStatement stmt) throws SQLException {
        String assetName = AssetHistoryModel.getInstance().getAssetName();
        int identifier = AssetHistoryModel.getInstance().getIdentifier();
        String assetOwner = AssetHistoryModel.getInstance().getAssetOwner();
        Date dateAcquired = AssetHistoryModel.getInstance().getDateAcquired();
                
        preparedStatement.setString(1, assetName);
        preparedStatement.setInt(2, identifier);
        preparedStatement.setString(3, assetOwner);
        preparedStatement.setDate(4, dateAcquired);
                
    }
}
 
    

