package persistance;

import java.io.Serializable;

/** État minimal d'une partie sauvegardée, écrit sur disque. */
public class EtatPartie implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Nom de la zone courante au moment de la sauvegarde. */
    public final String nomZone;

    /** Prochain rang d'ingrédient attendu (1 = aucun collecté, 6 = tous collectés). */
    public final int prochainRangAttendu;

    /** Vrai si la recette a été obtenue via l'ordinateur. */
    public final boolean recetteObtenue;

    /** Nombre de rencontres avec le monstre. */
    public final int nbRencontres;

    /** Temps restant en millisecondes au moment de la sauvegarde. */
    public final long tempsRestantMs;

    /** Crée un snapshot de l'état courant de la partie. */
    public EtatPartie(String nomZone, int prochainRangAttendu, boolean recetteObtenue,
                      int nbRencontres, long tempsRestantMs) {
        this.nomZone = nomZone;
        this.prochainRangAttendu = prochainRangAttendu;
        this.recetteObtenue = recetteObtenue;
        this.nbRencontres = nbRencontres;
        this.tempsRestantMs = tempsRestantMs;
    }
}
