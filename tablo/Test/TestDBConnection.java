import Tablo.DBConnection;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * classe de test
 */
public class TestDBConnection {

	/**
	 * test de la connection à la base de données
	 * @throws SQLException
	 */
	@Test
	public void test_connectionSingletonOK() throws SQLException {
		Connection dbc1 = DBConnection.getConnection();
		Connection dbc2 = DBConnection.getConnection();
		assertEquals(dbc2, dbc1, "Les connections sont les mêmes");
	}
}