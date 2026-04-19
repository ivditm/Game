package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import danger.Monstre;
import interactions.Interactable;
import jeu.Direction;
import jeu.Zone;

class ZoneTest {

	private Zone zone;
	private Zone zoneVoisine;
	private Interactable fausseEnigme;
	private Monstre fauxMonstre;

	@BeforeEach
	void setUp() throws Exception {
		zone = new Zone("Couloir", "Un couloir sombre", "couloir.png");
		zoneVoisine = new Zone("Labo", "Un laboratoire secret", "labo.png");

		fausseEnigme = new Interactable() {
			@Override
			public String toString() {
				return "EnigmePorte";
			}
		};

		fauxMonstre = new Monstre() {
		};
	}

	@Test
	final void testZone() {
		assertNotNull(zone, "La zone ne doit pas être null");
		assertEquals("Couloir", zone.getNom());
		assertEquals("couloir.png", zone.nomImage());
		assertTrue(zone.getInteractables().isEmpty(), "La liste des interactables doit être vide au départ");
		assertNull(zone.getMonstre(), "Il ne doit pas y avoir de monstre au départ");
	}

	@Test
	final void testGetNom() {
		assertEquals("Couloir", zone.getNom());
	}

	@Test
	final void testNomImage() {
		assertEquals("couloir.png", zone.nomImage());
	}

	@Test
	final void testAjouteSortieEtObtientSortie() {
		assertNull(zone.obtientSortie(Direction.NORD), "Doit retourner null si aucune sortie n'existe");

		zone.ajouteSortie(Direction.NORD, zoneVoisine);
		assertEquals(zoneVoisine, zone.obtientSortie(Direction.NORD), "Doit retourner la zone voisine ajoutée");
	}

	@Test
	final void testAjouteObstacleEtGetObstacle() {
		assertNull(zone.getObstacle(Direction.EST), "Doit retourner null si aucun obstacle n'existe");

		zone.ajouteObstacle(Direction.EST, fausseEnigme);
		assertEquals(fausseEnigme, zone.getObstacle(Direction.EST), "Doit retourner l'obstacle ajouté");
	}

	@Test
	final void testRetirerObstacle() {
		zone.ajouteObstacle(Direction.SUD, fausseEnigme);
		assertNotNull(zone.getObstacle(Direction.SUD));

		zone.retirerObstacle(Direction.SUD);
		assertNull(zone.getObstacle(Direction.SUD), "L'obstacle doit être retiré");
	}

	@Test
	final void testSetMonstreEtGetMonstre() {
		zone.setMonstre(fauxMonstre);
		assertEquals(fauxMonstre, zone.getMonstre(), "Doit retourner le monstre défini");
	}

	@Test
	final void testPossedeMonstre() {
		assertFalse(zone.possedeMonstre(), "Doit être false par défaut");
		zone.setMonstre(fauxMonstre);
		assertTrue(zone.possedeMonstre(), "Doit être true après l'ajout d'un monstre");
	}

	@Test
	final void testAjouterInteractableEtGetInteractables() {
		zone.ajouterInteractable(fausseEnigme);
		assertEquals(1, zone.getInteractables().size(), "La liste doit contenir 1 élément");
		assertTrue(zone.getInteractables().contains(fausseEnigme), "La liste doit contenir l'énigme ajoutée");
	}

	@Test
	final void testToString() {
		assertEquals("Couloir : Un couloir sombre", zone.toString());
	}

	@Test
	final void testDescriptionLongue() {
		String descVide = zone.descriptionLongue();
		assertTrue(descVide.contains("Vous êtes dans : Couloir"), "Doit contenir le nom");
		assertTrue(descVide.contains("Un couloir sombre"), "Doit contenir la description");
		assertFalse(descVide.contains("Éléments interactifs ici"),
				"Ne doit pas afficher la section interactables si vide");
		assertTrue(descVide.contains("Sorties possibles : []"), "Doit afficher une liste de sorties vide");
		zone.ajouterInteractable(fausseEnigme);
		String descAvecObjets = zone.descriptionLongue();
		assertTrue(descAvecObjets.contains("Éléments interactifs ici : EnigmePorte"), "Doit lister les interactables");
		zone.ajouteSortie(Direction.NORD, zoneVoisine);
		zone.ajouteObstacle(Direction.EST, fausseEnigme);
		zone.ajouteSortie(Direction.SUD, zoneVoisine);
		zone.ajouteObstacle(Direction.SUD, fausseEnigme);
		String descAvecSorties = zone.descriptionLongue();
		assertTrue(descAvecSorties.contains("NORD"), "Doit contenir la sortie libre");
		assertTrue(descAvecSorties.contains("EST"), "Doit contenir la direction bloquée");
		assertTrue(descAvecSorties.contains("SUD"), "Doit contenir le SUD une seule fois");
	}
}