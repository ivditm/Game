package danger;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import jeu.Zone;

/**
 * Représente le monstre antagoniste du jeu.
 * Il se déplace aléatoirement entre les zones à chaque tour,
 * ce qui constitue la principale source de danger pour le joueur.
 */
public class Monstre implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Random random = new Random();

    /** Zone où se trouve actuellement le monstre. */
    private Zone zoneCourante;

    /** Crée un monstre placé dans la zone donnée. */
    public Monstre(Zone zoneCourante) {
        this.zoneCourante = zoneCourante;
    }

    /** Crée un monstre sans zone initiale. */
    public Monstre() {
        this.zoneCourante = null;
    }

    /** Retourne la zone courante du monstre. */
    public Zone getZoneCourante() {
        return zoneCourante;
    }

    /** Déplace le monstre vers la zone indiquée. */
    public void setZoneCourante(Zone zone) {
        this.zoneCourante = zone;
    }

    /**
     * Déplace le monstre vers une zone choisie aléatoirement dans la liste.
     * Ne fait rien si la liste est null ou vide.
     */
    public void seDeplacerAleatoirement(List<Zone> toutesLesZones) {
        if (toutesLesZones != null && !toutesLesZones.isEmpty()) {
            zoneCourante = toutesLesZones.get(random.nextInt(toutesLesZones.size()));
        }
    }

    /** Retourne le nom de la zone courante du monstre. */
    @Override
    public String toString() {
        String nomZone = (zoneCourante != null) ? zoneCourante.getNom() : "inconnue";
        return "Monstre dans : " + nomZone;
    }
}
