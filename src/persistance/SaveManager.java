package persistance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Gère l'arborescence des dossiers par Joueur et la sauvegarde de la partie.
 */
public class SaveManager implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String DOSSIER_RACINE = "sauvegardes";
	private static final String FICHIER_PARTIE = "partie.ser";

	/**
	 * Crée le dossier du joueur s'il n'existe pas.
	 */
	public void creerCompte(String nomJoueur) {
		File dossier = getDossierJoueur(nomJoueur);
		if (!dossier.exists()) {
			dossier.mkdirs();
		}
	}

	/**
	 * Retourne la liste de tous les joueurs existants.
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
	 * Sauvegarde un tableau d'objets représentant l'état du jeu. * @return true si
	 * la sauvegarde a réussi
	 */
	public boolean sauvegarderPartie(String nomJoueur, Object[] etatDuJeu) {
		creerCompte(nomJoueur); // S'assure que le dossier existe
		File fichier = new File(getDossierJoueur(nomJoueur), FICHIER_PARTIE);
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichier))) {
			oos.writeObject(etatDuJeu);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Charge et retourne l'état du jeu. * @return Un tableau d'objets ou null si
	 * aucune sauvegarde.
	 */
	public Object[] chargerPartie(String nomJoueur) {
		File fichier = new File(getDossierJoueur(nomJoueur), FICHIER_PARTIE);
		if (!fichier.exists()) {
			return null;
		}
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier))) {
			return (Object[]) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Supprime la sauvegarde du joueur (ex: après une fin de partie).
	 */
	public void supprimerSauvegarde(String nomJoueur) {
		File fichier = new File(getDossierJoueur(nomJoueur), FICHIER_PARTIE);
		if (fichier.exists()) {
			fichier.delete();
		}
	}

	private File getDossierJoueur(String nomJoueur) {
		return new File(DOSSIER_RACINE + File.separator + nomJoueur);
	}
}