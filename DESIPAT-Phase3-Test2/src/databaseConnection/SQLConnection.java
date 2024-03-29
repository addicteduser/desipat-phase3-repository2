package databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author sgtan_
 */
public class SQLConnection extends DBFactory {

	@Override
	public Connection getConnection() {
		try {
			Class.forName(getDriverName());
			Connection conn = DriverManager.getConnection(getUrl(),
					getUsername(), getPassword());
			return conn;
		} catch (SQLException ex) {
			Logger.getLogger(SQLConnection.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(SQLConnection.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return null;
	}

}
