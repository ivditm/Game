package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import play.Ingredient;
import play.Objet;
import play.Recette;
import play.TypeObjet;

class RecetteTest {

	private Recette recette;
	private List<Ingredient> listeTest;
	private Ingredient ing1;
	private Ingredient ing2;

	@BeforeEach
	void setUp() throws Exception {
		ing1 = new Ingredient("Poudre stabilisatrice", "Poudre rouge", 1);
		ing2 = new Ingredient("Adrénaline pure", "Liquide bleu", 2);
		listeTest = new ArrayList<>();
		listeTest.add(ing1);
		listeTest.add(ing2);
		recette = new Recette("Protocole Alpha", "Document confidentiel", listeTest);
	}

	@Test
	void testRecette() {
		assertNotNull(recette, "L'objet Recette ne doit pas être null après l'initialisation");
	}

	@Test
	void testObjet() {
		assertTrue(recette instanceof Objet, "Recette doit hériter de la classe Objet");
	}

	@Test
	void testGetNom() {
		assertEquals("Protocole Alpha", recette.getNom());
	}

	@Test
	void testGetDescription() {
		assertEquals("Document confidentiel", recette.getDescription());
	}

	@Test
	void testGetType() {
		assertEquals(TypeObjet.DOCUMENT, recette.getType());
	}

	@Test
	void testToString() {
		String resultat = recette.toString();
		assertTrue(resultat.contains("=== Protocole Alpha ==="));
		assertTrue(resultat.contains("Document confidentiel"));
		assertTrue(resultat.contains("Étape 1 : Poudre stabilisatrice"));
		assertTrue(resultat.contains("Étape 2 : Adrénaline pure"));
	}

	@Test
	void testEqualsObject() {
		Recette recetteIdentique = new Recette("Protocole Alpha", "Document confidentiel", listeTest);
		Recette recetteDifferente = new Recette("Autre doc", "Rien", new ArrayList<>());
		assertNotEquals("Protocole Alpha", recetteIdentique);
		assertEquals(recetteIdentique, recetteIdentique);
		assertEquals(recette, recetteIdentique, "Deux recettes avec les mêmes attributs doivent être égales");
		assertNotEquals(recette, recetteDifferente, "Deux recettes différentes ne doivent pas être égales");
		assertNotEquals(recette, null, "La recette ne doit pas être égale à null");
	}

	@Test
	void testHashCode() {
		Recette recetteIdentique = new Recette("Protocole Alpha", "Document confidentiel", listeTest);
		assertEquals(recette.hashCode(), recetteIdentique.hashCode());
	}
}
