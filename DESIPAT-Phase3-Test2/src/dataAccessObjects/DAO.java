package dataAccessObjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import databaseConnection.DBFactory;

public class DAO {

	private DBFactory dbfactory = DBFactory.getInstance();

	public Connection getConnection() {
		Connection connection = dbfactory.getConnection();
		return connection;
	}

	public void update(PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.executeUpdate();
	}

	public ResultSet query(PreparedStatement preparedStatement)
			throws SQLException {
		return preparedStatement.executeQuery();
	}

	public void close(PreparedStatement preparedStatement, Connection connection) {
		dbfactory.closeStatement(preparedStatement);
		dbfactory.closeConnection(connection);
	}

}
