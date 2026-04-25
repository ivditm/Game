package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jeu.Zone;
import play.Personnage;

/** Tests unitaires de Personnage. */
class PersonnageTest {

	private Zone zoneDepart;
	private Zone zoneArrivee;
	private Personnage personnage;

	@BeforeEach
	void setUp() {
		zoneDepart = new Zone("Hall", "Le hall d'entrée", "hall.png");
		zoneArrivee = new Zone("Labo", "Un laboratoire", "labo.png");
		personnage = new Personnage(zoneDepart);
	}

	@Test
	final void testConstructeur() {
		assertNotNull(personnage, "Le personnage ne doit pas être null");
		assertEquals(zoneDepart, personnage.getZoneCourante(), "La zone courante doit être la zone de départ");
	}

	@Test
	final void testInventaireInitialVide() {
		assertNotNull(personnage.getInventaire(), "L'inventaire ne doit pas être null");
		assertTrue(personnage.getInventaire().getObjets().isEmpty(), "L'inventaire doit être vide au départ");
	}

	@Test
	final void testGetZoneCourante() {
		assertEquals(zoneDepart, personnage.getZoneCourante());
	}

	@Test
	final void testSetZoneCourante() {
		personnage.setZoneCourante(zoneArrivee);
		assertEquals(zoneArrivee, personnage.getZoneCourante(), "La zone courante doit être mise à jour");
	}

	@Test
	final void testSetZoneCouranteNull() {
		personnage.setZoneCourante(null);
		assertEquals(null, personnage.getZoneCourante());
	}

	@Test
	final void testToStringContientNomZone() {
		String description = personnage.toString();
		assertTrue(description.contains("Hall"), "toString doit contenir le nom de la zone courante");
	}

	@Test
	final void testToStringApresDeplacementContientNouvelleZone() {
		personnage.setZoneCourante(zoneArrivee);
		String description = personnage.toString();
		assertTrue(description.contains("Labo"), "toString doit refléter la nouvelle zone");
	}

	@Test
	final void testToStringZoneNull() {
		personnage.setZoneCourante(null);
		String description = personnage.toString();
		assertTrue(description.contains("inconnue"), "toString doit indiquer 'inconnue' quand la zone est null");
	}
}
