package finalDeleteModule;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dataAccessObjects.AssetDAO;
import dataAccessObjects.UserDAO;
import dataModel.UserModel;

public class DeleteModel {
	ArrayList<String> assetName = new ArrayList<String>();
	List<UserModel> usermdl;
	AssetDAO adao = AssetDAO.getInstance();
	UserDAO udao = UserDAO.getInstance();

	public void getAllAsset() throws SQLException {
		assetName = AssetDAO.getInstance().getAllAssetNames();
	}

	public void getAllUserModel() throws SQLException {
		usermdl = UserDAO.getInstance().getAllUsers();
	}
}
