package danger;

import java.io.Serializable;
import java.util.List;

import jeu.Zone;
import play.Personnage;

/**
 * Gère les rencontres entre le personnage et le monstre.
 * Vérifie à chaque déplacement si les deux entités occupent la même zone
 * et comptabilise les rencontres. Quand le compteur atteint 3, la partie est perdue.
 */
public class GestionRencontres implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Le monstre suivi par ce gestionnaire. */
    private Monstre monstre;

    /** Nombre de rencontres depuis le début de la partie. */
    private int nbRencontres;

    /** Crée un gestionnaire pour le monstre donné, compteur initialisé à zéro. */
    public GestionRencontres(Monstre monstre) {
        this.monstre = monstre;
        this.nbRencontres = 0;
    }

    /**
     * Vérifie si le personnage et le monstre sont dans la même zone.
     * Incrémente le compteur et retourne vrai si c'est le cas.
     */
    public boolean verifierRencontre(Personnage personnage) {
        Zone zonePersonnage = personnage.getZoneCourante();
        Zone zoneMonstre = monstre.getZoneCourante();
        if (zonePersonnage != null && zonePersonnage.equals(zoneMonstre)) {
            nbRencontres++;
            return true;
        }
        return false;
    }

    /** Déplace le monstre vers une zone aléatoire parmi la liste fournie. */
    public void deplacerMonstre(List<Zone> toutesLesZones) {
        monstre.seDeplacerAleatoirement(toutesLesZones);
    }

    /** Retourne le nombre de rencontres depuis le début de la partie. */
    public int getNbRencontres() {
        return nbRencontres;
    }

    /** Retourne le monstre géré par ce gestionnaire. */
    public Monstre getMonstre() {
        return monstre;
    }

    /** Restaure le compteur de rencontres lors du chargement d'une partie. */
    public void setNbRencontres(int nbRencontres) {
        this.nbRencontres = nbRencontres;
    }
}
