package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import interactions.Enigme;

class EnigmeTest {

	private Enigme enigme;

	@BeforeEach
	void setUp() {
		Map<String, String> propositions = new LinkedHashMap<>();
		propositions.put("A", "Serializable");
		propositions.put("B", "Runnable");
		propositions.put("C", "Cloneable");

		enigme = new Enigme("Quelle interface permet la sauvegarde d'objet en Java ?", propositions, "A",
				"La sérialisation Java repose sur Serializable.");
	}

	@Test
	void testExaminer() {
		String texte = enigme.examiner();

		assertTrue(texte.contains("Quelle interface permet la sauvegarde d'objet en Java ?"));
		assertTrue(texte.contains("A. Serializable"));
		assertTrue(texte.contains("B. Runnable"));
	}

	@Test
	void testValiderReponse() {
		assertTrue(enigme.validerReponse("A"));
		assertTrue(enigme.validerReponse(" a "));
		assertFalse(enigme.validerReponse("B"));
	}

	@Test
	void testResoudre() {
		assertFalse(enigme.estResolue());
		assertFalse(enigme.resoudre("C"));
		assertFalse(enigme.estResolue());

		assertTrue(enigme.resoudre("A"));
		assertTrue(enigme.estResolue());
		assertTrue(enigme.examiner().contains("Énigme résolue."));
	}

	@Test
	void testDonnerExplication() {
		assertEquals("La sérialisation Java repose sur Serializable.", enigme.donnerExplication());
	}
}
