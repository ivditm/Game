package commande;

import java.io.Serializable;

/**
 * Énumération représentant les commandes directionnelles du joueur.
 */
public enum Commande implements Serializable {
    NORD,
    SUD,
    EST,
    OUEST;

    /**
     * Convertit une chaîne de caractères en Commande.
     * @param input la saisie du joueur
     * @return la Commande correspondante, ou null si non reconnue
     */
    public static Commande fromString(String input) {
        if (input == null) return null;
        switch (input.trim().toUpperCase()) {
            case "NORD":  case "N": return NORD;
            case "SUD":   case "S": return SUD;
            case "EST":   case "E": return EST;
            case "OUEST": case "O": return OUEST;
            default: return null;
        }
    }
}
