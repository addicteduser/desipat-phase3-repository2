package dataAccessObjects;

import Strategy.AddSystemLogStrategy;
import Template.AddSystemLogTemplate;
import Template.QueryTemplate;

import java.sql.*;
import java.util.ArrayList;

import dataModel.*;

/**
 *
 * @author sgtan_
 */
public class SystemLogDAO extends DAO{

	private static SystemLogDAO logDAO;
	public static String access;
	public static String username;

	private SystemLogDAO(){

	}

	public static SystemLogDAO getInstance(){
		if(logDAO == null)
			logDAO = new SystemLogDAO();

		return logDAO;
	}

	public void saveAccess (String access, String user) throws SQLException{
		SystemLogDAO.access = access;
		SystemLogDAO.username = user;

		QueryTemplate template = new AddSystemLogTemplate();
		template.executeQuery(new AddSystemLogStrategy());
	}

	public ArrayList<SystemLogModel> getAllDates() throws SQLException {

		ArrayList<SystemLogModel> SystemLogModels = new ArrayList<SystemLogModel>();
		Connection connection = getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("select * from systemLog");
		ResultSet resultSet = query(preparedStatement);
		while (resultSet.next()) {
			SystemLogModel slmdl = new SystemLogModel();
			slmdl.setAccess(resultSet.getString("access"));
			slmdl.setUsername(resultSet.getString("username"));
			slmdl.setDate(resultSet.getString("DATE"));
			SystemLogModels.add(slmdl);
		}
		return SystemLogModels;

	}
	public ArrayList<SystemLogModel> getDateContent(String aname) throws SQLException {

		ArrayList<SystemLogModel> SystemLogModels = new ArrayList<SystemLogModel>();
		Connection connection = getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("select * from systemLog where (DATE LIKE '%" + aname + "%') ");
		ResultSet resultSet = query(preparedStatement);
		while (resultSet.next()) {
			SystemLogModel slmdl = new SystemLogModel();
			slmdl.setAccess(resultSet.getString("access"));
			slmdl.setUsername(resultSet.getString("username"));
			slmdl.setDate(resultSet.getString("DATE"));
			SystemLogModels.add(slmdl);
		}
		return SystemLogModels;

	}
}