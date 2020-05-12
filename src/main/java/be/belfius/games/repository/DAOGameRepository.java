package be.belfius.games.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import be.belfius.games.domain.Category;
import be.belfius.games.exceptions.RepoException;
import be.belfius.games.services.ConnectParams;

public class DAOGameRepository {

	public void loadDriver() throws RepoException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RepoException(e.getMessage());
		}
	}

	public Connection connectGameDB() {
		Connection connTemp = null;
		try {
			connTemp = DriverManager.getConnection(ConnectParams.loadPropertiesFile().getProperty("db.url"),
					ConnectParams.loadPropertiesFile().getProperty("db.username"), "");
//					ConnectParams.loadPropertiesFile().getProperty("db.password"));

		} catch (Exception e) {
			throw new RepoException(e.getMessage());
		} finally {
			return connTemp;
		}
	}

//	public boolean testConnection() {
//		if (connection == null) {
//			this.connection = connectGameDB();
//		}
//		return ((connection != null));
//	}

//	public void disconnectGameDB() {
//		try {
//			connection.close();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			System.out.println(e.getMessage());
//		}
//	}

//	public Connection getCon() {
//		return connection;
//	}

}
