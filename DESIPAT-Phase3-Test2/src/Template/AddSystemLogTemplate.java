package Template;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import dataAccessObjects.SystemLogDAO;

/**
 *
 * @author sgtan_
 */
public class AddSystemLogTemplate extends QueryTemplate{

	private String getDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
		java.util.Date date = new java.util.Date();

		return dateFormat.format(date);
	}	

	@Override
	public void queryStatement(PreparedStatement stmt) throws SQLException {

		preparedStatement.setString(1, SystemLogDAO.getInstance().access);
		preparedStatement.setString(2, SystemLogDAO.getInstance().username);
		preparedStatement.setString(3, getDate());

	}
}
