import Tablo.Modele.Liste;
import org.junit.jupiter.api.*;
import java.sql.SQLException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestListeDB {


	/**
	 * création de la base de test
	 * @throws SQLException
	 */
	@BeforeEach
	public void creerDonnees() throws SQLException {
		// lien vers la base de test
		Liste.createTable();

		// ajoute
		new Liste("test1").save(); // id 1
		new Liste("test2").save(); // id 2
		new Liste("test3").save(); // id 3
		new Liste("test4").save(); // id 4
		new Liste("test1").save(); // id 5
	}

	/**
	 * destruction de la base de test
	 * @throws SQLException
	 */
	@AfterEach
	public void supprimerDonnees() throws SQLException {
		Liste.deleteTable();
	}

	/**
	 * test constructeur Personne
	 */
	@Test
	public void constructeurTest() {
		Liste l = new Liste("test");
		assertEquals(l.getId(), -1,"objet pas dans la base");
	}

	/**
	 * test find avec un id existant
	 */
	@Test
	public void testFindByIdExiste() throws SQLException {
		Liste l = Liste.findById(1);
		assertEquals(l.getId(), 1,"L'id doit être 1");
		assertEquals(l.getTitre(), "test1","Le titre doit être test1");
	}

	/**
	 * test find avec un titre existant
	 */
	@Test
	public void testFindByNameExiste() throws SQLException {
		ArrayList<Liste> l = Liste.findByName("test2");
		assertEquals(1, l.size(),"Il y a 1 enregistrements avec cette valeur dans la BD");
		assertEquals(l.get(0).getId(), 2,"L'id doit être 2");
	}

	/**
	 * test find avec un titre existant 2 fois
	 */
	@Test
	public void testFindByName2Existe() throws SQLException {
		ArrayList<Liste> l = Liste.findByName("test1");
		assertEquals(2, l.size(),"Il y a 2 enregistrements avec cette valeur dans la BD");
		assertEquals(l.get(0).getId(), 1,"L'id doit être 1");
		assertEquals(l.get(1).getId(), 5,"L'id doit être 5");
	}

	/**
	 * test find avec un titre inexistant
	 */
	@Test
	public void testFindByNameInexiste() throws SQLException {
		ArrayList<Liste> l = Liste.findByName("test5");
		assertEquals(0, l.size(),"Il n'y a pas d'enregistrement avec cette valeur dans la BD");
	}

	/**
	 * test findAll
	 */
	@Test
	public void testFindAll() throws SQLException {
		ArrayList<Liste> l = Liste.findAll();
		assertEquals(5, l.size(),"Il y a 5 enregistrements dans la BD");
		assertEquals(l.get(0).getId(), 1,"L'id doit être 1");
		assertEquals(l.get(1).getId(), 2,"L'id doit être 2");
		assertEquals(l.get(2).getId(), 3,"L'id doit être 3");
		assertEquals(l.get(3).getId(), 4,"L'id doit être 4");
		assertEquals(l.get(4).getId(), 5,"L'id doit être 5");
	}

	/**
	 * test sauvegarde nouvelle liste
	 */
	@Test
	public void testSaveNew() throws SQLException {
		Liste l = new Liste("test5");
		l.save();
		assertEquals(l.getId(), 6,"L'id doit être 6");
		ArrayList<Liste> l2 = Liste.findAll();
		assertEquals(6, l2.size(),"Il y a 6 enregistrements dans la BD");
		assertEquals(l2.get(5).getId(), 6,"L'id doit être 6");
	}

	/**
	 * test sauvegarde liste existante
	 */
	@Test
	public void testSaveExistant() throws SQLException {
		// on recupere la liste "test1" et on en modifie le titre
		Liste l = Liste.findById(1);
		l.setTitre("testFonctionnel1");
		l.save();
		// on verifie que la liste a bien ete modifiee sans que son id change
		assertEquals(l.getId(), 1,"L'id doit être 1");
		assertEquals(l.getTitre(), "testFonctionnel1","Le titre doit être testFonctionnel1");
		// on verifie que la liste a bien ete modifiee dans la BD et que le reste de la BD n'a pas change
		ArrayList<Liste> l2 = Liste.findAll();
		assertEquals(5, l2.size(),"Il y a 5 enregistrements dans la BD");
		assertEquals(l2.get(0).getId(), 1,"L'id doit être 1");
		assertEquals(l2.get(1).getId(), 2,"L'id doit être 2");
		assertEquals(l2.get(2).getId(), 3,"L'id doit être 3");
		assertEquals(l2.get(3).getId(), 4,"L'id doit être 4");
		assertEquals(l2.get(4).getId(), 5,"L'id doit être 5");
	}

	/**
	 * test suppression liste
	 */
	@Test
	public void testDelete() throws SQLException {
		// on supprime la liste "test1"
		Liste l = Liste.findById(1);
		l.delete();
		// on verifie que la liste a bien ete supprimee
		ArrayList<Liste> l2 = Liste.findAll();
		assertEquals(4, l2.size(),"Il y a 4 enregistrements dans la BD");
		assertEquals(l2.get(0).getId(), 2,"L'id doit être 2");
		assertEquals(l2.get(1).getId(), 3,"L'id doit être 3");
		assertEquals(l2.get(2).getId(), 4,"L'id doit être 4");
		assertEquals(l2.get(3).getId(), 5,"L'id doit être 5");
	}
}
