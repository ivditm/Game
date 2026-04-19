package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import play.Ingredient;
import play.Objet;
import play.TypeObjet;

class IngredientTest {

	private Ingredient ingredient;

	@BeforeEach
	void setUp() throws Exception {
		ingredient = new Ingredient("Poudre stabilisatrice", "Poudre rouge", 1);
	}

	@Test
	void testIngredient() {
		assertNotNull(ingredient, "L'ingrédient ne doit pas être null");
	}

	@Test
	void testObjet() {
		assertTrue(ingredient instanceof Objet, "Un ingrédient doit être une instance d'Objet");
	}

	@Test
	void testGetNom() {
		assertEquals("Poudre stabilisatrice", ingredient.getNom());
	}

	@Test
	void testGetDescription() {
		assertEquals("Poudre rouge", ingredient.getDescription());
	}

	@Test
	void testGetType() {
		assertEquals(TypeObjet.INGREDIENT, ingredient.getType());
	}

	@Test
	void testGetRang() {
		assertEquals(1, ingredient.getRang());
	}

	@Test
	void testToString() {
		assertEquals("Poudre stabilisatrice", ingredient.toString());
	}

	@Test
	void testEqualsObject() {
		Ingredient identique = new Ingredient("Poudre stabilisatrice", "Poudre rouge", 1);
		Ingredient diffNom = new Ingredient("Autre Nom", "Poudre rouge", 1);
		Ingredient diffDesc = new Ingredient("Poudre stabilisatrice", "Autre description", 1);
		Ingredient diffRang = new Ingredient("Poudre stabilisatrice", "Poudre rouge", 2);
		assertNotEquals("Poudre stabilisatrice", identique);
		assertTrue(ingredient.equals(ingredient), "Doit être égal à lui-même");
		assertFalse(ingredient.equals(null), "Ne doit pas être égal à null");
		assertFalse(ingredient.equals(new String("Poudre")), "Ne doit pas être égal à un autre type d'objet");
		assertTrue(ingredient.equals(identique), "Doit être égal à un ingrédient avec les mêmes valeurs");
		assertFalse(ingredient.equals(diffNom), "Doit être false si le nom est différent");
		assertFalse(ingredient.equals(diffDesc), "Doit être false si la description est différente");
		assertFalse(ingredient.equals(diffRang), "Doit être false si le rang est différent");
		Objet objetDiffType = new Objet("Poudre stabilisatrice", "Poudre rouge", TypeObjet.DOCUMENT) {
			private static final long serialVersionUID = 1L;
		};
		assertFalse(objetDiffType.equals(ingredient),
				"Doit être false si le TypeObjet est différent dans la classe mère");
	}

	@Test
	void testHashCode() {
		Ingredient identique = new Ingredient("Poudre stabilisatrice", "Poudre rouge", 1);
		assertEquals(ingredient.hashCode(), identique.hashCode(),
				"Les hashcodes doivent être identiques pour des objets égaux");
	}

}