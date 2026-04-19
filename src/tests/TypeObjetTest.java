package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import play.TypeObjet;

public class TypeObjetTest {

	@Test
	public void testGetNomAffichage() {
		assertEquals("Ingrédient chimique", TypeObjet.INGREDIENT.getNomAffichage());
		assertEquals("Document d'information", TypeObjet.DOCUMENT.getNomAffichage());
		assertEquals("Antidote expérimental", TypeObjet.ANTIDOTE.getNomAffichage());
	}

	@Test
	public void testToString() {
		assertEquals("Ingrédient chimique", TypeObjet.INGREDIENT.toString());
		assertEquals("Document d'information", TypeObjet.DOCUMENT.toString());
	}

	@Test
	public void testEnumValuesExist() {
		TypeObjet[] types = TypeObjet.values();
		assertEquals(3, types.length);
	}
}
