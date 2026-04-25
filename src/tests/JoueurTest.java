package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import play.Joueur;

/** Tests unitaires de Joueur. */
class JoueurTest {

	private Joueur joueur;

	@BeforeEach
	void setUp() {
		joueur = new Joueur("Alex");
	}

	@Test
	final void testConstructeur() {
		assertNotNull(joueur, "Le joueur ne doit pas être null");
		assertEquals("Alex", joueur.getPseudo());
		assertEquals(0, joueur.getNbVictoires());
		assertEquals(0, joueur.getNbDefaites());
		assertTrue(joueur.getSauvegardes().isEmpty());
	}

	@Test
	final void testGetPseudo() {
		assertEquals("Alex", joueur.getPseudo());
	}

	@Test
	final void testAjouterVictoire() {
		joueur.ajouterVictoire();
		assertEquals(1, joueur.getNbVictoires());
	}

	@Test
	final void testAjouterPlusieursVictoires() {
		joueur.ajouterVictoire();
		joueur.ajouterVictoire();
		joueur.ajouterVictoire();
		assertEquals(3, joueur.getNbVictoires());
	}

	@Test
	final void testAjouterDefaite() {
		joueur.ajouterDefaite();
		assertEquals(1, joueur.getNbDefaites());
	}

	@Test
	final void testAjouterPlusieursDefaites() {
		joueur.ajouterDefaite();
		joueur.ajouterDefaite();
		assertEquals(2, joueur.getNbDefaites());
	}

	@Test
	final void testVictoiresEtDefaitesIndependants() {
		joueur.ajouterVictoire();
		joueur.ajouterDefaite();
		joueur.ajouterDefaite();
		assertEquals(1, joueur.getNbVictoires());
		assertEquals(2, joueur.getNbDefaites());
	}

	@Test
	final void testAjouterSauvegarde() {
		joueur.ajouterSauvegarde("sauvegardes/Alex/partie1.ser");
		assertEquals(1, joueur.getSauvegardes().size());
		assertTrue(joueur.getSauvegardes().contains("sauvegardes/Alex/partie1.ser"));
	}

	@Test
	final void testAjouterPlusieursSauvegardes() {
		joueur.ajouterSauvegarde("sauvegardes/Alex/partie1.ser");
		joueur.ajouterSauvegarde("sauvegardes/Alex/partie2.ser");
		assertEquals(2, joueur.getSauvegardes().size());
	}

	@Test
	final void testSupprimerSauvegarde() {
		joueur.ajouterSauvegarde("sauvegardes/Alex/partie1.ser");
		joueur.supprimerSauvegarde("sauvegardes/Alex/partie1.ser");
		assertTrue(joueur.getSauvegardes().isEmpty(), "La liste doit être vide après suppression");
	}

	@Test
	final void testSupprimerSauvegardeInexistante() {
		joueur.ajouterSauvegarde("sauvegardes/Alex/partie1.ser");
		joueur.supprimerSauvegarde("inexistant.ser");
		assertEquals(1, joueur.getSauvegardes().size(), "La liste ne doit pas changer si le fichier n'existe pas");
	}

	@Test
	final void testToStringContientPseudo() {
		assertTrue(joueur.toString().contains("Alex"));
	}

	@Test
	final void testToStringContientStatistiques() {
		joueur.ajouterVictoire();
		joueur.ajouterDefaite();
		String desc = joueur.toString();
		assertTrue(desc.contains("1"), "toString doit contenir les statistiques");
		assertFalse(desc.isEmpty());
	}
}
