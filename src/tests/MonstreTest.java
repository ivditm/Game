package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import danger.Monstre;
import jeu.Zone;

/** Tests unitaires de Monstre. */
class MonstreTest {

	private Zone zone1;
	private Zone zone2;
	private Zone zone3;
	private Monstre monstre;

	@BeforeEach
	void setUp() {
		zone1 = new Zone("Entrée", "L'entrée du bâtiment", "entree.png");
		zone2 = new Zone("Couloir", "Un long couloir", "couloir.png");
		zone3 = new Zone("Labo", "Un laboratoire", "labo.png");
		monstre = new Monstre(zone1);
	}

	@Test
	final void testConstructeurAvecZone() {
		assertNotNull(monstre);
		assertEquals(zone1, monstre.getZoneCourante());
	}

	@Test
	final void testConstructeurSansZone() {
		Monstre monstreSansZone = new Monstre();
		assertNull(monstreSansZone.getZoneCourante());
	}

	@Test
	final void testGetZoneCourante() {
		assertEquals(zone1, monstre.getZoneCourante());
	}

	@Test
	final void testSetZoneCourante() {
		monstre.setZoneCourante(zone2);
		assertEquals(zone2, monstre.getZoneCourante());
	}

	@Test
	final void testSetZoneCouranteNull() {
		monstre.setZoneCourante(null);
		assertNull(monstre.getZoneCourante());
	}

	@Test
	final void testSeDeplacerAleatoirementResteDansLaListe() {
		List<Zone> zones = Arrays.asList(zone1, zone2, zone3);
		monstre.seDeplacerAleatoirement(zones);
		assertTrue(zones.contains(monstre.getZoneCourante()),
				"Le monstre doit être dans une zone de la liste après déplacement");
	}

	@Test
	final void testSeDeplacerAleatoirementListeUneZone() {
		List<Zone> zones = Arrays.asList(zone2);
		monstre.seDeplacerAleatoirement(zones);
		assertEquals(zone2, monstre.getZoneCourante(), "Avec une seule zone, le monstre doit y aller");
	}

	@Test
	final void testSeDeplacerAleatoirementListeVide() {
		List<Zone> zones = new ArrayList<>();
		Zone ancienneZone = monstre.getZoneCourante();
		monstre.seDeplacerAleatoirement(zones);
		assertEquals(ancienneZone, monstre.getZoneCourante(), "Une liste vide ne doit pas changer la position");
	}

	@Test
	final void testSeDeplacerAleatoirementListeNull() {
		Zone ancienneZone = monstre.getZoneCourante();
		monstre.seDeplacerAleatoirement(null);
		assertEquals(ancienneZone, monstre.getZoneCourante(), "Une liste null ne doit pas changer la position");
	}

	@Test
	final void testToStringContientNomZone() {
		assertTrue(monstre.toString().contains("Entrée"));
	}

	@Test
	final void testToStringZoneNull() {
		Monstre monstreSansZone = new Monstre();
		assertTrue(monstreSansZone.toString().contains("inconnue"));
	}
}
