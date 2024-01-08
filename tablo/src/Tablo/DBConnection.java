package Tablo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

	/**
	 * attribut de l'objet java.sql.Connection de la connection à notre bd
	 */
	private static Connection connection;
	/**
	 * attribut correspondant au nom de l'utilisateur afin de se connecter à la bd (root par défaut)
	 */
	private static String userName = "root";
	/**
	 * attribut correspondant au mot de passe de l'utilisateur afin de se connecter à la bd (vide par défaut)
	 */
	private static String password = "";
	/**
	 * attribut serverName correspondant au nom du serveur sur lequel est stocké la bd (localhost par défaut)
	 */
	private static String serverName = "localhost";
	/**
	 * attribut portNumber correspondant au numéro de port (3306 par défaut pour tout le monde à priori)
	 */
	private static String portNumber = "3306";
	/**
	 * attribut dbName correspondant au nom de la base de données (tablo chez nous)
	 */
	private static String dbName = "tablo";

	/**
	 * méthode getConnection permettant de créer la connection si elle est null, et de la retourner sinon
	 * @return connection, Objet Connection de la connection à notre bd
	 * @throws SQLException
	 */
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
}

