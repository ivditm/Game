package play;

import java.io.Serializable;

import jeu.Zone;

/**
 * Représente l'avatar du joueur pendant une session de jeu.
 * Contient la zone courante et l'inventaire du personnage.
 */
public class Personnage implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Zone où se trouve actuellement le personnage. */
    private Zone zoneCourante;

    /** Inventaire contenant les objets collectés. */
    private Inventaire inventaire;

    /** Crée un personnage placé dans la zone de départ avec un inventaire vide. */
    public Personnage(Zone zoneCourante) {
        this.zoneCourante = zoneCourante;
        this.inventaire = new Inventaire();
    }

    /** Retourne la zone courante du personnage. */
    public Zone getZoneCourante() {
        return zoneCourante;
    }

    /** Déplace le personnage vers la zone indiquée. */
    public void setZoneCourante(Zone zone) {
        this.zoneCourante = zone;
    }

    /** Retourne l'inventaire du personnage. */
    public Inventaire getInventaire() {
        return inventaire;
    }

    /** Retourne la zone courante et le contenu de l'inventaire. */
    @Override
    public String toString() {
        String nomZone = (zoneCourante != null) ? zoneCourante.getNom() : "inconnue";
        return "Personnage dans : " + nomZone + "\n" + inventaire.toString();
    }
}
