package persistance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe technique gérant l'arborescence des dossiers par Joueur et la
 * sérialisation/désérialisation du Personnage (.ser).
 */
public class SaveManager implements Serializable {

	private static final long serialVersionUID = 1L;

	// Dossier racine où sont stockés tous les profils joueurs
	private static final String DOSSIER_RACINE = "sauvegardes";
	private static final String FICHIER_PARTIE = "partie.ser";
	private static final String FICHIER_STATS = "stats.txt";

	// -------------------------------------------------------
	// Gestion des comptes joueurs
	// -------------------------------------------------------

	/**
	 * Crée le dossier du joueur s'il n'existe pas encore. Initialise aussi le
	 * fichier de stats à 0.
	 * 
	 * @param nomJoueur le nom du joueur
	 */
	public void creerCompte(String nomJoueur) {
		File dossier = getDossierJoueur(nomJoueur);
		if (!dossier.exists()) {
			dossier.mkdirs();
			initialiserStats(nomJoueur);
			System.out.println("Compte créé pour : " + nomJoueur);
		} else {
			System.out.println("Ce compte existe déjà.");
		}
	}

	/**
	 * Retourne la liste de tous les joueurs existants.
	 * 
	 * @return liste des noms de joueurs
	 */
	public List<String> listerJoueurs() {
		List<String> joueurs = new ArrayList<>();
		File racine = new File(DOSSIER_RACINE);
		if (racine.exists() && racine.isDirectory()) {
			for (File f : racine.listFiles()) {
				if (f.isDirectory()) {
					joueurs.add(f.getName());
				}
			}
		}
		return joueurs;
	}

	/**
	 * Vérifie si un joueur existe déjà.
	 * 
	 * @param nomJoueur le nom du joueur
	 * @return true si le dossier existe
	 */
	public boolean joueurExiste(String nomJoueur) {
		return getDossierJoueur(nomJoueur).exists();
	}

	// -------------------------------------------------------
	// Gestion des statistiques
	// -------------------------------------------------------

	/**
	 * Initialise le fichier stats du joueur (0 victoires, 0 défaites).
	 * 
	 * @param nomJoueur le nom du joueur
	 */
	private void initialiserStats(String nomJoueur) {
		try {
			File stats = new File(getDossierJoueur(nomJoueur), FICHIER_STATS);
			PrintWriter pw = new PrintWriter(new FileWriter(stats));
			pw.println("victories=0");
			pw.println("defaites=0");
			pw.close();
		} catch (IOException e) {
			System.err.println("Erreur initialisation stats : " + e.getMessage());
		}
	}

	/**
	 * Incrémente le compteur de victoires du joueur.
	 * 
	 * @param nomJoueur le nom du joueur
	 */
	public void ajouterVictoire(String nomJoueur) {
		modifierStat(nomJoueur, "victories");
	}

	/**
	 * Incrémente le compteur de défaites du joueur.
	 * 
	 * @param nomJoueur le nom du joueur
	 */
	public void ajouterDefaite(String nomJoueur) {
		modifierStat(nomJoueur, "defaites");
	}

	/**
	 * Lit et affiche les stats du joueur.
	 * 
	 * @param nomJoueur le nom du joueur
	 */
	public void afficherStats(String nomJoueur) {
		try {
			File stats = new File(getDossierJoueur(nomJoueur), FICHIER_STATS);
			BufferedReader br = new BufferedReader(new FileReader(stats));
			String ligne;
			System.out.println("=== Stats de " + nomJoueur + " ===");
			while ((ligne = br.readLine()) != null) {
				System.out.println(ligne);
			}
			br.close();
		} catch (IOException e) {
			System.err.println("Erreur lecture stats : " + e.getMessage());
		}
	}

	/**
	 * Modifie une stat spécifique (victories ou defaites).
	 * 
	 * @param nomJoueur le nom du joueur
	 * @param cle       la clé à incrémenter
	 */
	private void modifierStat(String nomJoueur, String cle) {
		try {
			File stats = new File(getDossierJoueur(nomJoueur), FICHIER_STATS);
			List<String> lignes = Files.readAllLines(stats.toPath());
			List<String> nouvLignes = new ArrayList<>();
			for (String l : lignes) {
				if (l.startsWith(cle + "=")) {
					int val = Integer.parseInt(l.split("=")[1]);
					nouvLignes.add(cle + "=" + (val + 1));
				} else {
					nouvLignes.add(l);
				}
			}
			Files.write(stats.toPath(), nouvLignes);
		} catch (IOException e) {
			System.err.println("Erreur modification stat : " + e.getMessage());
		}
	}

	// -------------------------------------------------------
	// Sauvegarde et chargement de la partie
	// -------------------------------------------------------

	/**
	 * Sauvegarde l'objet Personnage dans le dossier du joueur (sérialisation).
	 * 
	 * @param nomJoueur  le nom du joueur
	 * @param personnage l'objet Personnage à sauvegarder
	 */
	public void sauvegarderPartie(String nomJoueur, Object personnage) {
		File fichier = new File(getDossierJoueur(nomJoueur), FICHIER_PARTIE);
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichier))) {
			oos.writeObject(personnage);
			System.out.println("Partie sauvegardée pour : " + nomJoueur);
		} catch (IOException e) {
			System.err.println("Erreur sauvegarde : " + e.getMessage());
		}
	}

	/**
	 * Charge et retourne l'objet Personnage depuis le fichier de sauvegarde.
	 * 
	 * @param nomJoueur le nom du joueur
	 * @return l'objet Personnage désérialisé, ou null si pas de sauvegarde
	 */
	public Object chargerPartie(String nomJoueur) {
		File fichier = new File(getDossierJoueur(nomJoueur), FICHIER_PARTIE);
		if (!fichier.exists()) {
			System.out.println("Aucune partie sauvegardée pour : " + nomJoueur);
			return null;
		}
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier))) {
			Object personnage = ois.readObject();
			System.out.println("Partie chargée pour : " + nomJoueur);
			return personnage;
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Erreur chargement : " + e.getMessage());
			return null;
		}
	}

	/**
	 * Vérifie si une partie sauvegardée existe pour ce joueur.
	 * 
	 * @param nomJoueur le nom du joueur
	 * @return true si un fichier .ser existe
	 */
	public boolean partieSauvegardeExiste(String nomJoueur) {
		return new File(getDossierJoueur(nomJoueur), FICHIER_PARTIE).exists();
	}

	/**
	 * Supprime la sauvegarde du joueur (ex: après une fin de partie).
	 * 
	 * @param nomJoueur le nom du joueur
	 */
	public void supprimerSauvegarde(String nomJoueur) {
		File fichier = new File(getDossierJoueur(nomJoueur), FICHIER_PARTIE);
		if (fichier.exists()) {
			fichier.delete();
			System.out.println("Sauvegarde supprimée pour : " + nomJoueur);
		}
	}

	// -------------------------------------------------------
	// Utilitaire
	// -------------------------------------------------------

	/**
	 * Retourne le dossier du joueur.
	 * 
	 * @param nomJoueur le nom du joueur
	 * @return le File correspondant
	 */
	private File getDossierJoueur(String nomJoueur) {
		return new File(DOSSIER_RACINE + File.separator + nomJoueur);
	}
}
