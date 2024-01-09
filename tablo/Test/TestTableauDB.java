import Tablo.Modele.Tableau;
import org.junit.jupiter.api.*;
import java.sql.SQLException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTableauDB {


	/**
	 * création de la base de test
	 * @throws SQLException
	 */
	@BeforeEach
	public void creerDonnees() throws SQLException {
		// lien vers la base de test
		Tableau.createTable();

		// ajoute
		new Tableau("test1").save(); // id 1
		new Tableau("test2").save(); // id 2
		new Tableau("test3").save(); // id 3
		new Tableau("test4").save(); // id 4
		new Tableau("test1").save(); // id 5
	}

	/**
	 * destruction de la base de test
	 * @throws SQLException
	 */
	@AfterEach
	public void supprimerDonnees() throws SQLException {
		Tableau.deleteTable();
	}

	/**
	 * test constructeur Personne
	 */
	@Test
	public void constructeurTest() {
		Tableau t = new Tableau("test");
		assertEquals(t.getId(), -1,"objet pas dans la base");
	}

	/**
	 * test find avec un id existant
	 */
	@Test
	public void testFindByIdExiste() throws SQLException {
		Tableau t = Tableau.findById(1);
		assertEquals(t.getId(), 1,"L'id doit être 1");
		assertEquals(t.getTitre(), "test1","Le titre doit être test1");
	}

	/**
	 * test find avec un titre existant
	 */
	@Test
	public void testFindByNameExiste() throws SQLException {
		ArrayList<Tableau> t = Tableau.findByName("test2");
		assertEquals(1, t.size(),"Il y a 1 enregistrements avec cette valeur dans la BD");
		assertEquals(t.get(0).getId(), 2,"L'id doit être 2");
	}

	/**
	 * test find avec un titre existant 2 fois
	 */
	@Test
	public void testFindByName2Existe() throws SQLException {
		ArrayList<Tableau> t = Tableau.findByName("test1");
		assertEquals(2, t.size(),"Il y a 2 enregistrements avec cette valeur dans la BD");
		assertEquals(t.get(0).getId(), 1,"L'id doit être 1");
		assertEquals(t.get(1).getId(), 5,"L'id doit être 5");
	}

	/**
	 * test find avec un titre inexistant
	 */
	@Test
	public void testFindByNameInexiste() throws SQLException {
		ArrayList<Tableau> t = Tableau.findByName("test5");
		assertEquals(0, t.size(),"Il n'y a pas d'enregistrement avec cette valeur dans la BD");
	}

	/**
	 * test findAll
	 */
	@Test
	public void testFindAll() throws SQLException {
		ArrayList<Tableau> t = Tableau.findAll();
		assertEquals(5, t.size(),"Il y a 5 enregistrements dans la BD");
		assertEquals(t.get(0).getId(), 1,"L'id doit être 1");
		assertEquals(t.get(1).getId(), 2,"L'id doit être 2");
		assertEquals(t.get(2).getId(), 3,"L'id doit être 3");
		assertEquals(t.get(3).getId(), 4,"L'id doit être 4");
		assertEquals(t.get(4).getId(), 5,"L'id doit être 5");
	}

	/**
	 * test sauvegarde nouveau tableau
	 */
	@Test
	public void testSaveNew() throws SQLException {
		Tableau t = new Tableau("test5");
		t.save();
		assertEquals(t.getId(), 6,"L'id doit être 6");
		ArrayList<Tableau> t2 = Tableau.findAll();
		assertEquals(6, t2.size(),"Il y a 6 enregistrements dans la BD");
		assertEquals(t2.get(5).getId(), 6,"L'id doit être 6");
	}

	/**
	 * test sauvegarde tableau existante
	 */
	@Test
	public void testSaveExistant() throws SQLException {
		// on recupere le tableau "test1" et on en modifie le titre
		Tableau t = Tableau.findById(1);
		t.changerTitre("testFonctionnel1");
		t.save();
		// on verifie que le tableau a bien ete modifiee sans que son id change
		assertEquals(t.getId(), 1,"L'id doit être 1");
		assertEquals(t.getTitre(), "testFonctionnel1","Le titre doit être testFonctionnel1");
		// on verifie que le tableau a bien ete modifiee dans la BD et que le reste de la BD n'a pas change
		ArrayList<Tableau> t2 = Tableau.findAll();
		assertEquals(5, t2.size(),"Il y a 5 enregistrements dans la BD");
		assertEquals(t2.get(0).getId(), 1,"L'id doit être 1");
		assertEquals(t2.get(1).getId(), 2,"L'id doit être 2");
		assertEquals(t2.get(2).getId(), 3,"L'id doit être 3");
		assertEquals(t2.get(3).getId(), 4,"L'id doit être 4");
		assertEquals(t2.get(4).getId(), 5,"L'id doit être 5");
	}

	/**
	 * test suppression tableau
	 */
	@Test
	public void testDelete() throws SQLException {
		// on supprime la tableau "test1"
		Tableau t = Tableau.findById(1);
		t.delete();
		// on verifie que la tableau a bien ete supprimee
		ArrayList<Tableau> t2 = Tableau.findAll();
		assertEquals(4, t2.size(),"Il y a 4 enregistrements dans la BD");
		assertEquals(t2.get(0).getId(), 2,"L'id doit être 2");
		assertEquals(t2.get(1).getId(), 3,"L'id doit être 3");
		assertEquals(t2.get(2).getId(), 4,"L'id doit être 4");
		assertEquals(t2.get(3).getId(), 5,"L'id doit être 5");
	}
}
