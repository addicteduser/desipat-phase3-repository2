package finalNavigatorModule;

import java.sql.SQLException;

import dataAccessObjects.UserDAO;

/**
 * MODEL for Navigator
 */
public class NavigatorModel {
	public static String username;
	public static String usertype;

	public NavigatorModel() {
		//delete this
	}

	public NavigatorModel(String username) throws SQLException {
		this.username = username;
		usertype = UserDAO.getInstance().getUser(username).getUsertype();
	}

	public String getUsername() {
		return username;
	}

	public String getUsertype() {
		return usertype;
	}
}
