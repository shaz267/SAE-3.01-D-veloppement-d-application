package Tablo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

	private static Connection connection;
	private static String userName = "root";
	private static String password = "";
	private static String serverName = "localhost";
	private static String portNumber = "3306";
	private static String dbName = "testpersonne";

	public static synchronized Connection getConnection() throws SQLException {

		String nomDb = null;
		if(connection != null){
			nomDb = connection.getCatalog();
		}
		if(connection == null && !dbName.equals(nomDb)){
			Properties connectionProps = new Properties();
			connectionProps.put("user", userName);
			connectionProps.put("password", password);
			String urlDB = "jdbc:mysql://" + serverName + ":";
			urlDB += portNumber + "/" + dbName;
			connection = DriverManager.getConnection(urlDB, connectionProps);
		}
		return connection;
	}

	public static void setDbName(String dbn) {
		DBConnection.dbName = dbn;
	}

}

