package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import interactions.Coffre;
import play.Ingredient;
import play.Inventaire;
import play.Recette;

class CoffreTest {

	private Inventaire inventaire;
	private Ingredient ingredient1;
	private Ingredient ingredient2;
	private Recette recette;

	@BeforeEach
	void setUp() {
		inventaire = new Inventaire();
		ingredient1 = new Ingredient("Poudre stabilisatrice", "Premier ingrédient", 1);
		ingredient2 = new Ingredient("Adrénaline pure", "Deuxième ingrédient", 2);
		recette = new Recette("Protocole", "Document scientifique", new ArrayList<>());
	}

	@Test
	void testExaminerCoffreFerme() {
		Coffre coffre = new Coffre("Coffre nord", "Un coffre métallique fermé.", ingredient1);

		assertTrue(coffre.examiner().contains("quelque chose d'utile"));
		assertFalse(coffre.estOuvert());
	}

	@Test
	void testOuvrirCoffreAvecIngredientValide() {
		Coffre coffre = new Coffre("Coffre nord", "Un coffre métallique fermé.", ingredient1);

		String resultat = coffre.interagir(inventaire);

		assertTrue(resultat.contains("Poudre stabilisatrice"));
		assertTrue(coffre.estOuvert());
		assertTrue(inventaire.getObjets().contains(ingredient1));
	}

	@Test
	void testIngredientRefuseSiOrdreIncorrect() {
		Coffre coffre = new Coffre("Coffre sud", "Un coffre verrouillé.", ingredient2);

		String resultat = coffre.interagir(inventaire);

		assertTrue(resultat.contains("respecter l'ordre"));
		assertFalse(coffre.estOuvert());
		assertFalse(inventaire.getObjets().contains(ingredient2));
	}

	@Test
	void testOuvrirCoffreAvecDocument() {
		Coffre coffre = new Coffre("Casier", "Un ancien casier de laboratoire.", recette);

		String resultat = coffre.interagir(inventaire);

		assertTrue(resultat.contains("Protocole"));
		assertTrue(coffre.estOuvert());
		assertTrue(inventaire.getObjets().contains(recette));
	}

	@Test
	void testCoffreDejaOuvert() {
		Coffre coffre = new Coffre("Coffre nord", "Un coffre métallique fermé.", ingredient1);

		coffre.interagir(inventaire);
		String resultat = coffre.interagir(inventaire);

		assertEquals("Coffre nord est déjà ouvert.", resultat);
	}
}
