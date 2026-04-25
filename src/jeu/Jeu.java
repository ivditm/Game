package jeu;

import java.util.ArrayList;
import java.util.List;

import play.Ingredient;

/**
 * La classe {@code Jeu} représente la logique principale du jeu.
 * <p>
 * Elle gère les zones, les déplacements, les commandes utilisateur et
 * l'interaction avec l'interface graphique {@link GUI}.
 * <p>
 * Les fonctionnalités principales incluent :
 * <ul>
 * <li>Création et initialisation des zones du jeu</li>
 * <li>Affichage de la localisation et des images correspondantes</li>
 * <li>Traitement des commandes saisies par l'utilisateur</li>
 * <li>Gestion de la fin du jeu</li>
 * </ul>
 *
 * Exemple d'utilisation :
 * 
 * <pre>
 * Jeu jeu = new Jeu();
 * GUI gui = new GUI(jeu);
 * jeu.setGUI(gui);
 * </pre>
 */
public class Jeu {

	/** Interface graphique associée au jeu */
	private GUI gui;

	/** Zone actuelle dans laquelle se trouve le joueur */
	private Zone zoneCourante;

	/** Zone precedente ds laquelle s'est trouvé le jouer */
	private Zone zonePrecedente;

	private List<Zone> toutesLesZones;

	/**
	 * Construit un nouveau jeu.
	 * <p>
	 * Les zones sont créées et reliées entre elles, mais l'interface graphique
	 * n'est pas encore initialisée. Utiliser {@link #setGUI(GUI)} pour associer une
	 * interface.
	 */
	public Jeu() {
		creerCarte();
		gui = null;
	}

	/**
	 * Associe une interface graphique au jeu et affiche le message de bienvenue.
	 *
	 * @param g l'instance de {@link GUI} à associer
	 */
	public void setGUI(GUI g) {
		gui = g;
		afficherMessageDeBienvenue();
	}

	/**
	 * Crée et initialise les zones du jeu et leurs sorties.
	 */
	private void creerCarte() {
		// ==========================================
		// CRÉATION DES ZONES (18 zones au total)
		// ==========================================

		// Bâtiment A (6 zones)
		Zone salleInterdite = new Zone("Salle Interdite", "Une pièce sombre et lugubre. L'expérience a mal tourné ici.",
				"1_SALLE_INTERDITE.jpg");
		Zone couloirA = new Zone("Couloir Central (A)", "Un couloir sombre...", "Couloir_A.jpg");
		Zone laboPrincipal = new Zone("Laboratoire Principal", "Des paillasses renversées.", "Labo_Principal.jpg");
		Zone salleInfoA = new Zone("Salle Informatique (A)", "L'ordinateur contient la recette.",
				"2_SALLE_INFORMATIQUE.jpg");
		Zone stockageChimique = new Zone("Stockage Chimique", "Une odeur âcre flotte dans l'air.",
				"3_STOCKAGE_CHIMIQUE.jpg");
		Zone infirmerie = new Zone("Infirmerie", "Des lits médicaux en désordre.", "4_INFIRMERIE.jpg");

		// Bâtiment B (7 zones)
		Zone cafeteria = new Zone("Cafétéria", "Des tables renversées.", "Cafeteria.jpg");
		Zone cuisine = new Zone("Cuisine", "Les frigos sont ouverts et vides.", "Cuisine.jpg");
		Zone couloirB = new Zone("Couloir (B)", "Un long couloir vitré.", "Couloir_B.jpg");
		Zone buPrincipale = new Zone("Bibliothèque Universitaire", "Des rangées de livres poussiéreux.",
				"BU_Principale.jpg");
		Zone salleInfoB = new Zone("Salle Informatique (BU)", "Des PC alignés.", "Salle_Info_BU.jpg");
		Zone salleEtude = new Zone("Salle d'Étude", "Un silence pesant.", "Salle_Etude.jpg");
		Zone toilettesBU = new Zone("Toilettes (BU)", "Des lavabos brisés.", "Toilettes_BU.jpg");

		// Bâtiment C (5 zones)
		Zone cafeteriaProfs = new Zone("Cafétéria des Professeurs", "Un espace de détente saccagé.",
				"Cafeteria_Profs.jpg");
		Zone salleReunion = new Zone("Salle de Réunion", "Une grande table ovale.", "Salle_Reunion.jpg");
		Zone toilettesC1 = new Zone("Toilettes (Cafét Profs)", "Des cabines fermées.", "Toilettes_C1.jpg");
		Zone toilettesC2 = new Zone("Toilettes (Salle Réunion)", "L'eau coule encore.", "Toilettes_C2.jpg");

		// ==========================================
		// CONNEXIONS DES ZONES
		// ==========================================

		// Bâtiment A
		salleInterdite.ajouteSortie(Direction.SUD, couloirA);
		couloirA.ajouteSortie(Direction.NORD, salleInterdite);
		couloirA.ajouteSortie(Direction.SUD, laboPrincipal);
		couloirA.ajouteSortie(Direction.NORD_EST, salleInfoA);
		couloirA.ajouteSortie(Direction.NORD_OUEST, stockageChimique);
		couloirA.ajouteSortie(Direction.OUEST, infirmerie);

		// Connexion inter-bâtiments (A vers B) [cite: 401]
		// Normalement verrouillé, mais on définit la connexion géographique
		couloirA.ajouteSortie(Direction.EST, cafeteria);
		cafeteria.ajouteSortie(Direction.OUEST, couloirA); // Retour (si permis)

		// Bâtiment B [cite: 412, 418, 421-424]
		cafeteria.ajouteSortie(Direction.OUEST, cuisine);
		// (Ajoute ici les autres connexions du Bâtiment B selon ton UML/Plan)
		buPrincipale.ajouteSortie(Direction.NORD, salleInfoB);
		buPrincipale.ajouteSortie(Direction.EST, salleEtude);
		buPrincipale.ajouteSortie(Direction.NORD_OUEST, toilettesBU);

		// Connexion inter-bâtiments (A vers C, via le Sud)
		couloirA.ajouteSortie(Direction.SUD, cafeteriaProfs); // ou couloirC

		// Bâtiment C
		cafeteriaProfs.ajouteSortie(Direction.OUEST, toilettesC1);
		cafeteriaProfs.ajouteSortie(Direction.NORD, salleReunion);
		salleReunion.ajouteSortie(Direction.EST, toilettesC2);

		// ==========================================
		// CRÉATION ET PLACEMENT DES INGRÉDIENTS
		// ==========================================

		// Ingrédient 1
		Ingredient ing1 = new Ingredient("Poudre stabilisatrice", "Base chimique.", 1);
		stockageChimique.ajouterInteractable(ing1);

		// Ingrédient 2
		Ingredient ing2 = new Ingredient("Adrénaline pure", "Stimulant puissant.", 2);
		infirmerie.ajouterInteractable(ing2);

		// Ingrédient 3
		Ingredient ing3 = new Ingredient("Bicarbonate de soude", "Agent neutralisant.", 3);
		cuisine.ajouterInteractable(ing3);

		// Ingrédient 4
		Ingredient ing4 = new Ingredient("Échantillon fongique caché", "Souche biologique.", 4);
		toilettesBU.ajouterInteractable(ing4);

		// Ingrédient 5
		Ingredient ing5 = new Ingredient("Solvant expérimental", "Catalyseur final.", 5);
		salleReunion.ajouterInteractable(ing5);

		// ==========================================
		// INITIALISATION DU DÉPART
		// ==========================================

		toutesLesZones = new ArrayList<>(18);

		// Bâtiment A
		toutesLesZones.add(salleInterdite);
		toutesLesZones.add(couloirA);
		toutesLesZones.add(laboPrincipal);
		toutesLesZones.add(salleInfoA);
		toutesLesZones.add(stockageChimique);
		toutesLesZones.add(infirmerie);

		// Bâtiment B
		toutesLesZones.add(cafeteria);
		toutesLesZones.add(cuisine);
		toutesLesZones.add(couloirB);
		toutesLesZones.add(buPrincipale);
		toutesLesZones.add(salleInfoB);
		toutesLesZones.add(salleEtude);
		toutesLesZones.add(toilettesBU);

		// Bâtiment C
		toutesLesZones.add(cafeteriaProfs);
		toutesLesZones.add(salleReunion);
		toutesLesZones.add(toilettesC1);
		toutesLesZones.add(toilettesC2);
		// Zone de départ du joueur
		zonePrecedente = zoneCourante = salleInterdite;
	}

	/**
	 * Vérifie l'initialisation de gui
	 */
	private void verifieGUI() {
		if (gui == null) {
			throw new IllegalStateException("GUI non initialisée !");
		}
	}

	/**
	 * Affiche la description complète de la zone actuelle via l'interface
	 * graphique.
	 */
	private void afficherLocalisation() {
		verifieGUI();
		gui.afficher(zoneCourante.descriptionLongue());
		gui.afficher();
	}

	/**
	 * Affiche le message de bienvenue et la localisation initiale.
	 */
	private void afficherMessageDeBienvenue() {
		verifieGUI();
		gui.afficher("Bienvenue !");
		gui.afficher();
		gui.afficher("Tapez '?' pour obtenir de l'aide.");
		gui.afficher();
		afficherLocalisation();
		gui.afficheImage(zoneCourante.nomImage());
	}

	/**
	 * Traite une commande saisie par l'utilisateur.
	 * <p>
	 * Les commandes reconnues sont :
	 * <ul>
	 * <li>?: affiche l'aide</li>
	 * <li>AIDE: affiche l'aide</li>
	 * <li>N ou NORD: se déplacer vers le nord</li>
	 * <li>S ou SUD: se déplacer vers le sud</li>
	 * <li>E ou EST: se déplacer vers l'est</li>
	 * <li>O ou OUEST: se déplacer vers l'ouest</li>
	 * <li>Q ou QUITTER: termine le jeu</li>
	 * </ul>
	 * Toute commande non reconnue génère un message d'erreur.
	 *
	 * @param commande la commande saisie par l'utilisateur
	 */
	public void traiterCommande(String commande) {
		verifieGUI();
		gui.afficher("> " + commande + "\n");
		switch (commande.toUpperCase()) {
		case "?", "AIDE" -> afficherAide();
		case "N", "NORD" -> allerEn(Direction.NORD);
		case "S", "SUD" -> allerEn(Direction.SUD);
		case "E", "EST" -> allerEn(Direction.EST);
		case "O", "OUEST" -> allerEn(Direction.OUEST);
		case "Q", "QUITTER" -> terminer();
		case "R", "RETOUR" -> this.revenir();
		default -> gui.afficher("Commande inconnue");
		}
	}

	/**
	 * Affiche l'aide à l'utilisateur, incluant toutes les commandes disponibles.
	 */
	private void afficherAide() {
		verifieGUI();
		gui.afficher("Etes-vous perdu ?");
		gui.afficher();
		gui.afficher("Les commandes autorisées sont :");
		gui.afficher();
		gui.afficher(Commande.toutesLesDescriptions().toString());
		gui.afficher();
	}

	/**
	 * Déplace le joueur vers une nouvelle zone dans la direction donnée.
	 *
	 * @param direction ("NORD", "SUD", "EST" ou "OUEST")
	 */
	private void allerEn(Direction direction) {
		verifieGUI();
		Zone nouvelle = zoneCourante.obtientSortie(direction);
		if (nouvelle == null) {
			gui.afficher("Pas de sortie " + direction);
			gui.afficher();
		} else {
			zonePrecedente = zoneCourante;
			zoneCourante = nouvelle;
			gui.afficher(zoneCourante.descriptionLongue());
			gui.afficher();
			gui.afficheImage(zoneCourante.nomImage());
		}
	}

	/** Retour ds la chambre precedente */
	private void revenir() {
		verifieGUI();
		Zone temp = zoneCourante;
		zoneCourante = zonePrecedente;
		zonePrecedente = temp;
		gui.afficher(zoneCourante.descriptionLongue());
		gui.afficher();
		gui.afficheImage(zoneCourante.nomImage());
	}

	/**
	 * Termine le jeu, affiche un message d'au revoir et désactive l'interface.
	 */
	private void terminer() {
		verifieGUI();
		gui.afficher("Au revoir...");
		gui.enable(false);
	}
}
