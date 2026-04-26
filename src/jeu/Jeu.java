package jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import danger.GestionRencontres;
import danger.Monstre;
import interactions.Coffre;
import interactions.Enigme;
import interactions.Interactable;
import interactions.Ordinateur;
import play.Ingredient;
import play.Personnage;
import play.Recette;
import win.EtatJeu;
import win.FinDePartie;

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

	private Personnage joueur;

	/** Gestionnaire du monstre et des rencontres */
	private GestionRencontres gestionRencontres;

	/** Chronomètre global de la partie (ex: 15 minutes) */
	private TimeManagement chronometre;

	/** Arbitre qui vérifie les conditions de victoire/défaite */
	private FinDePartie arbitre;

	/**
	 * Construit un nouveau jeu.
	 * <p>
	 * Les zones sont créées et reliées entre elles, mais l'interface graphique
	 * n'est pas encore initialisée. Utiliser {@link #setGUI(GUI)} pour associer une
	 * interface.
	 */
	public Jeu() {
		this.chronometre = new TimeManagement(15); // 15 minutes pour sauver le monde
		this.arbitre = new FinDePartie();
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

		// TODO : поставить правильные пути проверить правильность описаний
		// Bâtiment A (6 zones)
		Zone salleInterdite = new Zone("Salle Interdite", "Une pièce sombre et lugubre. L'expérience a mal tourné ici.",
				"salleinterdite.png");
		Zone couloirA = new Zone("Couloir Central (A)", "Un couloir sombre...", "couloirprincipal.png");
		Zone laboPrincipal = new Zone("Laboratoire Principal", "Des paillasses renversées.", "laboprincipal.png");
		Zone salleInfoA = new Zone("Salle Informatique (A)", "L'ordinateur contient la recette.", "salleinfo.png");
		Zone stockageChimique = new Zone("Stockage Chimique", "Une odeur âcre flotte dans l'air.",
				"stockagechimique.png");
		Zone infirmerie = new Zone("Infirmerie", "Des lits médicaux en désordre.", "infirmerie.png");

		// Bâtiment B (7 zones)
		Zone cafeteria = new Zone("Cafétéria", "Des tables renversées.", "cafetaria.png");
		Zone cuisine = new Zone("Cuisine", "Les frigos sont ouverts et vides.", "cuisine.png");
		Zone couloirB = new Zone("Couloir (B)", "Un long couloir vitré.", "couloirbu.png");
		Zone buPrincipale = new Zone("Bibliothèque Universitaire", "Des rangées de livres poussiéreux.",
				"bibliotheque.png");
		Zone salleInfoB = new Zone("Salle Informatique (BU)", "Des PC alignés.", "salleinfobatimentB.png");
		Zone salleEtude = new Zone("Salle d'Étude", "Un silence pesant.", "salledetude.png");
		Zone toilettesBU = new Zone("Toilettes (BU)", "Des lavabos brisés.", "toilettes.png");

		// Bâtiment C (5 zones)
		Zone cafeteriaProfs = new Zone("Cafétéria des Professeurs", "Un espace de détente saccagé.",
				"cafetprofesseurs.png");
		Zone salleReunion = new Zone("Salle de Réunion", "Une grande table ovale.", "salledereunion.png");
		Zone toilettesC1 = new Zone("Toilettes (Cafét Profs)", "Des cabines fermées.", "toilettes.png");
		Zone toilettesC2 = new Zone("Toilettes (Salle Réunion)", "L'eau coule encore.", "toilettes.png");
		// ==========================================
		// CONNEXIONS DES ZONES
		// ==========================================

		salleInterdite.ajouteSortie(Direction.SUD, couloirA);
		couloirA.ajouteSortie(Direction.NORD, salleInterdite);

		couloirA.ajouteSortie(Direction.SUD, laboPrincipal);
		laboPrincipal.ajouteSortie(Direction.NORD, couloirA);

		couloirA.ajouteSortie(Direction.NORD_EST, salleInfoA);
		salleInfoA.ajouteSortie(Direction.SUD_OUEST, couloirA);

		// ВОЗВРАЩАЕМ ПОТЕРЯННЫЕ КОМНАТЫ В БАШНЕ А:
		couloirA.ajouteSortie(Direction.NORD_OUEST, stockageChimique);
		stockageChimique.ajouteSortie(Direction.SUD_EST, couloirA);

		couloirA.ajouteSortie(Direction.OUEST, infirmerie);
		infirmerie.ajouteSortie(Direction.EST, couloirA);

		// Connexion inter-bâtiments (A vers B)
		couloirA.ajouteSortie(Direction.EST, couloirB);
		couloirB.ajouteSortie(Direction.OUEST, couloirA);

		// Bâtiment B
		couloirB.ajouteSortie(Direction.NORD, cafeteria);
		cafeteria.ajouteSortie(Direction.SUD, couloirB);

		cafeteria.ajouteSortie(Direction.OUEST, cuisine);
		cuisine.ajouteSortie(Direction.EST, cafeteria);

		couloirB.ajouteSortie(Direction.SUD, buPrincipale);
		buPrincipale.ajouteSortie(Direction.NORD_EST, couloirB);

		buPrincipale.ajouteSortie(Direction.NORD, salleInfoB);
		salleInfoB.ajouteSortie(Direction.SUD, buPrincipale);

		buPrincipale.ajouteSortie(Direction.EST, salleEtude);
		salleEtude.ajouteSortie(Direction.OUEST, buPrincipale);

		// ВОЗВРАЩАЕМ ПОТЕРЯННЫЕ ТУАЛЕТЫ BU:
		buPrincipale.ajouteSortie(Direction.NORD_OUEST, toilettesBU);
		toilettesBU.ajouteSortie(Direction.SUD_EST, buPrincipale);

		// Connexion inter-bâtiments (A vers C)
		couloirA.ajouteSortie(Direction.SUD_EST, cafeteriaProfs);
		cafeteriaProfs.ajouteSortie(Direction.NORD_OUEST, couloirA);

		// Bâtiment C
		cafeteriaProfs.ajouteSortie(Direction.OUEST, toilettesC1);
		toilettesC1.ajouteSortie(Direction.EST, cafeteriaProfs);

		// ВОЗВРАЩАЕМ ВХОД В ПЕРЕГОВОРНУЮ:
		cafeteriaProfs.ajouteSortie(Direction.NORD, salleReunion);
		salleReunion.ajouteSortie(Direction.SUD, cafeteriaProfs);

		salleReunion.ajouteSortie(Direction.EST, toilettesC2);
		toilettesC2.ajouteSortie(Direction.OUEST, salleReunion);

		List<Enigme> poolEnigmes = genererToutesLesEnigmes();
		Collections.shuffle(poolEnigmes);

		// 3. On place les 4 premières énigmes du paquet mélangé sur nos portes
		// stratégiques
		couloirA.ajouteObstacle(Direction.NORD_OUEST, poolEnigmes.remove(0)); // Bloque le Stockage Chimique (Ingrédient
																				// 1)
		couloirA.ajouteObstacle(Direction.OUEST, poolEnigmes.remove(0)); // Bloque l'Infirmerie (Ingrédient 2)
		buPrincipale.ajouteObstacle(Direction.NORD_OUEST, poolEnigmes.remove(0)); // Bloque les Toilettes BU (Ingrédient
																					// 4)
		cafeteriaProfs.ajouteObstacle(Direction.NORD, poolEnigmes.remove(0)); // Bloque la Salle de Réunion (Ingrédient
																				// 5)

		// ==========================================
		// CRÉATION ET PLACEMENT DES INGRÉDIENTS
		// ==========================================

		// Ingrédient 1
		Ingredient ing1 = new Ingredient("Poudre stabilisatrice", "Base chimique.", 1);
		Coffre coffre1 = new Coffre("Armoire sécurisée", "Une armoire en métal lourd.", ing1);
		stockageChimique.ajouterInteractable(coffre1);

		// Ingrédient 2
		Ingredient ing2 = new Ingredient("Adrénaline pure", "Stimulant puissant.", 2);
		Coffre coffre2 = new Coffre("Trousse de secours", "Une trousse médicale rouge.", ing2);
		infirmerie.ajouterInteractable(coffre2);

		// Ingrédient 3
		Ingredient ing3 = new Ingredient("Bicarbonate de soude", "Agent neutralisant.", 3);
		Coffre coffre3 = new Coffre("Réfrigérateur", "Un vieux frigo ronronnant.", ing3);
		cuisine.ajouterInteractable(coffre3);

		// Ingrédient 4
		Ingredient ing4 = new Ingredient("Échantillon fongique caché", "Souche biologique.", 4);
		Coffre coffre4 = new Coffre("Casier abandonné", "Un casier rouillé.", ing4);
		toilettesBU.ajouterInteractable(coffre4);

		// Ingrédient 5
		Ingredient ing5 = new Ingredient("Solvant expérimental", "Catalyseur final.", 5);
		Coffre coffre5 = new Coffre("Mallette diplomatique", "Une mallette noire verrouillée.", ing5);
		salleReunion.ajouterInteractable(coffre5);

		List<Ingredient> listeRecette = new ArrayList<>();
		listeRecette.add(ing1);
		listeRecette.add(ing2);
		listeRecette.add(ing3);
		listeRecette.add(ing4);
		listeRecette.add(ing5);

		Recette recetteAntidote = new Recette("Protocole Antidote",
				"Document confidentiel indiquant l'ordre de synthèse.", listeRecette);
		Ordinateur ordi = new Ordinateur("Ordinateur central", "Un terminal sécurisé par un mot de passe.",
				recetteAntidote, poolEnigmes.remove(0));
		salleInfoA.ajouterInteractable(ordi);

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

		// ==========================================
		// OBSTACLES : PORTES VERROUILLÉES ALÉATOIREMENT
		// ==========================================
		class Porte {
			Zone zone;
			Direction direction;

			Porte(Zone z, Direction d) {
				this.zone = z;
				this.direction = d;
			}
		}

		List<Porte> portesDisponibles = new ArrayList<>();
		for (Zone z : toutesLesZones) {
			for (Direction d : Direction.values()) {
				if (z.obtientSortie(d) != null) {
					portesDisponibles.add(new Porte(z, d));
				}
			}
		}

		Collections.shuffle(portesDisponibles);
		int nbPortesABloquer = 7;
		for (int i = 0; i < nbPortesABloquer; i++) {
			if (!poolEnigmes.isEmpty() && !portesDisponibles.isEmpty()) {
				Porte porteCible = portesDisponibles.remove(0);
				Enigme enigmeCible = poolEnigmes.remove(0);
				porteCible.zone.ajouteObstacle(porteCible.direction, enigmeCible);
			}
		}
		this.joueur = new Personnage(salleInterdite);
		zonePrecedente = zoneCourante = salleInterdite;
		Monstre monstre = new Monstre(salleReunion);
		this.gestionRencontres = new GestionRencontres(monstre);
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

		// ДОБАВЛЕНЫ ДИАГОНАЛЬНЫЕ НАПРАВЛЕНИЯ
		case "NE", "NORD_EST" -> allerEn(Direction.NORD_EST);
		case "NO", "NORD_OUEST" -> allerEn(Direction.NORD_OUEST);
		case "SE", "SUD_EST" -> allerEn(Direction.SUD_EST);
		case "SO", "SUD_OUEST" -> allerEn(Direction.SUD_OUEST);

		case "I", "INTERAGIR" -> interagir();
		case "INV", "INVENTAIRE" -> afficherInventaire();
		case "Q", "QUITTER" -> terminer();
		case "R", "RETOUR" -> this.revenir();
		default -> gui.afficher("Commande inconnue");
		}

		// Проверяем статус игры. Если игра не окончена, выводим HUD (жизни и время)
		if (actualiserStatut()) {
			int viesRestantes = 3 - gestionRencontres.getNbRencontres();
			gui.afficher("---------------------------------------------------");
			gui.afficher("[ ♥ Vies : " + viesRestantes + "/3  |  ⏱ Temps restant : "
					+ chronometre.getTempsRestantFormate() + " ]");
			gui.afficher("---------------------------------------------------");
			gui.afficher();
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
		Interactable obstacle = zoneCourante.getObstacle(direction);
		if (obstacle != null && obstacle instanceof Enigme enigme) {
			gui.afficher("La porte vers l'" + direction + " est verrouillée !");
			String reponse = javax.swing.JOptionPane.showInputDialog(null,
					enigme.examiner() + "\nVotre réponse (A, B, C ou D) :", "Énigme",
					javax.swing.JOptionPane.QUESTION_MESSAGE);
			if (reponse == null || reponse.trim().isEmpty()) {
				gui.afficher("Vous avez abandonné l'énigme.");
				return;
			}
			if (enigme.resoudre(reponse)) {
				gui.afficher("Bonne réponse ! " + enigme.donnerExplication());
				gui.afficher("La porte se déverrouille.");
				zoneCourante.retirerObstacle(direction);
			} else {
				gui.afficher("Mauvaise réponse... Vous perdez du temps !");
				chronometre.appliquerPenalite(1);
				return;
			}
		}
		Zone nouvelle = zoneCourante.obtientSortie(direction);
		if (nouvelle == null) {
			gui.afficher("Pas de sortie " + direction);
			gui.afficher();
		} else {
			zonePrecedente = zoneCourante;
			zoneCourante = nouvelle;
			joueur.setZoneCourante(zoneCourante);
			gestionRencontres.deplacerMonstre(toutesLesZones);
			if (gestionRencontres.verifierRencontre(joueur)) {
				gui.afficher("!!! LE MONSTRE VOUS A TROUVÉ !!!");
				String imageAvecMonstre = zoneCourante.nomImage().replace(".jpg", "_Monstre.jpg");
				gui.afficheImage(imageAvecMonstre);
				javax.swing.JOptionPane.showMessageDialog(null, "Le monstre vous a attaqué ! Fuyez !", "DANGER DE MORT",
						javax.swing.JOptionPane.WARNING_MESSAGE);

				this.revenir();
				return;
			}
			gui.afficher(zoneCourante.descriptionLongue());
			gui.afficher();
			actualiserImagePiece();
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
		actualiserImagePiece();
	}

	/**
	 * Termine le jeu, affiche un message d'au revoir et désactive l'interface.
	 */
	private void terminer() {
		verifieGUI();
		gui.afficher("Au revoir...");
		gui.enable(false);
	}

	/**
	 * Méthode utilitaire pour créer rapidement une énigme sans polluer le code.
	 */
	private Enigme creerEnigme(String question, String repA, String repB, String repC, String repD, String bonneRep,
			String explication) {
		java.util.Map<String, String> propositions = new java.util.LinkedHashMap<>();
		propositions.put("A", repA);
		propositions.put("B", repB);
		if (repC != null) {
			propositions.put("C", repC);
		}
		if (repD != null) {
			propositions.put("D", repD);
		}

		return new Enigme(question, propositions, bonneRep, explication);
	}

	/**
	 * Génère la banque complète d'énigmes pour le jeu.
	 */
	private List<Enigme> genererToutesLesEnigmes() {
		List<Enigme> liste = new ArrayList<>();

		// Question 1
		liste.add(
				creerEnigme("En chemo-informatique, comment est représenté le méthane (CH4) dans la notation SMILES ?",
						"[Methane]", "C", "H4C", "CHHHH", "B", "Le carbone seul implique les hydrogènes (C)."));

		// Question 2
		liste.add(creerEnigme(
				"Quelle est la complexité d'un calcul d'interactions de paire sans optimisation pour un système de N atomes ?",
				"O(N)", "O(log N)", "O(N^2)", "O(1)", "C", "Chaque atome interagit avec tous les autres (O(N^2))."));

		// Question 3
		liste.add(creerEnigme("Quel est le numéro atomique maximal que l'on peut stocker avec 7 bits ?", "64", "127",
				"255", "118", "B", "La valeur maximale sur 7 bits est 2^7 - 1 = 127."));

		// Question 4
		liste.add(creerEnigme(
				"Si une réaction nécessite un catalyseur ET une température élevée, quel opérateur décrit cette condition ?",
				"OU (OR)", "NON (NOT)", "ET (AND)", "OU exclusif (XOR)", "C",
				"L'opérateur logique ET (AND) exige que les deux conditions soient vraies."));

		// Question 5
		liste.add(creerEnigme(
				"L'ADN est souvent traité comme une chaîne de caractères (String). Combien de bits minimum faut-il pour coder une seule base (A, T, C, G) sans compression ?",
				"1 bit", "2 bits", "4 bits", "8 bits", "B", "Il faut 2 bits car 2^2 = 4 combinaisons possibles."));

		// Question 6
		liste.add(creerEnigme(
				"Dans une grille 3D (Voxel) représentant une protéine, si on double la résolution dans les 3 dimensions, par combien le nombre de points de calcul est-il multiplié ?",
				"2", "4", "6", "8", "D", "Le volume est multiplié par 2^3 = 8."));

		// Question 7
		liste.add(creerEnigme(
				"Un spectromètre de masse génère un nuage de points. Quel algorithme est souvent utilisé pour regrouper les pics similaires ?",
				"Tri à bulles (Bubble Sort)", "Clustering (K-means)", "Recherche binaire", "Algorithme de Dijkstra",
				"B", "Le Clustering sert à regrouper des données par similarité."));

		// Question 8
		liste.add(creerEnigme(
				"Pour simuler une orbitale atomique, on utilise souvent des fonctions gaussiennes. Quel type de processeur est le plus efficace pour ces calculs parallèles massifs ?",
				"CPU (Processeur central)", "GPU (Processeur graphique)", "Disque Dur", "RAM", "B",
				"Les GPU possèdent des milliers de coeurs optimisés pour le calcul parallèle."));

		// Question 9
		liste.add(creerEnigme(
				"Quel langage est principalement utilisé pour extraire des informations d'une base de données de molécules (ex: PubChem) ?",
				"HTML", "CSS", "SQL", "Python", "C",
				"SQL est le langage standard d'interrogation de bases de données relationnelles."));

		// Question 10
		liste.add(creerEnigme(
				"En intelligence artificielle, comment appelle-t-on le processus consistant à prédire les propriétés d'une molécule à partir de sa structure ?",
				"Debugging", "Apprentissage automatique (Machine Learning)", "Compilation", "Overclocking", "B",
				"C'est l'objectif du Machine Learning."));

		// Question 11
		liste.add(creerEnigme(
				"Si on considère une molécule comme un graphe, que représentent les \"sommets\" (nodes) du graphe ?",
				"Les liaisons chimiques", "Les électrons", "Les atomes", "Les neutrons", "C",
				"Les atomes sont les sommets et les liaisons sont les arêtes."));

		// Question 12
		liste.add(creerEnigme(
				"En simulation chimique, quel est le risque d'utiliser des nombres à virgule flottante de faible précision (32-bit) sur des millions d'étapes ?",
				"Une explosion nucléaire", "L'accumulation d'erreurs d'arrondi", "Un virus informatique",
				"La suppression du fichier", "B",
				"La perte de précision finit par fausser totalement les résultats à long terme."));

		// Question 13
		liste.add(creerEnigme(
				"Lequel de ces éléments est un périphérique d'entrée utilisé pour déplacer le curseur à l'écran ?",
				"Imprimante", "Écran", "Souris", "Haut-parleur", "C",
				"La souris est un périphérique d'entrée de pointage."));

		// Question 14
		liste.add(creerEnigme("Que signifie l'acronyme \"WWW\" dans une adresse de site web ?", "World Wide Web",
				"Western Web World", "World Wifi Web", "Wide Window Web", "A",
				"C'est le système hypertexte public fonctionnant sur Internet."));

		// Question 15
		liste.add(creerEnigme(
				"Lequel de ces formats de fichiers est couramment utilisé pour enregistrer des images ou des photos ?",
				".MP3", ".JPEG", ".EXE", ".PDF", "B", "JPEG est le format de compression d'image le plus répandu."));

		// Question 16
		liste.add(creerEnigme("À quoi sert la mémoire RAM dans un ordinateur ?",
				"À stocker les fichiers de manière permanente", "À refroidir le processeur",
				"À stocker temporairement les données des programmes en cours d'exécution", "À imprimer des documents",
				"C", "La RAM est une mémoire de travail volatile et très rapide."));

		// Question 17
		liste.add(creerEnigme(
				"Comment appelle-t-on un programme malveillant conçu pour endommager ou infiltrer un système informatique ?",
				"Un algorithme", "Un antivirus", "Un virus", "Un navigateur", "C",
				"Les virus, chevaux de Troie et ransomwares font partie des logiciels malveillants (malwares)."));

		// Question 18
		liste.add(creerEnigme(
				"Quelle est la plus petite unité d'information en informatique, représentée par un 0 ou un 1 ?",
				"Un Octet (Byte)", "Un Gigaoctet (GB)", "Un Pixel", "Un Bit", "D",
				"Le bit (Binary digit) est l'unité de base."));
		return liste;
	}

	/**
	 * Gère l'interaction avec les objets de la zone (Coffres, Ordinateurs).
	 */
	private void interagir() {
		verifieGUI();
		java.util.List<interactions.Interactable> objets = zoneCourante.getInteractables();

		if (objets.isEmpty()) {
			gui.afficher("Il n'y a rien avec quoi interagir ici.");
			return;
		}
		Interactable objet = objets.get(0);
		if (objet instanceof interactions.Ordinateur ordi) {
			if (!ordi.estOuvert() && ordi.getEnigme() != null && !ordi.getEnigme().estResolue()) {
				String reponse = javax.swing.JOptionPane.showInputDialog(null,
						ordi.getEnigme().examiner() + "\nVotre mot de passe (A, B, C ou D) :",
						"Piratage de l'ordinateur", javax.swing.JOptionPane.QUESTION_MESSAGE);

				if (reponse != null && !reponse.trim().isEmpty()) {
					String resultat = ordi.repondreAEnigme(reponse, joueur.getInventaire());
					gui.afficher(resultat);
					actualiserImagePiece();
				} else {
					gui.afficher("Vous quittez le terminal.");
				}
			} else {
				gui.afficher(ordi.interagir(joueur.getInventaire()));
			}
		} else {
			String resultat = objet.interagir(joueur.getInventaire());
			gui.afficher(resultat);
			actualiserImagePiece();
		}
	}

	/**
	 * Affiche le contenu de l'inventaire et le temps restant.
	 */
	private void afficherInventaire() {
		verifieGUI();
		gui.afficher(joueur.getInventaire().toString());
		gui.afficher("Temps restant : " + chronometre.getTempsRestantFormate());
		gui.afficher();
	}

	/**
	 * Vérifie les conditions de victoire ou de défaite après chaque action.
	 */
	private boolean actualiserStatut() {
		if (chronometre.estTempsEcoule()) {
			gui.afficher("Le temps est écoulé... Le virus s'est propagé. GAME OVER.");
			terminer();
			return false;
		}

		EtatJeu etat = arbitre.verifierEtat(joueur.getInventaire(), gestionRencontres.getNbRencontres());

		if (etat == EtatJeu.VICTOIRE) {
			gui.afficher("=======================================");
			gui.afficher("FÉLICITATIONS ! Vous avez synthétisé l'antidote !");
			gui.afficher("Le monde est sauvé.");
			gui.afficher("=======================================");
			terminer();
			return false;
		} else if (etat == EtatJeu.DEFAITE) {
			gui.afficher("=======================================");
			gui.afficher("GAME OVER ! Le monstre vous a tué 3 fois...");
			gui.afficher("=======================================");
			terminer();
			return false;
		}

		return true; // Игра продолжается
	}

	/**
	 * Определяет, какую картинку комнаты показать (закрытую или открытую)
	 */
	private void actualiserImagePiece() {
		String imageAafficher = zoneCourante.nomImage();
		if (!zoneCourante.getInteractables().isEmpty()) {
			interactions.Interactable obj = zoneCourante.getInteractables().get(0);
			if (obj instanceof interactions.Coffre coffre && coffre.estOuvert()) {
				imageAafficher = imageAafficher.replace(".png", "_open.png");
			}
		}
		gui.afficheImage(imageAafficher);
	}
}
