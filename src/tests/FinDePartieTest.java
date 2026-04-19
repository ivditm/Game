package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import play.Ingredient;
import play.Inventaire;
import win.EtatJeu;
import win.FinDePartie;

class FinDePartieTest {

	private FinDePartie fin;
	private Inventaire inventaire;
	private Ingredient i1, i2, i3, i4, i5;

	@BeforeEach
	void setUp() throws Exception {
		fin = new FinDePartie();
		inventaire = new Inventaire();
		i1 = new Ingredient("I1", "D", 1);
		i2 = new Ingredient("I2", "D", 2);
		i3 = new Ingredient("I3", "D", 3);
		i4 = new Ingredient("I4", "D", 4);
		i5 = new Ingredient("I5", "D", 5);
	}

	@Test
	final void testVerifierEtat() {
		assertEquals(EtatJeu.EN_COURS, fin.verifierEtat(inventaire, 0), "La partie doit être en cours au début");

		assertEquals(EtatJeu.EN_COURS, fin.verifierEtat(inventaire, 2),
				"La partie doit être en cours après 2 rencontres");
		assertEquals(EtatJeu.DEFAITE, fin.verifierEtat(inventaire, 3), "Le joueur doit perdre à la 3ème rencontre");

		assertEquals(EtatJeu.DEFAITE, fin.verifierEtat(inventaire, 5),
				"Le joueur doit être en défaite si le score dépasse 3");
		inventaire.ajouterIngredient(i1);
		inventaire.ajouterIngredient(i2);
		inventaire.ajouterIngredient(i3);
		inventaire.ajouterIngredient(i4);
		inventaire.ajouterIngredient(i5);

		assertEquals(EtatJeu.VICTOIRE, fin.verifierEtat(inventaire, 0),
				"Le joueur doit gagner si l'antidote est complet");
		assertEquals(EtatJeu.VICTOIRE, fin.verifierEtat(inventaire, 3),
				"La victoire devrait être prioritaire sur la défaite si l'antidote est fini");
	}
}