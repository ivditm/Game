package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import danger.GestionRencontres;
import danger.Monstre;
import jeu.Zone;
import play.Personnage;

/** Tests unitaires de GestionRencontres. */
class GestionRencontresTest {

	private Zone zone1;
	private Zone zone2;
	private Zone zone3;
	private Monstre monstre;
	private Personnage personnage;
	private GestionRencontres gestion;

	@BeforeEach
	void setUp() {
		zone1 = new Zone("Hall", "Le hall d'entrée", "hall.png");
		zone2 = new Zone("Couloir", "Un couloir sombre", "couloir.png");
		zone3 = new Zone("Labo", "Un laboratoire", "labo.png");

		monstre = new Monstre(zone2);
		personnage = new Personnage(zone1);
		gestion = new GestionRencontres(monstre);
	}

	@Test
	final void testConstructeur() {
		assertNotNull(gestion);
		assertEquals(0, gestion.getNbRencontres(), "Le compteur doit être à 0 à l'initialisation");
		assertEquals(monstre, gestion.getMonstre());
	}

	@Test
	final void testVerifierRencontrePasDeMemeZone() {
		assertFalse(gestion.verifierRencontre(personnage),
				"Pas de rencontre si le personnage et le monstre sont dans des zones différentes");
		assertEquals(0, gestion.getNbRencontres());
	}

	@Test
	final void testVerifierRencontreMemeZone() {
		personnage.setZoneCourante(zone2);
		assertTrue(gestion.verifierRencontre(personnage),
				"Rencontre attendue quand le personnage et le monstre sont dans la même zone");
		assertEquals(1, gestion.getNbRencontres());
	}

	@Test
	final void testVerifierRencontreIncrementeCompteur() {
		personnage.setZoneCourante(zone2);
		gestion.verifierRencontre(personnage);
		gestion.verifierRencontre(personnage);
		gestion.verifierRencontre(personnage);
		assertEquals(3, gestion.getNbRencontres(), "3 rencontres consécutives doivent être comptabilisées");
	}

	@Test
	final void testVerifierRencontreZonePersonnageNull() {
		personnage.setZoneCourante(null);
		assertFalse(gestion.verifierRencontre(personnage),
				"Pas de rencontre si la zone du personnage est null");
		assertEquals(0, gestion.getNbRencontres());
	}

	@Test
	final void testVerifierRencontreAlterneZones() {
		personnage.setZoneCourante(zone2);
		gestion.verifierRencontre(personnage);

		personnage.setZoneCourante(zone1);
		gestion.verifierRencontre(personnage);

		personnage.setZoneCourante(zone2);
		gestion.verifierRencontre(personnage);

		assertEquals(2, gestion.getNbRencontres(), "Seules les rencontres en zone commune sont comptées");
	}

	@Test
	final void testDeplacerMonstre() {
		List<Zone> zones = Arrays.asList(zone1, zone2, zone3);
		gestion.deplacerMonstre(zones);
		assertTrue(zones.contains(gestion.getMonstre().getZoneCourante()),
				"Après déplacement, le monstre doit être dans une zone de la liste");
	}

	@Test
	final void testGetMonstre() {
		assertEquals(monstre, gestion.getMonstre());
	}

	@Test
	final void testGetNbRencontresInitial() {
		assertEquals(0, gestion.getNbRencontres());
	}
}
