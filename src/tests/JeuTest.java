package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jeu.Jeu;
import persistance.EtatPartie;
import play.Joueur;

class JeuTest {

	private Jeu jeu;
	private Joueur joueur;

	@BeforeEach
	void setUp() throws Exception {
		joueur = new Joueur("Testeur");
		jeu = new Jeu(joueur);
	}

	@Test
	void testJeu() {
		Jeu jeuSansCompte = new Jeu();
		assertNotNull(jeuSansCompte, "Le jeu par défaut doit s'instancier sans erreur.");
	}

	@Test
	void testJeuJoueur() {
		assertNotNull(jeu, "Le jeu avec un joueur spécifique doit s'instancier correctement.");
	}

	@Test
	void testJeuJoueurEtatPartie() {
		EtatPartie etat = new EtatPartie("Couloir Central (Bat A)", 2, false, 1, 600000);
		Jeu jeuReprise = new Jeu(joueur, etat);

		assertNotNull(jeuReprise, "Le jeu doit pouvoir charger un état de sauvegarde.");
	}

	@Test
	void testTraiterCommandeSansGUI() {
		Exception exception = assertThrows(IllegalStateException.class, () -> {
			jeu.traiterCommande("AIDE");
		});

		assertEquals("GUI non initialisée !", exception.getMessage(),
				"Le jeu doit lever une exception si la GUI est absente.");
	}
}