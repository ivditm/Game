package tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import interactions.Enigme;
import interactions.Ordinateur;
import play.Inventaire;
import play.Recette;

class OrdinateurTest {

	private Inventaire inventaire;
	private Recette recette;
	private Enigme enigme;

	@BeforeEach
	void setUp() {
		inventaire = new Inventaire();
		recette = new Recette("Protocole antidote", "Suite d'instructions confidentielles", new ArrayList<>());

		Map<String, String> propositions = new LinkedHashMap<>();
		propositions.put("A", "5");
		propositions.put("B", "6");
		propositions.put("C", "7");
		enigme = new Enigme("Combien d'ingrédients faut-il pour compléter l'antidote ?", propositions, "A",
				"L'antidote demande cinq ingrédients.");
	}

	@Test
	void testOrdinateurVerrouilleAvantResolution() {
		Ordinateur ordinateur = new Ordinateur("Terminal du labo", "Un écran clignote faiblement.", recette, enigme);

		String resultat = ordinateur.interagir(inventaire);

		assertTrue(resultat.contains("verrouillé"));
		assertFalse(ordinateur.estOuvert());
		assertFalse(inventaire.getObjets().contains(recette));
	}

	@Test
	void testMauvaiseReponse() {
		Ordinateur ordinateur = new Ordinateur("Terminal du labo", "Un écran clignote faiblement.", recette, enigme);

		String resultat = ordinateur.repondreAEnigme("B", inventaire);

		assertTrue(resultat.contains("Mauvaise réponse"));
		assertFalse(enigme.estResolue());
		assertFalse(ordinateur.estOuvert());
	}

	@Test
	void testBonneReponseDebloqueLeContenu() {
		Ordinateur ordinateur = new Ordinateur("Terminal du labo", "Un écran clignote faiblement.", recette, enigme);

		String resultat = ordinateur.repondreAEnigme("A", inventaire);

		assertTrue(resultat.contains("Bonne réponse"));
		assertTrue(enigme.estResolue());
		assertTrue(ordinateur.estOuvert());
		assertTrue(inventaire.getObjets().contains(recette));
	}
}
