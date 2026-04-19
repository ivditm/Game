package tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import play.Ingredient;
import play.Inventaire;
import win.FabriqueAntidote;

class FabriqueAntidoteTest {

	private FabriqueAntidote fabrique;
	private Inventaire inventaire;
	private Ingredient i1, i2, i3, i4, i5;

	@BeforeEach
	void setUp() throws Exception {
		fabrique = new FabriqueAntidote();
		inventaire = new Inventaire();
		i1 = new Ingredient("Ing1", "Desc", 1);
		i2 = new Ingredient("Ing2", "Desc", 2);
		i3 = new Ingredient("Ing3", "Desc", 3);
		i4 = new Ingredient("Ing4", "Desc", 4);
		i5 = new Ingredient("Ing5", "Desc", 5);
	}

	@Test
	final void testFabriquer() {
		assertFalse(fabrique.fabriquer(null), "La fabrication doit échouer si l'inventaire est null");
		inventaire.ajouterIngredient(i1);
		inventaire.ajouterIngredient(i2);
		assertFalse(fabrique.fabriquer(inventaire), "La fabrication doit échouer si l'inventaire n'est pas complet");
		inventaire.ajouterIngredient(i3);
		inventaire.ajouterIngredient(i4);
		inventaire.ajouterIngredient(i5);
		assertTrue(fabrique.fabriquer(inventaire), "La fabrication doit réussir quand les 5 ingrédients sont présents");
	}
}