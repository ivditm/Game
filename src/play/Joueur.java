package play;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente le compte persistant d'un utilisateur.
 * Stocke le pseudo, les statistiques (victoires et défaites)
 * et les chemins vers les fichiers de sauvegarde.
 */
public class Joueur implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Pseudo unique du joueur. */
    private String pseudo;

    /** Nombre de parties gagnées. */
    private int nbVictoires;

    /** Nombre de parties perdues. */
    private int nbDefaites;

    /** Chemins vers les fichiers de sauvegarde (.ser) du joueur. */
    private List<String> sauvegardes;

    /** Crée un joueur avec le pseudo donné, statistiques à zéro et aucune sauvegarde. */
    public Joueur(String pseudo) {
        this.pseudo = pseudo;
        this.nbVictoires = 0;
        this.nbDefaites = 0;
        this.sauvegardes = new ArrayList<>();
    }

    /** Retourne le pseudo du joueur. */
    public String getPseudo() {
        return pseudo;
    }

    /** Retourne le nombre de victoires. */
    public int getNbVictoires() {
        return nbVictoires;
    }

    /** Retourne le nombre de défaites. */
    public int getNbDefaites() {
        return nbDefaites;
    }

    /** Retourne la liste des chemins de sauvegarde. */
    public List<String> getSauvegardes() {
        return sauvegardes;
    }

    /** Incrémente le compteur de victoires. */
    public void ajouterVictoire() {
        nbVictoires++;
    }

    /** Incrémente le compteur de défaites. */
    public void ajouterDefaite() {
        nbDefaites++;
    }

    /** Ajoute un chemin de sauvegarde au profil du joueur. */
    public void ajouterSauvegarde(String cheminFichier) {
        sauvegardes.add(cheminFichier);
    }

    /** Supprime un chemin de sauvegarde du profil. Ne fait rien s'il est absent. */
    public void supprimerSauvegarde(String cheminFichier) {
        sauvegardes.remove(cheminFichier);
    }

    /** Retourne le pseudo et les statistiques du joueur. */
    @Override
    public String toString() {
        return "Joueur : " + pseudo
                + " | Victoires : " + nbVictoires
                + " | Défaites : " + nbDefaites;
    }
}
