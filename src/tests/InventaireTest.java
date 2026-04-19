package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import play.Ingredient;
import play.Inventaire;
import play.Recette;

class InventaireTest {

	private Inventaire inventaire;
	private Ingredient ing1;
	private Ingredient ing2;
	private Ingredient ing3;
	private Ingredient ing4;
	private Ingredient ing5;
	private Recette document;

	@BeforeEach
	void setUp() throws Exception {
		inventaire = new Inventaire();
		ing1 = new Ingredient("Poudre", "Description", 1);
		ing2 = new Ingredient("Adrénaline", "Description", 2);
		ing3 = new Ingredient("Acide", "Description", 3);
		ing4 = new Ingredient("Catalyseur", "Description", 4);
		ing5 = new Ingredient("Sérum", "Description", 5);
		document = new Recette("Protocole", "Desc", new ArrayList<>());
	}

	@Test
	void testInventaire() {
		assertNotNull(inventaire, "L'inventaire ne doit pas être null");
		assertTrue(inventaire.getObjets().isEmpty(), "L'inventaire doit être vide à la création");
		assertFalse(inventaire.estComplet(), "L'inventaire ne doit pas être complet à la création");
	}

	@Test
	void testAjouterIngredient() {
		assertFalse(inventaire.ajouterIngredient(ing2), "Doit refuser un ingrédient si le précédent manque");
		assertTrue(inventaire.ajouterIngredient(ing1), "Doit accepter l'ingrédient 1");
		assertTrue(inventaire.ajouterIngredient(ing2), "Doit accepter l'ingrédient 2 après le 1");
	}

	@Test
	void testAjouterObjetDivers() {
		inventaire.ajouterObjetDivers(document);
		assertTrue(inventaire.getObjets().contains(document), "Doit autoriser l'ajout d'un document");
		inventaire.ajouterObjetDivers(ing1);
		assertFalse(inventaire.getObjets().contains(ing1),
				"Ne doit pas autoriser l'ajout d'un ingrédient par cette méthode");
	}

	@Test
	void testPossedeIngredient() {
		assertFalse(inventaire.possedeIngredient(1), "Ne doit pas posséder l'ingrédient 1 au début");
		inventaire.ajouterIngredient(ing1);
		assertTrue(inventaire.possedeIngredient(1), "Doit posséder l'ingrédient 1 après ajout");
		assertFalse(inventaire.possedeIngredient(2), "Ne doit pas posséder l'ingrédient 2");
	}

	@Test
	void testEstComplet() {
		inventaire.ajouterIngredient(ing1);
		inventaire.ajouterIngredient(ing2);
		inventaire.ajouterIngredient(ing3);
		inventaire.ajouterIngredient(ing4);
		assertFalse(inventaire.estComplet(), "Ne doit pas être complet à 4 ingrédients");
		inventaire.ajouterIngredient(ing5);
		assertTrue(inventaire.estComplet(), "Doit être complet après les 5 ingrédients");
	}

	@Test
	void testGetObjets() {
		inventaire.ajouterIngredient(ing1);
		inventaire.ajouterObjetDivers(document);
		assertEquals(2, inventaire.getObjets().size(), "La liste doit contenir 2 objets");
		assertTrue(inventaire.getObjets().contains(ing1));
		assertTrue(inventaire.getObjets().contains(document));
	}

	@Test
	void testToString() {
		assertEquals("L'inventaire est vide.", inventaire.toString());
		inventaire.ajouterIngredient(ing1);
		inventaire.ajouterObjetDivers(document);
		String resultat = inventaire.toString();
		assertTrue(resultat.contains("Contenu de l'inventaire :"));
		assertTrue(resultat.contains("- Poudre"));
		assertTrue(resultat.contains("- Protocole"));
	}

}
